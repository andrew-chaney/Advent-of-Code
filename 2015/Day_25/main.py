import re

def findNumPassword(row: int, col: int) -> int:
    return sum(range(row + col - 1)) + col

def generatePassword(current: int) -> int:
    return (current * 252533) % 33554393

def main():
    base = "/Users/andrewchaney/Documents/Coding Challenges/Advent-of-Code/2015/Day_25/"
    path = base + "puzzle_input.txt"

    row = 0
    col = 0

    with open(path, "r") as file:
        for line in file:
            nums = re.findall(r'\d+', line)
            row, col = int(nums[0]), int(nums[1])

    passwd = 20151125
    num = findNumPassword(row, col)

    for _ in range(1, num):
        passwd = generatePassword(passwd)

    print(passwd)

if __name__ == "__main__":
    main()
