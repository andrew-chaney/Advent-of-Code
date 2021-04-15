import re, itertools

def quickRoute(cities: list, distances: dict) -> int:
    min_dist = 0
    for i in itertools.permutations(cities, len(cities)):
        route = list(i)
        dist = 0
        for j in range(len(route)):
            if j + 1 < len(route):
                if (route[j], route[j+1]) in distances:
                    dist += distances[(route[j], route[j+1])]
                else:
                    dist += distances[(route[j+1], route[j])]
        if min_dist == 0:
            min_dist = dist
        else:
            if dist < min_dist:
                min_dist = dist

    return min_dist

def longRoute(cities: list, distances: dict) -> int:
    max_dist = 0
    for i in itertools.permutations(cities, len(cities)):
        route = list(i)
        dist = 0
        for j in range(len(route)):
            if j + 1 < len(route):
                if (route[j], route[j+1]) in distances:
                    dist += distances[(route[j], route[j+1])]
                else:
                    dist += distances[(route[j+1], route[j])]
        if max_dist == 0:
            max_dist = dist
        else:
            if dist > max_dist:
                max_dist = dist

    return max_dist

def main():
    path = "puzzle_input.txt"
    
    cities = []
    distances = {}

    with open(path, "r") as file:
        for line in file:
            inputs = re.findall(r'\w+', line)
            a = inputs[0]
            b = inputs[2]
            distance = inputs[3]

            if a not in cities:
                cities.append(a)
            if b not in cities:
                cities.append(b)
            if (a,b) not in distances or (b,a) not in distances:
                distances[(a,b)] = int(distance)
    
    print(f"Shortest Route Distance: {quickRoute(cities, distances)}")
    print(f"Longest Route Distance: {longRoute(cities, distances)}")


if __name__=="__main__":
    main()
