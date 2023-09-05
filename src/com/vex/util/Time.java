package com.vex.util;

public class Time 
{
    public static float timeStarted = System.nanoTime();
    public static final double nanoSecondToSecondConversionRatio = 1E-9;

    public static float getTime()
    {
        return (float)((System.nanoTime() - timeStarted) * nanoSecondToSecondConversionRatio);
    }
}
