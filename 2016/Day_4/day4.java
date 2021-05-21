import java.io.File;
import java.util.Scanner;

public class day4 {

    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File myFile = new File(path);
        String[] input = new String[980];
        int index = 0;
        try {
            Scanner scanner = new Scanner(myFile);
            while (scanner.hasNextLine()) {
                input[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("ERROR:");
            e.printStackTrace();
            System.exit(1);
        }

        int part1 = 0;
        String part2 = new String();
        for (String line: input) {
            if (isRealRoom(getName(line), getChecksum(line))) {
                part1 += getSectorID(line);
            }
            // Part 2
            String decrypted = new String();
            int shift = getSectorID(line) % 26;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '[') {
                    break; // don't analyze the checksum
                } else if (line.charAt(i) == '-') {
                    decrypted += " "; // '-' are spaces
                    continue;
                } else if ((int) line.charAt(i) < 97) {
                    decrypted += line.charAt(i); // add numbers without shifting to string
                    continue;
                }
                int newLetter = (int) line.charAt(i) + shift;
                if (newLetter > 122) {
                    newLetter -= 26; // must fall within ASCII bounds for lowercase alphabet
                }
                decrypted += (char) newLetter;
            }
            if (decrypted.contains("north")) {
                part2 = decrypted;
            }
        }
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }

    static int[] getName(String input) 
    {
        int[] name = new int[26];
        for (int i = 0; i < input.length(); i++) {
            char val = input.charAt(i);
            if (val == '-') {
                continue;
            } else if ((int) val < (int) 'a') {
                break;
            }
            name[(int) val - (int) 'a'] += 1;
        }
        return name;
    }

    static String getChecksum(String input) 
    {
        return input.substring(input.indexOf('[') + 1, input.indexOf(']') - 1);
    }

    static int getSectorID(String input)
    {
        return Integer.parseInt(input.substring(input.lastIndexOf('-') + 1, input.indexOf('[')));
    }

    static boolean isRealRoom(int[] name, String checksum) 
    {
        for (int i = 0; i < checksum.length(); i++) {
            int max = 0;
            int max_index = 0;
            for (int j = 0; j < name.length; j++) {
                if (name[j] > max) {
                    max = name[j];
                    max_index = j;
                }
            }
            if ((int) checksum.charAt(i) == max_index + 97) {
                name[max_index] = 0;
            } else {
                return false;
            }
        }
        return true;
    }
}
