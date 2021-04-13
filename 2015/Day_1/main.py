path = "puzzle_input.txt"
file = open(path, "r")

floor = 0
index = 0
basement_index = 0
basement_index_found = False

for line in file:
    for char in line:
        if char == "(":
            floor += 1
        else:
            floor -= 1
        
        if floor == -1 and basement_index_found is False:
            basement_index = index + 1
            basement_index_found = True

        index += 1

print(f"Ending Floor: {floor}")
print(f"Basement Index: {basement_index}")