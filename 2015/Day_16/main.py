def pt1(input: list, desired: list) -> bool:
    for i in range(2, len(input), 2):
        if desired.__contains__(input[i].split(":")[0]):
            num = int(input[i + 1] if "," not in input[i + 1] else input[i + 1].split(",")[0])
            if desired[input[i].split(":")[0]] != num:
                return True
    return False

def pt2(input: list, desired: list) -> bool:
    for i in range(2, len(input), 2):
        if desired.__contains__(input[i].split(":")[0]):
            obj = input[i].split(":")[0]
            num = int(input[i + 1] if "," not in input[i + 1]
                      else input[i + 1].split(",")[0])
            if obj == "cats" or obj == "trees":
                if desired[input[i].split(":")[0]] > num:
                    return True
            elif obj == "pomeranians" or obj == "goldfish":
                if desired[input[i].split(":")[0]] < num:
                    return True
            else:
                if desired[input[i].split(":")[0]] != num:
                    return True
    return False

def main():
    path = "puzzle_input.txt"

    mfcsam = {
        "children": 3,
        "cats": 7,
        "samoyeds": 2,
        "pomeranians": 3,
        "akitas": 0,
        "vizslas": 0,
        "goldfish": 5,
        "trees": 3,
        "cars": 2,
        "perfumes": 1
    }

    aunt_part1 = 0
    aunt_part2 = 0

    with open(path, "r") as file:
        for line in file:
            input = line.split()
            # Part 1
            if pt1(input, mfcsam) == False:
                aunt_part1 = int(input[1].split(":")[0])
            # Part 2
            if pt2(input, mfcsam) == False:
                aunt_part2 = int(input[1].split(":")[0])
    
    print(f"Part 1: {aunt_part1}")
    print(f"Part 2: {aunt_part2}")

if __name__ == "__main__":
    main()
