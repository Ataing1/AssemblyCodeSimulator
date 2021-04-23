import java.util.Arrays;

public class Memory {

    //stored smallest to largest (little endian format)

    public String[] memory;

    public Memory(String hex) {

        memory = new String[8];
        setR_X(hex);
    }

    public Memory(Memory memory1){
        this.memory = new String[8];
        System.arraycopy(memory1.memory, 0, this.memory, 0, 8);
    }


    public void setR_X(String hex) {
        checkHex(hex.length(), 16);
        hex = fillHex(hex);
        Arrays.fill(memory, "");
        int position = 0;
        for (int i = 14; i >= 0; i -= 2) {
            memory[position]  = String.valueOf(hex.charAt(i)) + hex.charAt(i + 1);
            position++;
        }
    }

    public void setE_X(String hex) {
        checkHex(hex.length(), 8);
        hex = fillHex(hex);
        Arrays.fill(memory, "");
        int position = 0;
        for (int i = 14; i >= 0; i -= 2) {
            memory[position]  = String.valueOf(hex.charAt(i)) + hex.charAt(i + 1);
            position++;
        }
    }

    private void checkHex(int hexLength, int size) {
        if (hexLength > size) {
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

    public String getE_X() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i<4;i++){
            stringBuilder.insert(0, memory[i]);
        }
        return stringBuilder.toString();
    }

    public String getR_X() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i<8;i++){
            stringBuilder.insert(0, memory[i]);
        }
        return stringBuilder.toString();
    }

    public String getByte() {
        return memory[0];
    }

    public void shl(String hex){
        int index = Integer.parseInt(hex,16);
        long memoryValue = Long.parseLong(getR_X(), 16);
        memoryValue =  memoryValue<<index;
        setE_X(Long.toHexString(memoryValue));
    }

    public void sar(String hex){
        int index = Integer.parseInt(hex,16);
        long memoryValue = Long.parseLong(getR_X(), 16);
        memoryValue =  memoryValue>>index;
        setE_X(Long.toHexString(memoryValue));
    }

    public void and(String hex){
        int index = Integer.parseInt(hex,16);
        long memoryValue = Long.parseLong(getR_X(), 16);
        memoryValue =  memoryValue&index;
        setE_X(Long.toHexString(memoryValue));
    }


    public void print(){
        System.out.println("IN HEX FORMAT:");
        printHex();
        System.out.println("IN ENDIAN FORMAT");
        printEndian();
    }

    public void printHex() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("0x");
        for (int i = 7; i >= 0; i--) {

            stringBuffer.append(memory[i]);
        }
        System.out.println(stringBuffer.toString());
    }

    public void printEndian() {
        System.out.println(Arrays.toString(memory));
    }
}
