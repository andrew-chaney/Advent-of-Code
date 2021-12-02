const fs = require("fs");

function part1(input) {
    let depth = 0;
    let horizontal = 0;
    for (let i = 0; i < input.length; i++) {
        switch(input[i].direc) {
            case "forward":
                horizontal += input[i].amt;
                break;
            case "down":
                depth += input[i].amt;
                break;
            case "up":
                depth -= input[i].amt;
                break;
        }
    }
    return depth * horizontal;
}

function part2(input) {
    let depth = 0;
    let horizontal = 0;
    let aim = 0;
    for (let i = 0; i < input.length; i++) {
        switch (input[i].direc) {
            case "forward":
                horizontal += input[i].amt;
                depth += aim * input[i].amt;
                break;
            case "down":
                aim += input[i].amt;
                break;
            case "up":
                aim -= input[i].amt;
                break;
        }
    }
    return depth * horizontal;
}

function main() {
    let path = "puzzle_input.txt"
    let input;
    try {
        input = fs.readFileSync(path, "utf-8").split("\n").map(
            (x) => {
            return { direc: x.split(' ')[0], amt: Number(x.split(' ')[1])}
        });
    } catch {
        console.log(`ERROR: Couldn't open file ${path}`);
        process.exit(0);
    }

    let p1 = part1(input);
    let p2 = part2(input);

    console.log(`Part 1: ${p1}`);
    console.log(`Part 2: ${p2}`);
}

main();