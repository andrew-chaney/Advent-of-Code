import java.io.File;
import java.util.Scanner;

public class day8 {
    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File file = new File(path);
        String[] fileInput = new String[145];
        try {
            Scanner scanner = new Scanner(file);
            int index = 0;
            while (scanner.hasNextLine()) {
                fileInput[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        int[][] screen = new int[6][50];
        for (String line: fileInput) {
            String[] input = line.split(" ");
            if (input[0].equals("rotate")) {
                int index = Integer.parseInt(input[2].split("=")[1]);
                int amt = Integer.parseInt(input[4]);
                if (input[1].equals("row")) {
                    screen = rotateRow(screen, index, amt);
                } else {
                    screen = rotateCol(screen, index, amt);
                }
            } else {
                String[] nums = input[1].split("x");
                int width = Integer.parseInt(nums[0]);
                int height = Integer.parseInt(nums[1]);
                screen = rect(screen, height, width);
            }
        }
        int part1 = countLit(screen);
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2:");
        displayScreen(screen);
    }

    static int[][] rect(int[][] arr, int height, int width)
    {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                arr[i][j] = 1;
            }
        }
        return arr;
    }

    static int[][] rotateRow(int[][] arr, int index, int amt) 
    {
        int[] tmpRow = new int[50];
        for (int i = 0; i < arr[index].length; i++) {
            int cell = arr[index][i];
            if (i + amt < arr[index].length) {
                tmpRow[i + amt] = cell;
            } else {
                tmpRow[i + amt - arr[index].length] = cell;
            }
        }
        arr[index] = tmpRow;
        return arr;
    }

    static int[][] rotateCol(int[][] arr, int index, int amt) 
    {
        int[] tmpCol = new int[6];
        for (int i = 0; i < arr.length; i++) {
            int cell = arr[i][index];
            if (i + amt < arr.length) {
                tmpCol[i + amt] = cell;
            } else {
                tmpCol[i + amt - arr.length] = cell;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i][index] = tmpCol[i];
        }
        return arr;
    }

    static int countLit(int[][] arr) 
    {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 1) { count++; }
            }
        }
        return count;
    }

    static void displayScreen(int[][] arr) 
    {
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 1) {
                    System.out.print("XXX");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }
}