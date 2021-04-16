import json

def readAndSum(input, p2: bool) -> int:
    # Go through all data types recursively to find integers, when ints are founr return them
    if type(input) == type(dict()):
        # For part 2, look for "red" in dict values, if present ignore the dict
        if p2 == True:
            if "red" in input.values():
                return 0
        return sum([readAndSum(i, p2) for i in input.values()])
    elif type(input) == type(list()):
        return sum([readAndSum(i, p2) for i in input])
    elif type(input) == type(int()):
        return input
    # If it isn't one of the above values there isn't a number, return 0
    else:
        return 0

def main():
    path = "puzzle_input.txt"

    print(readAndSum({"d": "red", "e": [1, 2, 3, 4], "f": 5}, True))

    TEST_FUNCTION()

    with open(path, "r") as file:
        data = json.loads(file.read())
        part1 = readAndSum(data, False)
        part2 = readAndSum(data, True)

    print(f"Part 1: {part1}")
    print(f"Part 2: {part2}")

def TEST_FUNCTION() -> bool:
    # Part 1
    assert readAndSum({"a": 2, "b": 4}, False) == 6
    assert readAndSum({"a": {"b": 4}, "c": -1}, False) == 3
    assert readAndSum([-1, {"a": 1}], False) == 0
    assert readAndSum({}, False) == 0
    assert readAndSum([1, 2, 3], False) == 6
    assert readAndSum([[[3]]], False) == 3
    assert readAndSum({"a": [-1, 1]}, False) == 0
    assert readAndSum([], False) == 0
    # Part 2
    assert readAndSum([1, 2, 3], True) == 6
    assert readAndSum([1, {"c": "red", "b": 2}, 3], True) == 4
    assert readAndSum({"d": "red", "e": [1, 2, 3, 4], "f": 5}, True) == 0
    assert readAndSum([1, "red", 5], True) == 6

if __name__ == "__main__":
    main()
