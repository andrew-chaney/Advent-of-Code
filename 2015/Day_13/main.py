from itertools import permutations

def sort_seats(people: list[str], happiness: dict) -> int:
    max_happiness = 0
    seating_chart = []
    for i in permutations(people, len(people)):
        chart = list(i)
        current_happiness = 0
        for i in range(len(people)):
            if i == 0:
                current_happiness += happiness[(chart[i], chart[len(people)-1])]
                current_happiness += happiness[(chart[i], chart[i+1])]
            elif i == len(people) - 1:
                current_happiness += happiness[(chart[i], chart[i-1])]
                current_happiness += happiness[(chart[i], chart[0])]
            else:
                current_happiness += happiness[(chart[i],chart[i-1])]
                current_happiness += happiness[(chart[i], chart[i+1])]
        if current_happiness > max_happiness:
            max_happiness = current_happiness
            seating_chart = chart
    print(seating_chart)
    return max_happiness

def main():
    path = "puzzle_input.txt"

    people = []
    happiness_ratings = {}

    with open(path, "r") as file:
        for line in file:
            input = line.split()
            person1 = input[0]
            person2 = input[-1].split(".")[0]
            if person1 not in people:
                people.append(person1)
            if person2 not in people:
                people.append(person2)
            if input[2] == "gain":
                if (person1,person2) not in happiness_ratings:
                    happiness_ratings[(person1,person2)] = int(input[3])
            else:
                if (person1,person2) not in happiness_ratings:
                    happiness_ratings[(person1,person2)] = -int(input[3])
    
    # Part 2 - add yourself
    for i in range(len(people)):
        happiness_ratings[("Self", people[i])] = 0
        happiness_ratings[(people[i], "Self")] = 0
    people.append("Self")

    print(f"Happiness Result: {sort_seats(people, happiness_ratings)}")

if __name__ == "__main__":
    main()
