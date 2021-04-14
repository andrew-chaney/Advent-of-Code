class Coordinates:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def get(self):
        return (self.x, self.y)
    
    def getx(self):
        return self.x
    
    def gety(self):
        return self.y


def part1(directions):
    locations = set()
    locations.add((0,0))
    santa_loc = Coordinates(0,0)

    for c in directions:
        if c == '^':
            santa_loc = Coordinates(santa_loc.getx(), santa_loc.gety() + 1)
        elif c == 'v':
            santa_loc = Coordinates(santa_loc.getx(), santa_loc.gety() - 1)
        elif c == '>':
            santa_loc = Coordinates(santa_loc.getx() + 1, santa_loc.gety())
        else:
            santa_loc = Coordinates(santa_loc.getx() - 1, santa_loc.gety())
        locations.add(santa_loc.get())
    
    return locations


def part2(directions):
    locations = set()
    locations.add((0,0))
    santa_loc = Coordinates(0,0)
    robo_loc = Coordinates(0,0)

    turn = 0

    for c in directions:
        if turn % 2 == 0:
            if c == '^':
                santa_loc = Coordinates(santa_loc.getx(), santa_loc.gety() + 1)
            elif c == 'v':
                santa_loc = Coordinates(santa_loc.getx(), santa_loc.gety() - 1)
            elif c == '>':
                santa_loc = Coordinates(santa_loc.getx() + 1, santa_loc.gety())
            else:
                santa_loc = Coordinates(santa_loc.getx() - 1, santa_loc.gety())
            locations.add(santa_loc.get())
        else:
            if c == '^':
                robo_loc = Coordinates(robo_loc.getx(), robo_loc.gety() + 1)
            elif c == 'v':
                robo_loc = Coordinates(robo_loc.getx(), robo_loc.gety() - 1)
            elif c == '>':
                robo_loc = Coordinates(robo_loc.getx() + 1, robo_loc.gety())
            else:
                robo_loc = Coordinates(robo_loc.getx() - 1, robo_loc.gety())
            locations.add(robo_loc.get())
        turn += 1
    
    return locations


def main():
    path = "puzzle_input.txt"
    with open(path, "r") as file:
        directions = file.read()

    houses1 = len(part1(directions))
    houses2 = len(part2(directions))

    print(f"Houses Visited by Santa Alone: {houses1}")
    print(f"Houses Visited by Santa and Robo Santa: {houses2}")


if __name__=="__main__":
    main()
