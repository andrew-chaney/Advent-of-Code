public class day15 {
    public static void main(String[] args)
    {
        int[][] puzzleInput = { // hard-coded for time
            {0, 1, 2, 3, 4, 5, 6},
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12},
            {2, 0, 1},
            {2, 3, 4, 0, 1},
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16},
            {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 0, 1, 2, 3, 4, 5, 6},
            // For part 2 only
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
        };

        System.out.println("Part 1: " + dropTime(puzzleInput, false));
        System.out.println("Part 2: " + dropTime(puzzleInput, true));
    }

    static boolean isOpen(int[] disk, int time)
    {
        if (time >= disk.length) {
            time = time % disk.length;
        }
        if (disk[time] == 0) {
            return true;
        }
        return false;
    }

    static int dropTime(int[][] disks, boolean part2)
    {
        int numDisks = disks.length;
        if (!part2) {
            numDisks--;
        }
        int time = 0;
        while (true) {
            boolean allOpen = true;
            for (int i = 0; i < numDisks; i++) {
                if (!isOpen(disks[i], time + 1 + i)) {
                    allOpen = false;
                }
            }
            if (allOpen) {
                return time;
            }
            time++;
        }
    }
}