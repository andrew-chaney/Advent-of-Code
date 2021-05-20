import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class day7 {
    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File file = new File(path);
        String[] input = new String[2000];
        try {
            int index = 0;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                input[index++] = scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        int part1 = 0;
        int part2 = 0;
        for (String line: input) {
            if (supportsTLS(line)) {
                part1++;
            }
            if (supportsSSL(line)) {
                part2++;
            }
        }
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }

    static int count(String input, char match) 
    {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == match) {
                count++;
            }
        }
        return count;
    }

    static String[] parseLine(String line)
    {
        Pattern r = Pattern.compile("\\w+|\\[\\w+\\]");
        Matcher m = r.matcher(line);
        String[] output = new String[count(line, '[') * 2 + 1];
        int index = 0;
        while (m.find()) {
            output[index++] = m.group();
        }
        return output;
    }

    static boolean validABBA(String v) 
    {
        char a = v.charAt(0), b = v.charAt(1), c = v.charAt(2), d = v.charAt(3);
        if (a == d && b == c && a != b) {
            return true;
        }
        return false;
    }

    static boolean validABA(String v) 
    {
        char a = v.charAt(0), b = v.charAt(1), c = v.charAt(2);
        if (a == c && a != b) {
            return true;
        }
        return false;
    }

    static boolean supportsTLS(String line) 
    {
        String[] input = parseLine(line);
        boolean flag = false;
        boolean bracket_flag = false;
        for (String val: input) {
            for (int i = 0; i < val.length(); i++) {
                if (i + 4 <= val.length()) {
                    if (validABBA(val.substring(i, i + 4))) {
                        if (val.charAt(0) != '[') {
                            flag = true;
                        } else {
                            bracket_flag = true;
                        }
                    }
                }
            }
        }
        if (bracket_flag != true) {
            return flag;
        } 
        return false;
    }

    static boolean supportsSSL(String line)
    {
        ArrayList<String> aba = new ArrayList<String>();
        ArrayList<String> bab = new ArrayList<String>();
        String[] input = parseLine(line);
        for (String val: input) {
            for (int i = 0; i < val.length(); i++) {
                if (i + 3 <= val.length()) {
                    if (validABA(val.substring(i, i + 3))) {
                        if (val.charAt(0) != '[') {
                            aba.add(val.substring(i, i + 3));
                        } else {
                            bab.add(val.substring(i, i + 3));
                        }
                    }
                }
            }
        }
        for (String i: aba) {
            for (String j: bab) {
                if (i.substring(1).equals(j.substring(0,2))) {
                    return true;
                }
            }
        }
        return false;
    }

}
