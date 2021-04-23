

public class Machine {
    ByteHexUtil byteHexUtil;
    HexMemory hexMemory;
    Memory a;
    Memory d;

    public Machine() {
        byteHexUtil = new ByteHexUtil();

    }

    public void main() {

        System.out.println(byteHexUtil.hexToByte("40"));
        a = new Memory("54454d4f434c4557");
        d = new Memory("414e4f5a4952414f");
        hexMemory = new HexMemory("40");
        hexMemory.mov(SIZE.QWORD, "40", a);
        hexMemory.mov(SIZE.QWORD, "38", d);
        a.setQWORD("494e554554415453");
        d.setQWORD("59544953524556");
        hexMemory.mov(SIZE.QWORD, "30", a);
        hexMemory.mov(SIZE.QWORD, "28", d);
        hexMemory.mov(SIZE.QWORD, "20", new Memory("00"));
        hexMemory.printMemory();
        a.setDWORD(byteHexUtil.byteToHex((byte) hexMemory.strlen("40"))); //call   0x1080 <strlen@plt>
        hexMemory.mov(SIZE.DWORD, "0c", a);
        hexMemory.mov(SIZE.DWORD, "08", new Memory(""));
        hexMemory.printMemory();


        a.setDWORD(hexMemory.get(SIZE.DWORD, "08"));
        int op1 = Integer.parseInt(a.getDWORD(), 16);
        int op2 = Integer.parseInt(hexMemory.get(SIZE.DWORD, "0c"), 16);
        hexMemory.printMemoryEndian();
        hexMemory.puts("40");
        while (op1 < op2) {
            a.setDWORD(hexMemory.get(SIZE.DWORD, "08"));
            int intLocation = byteHexUtil.hexToByte("40") - Integer.parseInt(a.getQWORD(), 16);
            String hexLocation = byteHexUtil.byteToHex((byte) intLocation);
            String memByte = hexMemory.get(SIZE.BYTE, hexLocation);
            a.setDWORD(memByte);
            int eax = Integer.parseInt(a.getQWORD(), 16) + byteHexUtil.hexToByte("0e");
            a.setDWORD(byteHexUtil.byteToHex((byte) eax));
            hexMemory.mov(SIZE.BYTE, "01", a);

            op1 = byteHexUtil.hexToByte(hexMemory.get(SIZE.BYTE, "01"));
            op2 = byteHexUtil.hexToByte("5a");
            if (op1 > op2) {
                a.setDWORD(hexMemory.get(SIZE.BYTE, "01"));
                eax = Integer.parseInt(a.getQWORD(), 16) - byteHexUtil.hexToByte("1a");
                a.setDWORD(byteHexUtil.byteToHex((byte) eax));
                hexMemory.mov(SIZE.BYTE, "01", a);
            }
            a.setDWORD(hexMemory.get(SIZE.DWORD, "08"));
            d.setDWORD(hexMemory.get(SIZE.BYTE, "01"));
            intLocation = byteHexUtil.hexToByte("40") - Integer.parseInt(a.getQWORD(), 16);
            hexLocation = byteHexUtil.byteToHex((byte) intLocation);
            hexMemory.mov(SIZE.BYTE, hexLocation, d);
            int temp = Integer.parseInt(hexMemory.get(SIZE.DWORD, "08"), 16) + byteHexUtil.hexToByte("01");
            hexMemory.mov(SIZE.DWORD, "08", new Memory(byteHexUtil.byteToHex((byte) temp))); //DWORD PTR [rbp-0x8],0x1

            a.setDWORD(hexMemory.get(SIZE.DWORD, "08"));
            op1 = Integer.parseInt(a.getDWORD(), 16);
            op2 = Integer.parseInt(hexMemory.get(SIZE.DWORD, "0c"), 16);
            hexMemory.puts("40");
        }
        hexMemory.puts("40");

    }


}
