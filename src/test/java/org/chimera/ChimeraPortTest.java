package org.chimera;

import org.chimera.cognition.Vector128D;
import org.chimera.core.Register8192;
import org.chimera.isa.ChimeraCpu;
import org.chimera.isa.Instruction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChimeraPortTest {
    @Test void instructionDecodeIsLittleEndian16Bytes(){byte[] p=new byte[16];p[0]=1;p[2]=7;p[4]=8;p[6]=9;p[15]=(byte)0x80;var i=Instruction.decode(p,0);assertEquals(1,i.opcode());assertEquals(7,i.dst());assertEquals(8,i.srcA());assertEquals(9,i.srcB());assertEquals(Long.MIN_VALUE,i.immediate());}
    @Test void r8192ShiftRoundTrips(){var x=new Register8192();x.setLane(0,1);assertEquals(1,x.shiftLeft(1).shiftRight(1).lane(0));}
    @Test void cpuAddWorks(){var c=new ChimeraCpu();c.gpr[1].setLane(0,4);c.gpr[2].setLane(0,5);c.execute(new Instruction(1,0,1,2,0),false);assertEquals(9,c.gpr[0].lane(0));}
    @Test void vectorIs128D(){var v=new Vector128D();v.set(127,3);assertEquals(3,v.get(127));}
}
