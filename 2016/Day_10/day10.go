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

type BotFuncs interface {
	numChips()  // how many chips
	getChips()  // what chips
	loseChips() // gives chips away, loses them
	gainChip()  // receives chips
}

type Bot struct {
	chips    [2]int       // chips that the bot has
	commands [2][2]string // which bots/bins the bot gives its chips to (low then high)
}

func (x Bot) numChips() int {
	count := 0
	for _, chip := range x.chips {
		if chip != 0 {
			count++
		}
	}
	return count
}

func (x Bot) getChips() (int, int) {
	return x.chips[0], x.chips[1]
}

func (x Bot) loseChips() Bot {
	x.chips = [2]int{0, 0}
	return x
}

func (x Bot) gainChip(chip int) Bot {
	for i := 0; i < 2; i++ {
		if x.chips[i] == 0 {
			x.chips[i] = chip
			break
		}
	}
	return x
}

func (x Bot) loadCmds(cmds [2][2]string) Bot {
	x.commands = cmds
	return x
}

func (x Bot) getCmds() [2][2]string {
	return x.commands
}

func min(x, y int) int {
	if x < y {
		return x
	}
	return y
}

func max(x, y int) int {
	if x > y {
		return x
	}
	return y
}

func chipsInBins(output [21][]int) int {
	total := 0
	for i := 0; i < len(output); i++ {
		total += len(output[i])
	}
	return total
}

func distribute(bots [210]Bot, chip_total int, targetA int, targetB int) (int, int) {
	var output [21][]int // got size by: grep -o "output \d*" puzzle_input.txt | sort -u
	var targetBot int

	for chipsInBins(output) < chip_total {
		for i := 0; i < len(bots); i++ {
			if bots[i].numChips() == 2 {
				chipA, chipB := bots[i].getChips()
				if (chipA == targetA && chipB == targetB) || (chipA == targetB && chipB == targetA) {
					targetBot = i
				}
				commands := bots[i].getCmds()

				for j := 0; j < 2; j++ {
					var chip int
					if j == 0 {
						chip = min(chipA, chipB)
					} else {
						chip = max(chipA, chipB)
					}
					num, err := strconv.Atoi(string(commands[j][1]))
					handle(err)
					if string(commands[j][0]) != "output" {
						bots[num] = bots[num].gainChip(chip)
					} else {
						output[num] = append(output[num], chip)
					}
				}
				bots[i] = bots[i].loseChips()
			}
		}
	}
	return targetBot, (output[0][0] * output[1][0] * output[2][0])
}

func main() {
	path := "puzzle_input.txt"
	file, err := ioutil.ReadFile(path)
	handle(err)
	lines := strings.Split(string(file), "\n")

	var bots [210]Bot // got size by: grep -o "bot \d\d\d" puzzle_input.txt | sort -u

	chip_total := 0
	for _, line := range lines {
		if line != "" {
			input := strings.Split(line, " ")
			if input[0] == "value" {
				chip, err := strconv.Atoi(input[1])
				handle(err)
				chip_total++
				bot, err := strconv.Atoi(input[5])
				handle(err)
				bots[bot] = bots[bot].gainChip(chip)
			} else {
				bot, err := strconv.Atoi(input[1])
				handle(err)
				commands := [2][2]string{
					{input[5], input[6]},
					{input[10], input[11]},
				}
				bots[bot] = bots[bot].loadCmds(commands)
			}
		}
	}

	part1, part2 := distribute(bots, chip_total, 61, 17)
	fmt.Printf("Part 1: Bot #%d\n", part1)
	fmt.Println("Part 2:", part2)
}
