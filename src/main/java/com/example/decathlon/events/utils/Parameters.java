package com.example.decathlon.events.utils;

/*
 * Immutable class of Parameters, used for points calculation.
 * Immutable because once created they will never change.
 * */

public final class Parameters {

    private final double constA;
    private final double constB;
    private final double constC;

    public Parameters(double constA, double constB, double constC) {
        this.constA = constA;
        this.constB = constB;
        this.constC = constC;
    }

    public double getConstA() {
        return this.constA;
    }

    public double getConstB() {
        return this.constB;
    }

    public double getConstC() {
        return this.constC;
    }
}
