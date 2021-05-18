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

func getName(input string) [26]int {
	var name [26]int
	for _, val := range input {
		if string(val) == "-" {
			continue
		} else if val < 'a' {
			break
		}
		name[int(val)-int('a')] += 1
	}
	return name
}

func getChecksum(input string) string {
	r, err := regexp.Compile("\\[(.*?)\\]")
	handle(err)
	checksum := r.FindAllString(input, 1)[0]
	return checksum[1 : len(checksum)-1]
}

func getSectorID(input string) int {
	r, err := regexp.Compile("\\d+")
	handle(err)
	id := r.FindAllString(input, 1)[0]
	idAsInt, err := strconv.Atoi(id)
	handle(err)
	return idAsInt
}

func isRealRoom(name [26]int, checksum string) bool {
	for _, val := range checksum {
		var max int
		var max_index int
		for i, num := range name {
			if num > max {
				max = num
				max_index = i
			}
		}
		if int(val) == int(max_index)+97 {
			name[max_index] = 0
		} else {
			return false
		}
	}
	return true
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	handle(err)
	lines := strings.Split(string(file), "\n")

	part1 := 0
	var part2 string
	for _, line := range lines {
		if isRealRoom(getName(line), getChecksum(line)) {
			part1 += getSectorID(line)
		}

		// Part 2
		var decrypted string
		shift := getSectorID(line) % 26
		for _, letter := range line {
			if string(letter) == "[" {
				break
			} else if string(letter) == "-" {
				decrypted += " "
				continue
			} else if letter < 97 {
				decrypted += string(letter)
				continue
			}

			new_letter := int(letter) + shift
			if new_letter > 122 {
				new_letter -= 26
			}
			decrypted += string(rune(new_letter))
		}
		if strings.Contains(decrypted, "north") {
			part2 = decrypted
		}
	}

	fmt.Println(part1)
	fmt.Println(part2)
}
