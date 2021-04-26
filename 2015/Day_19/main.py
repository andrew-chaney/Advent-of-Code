import re

def part1(poss: list[list[str]], start: str) -> int:
    options = set()
    for element, replace in poss:
        for index in range(len(start)):
            if start[index:index+len(element)] == element:
                new_element = start[:index] + replace + start[index+len(element):]
                options.add(new_element)
    return len(options)

# Do this part in reverse to avoid backtracking
# I had to get help with this part
def part2(poss: dict, start: str, goal: str) -> int:

    def getReplacement(i):
        return poss[i.group()]
    
    count = 0
    while start != goal:
        start = re.sub('|'.join(poss.keys()), getReplacement, start, 1)
        count += 1
    
    return count

def main():
    path = "puzzle_input.txt"

    poss = []
    altered_poss = {} # can be in a dict since the keys and values will be swapped and reversed
    
    with open(path, "r") as file:
        for line in file:
            if "=>" in line:
                input = line.split(" => ")
                key = input[0].strip()
                val = input[1].strip()
                poss.append([key,val])
                altered_poss[val[::-1]] = key[::-1]
            else:
                start = line    

    print(f"Part 1: {part1(poss, start)}")

    goal = "e" 
    print(f"Part 2: {part2(altered_poss, start[::-1], goal)}")

if __name__ == "__main__":
    main()