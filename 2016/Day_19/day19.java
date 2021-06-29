class ElfNode {
    int id;
    ElfNode next;
    ElfNode prev;

    public ElfNode remove() {
        this.next.prev = this.prev;
        this.prev.next = this.next;
        return this.next;
    }
}

public class day19 {
    public static void main(String[] args) {
        int input = 3014387;
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }

    static ElfNode createCircle(int numElves) {
        ElfNode head, tail;
        head = tail = new ElfNode();
        head.id = 1;
        for (int i = 2; i <= numElves; i++) {
            ElfNode node = new ElfNode();
            node.id = i;
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        tail.next = head;
        head.prev = tail;
        return head;
    }

    static int part1(int numElves) {
        ElfNode elf = createCircle(numElves);
        while (elf.next != elf) {
            elf.next.remove();
            elf = elf.next;
        }
        return elf.id;
    }

    static int part2(int numElves) {
        ElfNode elf = createCircle(numElves);
        ElfNode midElf = elf;
        for (int i = 0; i < numElves / 2; i++) {
            midElf = midElf.next;
        }
        while (elf.next != elf) {
            midElf = midElf.remove();
            if (numElves-- % 2 == 1) {
                midElf = midElf.next;
            }
            elf = elf.next;
        }
        return elf.id;
    }
}
