from itertools import cycle

def main():
    path = "puzzle_input.txt"

    race_length = 2503
    stats = {}
    distances = {}
    points = {}

    with open(path, "r") as file:
        for line in file:
            input = line.split()
            name = input[0]
            speed = input[3]
            flight_time = input[6]
            rest_time = input[13]
            stats[name] = (int(speed), int(flight_time), int(rest_time))

    for deer in stats:
        x = stats[deer]
        points[deer] = 0
        travel_buffer = cycle([x[0]]*x[1] + [0]*x[2])
        distances[deer] = [next(travel_buffer) for _ in range(race_length)]
    
    longest_dist = max([sum(distances[deer]) for deer in distances])
    print(f"Furthest Distance: {longest_dist}")

    for i in range(1, race_length + 1):
        dist = [sum(distances[deer][:i]) for deer in distances]
        for x, deer in enumerate(points):
            if dist[x] == max(dist):
                points[deer] += 1
    
    most_points = max([points[deer] for deer in points])
    print(f"Most Points: {most_points}")

if __name__ == "__main__":
    main()
