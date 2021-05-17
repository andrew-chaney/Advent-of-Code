package main

import (
	"bytes"
	"fmt"
	"io/ioutil"
	"regexp"
	"strconv"
)

type Coordinate struct {
	x, y int
}

func getDirection(facing string, turning string) string {
	var new_direction string

	compass := []byte{'W', 'N', 'E', 'S'}
	facing_index := bytes.IndexAny([]byte(compass), facing)

	if turning == "L" {
		if facing_index-1 >= 0 {
			new_direction = string(compass[facing_index-1])
		} else {
			new_direction = string(compass[len(compass)-1])
		}
	} else {
		if facing_index+1 < len(compass) {
			new_direction = string(compass[facing_index+1])
		} else {
			new_direction = string(compass[0])
		}
	}
	return new_direction
}

func blocksAway(x, y int) int {
	if x < 0 && y >= 0 {
		return -x + y
	} else if x >= 0 && y < 0 {
		return x + -y
	} else if x < 0 && y < 0 {
		return -x + -y
	} else {
		return x + y
	}
}

func part1(directions []string) int {
	hq_location := Coordinate{0, 0}
	facing := "N"
	for _, direc := range directions {
		turn := string(direc[0])
		distance, err := strconv.Atoi(string(direc[1:]))
		if err != nil {
			fmt.Println(err)
		}
		facing = getDirection(facing, turn)
		switch facing {
		case "N":
			hq_location.x += distance
		case "S":
			hq_location.x -= distance
		case "E":
			hq_location.y += distance
		case "W":
			hq_location.y -= distance
		}
	}
	return blocksAway(hq_location.x, hq_location.y)
}

func part2(directions []string) int {
	loc := Coordinate{0, 0}
	facing := "N"
	visited := make(map[Coordinate]bool)
	for _, direc := range directions {
		turn := string(direc[0])
		distance, err := strconv.Atoi(string(direc[1:]))
		if err != nil {
			fmt.Println(err)
		}
		facing = getDirection(facing, turn)
		for i := 0; i < distance; i++ {
			switch facing {
			case "N":
				loc.x += 1
			case "S":
				loc.x -= 1
			case "E":
				loc.y += 1
			case "W":
				loc.y -= 1
			}
			if visited[loc] == false {
				visited[loc] = true
			} else {
				return blocksAway(loc.x, loc.y)
			}
		}
	}
	return 0
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	if err != nil {
		fmt.Println("ERROR: could not open file")
		fmt.Println(err)
	}
	r, err := regexp.Compile("L\\d+|R\\d+")
	if err != nil {
		fmt.Println("ERROR: ", err)
	}

	directions := r.FindAllString(string(file), 176)

	fmt.Println("Part 1:", part1(directions))
	fmt.Println("Part 2:", part2(directions))
}
