import json

def readAndSum(input: str) -> int:
    while True:
        if type(input) == int:
            return int(input)
        elif type(input) == list:
            return sum([readAndSum(x) for x in input])

def main():
    path = "/home/andrewchaney/Documents/Coding Challenges/Advent-of-Code/2015/Day_12/puzzle_input.txt"

    sum = 0

    with open(path, "r") as file:
        data = json.loads(file.read())
        sum += readAndSum(data)
    print(sum)

if __name__ == "__main__":
    main()