from itertools import combinations
from math import prod, ceil

def findMinQE(packages: list[int], groups: int) -> int:
    for i in range(len(packages)):
        possible = []
        for j in combinations(packages, i):
            if sum(j) == sum(packages) / groups:
                possible.append(j)
        if possible != []:
            break
    
    return min([prod(i) for i in possible])

def main():
    base = "/Users/andrewchaney/Documents/Coding Challenges/Advent-of-Code/2015/Day_24/"
    path = base + "puzzle_input.txt"

    with open(path, "r") as file:
        packages = [int(line) for line in file]

    test = [1,2,3,4,5,7,8,9,10,11]

    # for n in range(len(packages)):
    #     possible = [i for i in combinations(packages, n) if sum(i) == sum(packages) / 3]
    #     if len(possible) > 0:
    #         break
    
    # print(min([prod(i) for i in possible]))

    part1 = findMinQE(packages, 3)
    part2 = findMinQE(packages, 4)
    print(f"Part 1: {part1}")
    print(f"Part 2: {part2}")

                    

if __name__ == "__main__":
    main()
