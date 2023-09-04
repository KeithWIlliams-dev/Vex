package com.vex;

import com.vex.util.GlobalConstants;

public class Main
{
    public static void main(String args[])
    {
        GlobalConstants.debugMode = Boolean.parseBoolean(System.getProperty("debug"));
        System.out.println("Debug property value: " + GlobalConstants.debugMode);
        new Window().run();
    }
    
}