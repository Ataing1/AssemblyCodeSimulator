import java.util.Arrays;

public class HexMemory {
    String[] memoryStack;


    public HexMemory(String hex) {

        int size = HexUtil.hexToInt(hex) + 1; //we need index 64, lets ignore 0
        System.out.println(size);
        memoryStack = new String[size];
        System.out.println("fill mem stack");
        Arrays.fill(memoryStack, "00");
        System.out.println("mem stack size: " + memoryStack.length);
    }

    public void mov(SIZE size, String hexLocation, Memory memory) {
        int movAmount;
        switch (size) {
            case BYTE:
                movAmount = 1;
                break;
            case WORD:
                movAmount = 2;
                break;
            case DWORD:
                movAmount = 4;
                break;
            case QWORD:
                movAmount = 8;
                break;
            default:
                throw new IllegalArgumentException("INVALID ENUM TYPE");
        }
        int index = HexUtil.hexToInt(hexLocation);
        for (int i = 0; i < movAmount; i++) {
            memoryStack[index - i] = memory.memory[i];
        }
    }

    public String get(SIZE size, String hexLocation){
        int movAmount;
        switch (size) {
            case BYTE:
                movAmount = 1;
                break;
            case WORD:
                movAmount = 2;
                break;
            case DWORD:
                movAmount = 4;
                break;
            case QWORD:
                movAmount = 8;
                break;
            default:
                throw new IllegalArgumentException("INVALID ENUM TYPE");
        }
        int index = HexUtil.hexToInt(hexLocation);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < movAmount; i++) {
             stringBuilder.insert(0, memoryStack[index - i]);
        }
        return stringBuilder.toString();
    }

    public void printMemory() {
        System.out.println("STANDARD HEX");
        StringBuilder stringBuilder = new StringBuilder();
        int hitEight = 0;
        for(int i = 1;i<memoryStack.length;i++){
            stringBuilder.append(memoryStack[i]);
            hitEight++;
            if(hitEight ==8){
                hitEight =0;
                stringBuilder.insert(0, "-0x"+ Integer.toHexString(i)+ ": ");
                String result = stringBuilder.toString();
                System.out.println(result);
                stringBuilder.setLength(0);
            }
        }
    }

    public void printMemoryEndian() {
        System.out.println("LITTLE ENDIAN");
        StringBuilder stringBuilder = new StringBuilder();
        int hitEight = 0;
        for(int i = 1;i<memoryStack.length;i++){
            stringBuilder.append(memoryStack[i]);
            hitEight++;
            if(hitEight ==8){
                hitEight =0;
                stringBuilder.reverse();
                stringBuilder.insert(0, "-0x"+ Integer.toHexString(i)+ ": ");
                String result = stringBuilder.toString();
                System.out.println(result);
                stringBuilder.setLength(0);
            }
        }
    }

    public int strlen(String hexLocation) {
        int index = HexUtil.hexToInt(hexLocation);
        if(index<0){
            throw new IllegalArgumentException("hex can't be negative");
        }
        int count = 0;
        while(!memoryStack[index].equals("00")){
            count++;
            index--;
        }
        return count;
    }

    public void puts(String hexLocation){
        int index = HexUtil.hexToInt(hexLocation);
        if(index<0){
            throw new IllegalArgumentException("hex can't be negative");
        }
        StringBuilder stringBuilder = new StringBuilder();
        while(!memoryStack[index].equals("00")){
            stringBuilder.append((char) Integer.parseInt(memoryStack[index], 16));
            index--;
        }

        System.out.println(stringBuilder.toString());
        //System.out.println(stringBuilder.reverse().toString());
    }




}
