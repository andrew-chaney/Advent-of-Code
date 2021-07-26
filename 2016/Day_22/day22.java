import java.util.*;
import java.util.regex.*;
import java.io.File;

class Coord {
    int x;      // x coordinate
    int y;      // y coordinate

    public Coord(int inX, int inY) {
        x = inX;
        y = inY;
    }

    public boolean equals(Coord c) {
        if (this.x == c.x && this.y == c.y) {
            return true;
        }
        return false;
    }
}

class Node {
    Coord loc;      // (x,y) location
    int size;       // Disk size in T
    int used;       // Amt. of disk space used
    int avail;      // Amt. of disk space available

    public Node(int x, int y, int inSize, int inUsed, int inAvail) {
        loc = new Coord(x, y);
        size = inSize;
        used = inUsed;
        avail = inAvail;
    }
}

public class day22 {

    public static void main(String[] args) {
        String filename = "puzzle_input.txt";
        File file = new File(filename);
        Node[][] nodes = new Node[26][38];
        try {
            Scanner scan = new Scanner(file);
            // Skip over the first two lines of the file.
            scan.nextLine();
            scan.nextLine();
            while (scan.hasNextLine()) {
                ArrayList<Integer> input = parseLine(scan.nextLine());
                int x = input.get(0);
                int y = input.get(1);
                int size = input.get(2);
                int used = input.get(3);
                int avail = input.get(4);
                nodes[y][x] = new Node(x, y, size, used, avail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        int part1 = viablePairs(nodes);
        System.out.println(part1);
        // Printed grid to understand the layout, and then solved part 2 by hand.
        printGrid(nodes);
    }

    static ArrayList<Integer> parseLine(String line) {
        Pattern r = Pattern.compile("\\d+");
        Matcher m = r.matcher(line);
        ArrayList<Integer> output = new ArrayList<>();
        while (m.find()) {
            output.add(Integer.parseInt(m.group()));
        }
        return output;
    }

    static int viablePairs(Node[][] nodes) {
        int pairCount = 0;
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                Node a = nodes[i][j];
                if (a.used == 0) {
                    continue;
                }
                for (int x = 0; x < nodes.length; x++) {
                    for (int y = 0; y < nodes[x].length; y++) {
                        if (x != i || y != j) {
                            Node b = nodes[x][y];
                            if (a.used <= b.avail) {
                                pairCount++;
                            }
                        }
                    }
                }
            }
        }
        return pairCount;
    }

    static void printGrid(Node[][] nodes) {
        for (int y = 0; y < nodes.length; y++) {
            for (int x = 0; x < nodes[y].length; x++) {
                String output = "";
                if (x == 0 && y == 0) {
                    output = "(.)";
                } else if (x == nodes[y].length - 1 && y == 0) {
                    output = "G";
                } else if (nodes[y][x].used == 0) {
                    output = "_";
                } else if (nodes[y][x].used > 99) {
                    output = "#";
                } else {
                    output = ".";
                }
                System.out.printf("%3s", output);
            }
            System.out.println();
        }
    }
}