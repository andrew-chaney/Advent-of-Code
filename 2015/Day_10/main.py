from itertools import groupby

def solve(input: str) -> str:
    output = ""
    for i,j in groupby(input):
        output += str(len(list(j))) + str(i)
    return output

def main():
    input = "1113122113"

    # Part 1
    p1 = 40
    for i in range(p1):
        if i == 0:
            next = solve(input)
        else:
            next = solve(next)
    print(f"Part 1 Answer: {len(next)}")

    # Part 2
    p2 = 50
    for i in range(p2):
        if i == 0:
            next = solve(input)
        else:
            next = solve(next)
    print(f"Part 2 Answer: {len(next)}")

if __name__ == "__main__":
    main()
