import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.*;

public class day17 {

    static int best = 100;
    static int worst = 0;
    static String bestPath = "";

    public static void main(String[] args)
    {
        String input = "udskfozm";

        search(input, "");
        System.out.println("Shortest Path: " + bestPath);
        System.out.println("Length of the Worst Path: " + worst);
    }

    static String hash(byte[] input) 
    {
        String output = new String();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = new byte[16];
            hash = digest.digest(input);
            BigInteger no = new BigInteger(1, hash);
            output = no.toString(16); // convert to hex value
            while (output.length() < 32) {
                output = "0" + output;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return output;
    }

    static ArrayList<Integer> getLocation(String path)
    {
        int x = 0, y = 0;
        for (char p: path.toCharArray()) {
            if (p == 'U') {
                y--;
            } else if (p == 'D') {
                y++;
            } else if (p == 'L') {
                x--;
            } else {
                x++;
            }
        }
        ArrayList<Integer> loc = new ArrayList<>();
        loc.add(x);
        loc.add(y);
        return loc;
    }

    static boolean solved(int x, int y)
    {
        if (x == 3 && y == 3) {
            return true;
        }
        return false;
    }

    static boolean isOpen(char door)
    {
        char[] open = { 'b', 'c', 'd', 'e', 'f' };
        for (char o : open) {
            if (o == door) {
                return true;
            }
        }
        return false;
    }

    static boolean isValid(ArrayList<Integer> location, char direction)
    {
        int x = location.get(0), y = location.get(1);
        if (direction == 'U' && y == 0) {
            return false;
        } else if (direction == 'D' && y == 3) {
            return false;
        } else if (direction == 'L' && x == 0) {
            return false;
        } else if (direction == 'R' && x == 3) {
            return false;
        } else {
            return true;
        }
    }

    static char whichDir(int i) 
    {
        if (i == 0) {
            return 'U';
        } else if (i == 1) {
            return 'D';
        } else if (i == 2) {
            return 'L';
        } else {
            return 'R';
        }
    }

    static ArrayList<String> moves(String input, String path, ArrayList<Integer> loc)
    {   
        char[] doors = hash((input + path).getBytes()).substring(0, 4).toCharArray();

        ArrayList<String> moves = new ArrayList<>();
        for (int i = 0; i < doors.length; i++) {
            char direction = whichDir(i);
            if (isOpen(doors[i]) && isValid(loc, direction)) {
                moves.add(path + direction);
            }
        }
        return moves;
    }


    static String search(String input, String path) 
    {   
        ArrayList<Integer> loc = getLocation(path);
        int x = loc.get(0);
        int y = loc.get(1);

        if (solved(x, y)) {
            return path;
        }

        ArrayList<String> possibleMoves = moves(input, path, loc);
        for (int i = 0; i < possibleMoves.size(); i++) {
            String result = search(input, possibleMoves.get(i));
            if (!(result.isEmpty())) {
                if (result.length() > worst) {
                    worst = result.length();
                }
                if (result.length() < best) {
                    bestPath = result;
                    best = result.length();
                }
            }
        }
        return "";
    }
}
