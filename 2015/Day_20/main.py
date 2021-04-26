def findHouse(input: int, part2=False) -> int:
    houses = [0 for _ in range(1_000_000)]
    for elf in range(1, len(houses)):
        if part2 == True:
            count = 0
        for house in range(elf, len(houses), elf):
            if part2 == True:
                houses[house] += elf * 11
                count += 1
                if count == 50:
                    break
            else:
                houses[house] += elf * 10
    for index, presents in enumerate(houses):
        if presents >= input:
            return index
    return 0

def main():
    input = 36_000_000

    print(f"Part 1: House # {findHouse(input)}")
    print(f"Part 2: House # {findHouse(input, True)}")

if __name__ == "__main__":
    main()