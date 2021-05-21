import java.io.File;
import java.util.Scanner;

public class day6 {
    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File myFile = new File(path);
        String[] input = new String[624];
        try {
            int index = 0;
            Scanner scanner = new Scanner(myFile);
            while (scanner.hasNextLine()) {
                input[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Part 1: " + errorCorrect(input, false));
        System.out.println("Part 2: " + errorCorrect(input, true));
    }

    static int maxIndex(int[] arr)
    {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[max]) {
                max = i;
            }
        }
        return max;
    }

    static int minIndex(int[] arr) 
    {
        int min = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < arr[min]) {
                min = i;
            }
        }
        return min;
    }

    static String errorCorrect(String[] input, boolean part2)
    {
        String message = new String();
        for (int i = 0; i < input[0].length(); i++) {
            int[] counter = new int[26];
            for (int j = 0; j < input.length; j++) {
                counter[(int) input[j].charAt(i) - 97] += 1;
            }
            if (part2 == false) {
                message += (char) (maxIndex(counter) + 97);
            } else {
                message += (char) (minIndex(counter) + 97);
            }
        }
        return message;
    }
}
