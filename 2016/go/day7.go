package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"regexp"
	"strings"
)

func handle(err error) {
	if err != nil {
		fmt.Println(err)
		fmt.Println("Exiting...")
		os.Exit(1) // exit because there is no point in continuing if the info isn't correct
	}
}

func parseLine(line string) []string {
	r, err := regexp.Compile("\\w+|\\[\\w+\\]")
	handle(err)
	return r.FindAllString(line, strings.Count(line, "[")*3)
}

func validABBA(v string) bool {
	if v[0] == v[3] && v[1] == v[2] && v[0] != v[1] {
		return true
	} else {
		return false
	}
}

func validABA(v string) bool {
	if v[0] == v[2] && v[0] != v[1] {
		return true
	} else {
		return false
	}
}

func supportsTLS(line string) bool {
	input := parseLine(line)
	flag := false
	bracket_flag := false
	for _, val := range input {
		for i := 0; i < len(val); i++ {
			if i+4 <= len(val) {
				if validABBA(val[i : i+4]) {
					if string(val[0]) != "[" {
						flag = true
					} else {
						bracket_flag = true
					}
				}
			}
		}
	}
	if bracket_flag != true {
		return flag
	} else {
		return false
	}
}

func supportsSSL(line string) bool {
	aba := make([]string, 0)
	bab := make([]string, 0)
	input := parseLine(line)
	for _, val := range input {
		for i := 0; i < len(val); i++ {
			if i+3 <= len(val) {
				if validABA(val[i : i+3]) {
					if string(val[0]) != "[" {
						aba = append(aba, val[i:i+3])
					} else {
						bab = append(bab, val[i:i+3])
					}
				}
			}
		}
	}
	for _, i := range aba {
		for _, j := range bab {
			if i[1:] == j[:2] {
				return true
			}
		}
	}
	return false
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	handle(err)
	lines := strings.Split(string(file), "\n")

	part1 := 0
	part2 := 0
	for _, line := range lines {
		if supportsTLS(line) {
			part1++
		}
		if supportsSSL(line) {
			part2++
		}
	}
	fmt.Println("Part 1:", part1)
	fmt.Println("Part 2:", part2)
}
