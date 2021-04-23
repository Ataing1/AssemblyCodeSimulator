import static java.lang.System.exit;

public class ShiftChallenge {

    HexMemory hexMemory;
    Memory rax;
    Memory rcx;
    Memory rdx;
    Memory rsi, rdi;
    int op1, op2;

    public ShiftChallenge(){
        rax = new Memory("");
        rcx = new Memory("");
        rdx = new Memory("");
        rsi = new Memory("");
        rdi = new Memory("");
    }

    public void main(){
        hexMemory = new HexMemory("10");
        hexMemory.mov(SIZE.DWORD, "8", new Memory("10"));
        hexMemory.mov(SIZE.DWORD, "4", new Memory("00"));

        while(true) {
            rcx.setE_X(hexMemory.get(SIZE.DWORD, "4"));
            rdx.setE_X("01");
            rdx.shl(rcx.getByte());
            rax.setE_X(rdx.getE_X());
            hexMemory.mov(SIZE.DWORD, "c", rax);
            rax.setE_X(hexMemory.get(SIZE.DWORD, "c"));
            op1 = HexUtil.hexToInt(rax.getE_X());
            op2 = HexUtil.hexToInt(hexMemory.get(SIZE.DWORD, "8"));
            if (op1 > op2) {
                exit(0);
            }
            rax.setE_X(hexMemory.get(SIZE.DWORD, "8"));
            rax.and(hexMemory.get(SIZE.DWORD, "c")); //and    eax,DWORD PTR [rbp-0xc]
            rdx.setE_X(rax.getE_X());
            rax.setE_X(hexMemory.get(SIZE.DWORD, "4"));
            rcx.setE_X(rax.getE_X());
            rdx.sar(rcx.getByte());
            rax.setE_X(rdx.getE_X());
            rsi.setE_X(rax.getE_X());
            rdi.setR_X("2004");
            rax.setE_X("0");
            System.out.println("printf result: " + HexUtil.hexToInt(rsi.getR_X()));
            int temp = HexUtil.hexToInt(hexMemory.get(SIZE.DWORD, "4")) + HexUtil.hexToInt("1");
            hexMemory.mov(SIZE.DWORD, "4", new Memory(Integer.toHexString(temp)));
        }






    }
}
