package main

import (
	"crypto/md5"
	"encoding/hex"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func hash(doorID string, index int) string {
	str_index := strconv.Itoa(index)
	input := []byte(doorID + str_index)
	h := md5.New()
	h.Write(input)
	return hex.EncodeToString(h.Sum(nil))
}

func part1(doorID string) string {
	index := 0
	var password string
	for len(password) < 8 {
		hash := hash(doorID, index)
		if "00000" == hash[:5] {
			password += string(hash[5])
		}
		index++
	}
	return password
}

func contains(arr [8]int, element int) bool {
	for _, v := range arr {
		if v == element {
			return true
		}
	}
	return false
}

func part2(doorID string) string {
	index := 0
	var password [8]string
	var tracker [8]int
	for {
		hash := hash(doorID, index)
		if "00000" == hash[:5] {
			if hash[5] > 47 && hash[5] < 56 {
				hash_index, e := strconv.Atoi(string(hash[5]))
				if e != nil {
					fmt.Println(e)
				}
				if tracker[hash_index] == 0 {
					password[hash_index] = string(hash[6])
					tracker[hash_index] += 1
				}
			}
		}

		if contains(tracker, 0) == false {
			break
		} else {
			index++
		}
	}
	return strings.Join(password[:], "")
}

func assert(input string, desired_output string) {
	if input != desired_output {
		fmt.Printf("ERROR: %s does not match %s\n", input, desired_output)
		fmt.Println("EXITING...")
		os.Exit(1)
	}
}

func main() {
	doorID := "abbhdwsy"
	testID := "abc"

	assert(part1(testID), "18f47a30")
	assert(part2(testID), "05ace8e3")

	p1 := part1(doorID)
	p2 := part2(doorID)

	fmt.Println("Part 1 Password:", p1)
	fmt.Println("Part 2 Password:", p2)
}
