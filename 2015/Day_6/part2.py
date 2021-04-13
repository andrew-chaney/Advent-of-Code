# Import regex
import re

# Turn off specified lights ('0' is off) - decrease by 1 with a min of 0
def turn_off(x1: int, y1: int, x2: int, y2: int, arr: list[list[int]]) -> list[list[int]]:
    # Iterate over the inputted range and turn on the lights
    if x1 != x2 and y1 != y2:
        for i in range(x1, x2 + 1):
            for j in range(y1, y2 + 1):
                if arr[i][j] > 0:
                    arr[i][j] -= 1
    elif x1 == x2 and y1 != y2:
        for j in range(y1, y2 + 1):
            if arr[x1][j] > 0:
                arr[x1][j] -= 1
    elif x1 != x2 and y1 == y2:
        for i in range(x1, x2 + 1):
            if arr[i][y1] > 0:
                arr[i][y1] -= 1
    else:
        if arr[x1][y1] > 0:
            arr[x1][y1] -= 1
    # Return the modified array
    return arr

# Turn on specified lights ('1' is on) - increase brightness by one
def turn_on(x1: int, y1: int, x2: int, y2: int, arr: list[list[int]]) -> list[list[int]]:
    # Iterate over the inputted range and turn on the lights
    if x1 != x2 and y1 != y2:
        for i in range(x1, x2 + 1):
            for j in range(y1, y2 + 1):
                arr[i][j] += 1
    elif x1 == x2 and y1 != y2:
        for j in range(y1, y2 + 1):
            arr[x1][j] += 1
    elif x1 != x2 and y1 == y2:
        for i in range(x1, x2 + 1):
            arr[i][y1] += 1
    else:
        arr[x1][y1] += 1
    # Return the modified array
    return arr

# Increase brightness by 2
def toggle(x1: int, y1: int, x2: int, y2: int, arr: list[list[int]]) -> list[list[int]]:
    # Iterate over the inputted range and toggle the lights
    if x1 != x2 and y1 != y2:
        for i in range(x1, x2 + 1):
            for j in range(y1, y2 + 1):
                arr[i][j] += 2
    elif x1 == x2 and y1 != y2:
        for j in range(y1, y2 + 1):
            arr[x1][j] += 2
    elif x1 != x2 and y1 == y2:
        for i in range(x1, x2 + 1):
            arr[i][y1] += 2
    else:
        arr[x1][y1] += 2
    # Return the modified array
    return arr


def main():
    path = "puzzle_input.txt"
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

    # Count the total brightness of all lights
    lit_count = 0
    for i in range(len(lights)):
        for j in range(len(lights)):
            lit_count += lights[i][j]

    print(f"Total Brightness: {lit_count}")


if __name__ == "__main__":
    main()
