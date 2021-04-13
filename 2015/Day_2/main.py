def surface_area(l, w, h):
    return (2*l*w)+(2*w*h)+(2*l*h)

def small_side(l, w, h):
    sides = [l*w, l*h, w*h]
    return min(sides)

def paper_needed(l, w, h):
    return surface_area(l,w,h) + small_side(l,w,h)

def ribbon_needed(l, w, h):
    dimensions = [l, w, h]
    dimensions.sort()
    ribbon = 1
    for i in dimensions:
        ribbon *= i
    for i in range(2):
        ribbon += 2 * dimensions[i]
    return ribbon

def main():
    path = "puzzle_input.txt"

    file = open(path, "r")

    total_paper = 0
    total_ribbon = 0

    for line in file:
        l, w, h  = line.split("x")
        length, width, height = int(l), int(w), int(h)

        total_paper += paper_needed(length, width, height)
        total_ribbon += ribbon_needed(length, width, height)

    print(f"Wrapping Paper Needed: {total_paper}")
    print(f"Ribbon Needed: {total_ribbon}")

if __name__=="__main__":
    main()
