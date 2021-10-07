#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
using namespace std;

int getDiff(int nums[16]) {
    int max = nums[0];
    int min = nums[0];
    for (int i = 1; i < 16; i++) {
        if (nums[i] > max) {
            max = nums[i];
        }
        if (nums[i] < min) {
            min = nums[i];
        }
    }
    return max - min;
}

int getDiv(int nums[16]) {
    for (int i = 0; i < 16; i++) {
        for (int j = 0; j < 16; j++) {
            if (i != j) {
                if (nums[i] % nums[j] == 0) {
                    return nums[i] / nums[j];
                }
            }
        }
    }
    return 0;
}

int main() {
    string path = "puzzle_input.txt";
    fstream file;
    int input[16][16];
    int checksum_1 = 0; // part 1
    int checksum_2 = 0; // part 2

    file.open(path, ios::in);
    if (!file) {
        cout << "Can't access file." << endl;
        return 1;
    } else {
        string line;
        int lineCount = 0;
        while (getline(file, line)) {
            stringstream L(line);
            int numCount = 0;

            int x;
            while (L >> x) {
                input[lineCount][numCount++] = x;
            }
            lineCount++;

        }
        file.close();
    }

    for (int i = 0; i < 16; i++) {
        checksum_1 += getDiff(input[i]);
        checksum_2 += getDiv(input[i]);
    }
    
    cout << "Part 1: " << checksum_1 << endl;
    cout << "Part 2: " << checksum_2 << endl;

    return 0;
}