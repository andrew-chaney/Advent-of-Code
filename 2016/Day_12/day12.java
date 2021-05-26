import java.io.File;
import java.util.*;//Scanner;

public class day12 {
    public static void main(String[] args) 
    {
        File file = new File("puzzle_input.txt");
        String[] input = new String[23]; // # of lines in puzzle file
        try {
            Scanner scanner = new Scanner(file);
            int index = 0;
            while ( scanner.hasNextLine() ) {
                input[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        int[] registers = new int[4]; // registers: {a=0,b=1,c=2,d=3}
        // Uncomment to solve for part 2.
        //registers[2] = 1;

        int index = 0;
        while (index < input.length) {
            String[] instructs = input[index].split(" ");
            if (instructs[0].equals("cpy")) { // copy
                int value;
                int register = (int) instructs[2].charAt(0) - 97;
                if (isRegister(instructs[1])) {
                    value = registers[(int) instructs[1].charAt(0) - 97];
                } else {
                    value = Integer.parseInt(instructs[1]);
                }
                registers[register] = value;
            } else if (instructs[0].equals("jnz")) { // jump
                if (isRegister(instructs[1])) {
                    if (registers[(int) instructs[1].charAt(0) - 97] > 0) {
                        index += Integer.parseInt(instructs[2]);
                        continue;
                    }
                } else if (Integer.parseInt(instructs[1]) > 0) {
                    index += Integer.parseInt(instructs[2]);
                    continue;
                }
            } else { // increment / decrement
                int register = (int) instructs[1].charAt(0) - 97;
                if (instructs[0].equals("inc")) {
                    registers[register]++;
                } else {
                    registers[register]--;
                }
            }
            index++;
        }
        System.out.println("Register A = " + registers[0]);
    }

    static boolean isRegister(String x)
    {
        String[] registers = new String[] {"a", "b", "c", "d"};
        for (String register: registers) {
            if (x.equals(register)) {
                return true;
            }
        }
        return false;
    }
}