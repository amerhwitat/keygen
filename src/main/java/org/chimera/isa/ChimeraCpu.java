package org.chimera.isa;

import org.chimera.core.Register8192;

/** Semantic Java port of the implemented R8192 ALU subset; privileged operations are policy-gated. */
public final class ChimeraCpu {
    public static final int GPRS=1024;
    public final Register8192[] gpr=new Register8192[GPRS];
    public long flags;
    public long pc;
    public ChimeraCpu(){ for(int i=0;i<GPRS;i++)gpr[i]=new Register8192(); }

    public void execute(Instruction i, boolean privileged){
        if(i.dst()<0||i.dst()>=GPRS||i.srcA()<0||i.srcA()>=GPRS||i.srcB()<0||i.srcB()>=GPRS) throw new IllegalArgumentException("register index");
        var a=gpr[i.srcA()]; var b=gpr[i.srcB()];
        switch(i.opcode()){
            case 0x0001 -> gpr[i.dst()]=a.add(b);
            case 0x0002 -> gpr[i.dst()]=a.sub(b);
            case 0x0003 -> gpr[i.dst()]=a.and(b);
            case 0x0004 -> gpr[i.dst()]=a.or(b);
            case 0x0005 -> gpr[i.dst()]=a.xor(b);
            case 0x0006 -> gpr[i.dst()]=a.not();
            case 0x0007 -> gpr[i.dst()]=a.shiftLeft((int)(i.immediate()%8192));
            case 0x0008 -> gpr[i.dst()]=a.shiftRight((int)(i.immediate()%8192));
            case 0x0009 -> gpr[i.dst()]=a.rotateLeft((int)(i.immediate()%8192));
            case 0x000A -> gpr[i.dst()]=a.rotateRight((int)(i.immediate()%8192));
            case 0x000B -> { var r=new Register8192(); for(int k=0;k<128;k++)r.setLane(k,a.lane(k)*b.lane(k)); gpr[i.dst()]=r; }
            case 0x000C -> { var r=new Register8192(); for(int k=0;k<128;k++)r.setLane(k,mulHi64(a.lane(k),b.lane(k))); gpr[i.dst()]=r; }
            case 0x000F -> gpr[i.dst()]=a; // Barrett service is delegated in the research runtime.
            case 0x0010 -> { var r=new Register8192(); for(int k=0;k<128;k++){if(b.lane(k)==0)throw new ArithmeticException("divide by zero");r.setLane(k,a.lane(k)/b.lane(k));}gpr[i.dst()]=r; }
            case 0x0011 -> { var r=new Register8192(); for(int k=0;k<128;k++){if(b.lane(k)==0)throw new ArithmeticException("divide by zero");r.setLane(k,a.lane(k)%b.lane(k));}gpr[i.dst()]=r; }
            case 0x0015 -> gpr[i.dst()]=a;
            case 0x0012,0x0013,0x0014 -> flags=compare(a,b,i.opcode());
            case 0x0047,0x00D4,0x0103 -> { if(!privileged) throw new SecurityException("privileged Chimera operation"); }
            default -> throw new UnsupportedOperationException("opcode 0x"+Integer.toHexString(i.opcode()));
        }
    }
    private static long compare(Register8192 a,Register8192 b,int op){
        if(op==0x0013)return a.equals(b)?1:0;
        boolean lt=false; for(int k=127;k>=0;k--){long x=a.lane(k),y=b.lane(k);if(x!=y){lt=Long.compareUnsigned(x,y)<0;break;}}
        return op==0x0014?(lt?1:0):(a.equals(b)?1:0)|(lt?2:0);
    }
    private static long mulHi64(long a,long b){
        long a0=a&0xffffffffL,a1=a>>>32,b0=b&0xffffffffL,b1=b>>>32,w0=a0*b0,t=a1*b0+(w0>>>32),w1=t&0xffffffffL,w2=t>>>32;
        w1+=a0*b1; return a1*b1+w2+(w1>>>32);
    }
}
