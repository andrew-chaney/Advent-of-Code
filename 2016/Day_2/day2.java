import java.io.File;
import java.util.Scanner;

public class day2 {

    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File myFile = new File(path);
        String[] instructions = new String[5];
        int index = 0;
        try {
            Scanner scanner = new Scanner(myFile);
            while (scanner.hasNextLine()) {
                instructions[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
            System.exit(1);
        }

        int button1 = 5;
        String password1 = new String();
        char button2 = '5';
        String password2 = new String();
        for (int i = 0; i < instructions.length; i++) {
            button1 = solutionPart1(instructions[i], button1);
            password1 +=  String.valueOf(button1);
            button2 = solutionPart2(instructions[i], button2);
            password2 += String.valueOf(button2);
        }
        System.out.println("Part 1: " + password1);
        System.out.println("Part 2: " + password2);
    }
    
    static int solutionPart1(String instructions, int button) 
    {
        for (int i = 0 ; i < instructions.length(); i++) {
            char current = instructions.charAt(i);
            if (current == 'U') {
                if (button > 3) {
                    button -= 3;
                }
            } else if (current == 'D') {
                if (button < 7) {
                    button += 3;
                }
            } else if (current == 'L') {
                if (button != 1 && button != 4 && button != 7) {
                    button--;
                }
            } else {    // 'R'
                if (button != 3 && button != 6 && button != 9) {
                    button++;
                }
            }
        }
        return button;
    }

    static int[] findButtonIndex(char[][] keypad, char button) 
    {
        for (int i = 0; i < keypad.length; i++) {
            for (int j = 0; j < keypad[i].length; j++) {
                if (keypad[i][j] == button) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    static char solutionPart2(String instructions, char button) 
    {
        char[][] keypad = new char[][] {
            {' ', ' ', '1', ' ', ' '},
            {' ', '2', '3', '4', ' '},
            {'5', '6', '7', '8', '9'},
            {' ', 'A', 'B', 'C', ' '},
            {' ', ' ', 'D', ' ', ' '}  };
        int[] index = findButtonIndex(keypad, button);
        if (index == null) {
            System.out.println("ERROR: null pointer");
            System.exit(1);
        }
        int row = index[0], col = index[1];
        for (int i = 0; i < instructions.length(); i++) {
            char current = instructions.charAt(i);
            if (current == 'U') {
                if (row - 1 >= 0 && keypad[row - 1][col] != ' ') {
                    row--;
                }
            } else if (current == 'D') {
                if (row + 1 < keypad.length && keypad[row + 1][col] != ' ') {
                    row++;
                }
            } else if (current == 'R') {
                if (col + 1 < keypad[row].length && keypad[row][col + 1] != ' ') {
                    col++;
                }
            } else {    // 'L' 
                if (col - 1 >= 0 && keypad[row][col - 1] != ' ') {
                    col--;
                }
            }
        }
        return keypad[row][col];
    }
}
