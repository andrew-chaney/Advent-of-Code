import re

def part1(file_path: str) -> int:
    total_char_count = 0
    total_mem_count = 0

    with open(file_path, "r") as file:
        for line in file:
            line = line.strip("\n")  # remove escape char for new line
            total_char_count += len(line)

            line = line.strip(line[0])  # remove outer quotations

            escape_chars = re.findall(
                r'\\\\|\\"|\\x[0-9a-zA-Z][0-9a-zA-Z]', line)  # find escape chars
            for sub in escape_chars:
                # remove escape chars
                line = line.replace(sub, "")
            total_mem_count += len(line) + len(escape_chars)

    return total_char_count - total_mem_count

def part2(file_path: str) -> int:
    og_count = 0
    new_count = 0

    with open(file_path, "r") as file:
        for line in file:
            line = line.strip("\n")
            og_count += len(line)
            new_count += len(encode(line))
    
    return new_count - og_count

def encode(input: str) -> str:
    output = ""

    for char in input:
        if char == "\"":
            output += "\\\""
        elif char == "\\":
            output += "\\\\"
        else:
            output += char

    return "\"" + output + "\""

# Run using test_input.txt - ensures proper encoding
def TEST_FUNCTION(file_path: str):
    file = open(file_path, "r")
    assert encode(next(file).strip("\n")) == '"\\"\\""'
    assert encode(next(file).strip("\n")) == '"\\"abc\\""'
    assert encode(next(file).strip("\n")) == '"\\"aaa\\\\\\"aaa\\""'
    assert encode(next(file).strip("\n")) == '"\\"\\\\x27\\""'
    file.close()

def main():
    test_path = "test_input.txt"
    path = "puzzle_input.txt"

    TEST_FUNCTION(test_path)

    print(f"Part 1 Delta: {part1(path)}")

    print(f"Part 2 Delta: {part2(test_path)}")

if __name__=="__main__":
    main()
