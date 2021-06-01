public class day16 {
    public static void main(String[] args)
    {
        String input = "01111001100111011";
        int diskSize = 272;
        
        // Part 1
        String filledDisk = fillDisk(diskSize, input);
        String checksum = getChecksum(filledDisk.toCharArray());
        System.out.println("Part 1: " + checksum);

        // Part 2
        diskSize = 35651584;
        filledDisk = fillDisk(diskSize, input);
        checksum = getChecksum(filledDisk.toCharArray());
        System.out.println("Part 2: " + checksum);
    }

    static String fillDisk(int diskSize, String data)
    {
        String inverse = new StringBuilder(data).reverse().toString();
        inverse = inverse.replace('0', 't');
        inverse = inverse.replace('1', '0');
        inverse = inverse.replace('t', '1');
        String output = data + '0' + inverse;
        if (output.length() < diskSize) {
            output = fillDisk(diskSize, output);
        }
        return output.substring(0, diskSize);
    }

    static String getChecksum(char[] data)
    {
        StringBuilder checksum = new StringBuilder();
        for (int i = 0; i < data.length; i += 2) {
            if (data[i] == data[i+1]) {
                checksum.append('1');
            } else {
                checksum.append('0');
            }
        }
        if (checksum.length() % 2 != 0) {
            return checksum.toString();
        } 
        return getChecksum(checksum.toString().toCharArray());
    }
}