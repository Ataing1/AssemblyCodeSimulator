import java.util.Arrays;

public class Memory {

    //stored smallest to largest (little endian format)

    public String[] memory;

    public Memory(String hex) {

        memory = new String[8];
        setQWORD(hex);
    }

    public Memory(Memory memory1){
        this.memory = new String[8];
        System.arraycopy(memory1.memory, 0, this.memory, 0, 8);
    }


    public void setQWORD(String hex) {
        checkHex(hex.length(), 16);
        hex = fillHex(hex);
        Arrays.fill(memory, "");
        int position = 0;
        for (int i = 14; i >= 0; i -= 2) {
            memory[position]  = String.valueOf(hex.charAt(i)) + hex.charAt(i + 1);
            position++;
        }
    }

    public void setDWORD(String hex) {
        checkHex(hex.length(), 8);
        hex = fillHex(hex);
        Arrays.fill(memory, "");
        int position = 0;
        for (int i = 14; i >= 0; i -= 2) {
            memory[position]  = String.valueOf(hex.charAt(i)) + hex.charAt(i + 1);
            position++;
        }
    }

    /**
     * allows for single byte swapping
     * @param hex the new byte to by swapped
     * @param position the position where the new byte should go
     */
    public void setByte(String hex, int position) {
        checkHex(hex.length(), 2);
        memory[position] += hex.charAt(0);
        memory[position] += hex.charAt(1);
    }

    private void checkHex(int hexLength, int size) {
        if (hexLength > size || hexLength % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }
    }

    private String fillHex(String hex){
        StringBuilder hexBuilder = new StringBuilder(hex);
        while (hexBuilder.length() < 16) {
            hexBuilder.insert(0, "0");
        }
        return hexBuilder.toString();
    }

    public String getDWORD() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i<4;i++){
            stringBuilder.insert(0, memory[i]);
        }
        return stringBuilder.toString();
    }

    public String getQWORD() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i<8;i++){
            stringBuilder.insert(0, memory[i]);
        }
        return stringBuilder.toString();
    }

    public String getByte() {
        return memory[0];
    }

    public void print(){
        System.out.println("IN HEX FORMAT:");
        printHex();
        System.out.println("IN ENDIAN FORMAT");
        printEndian();
    }

    public void printHex() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0x");
        for (int i = 7; i >= 0; i--) {
            stringBuffer.append(memory[i]);
        }
        System.out.println(stringBuffer.toString());
    }

    public void printEndian() {
        System.out.println(Arrays.toString(memory));
        String[] forPrint = new String[8];
        for (int i = 0; i < 8; i++) {
            forPrint[i] = i + " ";
        }
        System.out.println(Arrays.toString(forPrint));
    }
}
