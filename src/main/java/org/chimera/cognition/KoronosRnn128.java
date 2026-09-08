package org.chimera.cognition;

import java.util.SplittableRandom;

/**
 * Compact trainable recurrent cell for Koronos research workloads.
 * The 128D state is explicit so observations, memory, policy and telemetry can share a common space.
 */
public final class KoronosRnn128 {
    private final double[][] whh=new double[128][128];
    private final double[][] wxh=new double[128][128];
    private final double[] bias=new double[128];
    private Vector128D hidden=new Vector128D();
    private final double learningRate;

    public KoronosRnn128(double learningRate,long seed){
        this.learningRate=learningRate; SplittableRandom r=new SplittableRandom(seed);
        double scale=Math.sqrt(2.0/128.0);
        for(int i=0;i<128;i++)for(int j=0;j<128;j++){whh[i][j]=(r.nextDouble()-0.5)*scale;wxh[i][j]=(r.nextDouble()-0.5)*scale;}
    }
    public Vector128D state(){return new Vector128D(hidden.copy());}
    public Vector128D step(Vector128D input){
        double[] next=new double[128],in=input.copy(),old=hidden.copy();
        for(int i=0;i<128;i++){double s=bias[i];for(int j=0;j<128;j++)s+=wxh[i][j]*in[j]+whh[i][j]*old[j];next[i]=Math.tanh(s);}
        hidden=new Vector128D(next); return state();
    }
    /** One-step self-supervised adaptation toward a target representation, with bounded updates. */
    public double adapt(Vector128D input,Vector128D target){
        Vector128D predicted=step(input); double[] p=predicted.copy(),t=target.copy(),in=input.copy(); double loss=0;
        for(int i=0;i<128;i++){double e=p[i]-t[i];loss+=e*e;double grad=2*e*(1-p[i]*p[i]);bias[i]-=learningRate*grad;for(int j=0;j<128;j++)wxh[i][j]-=learningRate*grad*in[j];}
        return loss/128.0;
    }
}
