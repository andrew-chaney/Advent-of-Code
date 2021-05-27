import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Arrays;

public class day13 {
    public static void main(String[] args)
    {
        int puzzleInput = 1364;
        int startX = 1, startY = 1;
        int endX = 31, endY = 39; // 31,39
        int size = 50;
        char[][] map = generateMap(startX, startY, size, puzzleInput);
        int part1 = search(map, startX, startY, endX, endY, false);
        map = generateMap(startX, startY, size, puzzleInput);
        int part2 = search(map, startX, startY, -1, -1, true);
        System.out.println("Part 1: " + part1 + " steps");
        System.out.println("Part 2: " + part2 + " locations");
    }

    static int count(String input, char x) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == x) {
                count++;
            }
        }
        return count;
    }

    static boolean wallOrOpen(int puzzleInput, int x, int y)
    {
        // Wall = false - open = true
        int coordEval = (x * x) + (3 * x) + (2 * x * y) + y + (y * y);
        int sum = coordEval + puzzleInput;
        String binaryRep = Integer.toBinaryString(sum);
        if (count(binaryRep, '1') % 2 == 0) {
            return true;
        }
        return false;
    }

    static char[][] generateMap(int startX, int startY, int size, int puzzleInput)
    {
        char[][] map = new char[size][size];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (wallOrOpen(puzzleInput, x, y)) {
                    map[y][x] = ' ';
                } else {
                    map[y][x] = '#';
                }
            }
        }
        map[startY][startX] = 'o';
        return map;
    }

    static ArrayList<Integer> currentCell(int startX, int startY, String directions) 
    {
        int x = startX, y = startY;
        if (!directions.equals("")) {
            for (char l: directions.toCharArray()) {
                if (l == 'U') {
                    y--;
                } else if (l == 'D') {
                    y++;
                } else if (l == 'L') {
                    x--;
                } else {
                    x++;
                }
            }
        }
        ArrayList<Integer> cell = new ArrayList<Integer>();
        cell.add(x);
        cell.add(y);
        return cell;
    }

    static boolean alreadyVisited(ArrayList<ArrayList<Integer>> visited, int x, int y) 
    {
        ArrayList<Integer> loc = new ArrayList<Integer>();
        loc.add(x);
        loc.add(y);
        if (visited.contains(loc)) {
            return true;
        }
        return false;
    }

    static ArrayList<Character> possibleMoves(char[][] map, ArrayList<ArrayList<Integer>> visited, int x, int y) 
    {
        ArrayList<Character> moves = new ArrayList<Character>();
        if (y - 1 >= 0) {
            if (map[y - 1][x] == ' ' && !alreadyVisited(visited, x, y - 1)) { // check up
                moves.add('U');
            }
        }
        if (y + 1 < map.length) {
            if (map[y + 1][x] == ' ' && !alreadyVisited(visited, x, y + 1)) { // check down
                moves.add('D');
            }
        }
        if (x - 1 >= 0) {
            if (map[y][x - 1] == ' ' && !alreadyVisited(visited, x - 1, y)) { // check left
                moves.add('L');
            }
        }
        if (x + 1 < map[y].length) {
            if (map[y][x + 1] == ' ' && !alreadyVisited(visited, x + 1, y)) { // check right
                moves.add('R');
            }
        }
        return moves;
    }

    static void printPath(char[][] map, int x, int y, String directions) 
    {
        for (char l: directions.toCharArray()) {
            if (l == 'U') {
                y--;
            } else if (l == 'D') {
                y++;
            } else if (l == 'L') {
                x--;
            } else {
                x++;
            }
            map[y][x] = 'X';
        }
        for (char[] m: map) {
            System.out.println(Arrays.toString(m));
        }
    }

    // Find the shorted path with a breadth-first-search (BFS)
    static int search(char[][] map, int startX, int startY, int endX, int endY, boolean part2)
    {
        // Track possible paths
        Queue<String> paths = new LinkedList<String>();
        paths.add("");
        // Track cells we've already visited
        ArrayList<ArrayList<Integer>> visited = new ArrayList<ArrayList<Integer>>();
        
        while (true) {
            String directions = paths.remove();
            ArrayList<Integer> loc = currentCell(startX, startY, directions);
            // Current (x,y) location
            int x = loc.get(0), y = loc.get(1);
            // Add current location to the visited arraylist
            if (!visited.contains(loc)) {
                visited.add(loc);
            }

            if (!part2) {
                // If the current location is the desired endpoint return distance
                if (x == endX && y == endY) {
                    printPath(map, startX, startY, directions);
                    return directions.length();
                }
            } else {
                if (directions.length() == 50) {
                    return visited.size();
                }
            }

            // Possible moves from current location
            ArrayList<Character> moves = possibleMoves(map, visited, x, y);
            for (int i = 0; i < moves.size(); i++) {
                String newDirections = directions + moves.get(i);
                paths.add(newDirections);
            }
        }
    }
}