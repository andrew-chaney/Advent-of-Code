import java.io.File;
import java.util.*;

public class day21 {
    public static void main(String[] args) {
        String path = "puzzle_input.txt";
        File file = new File(path);
        ArrayList<String> instructions = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                instructions.add(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        String toScramble = "abcdefgh";
        System.out.println("Part 1: " + scramble(toScramble, instructions));
        String toUnscramble = "fbgdceah";
        System.out.println("Part 2: " + unscramble(toUnscramble, instructions));
    }

    static String swapPosition(String input, int x, int y) {
        char[] word = input.toCharArray();
        char tmp = word[x];
        word[x] = word[y];
        word[y] = tmp;
        return String.valueOf(word);
    }

    static String swapLetter(String input, char x, char y) {
        input = input.replace(x, '0');
        input = input.replace(y, x);
        input = input.replace('0', y);
        return input;
    } 
    
    static String rotateLeft(String input, int amt) {
        if (amt >= input.length()) {
            amt = amt % input.length();
        }
        StringBuilder output = new StringBuilder();
        output.append(input.substring(amt));
        output.append(input.substring(0, amt));
        return output.toString();
    }

    static String rotateRight(String input, int amt) {
        return rotateLeft(input, input.length() - amt);
    }

    static String rotatePosition(String input, char position, boolean p2) {
        int amt = input.indexOf(position);
        if (!p2) {
            if (amt >= 4) {
                amt++;
            }
            input = rotateRight(input, 1);
            input = rotateRight(input, amt);
        } else {
            // Drew out all shifts depending on letter positions and found the following: 
            if (amt == 0 || amt == 1) {
                input = rotateLeft(input, 1);
            } else if (amt == 2) {
                input = rotateRight(input, 2);
            } else if (amt == 3) {
                input = rotateLeft(input, 2);
            } else if (amt == 4) {
                input = rotateRight(input, 1);
            } else if (amt == 5) {
                input = rotateLeft(input, 3);
            } else if (amt == 7) {
                input = rotateRight(input, 4);
            }
        }
        return input;
    }

    static String reverse(String input, int x, int y) {
        String rev = new StringBuilder(input.substring(x, y + 1)).reverse().toString();
        StringBuilder output = new StringBuilder();
        output.append(input.substring(0, x));
        output.append(rev);
        output.append(input.substring(y + 1));
        return output.toString();
    }

    static String move(String input, int x, int y) {
        char target = input.charAt(x);
        StringBuilder output = new StringBuilder();
        output.append(input.substring(0, x));
        output.append(input.substring(x + 1));
        output.insert(y, target);
        return output.toString();
    }

    static String parseInstructions(String line, String input, boolean p2) {
        String[] instructs = line.split(" ");
        // Look for swap
        if (instructs[0].equals("swap")) {
            // Look for swap position
            if (instructs[1].equals("position")) {
                int x = Integer.parseInt(instructs[2]);
                int y = Integer.parseInt(instructs[5]);
                return swapPosition(input, x, y);
            }
            // Otherwise, swap by letter
            else {
                char x = instructs[2].charAt(0);
                char y = instructs[5].charAt(0);
                return swapLetter(input, x, y);
            }
        }
        // Look for rotate
        else if (instructs[0].equals("rotate")) {
            // Loof for rotate left
            if (instructs[1].equals("left")) {
                int amt = Integer.parseInt(instructs[2]);
                if (p2) {
                    return rotateRight(input, amt);
                }
                return rotateLeft(input, amt);
            }
            // Look for rotate right
            else if (instructs[1].equals("right")) {
                int amt = Integer.parseInt(instructs[2]);
                if (p2) {
                    return rotateLeft(input, amt);
                }
                return rotateRight(input, amt);
            }
            // Otherwise, rotate based on position of letter
            else {
                return rotatePosition(input, instructs[6].charAt(0), p2);
            }
        }
        // Look for reverse
        else if (instructs[0].equals("reverse")) {
            int x = Integer.parseInt(instructs[2]);
            int y = Integer.parseInt(instructs[4]);
            return reverse(input, x, y);
        }
        // Otherwise, move.
        else {
            int x = Integer.parseInt(instructs[2]);
            int y = Integer.parseInt(instructs[5]);
            if (p2) {
                return move(input, y, x);
            }
            return move(input, x, y);
        }
    }

    static String scramble(String input, ArrayList<String> instructions) {
        for (String line: instructions) {
            input = parseInstructions(line, input, false);
        }
        return input;
    }

    static String unscramble(String input, ArrayList<String> instructions) {
        // Do the scrambling process backwards.
        for (int i = instructions.size() - 1; i >= 0; i--) {
            String line = instructions.get(i);
            input = parseInstructions(line, input, true);
        }
        return input;
    }
}