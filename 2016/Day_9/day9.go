package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"strconv"
	"strings"
)

func handle(err error) {
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}

func decompressedLength(input string, part2 bool) int {
	index1 := strings.Index(input, "(")
	index2 := strings.Index(input, ")")
	if index1 == -1 || index2 == -1 {
		return len(input)
	}
	marker := string(input[index1+1 : index2])
	nums := strings.Split(marker, "x")
	slice_length, err := strconv.Atoi(nums[0])
	handle(err)
	repetitions, err := strconv.Atoi(nums[1])
	handle(err)
	var size int
	if part2 == false {
		size = slice_length
	} else {
		size = decompressedLength(string(input[index2+1:index2+1+slice_length]), part2)
	}
	return len(input[:index1]) + (size * repetitions) + decompressedLength(input[index2+1+slice_length:], part2)
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	handle(err)
	lines := strings.Split(string(file), "\n")

	part1 := 0
	part2 := 0
	for _, line := range lines {
		part1 += decompressedLength(line, false)
		part2 += decompressedLength(line, true)
	}

	fmt.Println("Part 1:", part1)
	fmt.Println("Part 2:", part2)
}
