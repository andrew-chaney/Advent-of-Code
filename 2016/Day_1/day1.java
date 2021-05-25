import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class day1 {
    public static void main (String[] args) {
        String path = "puzzle_input.txt";
        File myFile = new File(path);
        ArrayList<String> directions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(myFile);
            scanner.useDelimiter(", ");
            while (scanner.hasNext()) {
                directions.add(scanner.next());
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
            System.exit(0);
        }

        System.out.println("Part 1: " + part1(directions));
        System.out.println("Part 2: " + part2(directions));
    }

    static char getDirection(char facing, char turning) {
        char[] compass = new char[]{'W', 'N', 'E', 'S'};
        int facing_index = 0;
        for (int i = 0; i < compass.length; i++) {
            if (facing == compass[i]) {
                facing_index = i;
            }
        }
        if (turning == 'L') {
            if (facing_index - 1 >= 0) {
               return compass[facing_index - 1];
            } else {
                return compass[compass.length - 1];
            }
        } else {
            if (facing_index + 1 < compass.length) {
                return compass[facing_index + 1];
            } else {
                return compass[0];
            }
        }
    }

    static int blocksAway(int x, int y) {
        return Math.abs(x) + Math.abs(y);
    }

    static int part1(ArrayList<String> directions) {
        Coordinates hq_location = new Coordinates(0, 0);
        char facing = 'N';
        for (String direc: directions) {
            char turn = direc.charAt(0);
            int distance = Integer.parseInt(direc.substring(1));
            facing = getDirection(facing, turn);
            if (facing == 'N') {
                hq_location.incY(distance);
            } else if (facing == 'S') {
                hq_location.decY(distance);
            } else if (facing == 'E') {
                hq_location.incX(distance);
            } else {
                hq_location.decX(distance);
            }
        }   
        return blocksAway(hq_location.getX(), hq_location.getY());
    }

    static int part2(ArrayList<String> directions) {
        Coordinates loc = new Coordinates(0,0);
        char facing = 'N';
        ArrayList<ArrayList<Integer>> visited = new ArrayList<ArrayList<Integer>>();
        for (String direc: directions) {
            char turn = direc.charAt(0);
            int distance = Integer.parseInt(direc.substring(1));
            facing = getDirection(facing, turn);
            for (int i = 0; i < distance; i++) {
                if (facing == 'N') {
                    loc.incY(1);
                } else if (facing == 'S') {
                    loc.decY(1);
                } else if (facing == 'E') {
                    loc.incX(1);
                } else {
                    loc.decX(1);
                }
                ArrayList<Integer> current = new ArrayList<>();
                current.add(loc.getX());
                current.add(loc.getY());
                if (visited.contains(current)) {
                    return blocksAway(loc.getX(), loc.getY());
                } else {
                    visited.add(current);
                }
            }
        }
        return 0;
    }
}
