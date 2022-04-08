#include <fstream>
#include <iostream>
#include <map>
#include <set>
#include <sstream>
#include <vector>

using std::cout;
using std::endl;
using std::map;
using std::set;
using std::string;
using std::vector;

// Split a long string into separate strings
vector<string> parse_line(string x) {
    vector<string> output;

    std::stringstream ss(x);
    string word;
    while (ss >> word) {
        if (word.find(',') != string::npos) {
            output.push_back(word.substr(0, word.size() - 1));
        } else if (word == "->") {
            continue;
        } else if (word.at(0) == '(') {
            output.push_back(word.substr(1, word.size() - 2));
        } else {
            output.push_back(word);
        }
    }
    return output;
}

string get_top_node(set<string> nodes, set<string> children) {
    for (string n : nodes) {
        bool found = false;
        for (string c : children) {
            if (n == c) {
                found = true;
                break;
            }
        }
        if (found == false) {
            return n;
        }
    }
}

int sum_weight(string node, map<string, int> values, map<string, vector<string> > children) {
    int sum = values.at(node);
    for (string c : children[node]) {
        sum += sum_weight(c, values, children);
    }
    return sum;
}

map<int, int> occurence_counts(vector<int> weights) {
    map<int, int> counter;
    for (int w : weights) {
        if (counter.count(w) == 0) {
            counter[w] = 1;
            continue;
        }
        counter[w]++;
    }
    return counter;
}

int balance(string node, int difference, map<string, int> values, map<string, vector<string> > children) {
    vector<int> total_weights;
    for (string c : children[node]) {
        total_weights.push_back(sum_weight(c, values, children));
    }

    map<int, int> counts = occurence_counts(total_weights);

    if (counts.size() == 1) {
        return values[node] + difference;
    }

    int big_w = INT32_MIN;
    int sml_w = INT32_MAX;
    for (int w : total_weights) {
        if (w > big_w) {
            big_w = w;
        }
        if (w < sml_w) {
            sml_w = w;
        }
    }

    int right_weight;
    int off_weight;
    if (counts[big_w] > counts[sml_w]) {
        right_weight = big_w;
        off_weight = sml_w;
    } else {
        right_weight = sml_w;
        off_weight = big_w;
    }

    string unbalanced;
    for (string c : children[node]) {
        if (sum_weight(c, values, children) == off_weight) {
            unbalanced = c;
            break;
        }
    }
    
    return balance(unbalanced, (right_weight - off_weight), values, children);
}

int main() {
    string path = "puzzle_input.txt";
    std::fstream file;

    // What I want to track:
    //  1. Each node and it's value (dict of string and int)
    //  2. Each parent node's children (dict of string and vector)
    //  3. List of all children (set of strings)
    //  4. List of all nodes (set of strings)

    map<string, int> values;
    map<string, vector<string> > children;
    set<string> all_children;
    set<string> all_nodes;

    file.open(path, std::ios::in);
    if (!file) {
        cout << "Can't access the file " << path << endl;
        return 1;
    } else {
        string line;
        while (getline(file, line))
        {
            vector<string> contents = parse_line(line);
            // Contents: {Node Value Children(if any)}

            // Add node and its weight to the map
            values[contents.at(0)] = std::stoi(contents.at(1));
            // Track node
            all_nodes.insert(contents.at(0));

            // Check if the current node is a parent node
            if (contents.size() > 2) {
                // Get all kids from the vector and add to children set
                vector<string> kids;
                for (int i = 2; i < contents.size(); i++) {
                    kids.push_back(contents.at(i));
                    all_children.insert(contents.at(i));
                }
                // Add node and its children to the map
                children[contents.at(0)] = kids;
            }
        }
        file.close();
    }

    string parent = get_top_node(all_nodes, all_children);
    cout << "Part 1: " << parent << endl;
    cout << "Part 2: " << balance(parent, 0, values, children) << endl;

    return 0;
}