#include <fstream>
#include <iomanip>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

std::vector<int> parse_lengths(std::string input) {
    std::vector<int> output;
    std::stringstream ss(input);
    std::string length;
    while (getline(ss, length, ',')) {
        output.push_back(std::stoi(length));
    }
    return output;
}

void knot_hash(int list_size, int list[], std::vector<int> lengths, int reps) {
    int index = 0, skip = 0;
    for (int i = 0; i < reps; i++) {
        for (int l : lengths) {
            std::vector<int> sub;
            for (int j = 0; j < l; j++) {
                sub.push_back(list[(j + index) % list_size]);
            }
            for (int j = 0; j < l; j++) {
                list[(index + l - 1 - j) % list_size] = sub[j];
            }
            index += l + skip++;
        }
    }
}

std::vector<int> get_ascii(std::string input) {
    std::vector<int> output;
    for (char i : input) {
        output.push_back(int(i));
    }
    output.push_back(17);
    output.push_back(31);
    output.push_back(73);
    output.push_back(47);
    output.push_back(23);
    return output;
}

std::string knot_to_hex(int list_size, int list[]) {
    // Dense hash the knot
    int dense[16];
    for (int i = 0; i < 16; i++) {
        dense[i] = list[i * 16];
        for (int j = 1; j < 16; j++) {
            dense[i] ^= list[(i * 16) + j];
        }
    }
    // Convert the dense hash to hex
    std::stringstream output;
    for (int i = 0; i < 16; i++) {
        output << std::setfill('0') << std::setw(2) << std::hex << dense[i];
    }
    return output.str();
}

int main() {
    std::string path = "puzzle_input.txt";
    int list_size = 256;
    int part1[list_size];
    int part2[list_size];
    for (int i = 0; i < list_size; i++) {
        part1[i] = i;
        part2[i] = i;
    }

    std::fstream file;
    std::string line;
    file.open(path, std::ios::in);
    if (!file) {
        std::cout << "Can't access file " << path << std::endl;
    } else {
        getline(file, line);
        file.close();
    }

    std::vector<int> lengths = parse_lengths(line);
    std::vector<int> ascii_lengths = get_ascii(line);

    // Part 1
    knot_hash(list_size, part1, lengths, 1);
    std::cout << "Part 1: " << part1[0] * part1[1] << std::endl;
    // Part 2
    knot_hash(list_size, part2, ascii_lengths, 64);
    std::cout << "Part 2: " << knot_to_hex(list_size, part2) << std::endl;

    return 0;
}
