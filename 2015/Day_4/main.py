import hashlib

def crack(input):
    hashend = 0
    while True:
        hasher = input + str(hashend)
        result = hashlib.md5(hasher.encode())
        if result.hexdigest()[:5] == "00000":
            return hashend
        hashend += 1

def crack2(input):
    hashend = 0
    while True:
        hasher = input + str(hashend)
        result = hashlib.md5(hasher.encode())
        if result.hexdigest()[:6] == "000000":
            return hashend
        hashend += 1

def main():
    input = "ckczppom"

    print(f"Part 1: {crack(input)}")
    print(f"Part 2: {crack2(input)}")

if __name__=="__main__":
    main()
