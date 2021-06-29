import java.io.File;
import java.util.*;

class SortedPair {
    static SortedPair head, tail;
    long upper;
    long lower;
    SortedPair next;

    public void add(long l, long u) {
        // If the list is empty, start it with input.
        if (head == null) {
            head = tail = new SortedPair();
            head.lower = l;
            head.upper = u;
            return;
        }

        // Create a new node for the inputs.
        SortedPair node = new SortedPair();
        node.lower = l;
        node.upper = u;

        // Check to see if the new node belongs at the head of the list.
        if (head.lower > l) {
            node.next = head;
            head = node;
        }
        // Check to see if the node belongs at the end of the list.
        else if (tail.lower < l) {
            tail.next = node;
            tail = node;
        }
        // Otherwise, find where in the link the node belongs and adjust surrounding node pointers.
        else {
            SortedPair pointer, prevPointer;
            pointer = prevPointer = head;
            while ( (pointer != null) && (pointer.lower < l) ) {
                prevPointer = pointer;
                pointer = pointer.next;
            }
            prevPointer.next = node;
            node.next = pointer;
        }
    }

    public LinkedList<Long[]> gather()
    {
        SortedPair pointer = head;
        LinkedList<Long[]> bounds = new LinkedList<>();
        while (pointer != null) {
            Long[] nodeBounds = {pointer.lower, pointer.upper};
            bounds.add(nodeBounds);
            pointer = pointer.next;
        }
        return bounds;
    }
}

public class day20 {
    public static void main(String[] args) {
        String path = "puzzle_input.txt";
        File file = new File(path);
        SortedPair pairs = new SortedPair();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] nums = line.split("-");
                Long[] bounds = {Long.parseLong(nums[0]), Long.parseLong(nums[1])};
                pairs.add(bounds[0], bounds[1]);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }

        LinkedList<Long[]> blockedIPs = pairs.gather(); 
        System.out.println("Part 1: " + solve(blockedIPs, false));
        System.out.println("Part 2: " + solve(blockedIPs, true));
    }

    static long solve(LinkedList<Long[]> blockedIPs, boolean part2) {
        long allowed = 0;
        long ip = 0;
        long range = 4294967295L;
        
        while (ip < range) {
            boolean flag = true;
            for (Long[] bounds : blockedIPs) {
                long lower = bounds[0];
                long upper = bounds[1];
                if ((ip >= lower) && (ip <= upper)) {
                    ip = upper;
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                if (!part2) {
                    return ip;
                }
                allowed++;
            }
            ip++;
        }
        return allowed;
    }
}