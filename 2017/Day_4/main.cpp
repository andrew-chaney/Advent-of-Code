#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <iterator>
#include <vector>

using namespace std;

vector<string> split(string input) {
    string buffer;
    stringstream stream(input);
    vector<string> output;
    while (getline(stream, buffer, ' ')) {
        output.push_back(buffer);
    }
    return output;
}

int validPassphrase_pt1(vector<string> input) {
    for (int i = 0; i < input.size(); i++) {
        for (int j = 0; j < input.size(); j++) {
            if (i != j && (input[i].compare(input[j]) == 0)) {
                return 0;
            }
        }
    }
    return 1;
}

int compStringContents(string s1, string s2) {
    int s1Breakdown [26] = {};
    int s2Breakdown [26] = {};

    // for (int i = 0; i < 26; i++) {
    //     s1Breakdown[i] = 0;
    //     s2Breakdown[i] = 0;
    // }

    // They can share the same loop since we already made sure
    // that they are the same size
    for (int i = 0; i < s1.length(); i++) {
        s1Breakdown[int(s1.at(i)) - 97] += 1;
        s2Breakdown[int(s2.at(i)) - 97] += 1;
    }

    for (int i = 0; i < 26; i++) {
        if (s1Breakdown[i] != s2Breakdown[i]) {
            return 0; // strings don't have the same letters
        }
    }
    return 1; // strings have the same letters
}

int validPassphrase_pt2(vector<string> input) {
    for (int i = 0; i < input.size(); i++) {
        for (int j = 0; j < input.size(); j++) {
            if (i != j) {
                if (input[i].length() == input[j].length()) {
                    // If the strings share the same letters then the
                    // passphrase is invalid.
                    if (compStringContents(input[i], input[j]) == 1) {
                        return 0;
                    }   
                }
            }
        }
    }
    return 1;
}

int main() {
    string path = "puzzle_input.txt";
    fstream file;
    string testStrings[] = {
        "abcde fghij",          // valid
        "abcde xyz ecdab",      // invalid
        "a ab abc abd abf abj", // valid
    };

    int part1 = 0;
    int part2 = 0;

    file.open(path, ios::in);
    if (!file) {
        cout << "Can't access the file " << path << endl;
        return 1;
    } else {
        string line;
        while (getline(file, line)) {
            vector<string> pass = split(line);
            part1 += validPassphrase_pt1(pass);
            part2 += validPassphrase_pt2(pass);
        }
        file.close();
    }

    cout << "Part 1: " << part1 << endl;
    cout << "Part 2: " << part2 << endl;
    
    return 0;
}