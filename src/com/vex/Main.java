package com.vex;

import com.vex.util.GlobalConstants;

public class Main
{
    public static void main(String args[])
    {
        GlobalConstants.debugMode = Boolean.parseBoolean(System.getProperty("debug"));
        GlobalConstants.fpsCounter = Boolean.parseBoolean(System.getProperty("fpsCounter"));
        System.out.println("Debug property value: " + GlobalConstants.debugMode);
        System.out.println("FPS property value: " + GlobalConstants.fpsCounter);
        new Window().run();
    }
    
}