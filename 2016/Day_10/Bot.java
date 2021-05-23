public class Bot {
    private int[] chips = new int[2];
    private String[][] commands = new String[2][2];

    public Bot() {
        this.chips = new int[]{0,0};
        this.commands = new String[][]{{""},{""}};
    }

    public int numChips()
    {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            if (this.chips[i] != 0) { count++; }
        }
        return count;
    }

    public int[] getChips()
    {
        return this.chips;
    }

    public void loseChips()
    {
        this.chips[0] = 0; 
        this.chips[1] = 0;
    }

    public void gainChip(int chip)
    {
        for (int i = 0; i < 2; i++) {
            if (this.chips[i] == 0) {
                this.chips[i] = chip;
                break;
            }
        }
    }

    public void loadCmds(String[][] cmds)
    {
        this.commands = cmds;
    }

    public String[][] getCmds()
    {
        return this.commands;
    }
}
