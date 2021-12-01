const fs = require("fs");

function main() {
    let filename = "puzzle_input.txt";
    let input = [];
    try {
        input = fs.readFileSync(filename, "utf-8").split("\n").map(Number);
    } catch {
        console.log(`ERROR: Couldn't read file ${filename}`);
        process.exit(0);
    }
    
    let part1 = input.filter(
        (currentElement, currentIndex, array) => 
        currentElement < array[currentIndex + 1]
        ).length;
    
    let part2 = input.filter(
        (currentElement, currentIndex, array) =>
        (currentElement + array[currentIndex + 1] + array[currentIndex + 2]) <
        (array[currentIndex + 1] + array[currentIndex + 2] + array[currentIndex + 3])
        ).length;
    
    console.log(`Part 1: ${part1}`);
    console.log(`Part 2: ${part2}`);
}

main();