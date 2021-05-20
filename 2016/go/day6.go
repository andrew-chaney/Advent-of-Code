package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

func handle(err error) {
	if err != nil {
		fmt.Println(err)
	}
}

func maxIndex(arr [26]int) int {
	var max int
	var indexOfMax int
	for i, v := range arr {
		if v > max {
			max = v
			indexOfMax = i
		}
	}
	return indexOfMax
}

func minIndex(arr [26]int) int {
	min := 1000
	var indexOfMin int
	for i, v := range arr {
		if v < min && v > 0 {
			min = v
			indexOfMin = i
		}
	}
	return indexOfMin
}

func errorCorrect(input []string, part2 bool) string {
	var message string
	for i := 0; i < len(input[0]); i++ {
		var counter [26]int
		for j := 0; j < len(input); j++ {
			counter[int(input[j][i]-97)] += 1
		}
		if part2 == false {
			message += string(rune(maxIndex(counter)) + 97)
		} else {
			message += string(rune(minIndex(counter)) + 97)
		}
	}
	return message
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	handle(err)
	input := strings.Split(string(file), "\n")

	fmt.Println("Part 1:", errorCorrect(input, false))
	fmt.Println("Part 2:", errorCorrect(input, true))
}
