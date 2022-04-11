#include <fstream>
#include <iostream>
#include <string>

int main() {
    std::string path = "puzzle_input.txt";
    std::fstream file;

    std::string input;
    file.open(path, std::ios::in);
    if (!file) {
        std::cout << "Can't access the file " << path << std::endl;
    } else {
        getline(file, input);
        file.close();
    }

    int part_1 = 0;
    int part_2 = 0;
    int group_score = 0;
    int index = 0;
    bool garbage = false;

    while (index < input.length()) {
        if (input[index] == '!') {
            index++;
        } else if (garbage) {
            if (input[index] == '>') {
                garbage = false;
            } else {
                part_2++;
            }
        } else if (input[index] == '<') {
            garbage = true;
        } else if (input[index] == '{') {
            group_score++;
        } else if (input[index] == '}') {
            part_1 += group_score--;
        }
        index++;
    }

    std::cout << "Part 1: " << part_1 << std::endl;
    std::cout << "Part 2: " << part_2 << std::endl;

    return 0;
}