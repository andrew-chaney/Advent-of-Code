package main

import (
	"fmt"
	"io/ioutil"
	"strconv"
	"strings"
)

func solutionPart1(instructions string, button int) int {
	for _, instruct := range instructions {
		switch string(instruct) {
		case "U":
			if button > 3 {
				button -= 3
			}
		case "D":
			if button < 7 {
				button += 3
			}
		case "L":
			if button != 1 && button != 4 && button != 7 {
				button -= 1
			}
		case "R":
			if button != 3 && button != 6 && button != 9 {
				button += 1
			}
		}
	}
	return button
}

func moveUp(button string) string {
	up := []string{"1", "2", "4", "5", "9"} // can't go up from these buttons
	for _, val := range up {
		if val == button {
			return button
		}
	}
	if button == "A" {
		return "6"
	} else if button == "B" {
		return "7"
	} else if button == "C" {
		return "8"
	} else if button == "D" {
		return "B"
	} else {
		i_button, err := strconv.Atoi(button)
		if err != nil {
			fmt.Println(err)
		}
		if i_button-4 < 0 {
			return strconv.Itoa(-(i_button - 4))
		} else {
			return strconv.Itoa(i_button - 4)
		}
	}
}

func moveDown(button string) string {
	down := []string{"5", "9", "A", "C", "D"}
	for _, val := range down {
		if val == button {
			return button
		}
	}
	if button == "6" {
		return "A"
	} else if button == "7" {
		return "B"
	} else if button == "8" {
		return "C"
	} else if button == "B" {
		return "D"
	} else if button == "1" {
		return "3"
	} else {
		i_button, err := strconv.Atoi(button)
		if err != nil {
			fmt.Println(err)
		}
		return strconv.Itoa(i_button + 4)
	}
}

func moveLeft(button string) string {
	left := []string{"1", "2", "5", "A", "D"}
	for _, val := range left {
		if val == button {
			return button
		}
	}
	if button == "B" {
		return "A"
	} else if button == "C" {
		return "B"
	} else {
		i_button, err := strconv.Atoi(button)
		if err != nil {
			fmt.Println(err)
		}
		return strconv.Itoa(i_button - 1)
	}
}

func moveRight(button string) string {
	right := []string{"1", "4", "9", "C", "D"}
	for _, val := range right {
		if val == button {
			return button
		}
	}
	if button == "A" {
		return "B"
	} else if button == "B" {
		return "C"
	} else {
		i_button, err := strconv.Atoi(button)
		if err != nil {
			fmt.Println(err)
		}
		return strconv.Itoa(i_button + 1)
	}
}

func solutionPart2(instructions string, button string) string {
	for _, instruct := range instructions {
		switch string(instruct) {
		case "U":
			button = moveUp(button)
		case "D":
			button = moveDown(button)
		case "L":
			button = moveLeft(button)
		case "R":
			button = moveRight(button)
		}
	}
	return button
}

func main() {
	path := "puzzle_input.txt"
	// test := base + "test_input.txt"
	file, err := ioutil.ReadFile(path)
	if err != nil {
		fmt.Println(err)
	}
	instructions := strings.Split(string(file), "\n")

	button1 := 5
	password1 := make([]int, 0)
	button2 := "5"
	password2 := make([]string, 0)
	for _, instruct := range instructions {
		button1 = solutionPart1(instruct, button1)
		password1 = append(password1, button1)
		button2 = solutionPart2(instruct, button2)
		password2 = append(password2, button2)
	}

	fmt.Println("Part 1:", password1)
	fmt.Println("Part 2:", password2)
}
