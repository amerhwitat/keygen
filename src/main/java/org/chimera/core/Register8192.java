package org.chimera.core;

import java.math.BigInteger;
import java.util.Arrays;

/** 8192-bit value represented as exactly 128 little-endian 64-bit lanes. */
public final class Register8192 {
    public static final int LANES = 128;
    private final long[] lane = new long[LANES];

    public Register8192() {}
    public Register8192(long[] lanes) { System.arraycopy(lanes, 0, lane, 0, Math.min(LANES, lanes.length)); }
    public long lane(int i) { return lane[i]; }
    public void setLane(int i, long v) { lane[i] = v; }
    public long[] lanes() { return lane.clone(); }

    public Register8192 add(Register8192 b) { var r=new Register8192(); for(int i=0;i<LANES;i++) r.lane[i]=lane[i]+b.lane[i]; return r; }
    public Register8192 sub(Register8192 b) { var r=new Register8192(); for(int i=0;i<LANES;i++) r.lane[i]=lane[i]-b.lane[i]; return r; }
    public Register8192 and(Register8192 b) { var r=new Register8192(); for(int i=0;i<LANES;i++) r.lane[i]=lane[i]&b.lane[i]; return r; }
    public Register8192 or(Register8192 b) { var r=new Register8192(); for(int i=0;i<LANES;i++) r.lane[i]=lane[i]|b.lane[i]; return r; }
    public Register8192 xor(Register8192 b) { var r=new Register8192(); for(int i=0;i<LANES;i++) r.lane[i]=lane[i]^b.lane[i]; return r; }
    public Register8192 not() { var r=new Register8192(); for(int i=0;i<LANES;i++) r.lane[i]=~lane[i]; return r; }
    public Register8192 shiftLeft(int s) { return shift(s, true); }
    public Register8192 shiftRight(int s) { return shift(s, false); }
    private Register8192 shift(int s, boolean left) {
        var r=new Register8192(); if(s<0||s>=8192)return r; int q=s/64, bit=s%64;
        if(left){ for(int k=q;k<LANES;k++){ long v=lane[k-q]<<bit; if(bit!=0&&k>q)v|=lane[k-q-1]>>>(64-bit); r.lane[k]=v; }}
        else { for(int k=0;k<LANES-q;k++){ long v=lane[k+q]>>>bit; if(bit!=0&&k+q+1<LANES)v|=lane[k+q+1]<<(64-bit); r.lane[k]=v; }}
        return r;
    }
    public Register8192 rotateLeft(int s){ s=Math.floorMod(s,8192); return s==0?this:shiftLeft(s).or(shiftRight(8192-s)); }
    public Register8192 rotateRight(int s){ s=Math.floorMod(s,8192); return s==0?this:shiftRight(s).or(shiftLeft(8192-s)); }
    public BigInteger toBigInteger(){ byte[] out=new byte[1024]; for(int i=0;i<LANES;i++){ long x=lane[i]; for(int j=0;j<8;j++) out[1023-(i*8+j)]=(byte)(x >>> (56-8*j)); } return new BigInteger(1,out); }
    public static Register8192 fromBigInteger(BigInteger x){ byte[] b=x.and(BigInteger.ONE.shiftLeft(8192).subtract(BigInteger.ONE)).toByteArray(); var r=new Register8192(); for(int i=0;i<LANES;i++){ long v=0; for(int j=0;j<8;j++){ int p=b.length-1-(i*8+j); if(p>=0)v|=(long)(b[p]&255)<<(8*j); } r.lane[i]=v;} return r; }
    @Override public boolean equals(Object o){ return o instanceof Register8192 r && Arrays.equals(lane,r.lane); }
    @Override public int hashCode(){ return Arrays.hashCode(lane); }
}
