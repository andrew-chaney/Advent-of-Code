#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

vector<int> arrToVec(int input[16]) {
    vector<int> output;
    for (int i = 0; i < 16; i++) {
        output.push_back(input[i]);
    }
    return output;
}

void printVec(vector<int> vec) {
    for (auto i = vec.begin(); i != vec.end(); i++) {
        cout << *i << ' ';
    }
    cout << endl;
}

void printVec2(vector< vector<int> > vec) {
    for (int i = 0; i < vec.size(); i++) {
        printVec(vec[i]);
    }
}

int getMaxIndex(vector<int> input) {
    int max = 0;
    for (int i = 1; i < input.size(); i++) {
        if (input[i] > input[max]) {
            max = i;
        }
    }
    return max;
}

vector<int> redistribute(vector<int> input) {
    int targetBank = getMaxIndex(input);
    int amtToDistribute = input[targetBank];
    input[targetBank] = 0;
    int index = targetBank + 1;
    while (amtToDistribute > 0) {
        if (index >= input.size()) {
            index = 0;
        }
        input[index++]++;
        amtToDistribute--;
    }
    return input;
}

bool containsState(vector< vector<int> > states, vector<int> state) {
    for (int i = 0; i < states.size(); i++) {
        if (states[i] == state) {
            return true;
        }
    }
    return false;
}

vector< vector<int> > getRedistributions(vector<int> input) {
    vector< vector<int> > states;
    while (!containsState(states, input))
    {
        states.push_back(input);
        input = redistribute(input);
    }
    states.push_back(input);
    return states;
}

int getLoopSize(vector< vector<int> > states) {
    for (int i = 0; i < states.size(); i++) {
        if (i != states.size() - 1 && states[i] == states[states.size() - 1]) {
            return states.size() - 1 - i;
        }
    }
    return -1;
}

int main() {
    int puzzle_in[] = {4, 1, 15, 12, 0, 9, 9, 5, 5, 8, 7, 3, 14, 5, 12, 3};
    vector<int> input = arrToVec(puzzle_in);
    
    vector< vector<int> > states = getRedistributions(input);
    int part1 = states.size() - 1; // -1 because we add the last cycle into the vector at the very end in order to track loop size
    int part2 = getLoopSize(states);

    cout << "Part 1: " << part1 << endl;
    cout << "Part 2: " << part2 << endl;

    return 0;
}
