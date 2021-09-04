def main():
    path = "puzzle_input.txt"
    input = open(path, "r").read()

    print(f"Part 1: {part1(input)}")
    print(f"Part 2: {part2(input)}")

def part1(line) -> int:
    sum = 0
    for i in range(len(line)):
        if i + 1 < len(line):
            if line[i] == line[i + 1]:
                sum += int(line[i])
        else:
            if line[i] == line[0]:
                sum += int(line[i])
    return sum

def part2(line) -> int:
    sum = 0
    for i in range(len(line)):
        compIndex = int(i + len(line)/2)
        if compIndex >= len(line):
            compIndex = compIndex % len(line)
        if line[i] == line[compIndex]:
            sum += int(line[i])
    return sum

if __name__ == "__main__":
    main()
