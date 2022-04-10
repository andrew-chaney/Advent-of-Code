#include <fstream>
#include <iostream>
#include <map>
#include <sstream>
#include <vector>

using std::cout;
using std::endl;
using std::map;
using std::string;
using std::vector;

vector<string> split(string x) {
    vector<string> output;

    std::stringstream ss(x);
    string word;
    while (ss >> word) {
        output.push_back(word);
    }
    return output;
}

int main() {
    string path = "puzzle_input.txt";
    std::fstream file;

    // Track register values and max register value during the mapping
    map<string, int> registers;
    int max_for_proc = INT32_MIN;

    file.open(path, std::ios::in);
    if (!file) 
    {
        cout << "Can't access the file " << path << endl;
        return 1;
    } 
    else 
    {
        string line;
        while (getline(file, line)) 
        {
            // Pull all variables from instruction
            vector<string> contents = split(line);
            string reg_1 = contents.at(0);
            string op = contents.at(1);
            int op_amt = std::stoi(contents.at(2));
            string reg_2 = contents.at(4);
            string comp = contents.at(5);
            int comp_amt = std::stoi(contents.at(6));

            // Make sure each register exists already, if not assign value of 1
            if (registers.count(reg_1) == 0)
            {
                registers[reg_1] = 0;
            }
            if (registers.count(reg_2) == 0)
            {
                registers[reg_2] = 0;
            }

            // Check to see if if-statement of the instruction is true
            bool flag = false;
            if (comp == "==" && registers[reg_2] == comp_amt)
            {
                flag = true;
            }
            else if (comp == "!=" && registers[reg_2] != comp_amt)
            {
                flag = true;
            }
            else if (comp == ">=" && registers[reg_2] >= comp_amt)
            {
                flag = true;
            }
            else if (comp == "<=" && registers[reg_2] <= comp_amt)
            {
                flag = true;
            }
            else if (comp == ">" && registers[reg_2] > comp_amt)
            {
                flag = true;
            }
            else if (comp == "<" && registers[reg_2] < comp_amt)
            {
                flag = true;
            }

            // If the if-statement is met, apply the operation
            if (flag)
            {
                if (op == "inc")
                {
                    registers[reg_1] += op_amt;
                }
                else
                { // dec
                    registers[reg_1] -= op_amt;
                }
            }

            // For Part 2
            if (registers[reg_1] > max_for_proc)
            {
                max_for_proc = registers[reg_1];
            }
        }
        file.close();
    }

    // For Part 1
    int max_at_end = INT32_MIN;
    for (auto const& [k, v] : registers) 
    {
        if (v > max_at_end) 
        {
            max_at_end = v;
        }
    }

    cout << "Part 1: " << max_at_end << endl;
    cout << "Part 2: " << max_for_proc << endl;
}