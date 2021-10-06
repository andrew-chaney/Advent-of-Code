#include <iostream>
#include <string>
#include <fstream>
using namespace std;

int part1(string line)
{
    int sum = 0;
    for (int i = 0; i < line.length(); i++)
    {
        if (i + 1 < line.length())
        {
            if (line[i] == line[i + 1])
            {
                sum += line[i] - '0';
            }
        }
        else
        {
            if (line[i] == line[0])
            {
                sum += line[i] - '0';
            }
        }
    }
    return sum;
}

int part2(string line)
{
    int sum = 0;
    for (int i = 0; i < line.length(); i++)
    {
        int compIndex = i + (line.length() / 2);
        if (compIndex >= line.length())
        {
            compIndex = compIndex % line.length();
        }
        if (line[i] == line[compIndex])
        {
            sum += line[i] - '0';
        }
    }
    return sum;
}

int main() {
    string path = "puzzle_input.txt"; // Path to file
    fstream file; // File instance
    string input; // Writing file contents to this
    // Open the file and read its contents to our input variable.
    file.open(path, ios::in);
    if (!file) {
        cout << "Can't access file." << endl;
    } else {
        getline(file, input);
        file.close();
    }
    
    cout << "Part 1: " << part1(input) << endl;
    cout << "Part 2: " << part2(input) << endl;

    return 0;
}