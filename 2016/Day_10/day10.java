import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class day10 {
    public static void main(String[] args) 
    {
        String path = "puzzle_input.txt";
        File file = new File(path);
        String[] lines = new String[231];
        try {
            Scanner scanner = new Scanner(file);
            int index = 0;
            while (scanner.hasNextLine()) {
                lines[index++] = scanner.nextLine();
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        Bot[] bots = new Bot[210]; // got size by: grep -o "bot \d\d\d" puzzle_input.txt | sort -u
        for (int i = 0; i < bots.length; i++) {
            bots[i] = new Bot();
        }

        int chipTotal = 0;
        for (String line: lines) {
            String[] input = line.split(" ");
            if (input[0].equals("value")) {
                int chip = Integer.parseInt(input[1]);
                chipTotal++;
                int bot = Integer.parseInt(input[5]);
                bots[bot].gainChip(chip);
            } else {
                int bot = Integer.parseInt(input[1]);
                String[][] commands = new String[][]{
                    {input[5], input[6]},
                    {input[10], input[11]}
                };
                bots[bot].loadCmds(commands);
            }
        }
        int[] results = distribute(bots, chipTotal, 61, 17);
        System.out.println("Part 1: Bot #" + results[0]);
        System.out.println("Part 2: " + results[1]);
    }

    static int min(int x, int y)
    {
        if (x < y) {
            return x;
        }
        return y;
    }

    static int max(int x, int y)
    {
        if (x > y) {
            return x;
        }
        return y;
    }

    static int chipsInBins(ArrayList<ArrayList<Integer>> output)
    {
        int total = 0;
        for (int i = 0; i < output.size(); i++) {
            total += output.get(i).size();
        }
        return total;
    }

    static int[] distribute(Bot[] bots, int chipTotal, int targetA, int targetB) 
    {
        int size = 21; // got size by: grep -o "output \d*" puzzle_input.txt | sort -u
        ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>(size);
        for (int i = 0; i < size; i++) {
            output.add(new ArrayList<Integer>());
        }
        int targetBot = 0;

        while (chipsInBins(output) < chipTotal) {
            for (int i = 0; i < bots.length; i++) {
                if (bots[i].numChips() == 2) {
                    int[] chips = bots[i].getChips();
                    if ((chips[0] == targetA && chips[1] == targetB) || (chips[1] == targetA && chips[0] == targetB)) {
                        targetBot = i;
                    }
                    String[][] commands = bots[i].getCmds();           
                    for (int j = 0; j < 2; j++) {
                        int chip;
                        if (j == 0) {
                            chip = min(chips[0], chips[1]);
                        } else {
                            chip = max(chips[0], chips[1]);
                        }
                        int num = Integer.parseInt(commands[j][1]);
                        if (!commands[j][0].equals("output")) {
                            bots[num].gainChip(chip);
                        } else {
                            output.get(num).add(chip);
                        }
                    }
                    bots[i].loseChips();
                }
            }
        }
        int product = 1;
        for (int i = 0; i < 3; i++) {
            product *= output.get(i).get(0);
        }
        return new int[] { targetBot, product };
    }
}
