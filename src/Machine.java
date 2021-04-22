import javax.crypto.Mac;

import static java.lang.System.exit;

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
        jump149();
    }

    public void jump100() {
        a.setDWORD(hexMemory.get(SIZE.DWORD, "08"));
        int intLocation = byteHexUtil.hexToByte("40") - Integer.parseInt(a.getQWORD(), 16);
        String hexLocation = byteHexUtil.byteToHex((byte) intLocation);
        String memByte = hexMemory.get(SIZE.BYTE, hexLocation);
        a.setDWORD(memByte);
        int eax = Integer.parseInt(a.getQWORD(), 16) + byteHexUtil.hexToByte("0d");
        a.setDWORD(byteHexUtil.byteToHex((byte) eax));
        hexMemory.mov(SIZE.BYTE, "01", a);
        hexMemory.printMemory();
        int op1 = byteHexUtil.hexToByte(hexMemory.get(SIZE.BYTE, "01"));
        int op2 = byteHexUtil.hexToByte("5a");
        if (op1 > op2) {
            a.setDWORD(hexMemory.get(SIZE.BYTE, "01"));
            eax = Integer.parseInt(a.getQWORD(), 16) - byteHexUtil.hexToByte("1a");
            a.setDWORD(byteHexUtil.byteToHex((byte) eax));
            hexMemory.mov(SIZE.BYTE, "01", a);
        }
        jump132();

    }

    public void jump132() {
        a.setDWORD(hexMemory.get(SIZE.DWORD, "08"));
        a.print();
        d.setDWORD(hexMemory.get(SIZE.BYTE, "01"));
        int intLocation = byteHexUtil.hexToByte("40") - Integer.parseInt(a.getQWORD(), 16);
        String hexLocation = byteHexUtil.byteToHex((byte) intLocation);
        hexMemory.mov(SIZE.BYTE, hexLocation, d);
        int temp = byteHexUtil.hexToByte(hexMemory.get(SIZE.DWORD, "08")) + byteHexUtil.hexToByte("01");
        hexMemory.mov(SIZE.DWORD, "08", new Memory(byteHexUtil.byteToHex((byte) temp))); //DWORD PTR [rbp-0x8],0x1
        jump149();
    }

    public void jump149() {
        a.setDWORD(hexMemory.get(SIZE.DWORD, "08"));
        int op1 = Integer.parseInt(a.getDWORD(), 16);
        int op2 = Integer.parseInt(hexMemory.get(SIZE.DWORD, "0c"), 16);
        if (op1 < op2) {
            jump100();
        } else {
            System.out.println("FINAL ANSWER: " + hexMemory.get(SIZE.QWORD, "40"));
            exit(0);
        }


    }
}
