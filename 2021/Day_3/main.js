const fs = require("fs");

function part1(input) {
    let binarySize = input.length;
    let gamma = [];

    for (let i = 0; i < input[0].length; i++) {
        gamma.push(
            (input.reduce(
                (count, arr) => count + arr[i], 0)
            ) < binarySize / 2 ? 0 : 1
        );
    }

    let epsilon = gamma.map((x) => (x == 1) ? 0 : 1);
    
    return parseInt(gamma.join(''), 2) * parseInt(epsilon.join(''), 2);
}

function part2(input) {
    let o2arr = input;
    let co2arr = input;
    
    let i = 0;
    while (o2arr.length > 1) {
        let o2size = o2arr.length;
        let o2bit = o2arr.reduce(
            (c, a) => c + a[i], 0) < o2size / 2 ? 0 : 1;
        o2arr = o2arr.filter(x => x[i] == o2bit);
        i++;
    }

    i = 0;
    while (co2arr.length > 1) {
        let co2size = co2arr.length;
        let co2bit = co2arr.reduce(
            (c, a) => c + a[i], 0) < co2size / 2 ? 1 : 0;
        co2arr = co2arr.filter(x => x[i] == co2bit);
        i++;
    }

    return parseInt(o2arr[0].join(''), 2) * parseInt(co2arr[0].join(''), 2);
}

function main() {
    let path = "puzzle_input.txt";
    let input;
    try {
        input = fs.readFileSync(path, "utf-8").split("\n").map(
            (x) => x.split('').map(Number)
        );
    } catch {
        console.log(`ERROR: Couldn't read file ${path}`);
        process.exit(0);
    }

    console.log(`Part 1: ${part1(input)}`);
    console.log(`Part 2: ${part2(input)}`);
}

main();