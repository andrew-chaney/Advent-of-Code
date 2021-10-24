#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

int parseJumps(vector<int> input, bool part2) {
    int steps = 0;
    int index = 0;
    while (true) {
        if (index >= input.size())
        {
            return steps;
        }
        int mod = 1;
        if (part2 == true && input[index] >= 3) {
            mod = -1;
        }
        input[index] += mod;
        index += input[index] - mod;
        steps++;
    }
}

int main() {
    string path = "puzzle_input.txt";
    fstream file;

    vector<int> input;

    file.open(path, ios::in);
    if (!file) {
        cout << "Can't access the file " << path << endl;
        return 1;
    } else {
        string line;
        while (getline(file, line)) {
            int num = stoi(line);
            input.push_back(num);
        }
    }

    cout << parseJumps(input, false) << endl;
    cout << parseJumps(input, true) << endl;

    return 0;
}