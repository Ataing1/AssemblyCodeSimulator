

public class Machine {
    HexUtil hexUtil;
    HexMemory hexMemory;
    Memory a;
    Memory d;

    public Machine() {
        hexUtil = new HexUtil();

    }

    public void main() {

        System.out.println(HexUtil.hexToInt("40"));
        a = new Memory("54454d4f434c4557");
        d = new Memory("414e4f5a4952414f");
        hexMemory = new HexMemory("40");
        hexMemory.mov(SIZE.QWORD, "40", a);
        hexMemory.mov(SIZE.QWORD, "38", d);
        a.setR_X("494e554554415453");
        d.setR_X("59544953524556");
        hexMemory.mov(SIZE.QWORD, "30", a);
        hexMemory.mov(SIZE.QWORD, "28", d);
        hexMemory.mov(SIZE.QWORD, "20", new Memory("00"));
        hexMemory.printMemory();
        a.setE_X(Integer.toHexString( hexMemory.strlen("40"))); //call   0x1080 <strlen@plt>
        hexMemory.mov(SIZE.DWORD, "c", a);
        hexMemory.mov(SIZE.DWORD, "8", new Memory(""));
        hexMemory.printMemory();


        a.setE_X(hexMemory.get(SIZE.DWORD, "8"));
        int op1 = Integer.parseInt(a.getE_X(), 16);
        int op2 = Integer.parseInt(hexMemory.get(SIZE.DWORD, "c"), 16);
        hexMemory.printMemoryEndian();
        hexMemory.puts("40");
        while (op1 < op2) {
            a.setE_X(hexMemory.get(SIZE.DWORD, "8"));
            int intLocation = HexUtil.hexToInt("40") - Integer.parseInt(a.getR_X(), 16);
            String hexLocation = Integer.toHexString( intLocation);
            String memByte = hexMemory.get(SIZE.BYTE, hexLocation);
            a.setE_X(memByte);
            int eax = Integer.parseInt(a.getR_X(), 16) + HexUtil.hexToInt("e");
            a.setE_X(Integer.toHexString( eax));
            hexMemory.mov(SIZE.BYTE, "1", a);

            op1 = HexUtil.hexToInt(hexMemory.get(SIZE.BYTE, "1"));
            op2 = HexUtil.hexToInt("5a");
            if (op1 > op2) {
                a.setE_X(hexMemory.get(SIZE.BYTE, "1"));
                eax = Integer.parseInt(a.getR_X(), 16) - HexUtil.hexToInt("1a");
                a.setE_X(Integer.toHexString( eax));
                hexMemory.mov(SIZE.BYTE, "01", a);
            }
            a.setE_X(hexMemory.get(SIZE.DWORD, "8"));
            d.setE_X(hexMemory.get(SIZE.BYTE, "1"));
            intLocation = HexUtil.hexToInt("40") - Integer.parseInt(a.getR_X(), 16);
            hexLocation = Integer.toHexString( intLocation);
            hexMemory.mov(SIZE.BYTE, hexLocation, d);
            int temp = Integer.parseInt(hexMemory.get(SIZE.DWORD, "8"), 16) + HexUtil.hexToInt("01");
            hexMemory.mov(SIZE.DWORD, "8", new Memory(Integer.toHexString(temp))); //DWORD PTR [rbp-0x8],0x1

            a.setE_X(hexMemory.get(SIZE.DWORD, "8"));
            op1 = Integer.parseInt(a.getE_X(), 16);
            op2 = Integer.parseInt(hexMemory.get(SIZE.DWORD, "c"), 16);
            hexMemory.puts("40");
        }
        hexMemory.puts("40");

    }


}
