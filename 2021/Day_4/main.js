const fs = require("fs");
const { threadId } = require("worker_threads");

class Board {
    constructor() {
        this.board = [];
    }

    add(row) {
        this.board.push(row);
    }

    mark(num) {
        for (let i = 0; i < this.board.length; i++) {
            for (let j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] == num) {
                    this.board[i][j] = 'x';
                }
            }
        }
    }

    bingo() {
        let winningRow = this.board.filter((r) => r.filter((x) => x == 'x').   length == r.length).length > 0 ? true : false;
        let winningCol = false;
        for (let i = 0; i < this.board[0].length; i++) {
            if (this.board.filter((x) => x[i] == 'x').length == 5) {
                winningCol = true;
                break;
            }
        }
        if (winningRow || winningCol) {
            return true;
        }
        return false;
    }

    sum() {
        return this.board.reduce((x,i) =>
            x + i.reduce((y,j) => y + (j != 'x' ? j : 0), 0), 0);
    }

    printBoard() {
        for (const row of this.board) {
            console.log(row);
        }
        console.log();
    }
}

function getBoards(input) {
    let boards = [];
    let board = new Board();
    for (let i = 0; i < input.length; i++) {
        if (input[i] == '') {
            boards.push(board);
            board = new Board();
            continue;
        }
        board.add(input[i].split(' ').filter((x) => x != '').map((j) => parseInt(j)));
    }
    boards.push(board);

    return boards;
}

function playBingo(nums, boards) {
    let results = [];
    for (const board of boards) {
        for (let i = 0; i < nums.length; i++) {
            board.mark(nums[i]);
            if (board.bingo()) {
                results.push([i, board, nums[i]]);
                break;
            }
        }
    }
    results.sort((x,y) => x[0] - y[0]);
    let part1 = results[0][1].sum() * results[0][2];
    let part2 = results[results.length-1][1].sum() * results[results.length-1][2];
    
    console.log(`Part 1: ${part1}`);
    console.log(`Part 2: ${part2}`);
}

function main() {
    let path = "puzzle_input.txt";
    let input;
    try {
        input = fs.readFileSync(path, "utf-8").split("\n");
    } catch {
        console.log(`ERROR: Unable to read file ${path}`);
        process.exit(0);
    }

    let bingo_nums = input[0].split(',').map((x) => parseInt(x));
    let boards = getBoards(input.splice(2));

    playBingo(bingo_nums, boards)
}

main();