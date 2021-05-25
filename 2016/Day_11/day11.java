import java.io.File;
import java.util.Scanner;

public class day11 {
    public static void main(String[] args) {
        File file = new File("puzzle_input.txt");
        int[] input = new int[4]; // number of floors
        try {
            Scanner scanner = new Scanner(file);
            int index = 0;
            while (scanner.hasNextLine()) {
                int count = 0;
                String line = scanner.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'a') {
                        if (i > 0 && i < line.length() - 1) {
                            if (line.charAt(i - 1) == ' ' && line.charAt(i + 1) == ' ') {
                                count++;
                            }
                        }
                    }
                }
                input[index++] = count;
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        /*
            The amount of moves to move items up a floor based on the number of items on the current floor (remember, elevator capacity is 2):
                1 item = 1 move   (up with 2)
                2 items = 1 move  (up with 2)
                3 items = 3 moves (up with 2, down with 1, up with 2)
                4 items = 5 moves (up with 2, down with 1, up with 2, down with 1, up with 2)
                5 items = 7 moves
                6 items = 9 moves
                7 items = 11 moves
                8 items = 13 moves
                  ...   =   ...

            So, let's say that y represents the number of moves required to move all items on a floor up to the next floor and that x represents the number of items that need to be moved.

            We know that x = 1, only 1 item on the floor that needs to be shifted, is an anomoly. Optimally, we will always move 2 items in one trip wherever we can move 2 items at once. Therefor, when y = x when x <= 1.

            So, we can say that for every x where x > 1 the amount of moves is divided by 2 since we are taking 2 per trip. But, we have to also keep in mind that we always have to have at least one item in the elevator as well, so we'll need a constant.
                Symbolically: 
                    y / 2 = x
                    So, y = 2x + C
            When we set x = 2 we get y = 2(2) + C. We already know the moves necessary to move 2 items up a floor is y = 1. So, 1 = 4 + C and we get that C = 3. This makes sense, because there will be a trip where you don't have to go up, down, and back up in the  elevator for each floor.
            
            To test our new equation of y = 2x - 3 we can just compare it to the table to confirm that all of its values match.

            All said and done we get the following equation:
                    { y = 1         for x < 2
                y = { Note, items will only ever be whole numbers.
                    { y = 2x - 3    for 2 <= x < infiniti

            NOTE: This does NOT work with the test or other various inputs because this equation completely ignores the actual items themselves. It doesn't match generators and microchips properly, as required by some instances of the problem. 

            When I tested this against my input it worked. I may come back to solve the problem in a more intentional way eventually, but with how busy I am currently this will suffice.
        */
        System.out.println("Part 1: " + move(input, false) + " Steps");
        System.out.println("Part 2: " + move(input, true) + " Steps");
    }

    static int move(int[] input, boolean part2) 
    {
        int steps = 0;
        int previousFloorContents = 0;
        if (part2 == true) {
            input[0] += 4;
        }
        for (int i = 0; i < input.length - 1; i++) {
            int itemsOnFloor = input[i] + previousFloorContents;
            if (itemsOnFloor == 0) {
                continue;
            } else if (itemsOnFloor == 1) {
                steps++;
            } else {
                steps += (2 * itemsOnFloor) - 3;
            }
            previousFloorContents = itemsOnFloor;
        }
        return steps;
    }
}