import java.io.File;
import java.util.Scanner;

public class day3 {

    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File myFile = new File(path);
        // Sort the file into triangles by line
        int[][] triangles = new int[1902][3];
        try {
            Scanner scanner = new Scanner(myFile);
            int index = 0;
            while (scanner.hasNextLine()) {
                for (int i = 0; i < 3; i++) {
                    triangles[index][i] = Integer.parseInt(scanner.next());
                }
                index++;
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Part 1: " + part1(triangles));
        System.out.println("Part 2: " + part2(triangles));
    }   

    static boolean isValid(int[] v) 
    {
        if ((v[0] + v[1] > v[2]) && (v[0] + v[2] > v[1]) && (v[1] + v[2] > v[0])) {
            return true;
        }
        return false;
    }

    static int part1(int[][] triangles) 
    {
        int count = 0;
        for (int[] t: triangles) {
            if (isValid(t)) {
                count++;
            }
        }
        return count;
    }

    static int part2(int[][] triangles)
    {
        int count = 0;
        for (int i = 0; i < triangles.length; i += 3) {
            for (int j = 0; j < 3; j++) {
                int[] tri = new int[]{triangles[i][j], triangles[i+1][j], triangles[i+2][j]};
                if (isValid(tri)) {
                    count++;
                }
            }
        }
        return count;
    }
}
