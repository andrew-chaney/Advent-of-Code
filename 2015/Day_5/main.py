def vowel_test(input: str) -> bool:
    vowel_count = 0
    vowels = ['a','e','i','o','u']

    for i in input:
        if i in vowels:
            vowel_count += 1

    if vowel_count >= 3:
        return True
    
    return False


def pairs_test(input: str) -> bool:
    for i in range(len(input)):
        if i + 1 < len(input):
            if input[i] == input[i + 1]:
                return True


def naughty_test(input: str) -> bool:
    for i in range(len(input)):
        if i + 1 < len(input):
            if input[i] == 'a' and input[i + 1] == 'b':
                return False
            elif input[i] == 'c' and input[i + 1] == 'd':
                return False
            elif input[i] == 'p' and input[i + 1] == 'q':
                return False
            elif input[i] == 'x' and input[i + 1] == 'y':
                return False

    return True


def test_set1(input: str) -> bool:
    test1 = vowel_test(input)
    test2 = pairs_test(input)
    test3 = naughty_test(input)

    if test1 and test2 and test3:
        return True
    
    return False


def pairs_test2(input: str) -> bool:
    for i in range(len(input)):
        if i + 1 < len(input):
            a = input[i]
            b = input[i + 1]
            if i + 2 < len(input):
                for j in range(i + 2, len(input)):
                    if j + 1 < len(input):
                        if input[j] == a and input[j + 1] == b:
                            return True
    
    return False

def repeating2(input: str) -> bool:
    for i in range(len(input)):
        if i + 2 < len(input):
            if input[i] == input[i + 2]:
                return True
    
    return False


def test_set2(input: str) -> bool:
    test1 = pairs_test2(input)
    test2 = repeating2(input)

    if test1 and test2:
        return True
    
    return False


def main():
    path = "puzzle_input.txt"
    file = open(path, "r")

    nice_count1 = 0
    nice_count2 = 0

    for line in file:
        if test_set1(line):
            nice_count1 += 1
        if test_set2(line):
            nice_count2 += 1

    print(f"Number of Nice Strings Pt. 1: {nice_count1}")
    print(f"Number of Nice Strings Pt. 2: {nice_count2}")

    file.close()

if __name__=="__main__":
    main()
