package org.chimera.cognition;

import java.util.Arrays;

/** 128-dimensional feature/state vector used by the Chimera 128D research model. */
public final class Vector128D {
    public static final int DIM=128;
    private final double[] x=new double[DIM];
    public Vector128D(){}
    public Vector128D(double[] v){System.arraycopy(v,0,x,0,Math.min(DIM,v.length));}
    public double get(int i){return x[i];}
    public void set(int i,double v){x[i]=v;}
    public double[] copy(){return x.clone();}
    public double dot(Vector128D b){double s=0;for(int i=0;i<DIM;i++)s+=x[i]*b.x[i];return s;}
    public double norm(){return Math.sqrt(dot(this));}
    public Vector128D add(Vector128D b){var r=new Vector128D();for(int i=0;i<DIM;i++)r.x[i]=x[i]+b.x[i];return r;}
    public Vector128D scale(double s){var r=new Vector128D();for(int i=0;i<DIM;i++)r.x[i]=x[i]*s;return r;}
    public Vector128D tanh(){var r=new Vector128D();for(int i=0;i<DIM;i++)r.x[i]=Math.tanh(x[i]);return r;}
    @Override public boolean equals(Object o){return o instanceof Vector128D v&&Arrays.equals(x,v.x);}
    @Override public int hashCode(){return Arrays.hashCode(x);}
}
