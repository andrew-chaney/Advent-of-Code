from itertools import combinations

def separate(containers: list[int], amt: int, minimum_containers=None) -> int:
    combination_count = 0
    if minimum_containers == None:
        for i in range(1, len(containers)):
            possibilities = combinations(containers, i)
            for x in possibilities:
                if sum(list(x)) == amt:
                    combination_count += 1
    else:
        possibilities = combinations(containers, minimum_containers)
        for x in possibilities:
            if sum(list(x)) == amt:
                combination_count += 1
    return combination_count

def findMinNeeded(containers: list[int], amt: int) -> int:
    min_containers = len(containers)
    for i in range(1, len(containers)):
        possibilities = combinations(containers, i)
        for x in possibilities:
            if sum(list(x)) == amt:
                if len(list(x)) < min_containers:
                    min_containers = len(list(x))
    return min_containers

def main():
    path = "puzzle_input.txt"

    amt = 150
    containers = []

    with open(path, "r") as file:
        for line in file:
            containers.append(int(line))
    
    part1 = separate(containers, amt)
    print(f"Part 1: {part1}")

    part2 = separate(containers, amt, findMinNeeded(containers, amt))
    print(f"Part 2: {part2}")
    

if __name__ == "__main__":
    main()
