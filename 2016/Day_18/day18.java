import java.io.File;
import java.util.*;

public class day18 {

    static ArrayList<ArrayList<Character>> map = new ArrayList<ArrayList<Character>>();

    public static void main(String[] args)
    {
        String path = "puzzle_input.txt";
        File file = new File(path);
        map.add(new ArrayList<Character>());

        try {
            Scanner scanner = new Scanner(file);
            int lineTracker = 0;
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                for (char c: nextLine.toCharArray()) {
                    map.get(lineTracker).add(c);
                }
                lineTracker++;
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        buildMap(40);
        System.out.println("Part 1: " + countSafeTiles());
        buildMap(400_000);
        System.out.println("Part 2: " + countSafeTiles());
    }

    // '.' = safe && '^' = trap
    static boolean isTrap(char left, char center, char right) 
    {
        if (left == '^' && center == '^' && right == '.') {
            return true;
        } else if (left == '.' && center == '^' && right == '^') {
            return true;
        } else if (left == '^' && center == '.' && right == '.') {
            return true;
        } else if (left == '.' && center == '.' && right == '^') {
            return true;
        } else {
            return false;
        }
    }

    static void buildMap(int numRows) 
    {
        for (int i = map.size(); i < numRows; i++) {
            map.add(new ArrayList<Character>());
            for (int col = 0; col < map.get(i - 1).size(); col++) {
                boolean trap;
                if (col == 0) {
                    trap = isTrap('.', map.get(i - 1).get(col), map.get(i - 1).get(col + 1));
                } else if (col == map.get(i - 1).size() - 1) {
                    trap = isTrap(map.get(i - 1).get(col - 1), map.get(i - 1).get(col), '.');
                } else {
                    trap = isTrap(map.get(i - 1).get(col - 1), map.get(i - 1).get(col), map.get(i - 1).get(col + 1));
                }
                if (!trap) {
                    map.get(i).add('.');
                } else {
                    map.get(i).add('^');
                }
            }
        }
    }

    static int countSafeTiles() 
    {
        int safeCount = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j) == '.') {
                    safeCount++;
                }
            }
        }
        return safeCount;
    }
}