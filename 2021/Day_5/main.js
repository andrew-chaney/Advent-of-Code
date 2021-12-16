const fs = require("fs");

function buildGrid() {
    let grid = new Array(1000);
    for (let i = 0; i < grid.length; i++) {
        grid[i] = new Array(1000).fill(0);
    }
    return grid;
}

function solve(coordinates, includeDiags) {
    let intersections = 0;
    let grid = buildGrid();
    for (const [start, end] of coordinates) {
        let [x1,y1] = start;
        let [x2,y2] = end;

        if (!(includeDiags) && (x1 != x2 && y1 != y2)) {
            continue;
        }

        let [x,y] = [x1,y1];
        
        while (true) {
            if (++grid[x][y] == 2) { intersections++; }

            if (x == x2 && y == y2) { break; }

            if (x2 > x1) { x++; } else if (x2 < x1) { x--; }
            if (y2 > y1) { y++; } else if (y2 < y1) { y--; }
        }
    }
    return intersections;
}

function main() {
    let path = "puzzle_input.txt";
    let input;
    try {
        input = fs.readFileSync(path, "utf-8")
            .split("\n")
            .map(x => x.split(" -> ")
            .map(y => y.split(',')
            .map(z => parseInt(z,10))));
    } catch {
        console.log(`ERROR: unable to read file ${path}`);
        process.exit(0);
    }

    console.log(`Part 1: ${solve(input, false)}`);
    console.log(`Part 2: ${solve(input, true)}`);
}

main();