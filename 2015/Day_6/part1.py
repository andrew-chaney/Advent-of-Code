# Import regex
import re

# Turn off specified lights ('0' is off)
def turn_off(x1: int, y1: int, x2: int, y2: int, arr: list[list[int]]) -> list[list[int]]:
    # Iterate over the inputted range and turn on the lights
    if x1 != x2 and y1 != y2:
        for i in range(x1, x2 + 1):
            for j in range(y1, y2 + 1):
                arr[i][j] = 0
    elif x1 == x2 and y1 != y2:
        for j in range(y1, y2 + 1):
            arr[x1][j] = 0
    elif x1 != x2 and y1 == y2:
        for i in range(x1, x2 + 1):
            arr[i][y1] = 0
    else:
        arr[x1][y1] = 0
    # Return the modified array
    return arr

# Turn on specified lights ('1' is on)
def turn_on(x1: int, y1: int, x2: int, y2: int, arr: list[list[int]]) -> list[list[int]]:
    # Iterate over the inputted range and turn on the lights
    if x1 != x2 and y1 != y2:
        for i in range(x1, x2 + 1):
            for j in range(y1, y2 + 1):
                arr[i][j] = 1
    elif x1 == x2 and y1 != y2:
        for j in range(y1, y2 + 1):
            arr[x1][j] = 1
    elif x1 != x2 and y1 == y2:
        for i in range(x1, x2 + 1):
            arr[i][y1] = 1
    else:
        arr[x1][y1] = 1
    # Return the modified array
    return arr

# If lights are on then turn them off and vice versa
def toggle(x1: int, y1: int, x2: int, y2: int, arr: list[list[int]]) -> list[list[int]]:
    # Iterate over the inputted range and toggle the lights
    if x1 != x2 and y1 != y2:
        for i in range(x1, x2 + 1):
            for j in range(y1, y2 + 1):
                arr[i][j] = switch(arr[i][j])
    elif x1 == x2 and y1 != y2:
        for j in range(y1, y2 + 1):
            arr[x1][j] = switch(arr[x1][j])
    elif x1 != x2 and y1 == y2:
        for i in range(x1, x2 + 1):
            arr[i][y1] = switch(arr[i][y1])
    else:
        arr[x1][y1] = switch(arr[x1][y1])
    # Return the modified array
    return arr

# Switch the current light on or off
def switch(num: int) -> int:
    # If the light is off, turn it on
    if num == 0:
        return 1
    # Vice versa
    else:
        return 0


def main():
    path = "/Users/andrewchaney/Documents/Coding Challenges/Python-Advent-of-Code/2015/Day_6/puzzle_input.txt"
    file = open(path, "r")
    # Initialize light array with all lights starting off ('0' is off)
    lights = [[0 for i in range(1000)] for i in range(1000)]

    for line in file:
        actions = re.findall(r'turn on|turn off|toggle', line)
        action = actions[0]

        nums = re.findall(r'\d+', line)
        x1 = int(nums[0])
        y1 = int(nums[1])
        x2 = int(nums[2])
        y2 = int(nums[3])

        if action == "turn on":
            turn_on(x1, y1, x2, y2, lights)
        elif action == "turn off":
            turn_off(x1, y1, x2, y2, lights)
        elif action == "toggle":
            toggle(x1, y1, x2, y2, lights)
    
    file.close()

    # Count the lit lights
    lit_count = 0
    for i in range(len(lights)):
        for j in range(len(lights)):
            if lights[i][j] == 1:
                lit_count += 1
    
    print(f"Lights Lit: {lit_count}")


if __name__=="__main__":
    main()