package com.vex;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class InputHandler
{
    private Map<Integer, KeyState> keyStates = new HashMap<>();
    private Map<Integer, Long> keyHeldTime = new HashMap<>();

    public long secondInNanoSeconds = 1_000_000_000;

    private enum KeyState
    {
        RELEASED,
        PRESSED,
        HELD
    }

    public InputHandler(long window)
    {
        for (int i = 0; i < 512; i++)
        {
            keyStates.put(i, KeyState.RELEASED);
            keyHeldTime.put(i, 0L);   
        }

        glfwSetKeyCallback(window, this::keyCallback);
    }

    private void keyCallback(long window, int key, int scancode, int action, int mods)
    {
        if (key >= 0 && key < 512) 
        {
            switch (action)
            {
                case GLFW.GLFW_PRESS:
                    keyStates.put(key, KeyState.PRESSED);
                    keyHeldTime.put(key, System.nanoTime());
                    break;
                case GLFW.GLFW_RELEASE:
                    keyStates.put(key, KeyState.RELEASED);
                    keyHeldTime.put(key, 0L);
                    break;
            }
            
        }
    }

    public boolean isKeyReleaed(int key)
    {
        return keyStates.getOrDefault(key, KeyState.RELEASED) == KeyState.RELEASED;
    }

    public boolean isKeyPressed(int key)
    {
        return keyStates.getOrDefault(key, KeyState.RELEASED) == KeyState.PRESSED;
    }

    public boolean isKeyHeld(int key)
    {
        return keyStates.getOrDefault(key, KeyState.RELEASED) == KeyState.HELD;
    }

    public void update()
    {
        long currentTime = System.nanoTime();
        for (int key : keyStates.keySet())
        {
            long pressedTime = keyHeldTime.get(key);

            if ((keyStates.get(key) != KeyState.HELD) && (keyStates.get(key) != KeyState.PRESSED))
            {
                keyStates.put(key, KeyState.RELEASED);
                keyHeldTime.put(key, 0L);
            }
            else if (keyStates.get(key) == KeyState.PRESSED && (currentTime - pressedTime) >= (secondInNanoSeconds/2))
            {
                keyStates.put(key, KeyState.HELD);
            }

            if ((key == 97 || key == 100 || key == 115 || key == 119 || key == 27) && (keyStates.get(key) != KeyState.RELEASED))
                System.out.println(key+" is "+keyStates.get(key));
        }

    }


}