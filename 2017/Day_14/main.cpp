#include <cstring>
#include <iomanip>
#include <iostream>
#include <queue>
#include <set>
#include <sstream>
#include <string>
#include <tuple>
#include <vector>

std::string knothash(std::string input) {
    // Assemble the ascii lengths from the input
    std::vector<int> lengths;
    for (char i : input) {
        lengths.push_back(int(i));
    }
    lengths.push_back(17);
    lengths.push_back(31);
    lengths.push_back(73);
    lengths.push_back(47);
    lengths.push_back(23);
    // Assemble the knot
    int knot[256];
    for (int i = 0; i < 256; i++) {
        knot[i] = i;
    }
    int index = 0;
    int skip = 0;
    for (int i = 0; i < 64; i++) {
        for (int l : lengths) {
            std::vector<int> sub;
            for (int j = 0; j < l; j++) {
                sub.push_back(knot[(j + index) % 256]);
            }
            for (int j = 0; j < l; j++) {
                knot[(index + l - 1 - j) % 256] = sub[j];
            }
            index += l + skip++;
        }
    }
    // Dense hash the knot
    int dense[16];
    for (int i = 0; i < 16; i++) {
        dense[i] = knot[i * 16];
        for (int j = 1; j < 16; j++) {
            dense[i] ^= knot[(i * 16) + j];
        }
    }
    // Convert the dense hash to hex
    std::stringstream output;
    for (int i = 0; i < 16; i++) {
        output << std::setfill('0') << std::setw(2) << std::hex << dense[i];
    }
    return output.str();
}

std::string hex_to_bin(std::string input) {
    std::stringstream bin;
    for (char i : input) {
        switch(i) {
            case '0':
                bin << "0000";
                break;
            case '1':
                bin << "0001";
                break;
            case '2':
                bin << "0010";
                break;
            case '3':
                bin << "0011";
                break;
            case '4':
                bin << "0100";
                break;
            case '5':
                bin << "0101";
                break;
            case '6':
                bin << "0110";
                break;
            case '7':
                bin << "0111";
                break;
            case '8':
                bin << "1000";
                break;
            case '9':
                bin << "1001";
                break;
            case 'a':
                bin << "1010";
                break;
            case 'b':
                bin << "1011";
                break;
            case 'c':
                bin << "1100";
                break;
            case 'd':
                bin << "1101";
                break;
            case 'e':
                bin << "1110";
                break;
            case 'f':
                bin << "1111";
                break;
            default:
                std::cout << "ERROR - Invalid hex value: " << i << std::endl;
                break;
        }
    }
    return bin.str();
}

std::vector<std::vector<int> > assemble_grid(std::string input) {
    // 0: free
    // 1: used
    std::vector<std::vector<int> > grid;
    for (int i = 0; i < 128; i++) {
        std::vector<int> row;
        std::string knot = knothash(input + "-" + std::to_string(i));
        for (char k : hex_to_bin(knot)) {
            if (k == '1') {
                row.push_back(1);
            } else {
                row.push_back(0);
            }
        }
        grid.push_back(row);
    }
    return grid;
}

void print_grid(std::vector<std::vector<int> > grid) {
    for (std::vector<int> row : grid) {
        for (int cell : row) {
            std::cout << cell;
        }
        std::cout << std::endl;
    }
}

int count_used(std::vector<std::vector<int> > grid) {
    int used = 0;
    for (std::vector<int> row : grid) {
        for (int cell : row) {
            if (cell == 1) {
                used++;
            }
        }
    }
    return used;
}

int group_grid(std::vector<std::vector<int> > grid) {
    int group = 0;
    std::set<std::tuple<int, int> > unseen;
    for (int i = 0; i < grid.size(); i++) {
        for (int j = 0; j < grid[i].size(); j++) {
            if (grid[i][j] == 1) {
                unseen.insert(std::make_tuple(i, j));
            }
        }
    }
    while (unseen.size() > 0) {
        std::queue<std::tuple<int, int> > line;
        line.push(*unseen.begin());
        while (line.size() > 0) {
            std::tuple<int, int> coord = line.front();
            line.pop();
            if (unseen.find(coord) != unseen.end()) {
                unseen.erase(coord);
                int row = std::get<0>(coord);
                int col = std::get<1>(coord);
                line.push(std::make_tuple(row - 1, col));
                line.push(std::make_tuple(row + 1, col));
                line.push(std::make_tuple(row, col - 1));
                line.push(std::make_tuple(row, col + 1));
            }
        }
        group++;
    }
    return group;
}

int main() {
    std::string puzzle_in = "stpzcrnm";

    std::vector<std::vector<int> > grid = assemble_grid(puzzle_in);
    std::cout << "Part 1: " << count_used(grid) << std::endl;

    std::cout << "Part 2: " << group_grid(grid) << std::endl;

    return 0;
}


