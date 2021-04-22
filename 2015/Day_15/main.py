def main():
    path = "/home/andrewchaney/Documents/Coding Challenges/Advent-of-Code/2015/Day_15/puzzle_input.txt"

    available_tsp = 100

    # Ingredient - (capacity,durability,flavor,texture,calories)
    ingredients = {}

    with open(path, "r") as file:
        for line in file:
            input = line.split()
            ingred = input[0].split(":")[0]
            cap = int(input[2].split(",")[0])
            dur = int(input[4].split(",")[0])
            fla = int(input[6].split(",")[0])
            tex = int(input[8].split(",")[0])
            cal = int(input[10])
            ingredients[ingred] = (cap, dur, fla, tex, cal)

    # Hardcoded just for time
    categories = 5
    score = 0
    max_score = 0
    for i in range(available_tsp):
        j = available_tsp - i
        while j > 0:
            k = available_tsp - i - j
            while k > 0:
                l = available_tsp - i - j - k
                while l > 0:
                    c = 0   # capacity
                    d = 0   # durability
                    f = 0   # flavor
                    t = 0   # texture
                    cl = 0  # calories
                    for index, ingred in enumerate(ingredients):
                        if index == 0:
                            vals = [i*ingredients[ingred][x] for x in range(categories)]
                        elif index == 1:
                            vals = [j*ingredients[ingred][x] for x in range(categories)]
                        elif index == 2:
                            vals = [k*ingredients[ingred][x] for x in range(categories)]
                        else:
                            vals = [l*ingredients[ingred][x] for x in range(categories)]
                        c += vals[0]
                        d += vals[1]
                        f += vals[2]
                        t += vals[3]
                        cl += vals[4]
                    score = c * d * f * t
                    if c <= 0 or d <= 0 or f <= 0 or t <= 0:
                        score = 0
                    if score > max_score and cl == 500:
                        max_score = score
                    l -= 1
                k -= 1
            j -= 1
    
    print(f"Total Score: {max_score}")

if __name__ == '__main__':
    main()
