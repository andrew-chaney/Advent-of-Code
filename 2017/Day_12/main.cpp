#include <algorithm>
#include <fstream>
#include <iostream>
#include <map>
#include <set>
#include <sstream>
#include <string>

std::set<int> get_connected(std::map<int, std::set<int> > progs, std::set<int> conn, int cur) {
    // Recursively find all subprograms
    if (conn.count(cur)) {
        return conn;
    }
    conn.insert(cur);
    int num = 0;
    for (int p : progs[cur]) {
        conn = get_connected(progs, conn, p);
    }
    return conn;
}

int count_groups(std::map<int, std::set<int> > progs) {
    /*
     * Get a set of all programs, iterate through each program finding all
     * subconnected programs and remove them from the main set. Do this until
     * the main set is empty, each iteration is one group.
    */
    int groups = 0;
    std::set<int> base_progs;
    for (auto const& p : progs) {
        base_progs.insert(p.first);
    }
    while (base_progs.size() != 0) {
        groups++;
        int current = *std::next(base_progs.begin(), 0);
        base_progs.erase(current);
        std::set<int> connections = get_connected(progs, std::set<int>(), current);
        std::set<int> result;
        std::set_difference(
            base_progs.begin(),
            base_progs.end(),
            connections.begin(),
            connections.end(),
            std::inserter(result, result.end())
        );
        base_progs = result;
    }
    return groups;
}

int main() {
    std::string path = "puzzle_input.txt";
    std::map<int, std::set<int> > programs;

    std::fstream file;
    file.open(path, std::ios::in);
    if (!file) {
        std::cout << "Can't open file: " << path << std::endl;
    } else {
        std::string line;
        while (getline(file, line)) {
            int prog = std::stoi(line.substr(0, line.find('<') - 1));

            std::set<int> sub_progs;
            std::stringstream ss(line.substr(line.find('>') + 2, line.length()));
            std::string x;
            while (getline(ss, x, ',')) {
                // Trim whitespace from left and right
                int front = x.find_first_not_of(' ');
                int end = x.find_last_not_of(' ');
                if (front != std::string::npos && end != std::string::npos) {
                    sub_progs.insert(std::stoi(x.substr(front, end + 1)));
                } else if (front == std::string::npos && end != std::string::npos) {
                    sub_progs.insert(std::stoi(x.substr(0, end + 1)));
                } else if (front != std::string::npos && end == std::string::npos) {
                    sub_progs.insert(std::stoi(x.substr(front, x.length())));
                } else {
                    sub_progs.insert(std::stoi(x));
                }
            }
            programs[prog] = sub_progs;
        }
        file.close();
    }

    std::cout << "Part 1: "
        << get_connected(programs, std::set<int>(), 0).size()
        << std::endl;
    std::cout << "Part 2: " << count_groups(programs) << std::endl;
    return 0;
}
