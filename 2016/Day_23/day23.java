import java.io.File;
import java.util.Scanner;

public class day23 {

    static String[] instructions;

    public static void main(String[] args) {
        File file = new File("part1_input.txt");
        String[] input1 = new String[26]; // for part1 input
        try {
            Scanner scanner = new Scanner(file);
            int index = 0;
            while ( scanner.hasNextLine() ) {
                input1[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        // Part 1
        instructions = input1;
        System.out.println("Part 1: " + solve(7));

        // Part 2
        /*  Part 2 input is an altered version of the original input. Using the 
            hint on the puzzle page, a 'mul' command is added as a peephole 
            optimization to the instruction set. "mul x y z" takes the product 
            of y * z and stores it in register x. "cpy -16 c" is also changed 
            to "cpy -12 c" to allow for proper jumping after the toggle. 
            Otherwise, there will be an out of bounds exception.
        */
        file = new File("part2_input.txt");
        String[] input2 = new String[22];
        try {
            Scanner scanner = new Scanner(file);
            int index = 0;
            while ( scanner.hasNextLine() ) {
                input2[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        instructions = input2;
        System.out.println("Part 2: " + solve(12));
    }

    // Solves with the initial value of register A provided as 
    // input
    static int solve(int regAValue) {
        // Declare and initialize registers: {a=0,b=1,c=2,d=3}. Each index is
        // associated with a register
        int[] registers = new int[4];
        // Set register 'a' to the inputted value
        registers[0] = regAValue;
        // Iterate through instructions from puzzle input and execute
        // each line in the proper order
        int index = 0;
        while (index < instructions.length) {
            // Split the line of instructions into a String[] to be able to 
            // handle each variable within it
            String[] line = instructions[index].split(" ");
            // Handle each possible instruction
            switch (line[0]) {
                // Increment
                case "inc":
                    registers[(int) line[1].charAt(0) - 97]++;
                    break;
                // Decrement
                case "dec":
                    registers[(int) line[1].charAt(0) - 97]--;
                    break;
                // Copy
                case "cpy":
                    // Make sure both arguments aren't numbers
                    if (isNum(line[1]) && isNum(line[2])) {
                        // If both are true, invalid statement... skip.
                        break;
                    }
                    // Declare variable for the value to be assigned to the
                    // register
                    int value;
                    // The register being assigned the value
                    int register = (int) line[2].charAt(0) - 97;
                    // If the value being assigned to the register is the value 
                    // of another register, store in value variable
                    if (isRegister(line[1])) {
                        value = registers[(int) line[1].charAt(0) - 97];
                    } 
                    // Otherwise, pull the integer from the instructions
                    else {
                        value = Integer.parseInt(line[1]);
                    }
                    // Store the value in the register
                    registers[register] = value;
                    break;
                // Jump
                case "jnz":
                    // "jnz x y": if int, or value of register, x is greater
                    // than 0, jump y spaces
                    // + means forward, - means back
                    int x, y;
                    // If x is a register, grab that registers current value
                    if (isRegister(line[1])) {
                        x = registers[(int) line[1].charAt(0) - 97];
                    }
                    // Otherwise, parse the value of the integer
                    else {
                        x = Integer.parseInt(line[1]);
                    }
                    // Now, do the same thing for y that was done for x
                    if (isRegister(line[2])) {
                        y = registers[(int) line[2].charAt(0) - 97];
                    } else {
                        y = Integer.parseInt(line[2]);
                    }
                    // If x > 0, jump y instructions
                    if (x > 0) {
                        index += y;
                        continue;
                    }
                    break;
                // Toggle
                case "tgl":
                    // Find the index of the instructions to toggle
                    int numAway = registers[(int) line[1].charAt(0) - 97];
                    int toggleIndex = numAway + index;
                    // If the toggle index exceeds the length of instructions,
                    // nothing happens
                    if (toggleIndex >= instructions.length) {
                        break;
                    }
                    toggle(toggleIndex);
                    break;
                // Multiply
                case "mul":
                    // "mul a b c": store b * c in register a
                    int a = (int) line[1].charAt(0) - 97;
                    int b = registers[(int) line[2].charAt(0) - 97];
                    int c = registers[(int) line[3].charAt(0) - 97];
                    registers[a] = b * c;
                    break;
            }
            index++;
        }
        // Return the value of register 'a'
        return registers[0];
    }

    static void toggle(int toggleIndex) {
        String instructionToAdjust = instructions[toggleIndex];
        // Break the instructions into arguments to use
        String[] arg = instructionToAdjust.split(" ");
        // If it is a one-argument instructions, inc becomes dec and all others
        // become inc
        if (arg.length == 2) {
            if (arg[0].equals("inc")) {
                arg[0] = "dec";
            } else {
                arg[0] = "inc";
            }
        }
        // If it is a two-argument instruction, jnz becomes cpy and all others
        // become jnz
        else {
            if (arg[0].equals("jnz")) {
                arg[0] = "cpy";
            } else {
                arg[0] = "jnz";
            }
        }
        // All other arguments remain the same
        // Rebuild the string with adjusted values
        StringBuilder newInstruct = new StringBuilder();
        for (String a: arg) {
            newInstruct.append(a);
            newInstruct.append(" ");
        }
        // Swap out the current instruction with the modified one
        instructions[toggleIndex] = newInstruct.toString();
    }

    // Check if the inputted variable is a register
    static boolean isRegister(String x) {
        String[] registers = new String[] {"a", "b", "c", "d"};
        for (String register: registers) {
            if (x.equals(register)) {
                return true;
            }
        }
        return false;
    }

    static boolean isNum(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}