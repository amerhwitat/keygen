package org.chimera.isa;

public record Instruction(int opcode,int dst,int srcA,int srcB,long immediate) {
    public static final int SIZE=16;
    public static Instruction decode(byte[] p,int off){
        if(p==null||p.length-off<SIZE) throw new IllegalArgumentException("canonical Chimera-II instruction requires 16 bytes");
        int op=(p[off]&255)|((p[off+1]&255)<<8); int d=(p[off+2]&255)|((p[off+3]&255)<<8);
        int a=(p[off+4]&255)|((p[off+5]&255)<<8); int b=(p[off+6]&255)|((p[off+7]&255)<<8);
        long imm=0; for(int i=0;i<8;i++) imm|=(long)(p[off+8+i]&255)<<(8*i);
        return new Instruction(op,d,a,b,imm);
    }
}
