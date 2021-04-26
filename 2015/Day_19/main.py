def part1(poss: list[list[str]], start: str) -> int:
    options = set() # set over list to avoid duplicates
    for element, replace in poss:
        for index in range(len(start)):
            if start[index:index+len(element)] == element:
                new_element = start[:index] + replace + start[index+len(element):]
                options.add(new_element)
    return len(options)

# In reverse so we can match from right to left
def part2(poss, start: str, goal: str) -> int:
    count = 0

    while start != "e":
        previous = start
        for k in poss.keys():
            if k in start:
                start = start.replace(k, poss[k], 1)
                count += 1
                break
        if previous == start: # if it is no longer making updates, break
            break

    # Pull out all elements that won't match
    start = start.replace("rA", " ")
    start = start.replace("nR", " ")
    start = start.replace("Y", " ")
    leftovers = start.split()
    # Count remaining elements if they belong to a key
    leftover_count = 0
    for i in leftovers:
        if i in poss.values():
            leftover_count += 1

    return count + leftover_count

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
                start = line.strip()

    print(f"Part 1: {part1(poss, start)}")

    goal = "e" 
    print(f"Part 2: {part2(altered_poss, start[::-1], goal)}")

if __name__ == "__main__":
    main()
