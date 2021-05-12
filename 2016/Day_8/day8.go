package main

import (
	"fmt"
	"io/ioutil"
	"regexp"
	"strconv"
	"strings"
)

func handle(err error) {
	if err != nil {
		fmt.Println(err)
	}
}

func rect(arr [6][50]int, height int, width int) [6][50]int {
	for i := 0; i < height; i++ {
		for j := 0; j < width; j++ {
			arr[i][j] = 1
		}
	}
	return arr
}

func rotateRow(arr [6][50]int, index int, amt int) [6][50]int {
	var tmpRow [50]int
	for i := 0; i < len(arr[index]); i++ {
		cell := arr[index][i]
		if i+amt < len(arr[index]) {
			tmpRow[i+amt] = cell
		} else {
			tmpRow[i+amt-len(arr[index])] = cell
		}
	}
	arr[index] = tmpRow
	return arr
}

func rotateCol(arr [6][50]int, index int, amt int) [6][50]int {
	var tmpCol [6]int
	for i := 0; i < len(arr); i++ {
		cell := arr[i][index]
		if i+amt < len(arr) {
			tmpCol[i+amt] = cell
		} else {
			tmpCol[i+amt-len(arr)] = cell
		}
	}
	for i := 0; i < len(arr); i++ {
		arr[i][index] = tmpCol[i]
	}
	return arr
}

func countLit(arr [6][50]int) int {
	count := 0
	for i := 0; i < len(arr); i++ {
		for j := 0; j < len(arr[i]); j++ {
			if arr[i][j] == 1 {
				count++
			}
		}
	}
	return count
}

func displayScreen(arr [6][50]int) {
	for i := 0; i < len(arr); i++ {
		for j := 0; j < len(arr[i]); j++ {
			if arr[i][j] == 1 {
				fmt.Print("XXX")
			} else {
				fmt.Print("   ")
			}
		}
		fmt.Println()
	}
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	handle(err)
	r, err := regexp.Compile("\\w+")
	handle(err)
	lines := strings.Split(string(file), "\n")

	var screen [6][50]int
	for i := 0; i < len(lines); i++ {
		input := r.FindAllString(lines[i], len(lines[i]))
		if input[0] == "rotate" {
			index, err := strconv.Atoi(input[3])
			handle(err)
			amt, err := strconv.Atoi(input[5])
			handle(err)
			if input[1] == "row" {
				screen = rotateRow(screen, index, amt)
			} else {
				screen = rotateCol(screen, index, amt)
			}
		} else {
			nums := strings.Split(input[1], "x")
			width, err := strconv.Atoi(nums[0])
			handle(err)
			height, err := strconv.Atoi(nums[1])
			handle(err)
			screen = rect(screen, height, width)
		}
	}

	part1 := countLit(screen)
	fmt.Println("Part 1:", part1)
	fmt.Println("Part 2:\n")
	displayScreen(screen)
}
