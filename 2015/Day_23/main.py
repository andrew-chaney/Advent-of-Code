def hlf(r: float) -> float:
    return r / 2

def tpl(r: float) -> float:
    return r * 3

def inc(r: float) -> float:
    return r + 1

def jie(r: float, offset: int) -> int:
    return offset - 1 if r % 2 == 0 else 0

def jio(r: float, offset: int) -> int:
    return offset - 1 if r == 1 else 0

def findRegister(file_path: str, desired_register: str, part2=False) -> int:
    instructs = []
    if part2 != False:
        instructs.append("inc a".split())

    with open(file_path, "r") as file:
        for line in file:
            instructs.append(line.split())

    registers = {}
    i = 0
    while i < len(instructs):
        input = instructs[i]

        if len(input) > 2:
            action, register, offset = input[0], input[1].split(",")[0], int(input[2])
        else:
            if input[0] != "jmp":
                action, register, offset = input[0], input[1], 0
            else:
                offset = int(input[1])
                i += offset
                continue

        if not registers.__contains__(register):
            registers[register] = 0

        if action == "hlf":
            registers[register] = hlf(registers[register])
        elif action == "tpl":
            registers[register] = tpl(registers[register])
        elif action == "inc":
            registers[register] = inc(registers[register])
        elif action == "jie":
            i += jie(registers[register], offset)
        elif action == "jio":
            i += jio(registers[register], offset)
        i += 1
    return registers[desired_register]

def main():
    base = "/Users/andrewchaney/Documents/Coding Challenges/Advent-of-Code/2015/Day_23/"
    path = base + "puzzle_input.txt"
    
    part1 = findRegister(path, "b")
    print(f"Part 1: {part1}")
    part2 = findRegister(path, "b", True)
    print(f"Part 2: {part2}")

if __name__ == "__main__":
    main()
