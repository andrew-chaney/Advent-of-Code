import java.io.File;
import java.util.Scanner;

public class day9 {
    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File file = new File(path);
        String input = "";
        try {
            Scanner scanner = new Scanner(file);
            input = scanner.next();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Part 1: " + decompressedLength(input, false));
        System.out.println("Part 2: " + decompressedLength(input, true));
    }

    static long decompressedLength(String input, boolean part2) 
    {
        int index1 = input.indexOf("(");
        int index2 = input.indexOf(")");
        if (index1 == -1 || index2 == -1) {
            return input.length();
        }
        String marker = input.substring(index1 + 1, index2);
        String[] nums = marker.split("x");
        int sliceLength = Integer.parseInt(nums[0]);
        int repetitions = Integer.parseInt(nums[1]);
        long size;
        if (part2 == false) {
            size = sliceLength;
        } else {
            size = decompressedLength(input.substring(index2 + 1, index2 + 1 + sliceLength), part2);
        }
        return input.substring(0, index1).length() + (size * repetitions) + decompressedLength(input.substring(index2 + 1 + sliceLength), part2);
    }
}