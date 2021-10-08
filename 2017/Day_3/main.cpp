#include <iostream>
#include <fstream>
#include <string>
#include <cmath>
#include <vector>

using namespace std;

int cellValue(vector< vector<int> > grid, int row, int col) {
    int sum = 0;
    if (row > 0) {
        // Top Left
        if (col > 0) {
            sum += grid[row - 1][col - 1];
        }
        // Above
        sum += grid[row - 1][col];
        // Top 
        if (col < grid.size()) {
            sum += grid[row - 1][col + 1];
        }
    }
    // Left
    if (col > 0) {
        sum += grid[row][col - 1];
    }
    // Right
    if (col < grid.size())
    {
        sum += grid[row][col + 1];
    }
    if (row < grid.size()) {
        // Bottom Left
        if (col > 0) {
            sum += grid[row + 1][col - 1];
        }
        // Lower
        sum += grid[row + 1][col];
        // Bottom Right
        if (col < grid.size()) {
            sum += grid[row + 1][col + 1];
        }
    }
    return sum;
}

// Generate spiral, if desired ouput is found return it.
int  part2(int num) {
    // Generate vector for spiral
    int size = 100;
    vector< vector<int> > grid(size, vector<int>(size, 0));
    // Set origin of spiral to 1
    int r = size / 2; // row
    int c = size / 2; // col
    grid[r][c] = 1;
    // Establish size of side lengths of the square
    int side = 1;
    // Generate spiral with increasing size until number is found
    while (true) {
        // Traverse right
        for (int i = 0; i < side; i++) {
            c++;
            int cell = cellValue(grid, r, c);
            if (cell > num) {
                return cell;
            }
            grid[r][c] = cell;
        }
        // Traverse up
        for (int i = 0; i < side; i++) {
            r--;
            int cell = cellValue(grid, r, c);
            if (cell > num) {
                return cell;
            }
            grid[r][c] = cell;
        }
        // Increase length
        side++;
        // Traverse left
        for (int i = 0; i < side; i++) {
            c--;
            int cell = cellValue(grid, r, c);
            if (cell > num) {
                return cell;
            }
            grid[r][c] = cell;
        }
        // Traverse down
        for (int i = 0; i < side; i++) {
            r++;
            int cell = cellValue(grid, r, c);
            if (cell > num) {
                return cell;
            }
            grid[r][c] = cell;
        }
        // Increase length
        side++;
        // Repeat
    }
}

int stepsFromSquareStart(int square, int num) {
    int cell = square * square;
    int steps = 0;
    int row = 0 - ((square - 1) / 2);
    int col = 0 + ((square - 1) / 2);

    if (cell == num) {
        return abs(row) + abs(col);
    }

    // Traverse to the right one cell
    cell++;
    col++;
    // Traverse up the right side
    if (cell < num)
    {
        for (int i = 0; i < square; i++)
        {
            cell++;
            row++;
            if (cell == num)
            {
                break;
            }
        }
    }
    // Traverse left top side
    if (cell < num) {
        for (int i = 0; i < square + 1; i++)
        {
            cell++;
            col--;
            if (cell == num)
            {
                break;
            }
        }
    }
    // Traverse down left side
    if (cell < num) {
        for (int i = 0; i < square + 1; i++) {
            cell++;
            row--;
            if (cell == num) {
                break;
            }
        }
    }
    // Traverse right bottom side
    if (cell < num) {
        for (int i = 0; i < square + 1; i++)
        {
            cell++;
            col++;
            if (cell == num)
            {
                break;
            }
        }
    }

    return abs(row) + abs(col);
}

int seqOfSquares(int num) {
    if (num == 1) {
        return 1;
    }
    int square = 1;
    while ((square * square) < num) {
        square += 2;
    }
    return square - 2;
}

int main() {
    int puzzle_input = 325489;
    int test_input = 1024;

    /* 
        For Part 1 (Bottom Right Corners):
            1 -> 9 -> 25 -> 49
            SEQUENCE OF SQUARES:
            1^2  3^2  5^2   7^2
    */

    int part1 = stepsFromSquareStart(seqOfSquares(puzzle_input), puzzle_input);

    cout << "Part 1: " << part1 << endl;
    cout << "Part 2: " << part2(puzzle_input) << endl;

    return 0;
}