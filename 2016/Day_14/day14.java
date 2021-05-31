import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

public class day14 {
    public static void main(String[] args) 
    {
        String salt = "zpqevtbw";
        String testSalt = "abc";
        long start = System.currentTimeMillis();
        System.out.println("Part 1: " + generateKeys(salt, false));
        System.out.println("Time: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        System.out.println("Part 2: " + generateKeys(salt, true));
        System.out.println("Time: " + (System.currentTimeMillis() - start));
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

    static String repeatChar(char x, int amt) 
    {
        String output = "";
        for (int i = 0; i < amt; i++) {
            output += x;
        }
        return output;
    }

    static char containsTriple(String input) 
    {
        char[] in = input.toCharArray();
        for (int i = 0; i < input.length() - 2; i++) {
            if (in[i] == in [i + 1] && in[i] == in[i + 2]) {
                return in[i];
            }
        }
        return ' ';
    }

    static char containsQuintuple(String input) 
    {
        for (char i: input.toCharArray()) {
            if (input.contains(repeatChar(i, 5))) {
                return i;
            }
        }
        return ' ';
    }

    static int generateKeys(String salt, boolean part2) 
    {
        int amt = 1;
        if (part2) {
            amt = 2017;
        }

        ArrayList<ArrayList<String>> triples = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> quintuples = new ArrayList<ArrayList<String>>();

        int index = 0;
        while (index < 50000) {
            String current = salt + String.valueOf(index);
            for (int i = 0; i < amt; i++) {
                current = hash(current.getBytes());
            }
            char triple = containsTriple(current);
            if (triple != ' ') {
                ArrayList<String> pair = new ArrayList<>();
                pair.add(String.valueOf(triple));
                pair.add(String.valueOf(index));
                triples.add(pair);
                // Won't contain a quintuple if it doesn't have a triple
                char quintuple = containsQuintuple(current);
                if (quintuple != ' ') {
                    pair.set(0, String.valueOf(quintuple));
                    quintuples.add(pair);
                }
            }
            index++;
        }
        
        ArrayList<Integer> keyIndexes = new ArrayList<>();
        for (int i = 0; keyIndexes.size() < 64; i++) {
            ArrayList<String> current = triples.get(i);
            String key = current.get(0);
            int firstIndex = Integer.parseInt(current.get(1));
            for (int j = 0; j < quintuples.size(); j++) {
                String match = quintuples.get(j).get(0);
                if (match.equals(key)) {
                    int lastIndex = Integer.parseInt(quintuples.get(j).get(1));
                    if (lastIndex > firstIndex && lastIndex - firstIndex <= 1000) {
                        keyIndexes.add(firstIndex);
                        break;
                    }
                }
            }
        }
        return keyIndexes.get(keyIndexes.size() - 1);
    }
}