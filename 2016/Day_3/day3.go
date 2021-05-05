package main

import (
	"fmt"
	"io/ioutil"
	"regexp"
	"strconv"
	"strings"
)

func handleError(err error) {
	if err != nil {
		fmt.Println(err)
	}
}

func convertToNum(nums []string) (int, int, int) {
	a, err := strconv.Atoi(nums[0])
	handleError(err)
	b, err := strconv.Atoi(nums[1])
	handleError(err)
	c, err := strconv.Atoi(nums[2])
	handleError(err)
	return a, b, c
}

func sortTriangles(file []byte) [1902][3]int {
	lines := strings.Split(string(file), "\n")
	r, err := regexp.Compile("\\d+")
	handleError(err)
	var triangles [1902][3]int
	for index, line := range lines {
		nums := r.FindAllString(line, 3)
		a, b, c := convertToNum(nums)
		triangles[index][0] = a
		triangles[index][1] = b
		triangles[index][2] = c
	}
	return triangles
}

func isValid(v [3]int) bool {
	if v[0]+v[1] > v[2] && v[0]+v[2] > v[1] && v[1]+v[2] > v[0] {
		return true
	}
	return false
}

func part1(dimensions [1902][3]int) int {
	count := 0
	for _, v := range dimensions {
		if isValid(v) {
			count += 1
		}
	}
	return count
}

func part2(triangles [1902][3]int) int {
	count := 0
	for n := 0; n < len(triangles); n += 3 {
		for i := 0; i < 3; i++ {
			tri := [3]int{triangles[n][i], triangles[n+1][i], triangles[n+2][i]}
			if isValid(tri) {
				count += 1
			}
		}
	}
	return count
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	handleError(err)

	triangles := sortTriangles(file)

	fmt.Println("Part 1:", part1(triangles))
	fmt.Println("Part 2:", part2(triangles))
}
