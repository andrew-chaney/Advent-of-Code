#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <string>
#include <tuple>

std::tuple<int, int> parse_line(std::string line) {
    std::stringstream ss(line);
    std::string parsed;
    int idx = 0, pos, depth;
    std::string conts[2];
    while (getline(ss, parsed, ':')) {
        conts[idx++] = parsed;
    }
    pos = std::stoi(conts[0]);
    depth = std::stoi(conts[1].substr(
                conts[1].find_first_not_of(' '),
                conts[1].length()
    ));
    return std::make_tuple(pos, depth);
}

std::tuple<bool, int> resulting_severity(int delay, std::map<int, int> depths) {
    int severity = 0;
    // Caught bool beacuse if we get caught at 0 severity won't increase
    bool caught = false;
    for (const auto &d : depths) {
        if ((d.first + delay) % (2 * (d.second - 1)) == 0) {
            severity += (d.first * d.second);
            caught = true;
        }
    }
    return std::make_tuple(caught, severity);
}

int minimum_delay(std::map<int, int> depths) {
    int delay = 0;
    while (true) {
        if (!std::get<0>(resulting_severity(delay, depths))) {
            return delay;
        }
        delay++;
    }
}

int main() {
    std::string path = "puzzle_input.txt";
    std::map<int, int> depths;

    std::fstream file;
    file.open(path, std::ios::in);
    if (!file) {
        std::cout << "Can't open file " << path << std::endl;
    } else {
        std::string line;
        while (getline(file, line)) {
            std::tuple<int, int> parsed = parse_line(line);
            int index = std::get<0>(parsed);
            int depth = std::get<1>(parsed);
            depths[index] = depth;
        }
        file.close();
    }

    std::cout << "Part 1: " << std::get<1>(resulting_severity(0, depths)) << std::endl;
    // Brute force because it shouldn't take too long
    std::cout << "Part 2: " << minimum_delay(depths) << std::endl;

    return 0;
}
