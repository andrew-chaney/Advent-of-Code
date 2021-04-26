class Grid:
    def __init__(self, grid: list[list[str]]):
        self.state = grid

    def getCell(self, x: int, y: int) -> str:
        return self.state[x][y]

    def stateLength(self) -> int:
        return len(self.state)

    def stateWidth(self) -> int:
        return len(self.state[0]) 

    def updateState(self, grid: list[list[str]]):
        self.state = grid

    def morphState(self, part2: bool) -> list[list[str]]:
        new_state = [[] for _ in range(self.stateLength())]
        for row in range(self.stateLength()):
            for col in range(self.stateWidth()):
                if part2 and self.isCorner(row, col):
                    new_state[row].append("#")
                    continue
                neighbors = []
                for x in range(-1, 2):
                    if row + x >= 0 and row + x <= self.stateLength() - 1:
                        for y in range(-1, 2):
                            if col + y < 0 or col + y > self.stateWidth() - 1:
                                continue
                            elif x == 0 and y == 0:
                                continue
                            else:
                                neighbors.append(self.state[row+x][col+y])
                if self.getCell(row, col) == '#': # light is on
                    if neighbors.count('#') == 2 or neighbors.count('#') == 3:
                        new_state[row].append('#')
                    else:
                        new_state[row].append('.')
                else: # light is off
                    if neighbors.count('#') == 3:
                        new_state[row].append('#')
                    else:
                        new_state[row].append('.')
        self.updateState(new_state)
    
    def stepState(self, steps: int, part2: bool):
        for _ in range(steps):
            self.morphState(part2)
    
    def countLitLights(self) -> int:
        count = 0
        for x in range(len(self.state)):
            for y in range(len(self.state[x])):
                if self.state[x][y] == '#':
                    count += 1
        return count

    def isCorner(self, x: int, y: int) -> bool:
        l = self.stateLength()
        w = self.stateWidth()
        if x == 0 and y == 0:
            return True
        elif x == 0 and y == w - 1:
            return True
        elif x == l - 1 and y == 0:
            return True
        elif x == l - 1 and y == w - 1:
            return True
        else:
            return False

    def setStickyCorners(self):
        self.state[0][0] = "#"
        self.state[0][self.stateWidth()-1] = "#"
        self.state[self.stateLength()-1][0] = "#"
        self.state[self.stateLength()-1][self.stateWidth()-1] = "#"

def main():
    path = "puzzle_input.txt"

    initial = []
    steps = 100
    
    with open(path, "r") as file:
        for line in file:
            initial.append([i for i in line.strip()])

    part1 = Grid(initial)
    part1.stepState(steps, False)
    print(f"Part 1: {part1.countLitLights()}")

    part2 = Grid(initial)
    part2.setStickyCorners()
    part2.stepState(steps, True)
    print(f"Part 2: {part2.countLitLights()}")
    
if __name__ == "__main__":
    main()
