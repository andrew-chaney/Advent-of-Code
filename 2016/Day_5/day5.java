import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class day5 {
    public static void main(String[] args) 
    {
        String doorID = "abbhdwsy";
        //String testID = "abc";

        // Tests
        //assertion_test(part1(testID), "18f47a30", 1);
        //assertion_test(part2(testID), "05ace8e3", 2);

        System.out.println("Part 1: " + part1(doorID));
        System.out.println("Part 2: " + part2(doorID));
    }

    static String hash(String doorID, int index) 
    {
        String output = new String();
        String str_index = String.valueOf(index);
        try {
            byte[] input = (doorID + str_index).getBytes("UTF-8");
            MessageDigest hasher = MessageDigest.getInstance("MD5");
            byte[] hash = hasher.digest(input);
            BigInteger no = new BigInteger(1, hash);
            output = no.toString(16); // convert to hex value
            while (output.length() < 32) {
                output = "0" + output;
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return output;
    }

    static String part1(String doorID) 
    {
        int index = 0;
        String password = new String();
        while (password.length() < 8) {
            String h = hash(doorID, index);
            if (h.startsWith("00000")) {
                password += h.charAt(5);
            }
            index++;
        }
        return password;
    }

    static boolean contains(int[] arr, int element)
    {
        for (int v: arr) {
            if (v == element) {
                return true;
            }
        }
        return false;
    }

    static String part2(String doorID)
    {
        int index = 0;
        char[] password = new char[8];
        int[] tracker = new int[8];
        while (true) {
            String hash = hash(doorID, index);
            if (hash.startsWith("00000")) {
                if ((int) hash.charAt(5) > 47 && (int) hash.charAt(5) < 56) {
                    int hash_index = Character.getNumericValue(hash.charAt(5));
                    if (tracker[hash_index] == 0) {
                        password[hash_index] = hash.charAt(6);
                        tracker[hash_index] += 1;
                    }
                }
            }
            if (contains(tracker, 0) == false) {
                break;
            } else {
                index++;
            }
        }
        String result = new String();
        for (char p: password) {
            result += p;
        }
        return result;
    }

    static void assertion_test(String result, String expected, int part) 
    {
        if (result != expected) {
            System.out.println("ERROR");
            System.out.println("Result: " + result);
            System.out.println("Expected: " + expected);
            System.out.println("Part: " + part);
            System.exit(1);
        }
    }
}
