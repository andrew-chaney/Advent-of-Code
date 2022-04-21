#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

std::vector<std::string> parse_directions(std::string input) {
    std::vector<std::string> output;
    std::stringstream ss(input);
    std::string direction;
    while (getline(ss, direction, ',')) {
        output.push_back(direction);
    }
    return output;
}

int steps_away(int x, int y) {
    int steps = 0;
    while (x != 0 && y != 0) {
        if (y < 0) {
            y++;
        } else {
            y--;
        }
        if (x < 0) {
            x++;
        } else {
            x--;
        }
        steps++;
    }
    while (y != 0) {
        if (y < 0) {
            y++;
        } else {
            y--;
        }
        steps++;
    }
    while (x != 0) {
        if (x < 0) {
            x++;
        } else {
            x--;
        }
        steps++;
    }
    return steps;
}

int main() {
    std::string path = "puzzle_input.txt";
    std::string input;

    std::fstream file;
    file.open(path, std::ios::in);
    if (!file) {
        std::cout << "Cannot open file: " << path << std::endl;
    } else {
        getline(file, input);
        file.close();
    }

    std::vector<std::string> directions = parse_directions(input);

    int x = 0, y = 0, max_away = 0;
    for (std::string i : directions) {
        if (i == "n") {
            y++;
        } else if (i == "ne") {
            y++;
            x++;
        } else if (i == "se") {
            y--;
            x++;
        } else if (i == "s") {
            y--;
        } else if (i == "sw") {
            y--;
            x--;
        } else if (i == "nw") {
            y++;
            x--;
        }
        int current_away = steps_away(x, y);
        if (current_away > max_away) {
            max_away = current_away;
        }
    }

    std::cout << "Part 1: " << steps_away(x, y) << std::endl;
    std::cout << "Part 2: " << max_away << std::endl;

    return 0;
}
