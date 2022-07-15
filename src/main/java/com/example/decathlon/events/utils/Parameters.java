package com.example.decathlon.events.utils;

/*
 * Immutable class of Parameters, used for points calculation.
 * Immutable because once created they will never change.
 * */

public final class Parameters {

    private final double A;
    private final double B;
    private final double C;

    public Parameters(double a, double b, double c) {
        A = a;
        B = b;
        C = c;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }

}
