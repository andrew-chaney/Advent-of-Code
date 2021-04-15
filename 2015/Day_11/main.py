from itertools import groupby

def genPassword(current: str) -> str:
    alph = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']

    new_pass = list(current)

    while True:
        i = len(new_pass) - 1
        while i >= 0:
            if alph.index(new_pass[i]) + 1 >= len(alph):
                new_pass[i] = alph[0]
            else:
                new_pass[i] = alph[alph.index(new_pass[i])+1]
                break 
            i -= 1
        if runTests("".join(i for i in new_pass), alph):
            break
    return "".join(i for i in new_pass)

def straightTest(pswd: str, alph: list) -> bool:
    for i in range(len(pswd)):
        if i + 2 < len(pswd):
            index = alph.index(pswd[i])
            if alph.index(pswd[i+1]) == index + 1 and alph.index(pswd[i+2]) == index + 2:
                return True
    return False

def confusingLetterTest(pswd: str) -> bool:
    forbidden = ['i', 'o', 'l']
    for l in forbidden:
        if l in pswd:
            return False
    return True

def pairsTest(pswd: str) -> bool:
    count = 0
    for i,j in groupby(pswd):
        if len(list(j)) == 2:
            count += 1
    return True if count >= 2 else False

def runTests(pswd: str, alph: list) -> bool:
    return True if straightTest(pswd, alph) and confusingLetterTest(pswd) and pairsTest(pswd) else False

def main():
    input = "vzbxkghb"
    
    part1 = genPassword(input)
    part2 = genPassword(part1)

    print(f"Part 1: {part1}")
    print(f"Part 2: {part2}")

if __name__ == "__main__":
    main()
