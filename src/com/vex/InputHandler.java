package com.vex;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map;

public class InputHandler
{
    private static InputHandler inputHandler;
    private static long windowId;

    // Keyboard
    private static Map<Integer, KeyState> keyStates = new HashMap<>();
    private static Map<Integer, Long> keyHeldTime = new HashMap<>();

    // Mouse
    private double scrollX, scrollY;
    private double mouseXPosition, mouseYPosition, initialMouseXPosition, initialMouseYPosition;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    public final long secondInNanoSeconds = 1_000_000_000;

    private enum KeyState
    {
        RELEASED,
        PRESSED,
        HELD
    }

    public InputHandler(long windowId)
    {
        this.scrollX = this.scrollY = this.mouseXPosition = this.mouseYPosition = this.initialMouseXPosition = this.initialMouseYPosition = 0;
        InputHandler.windowId = windowId;

        for (int i = 0; i < 512; i++)
        {
            keyStates.put(i, KeyState.RELEASED);
            keyHeldTime.put(i, 0L);   
        }
    }

    public static InputHandler get()
    {
        if (inputHandler == null)
            inputHandler = new InputHandler(windowId);

        return inputHandler;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int modifier)
    {
        if (key >= 0 && key < 512) 
        {
            switch (action)
            {
                case GLFW_PRESS:
                    keyStates.put(key, KeyState.PRESSED);
                    keyHeldTime.put(key, System.nanoTime());
                    break;
                case GLFW_RELEASE:
                    keyStates.put(key, KeyState.RELEASED);
                    keyHeldTime.put(key, 0L);
                    break;
            }
            
        }
    }

    public static boolean isKeyReleaed(int key)
    {
        return keyStates.getOrDefault(key, KeyState.RELEASED) == KeyState.RELEASED;
    }

    public static boolean isKeyPressed(int key)
    {
        return keyStates.getOrDefault(key, KeyState.RELEASED) == KeyState.PRESSED;
    }

    public static boolean isKeyHeld(int key)
    {
        return keyStates.getOrDefault(key, KeyState.RELEASED) == KeyState.HELD;
    }

    public static void mousePositionCallback(long windoId, double newMouseXPosition, double newMouseYPosition)
    {
        get().initialMouseXPosition = get().mouseXPosition;
        get().initialMouseYPosition = get().mouseYPosition;

        get().mouseXPosition = newMouseXPosition;
        get().mouseYPosition = newMouseYPosition;

        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long windowId, int button, int action, int modifier)
    {  
        if (button < get().mouseButtonPressed.length)
        {
            switch (action)
            {
                case GLFW_PRESS: 
                    get().mouseButtonPressed[button] = true;
                    break;
                case GLFW_RELEASE:
                    get().mouseButtonPressed[button] = false;
                    get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long windowId, double xOffset, double yOffset)
    {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame()
    {
        get().scrollX = 0;
        get().scrollY = 0;
        get().initialMouseXPosition = get().mouseXPosition;
        get().initialMouseYPosition = get().mouseYPosition;
    }

    public static int getMouseXPosition()
    {
        return (int)get().mouseXPosition;
    }

    public static int getMouseYPosition()
    {
        return (int)get().mouseYPosition;
    }

    public static int getDeltaMouseXPosition()
    {
        return (int)(get().initialMouseXPosition - get().mouseXPosition);
    }
    
    public static int getDeltaMouseYPosition()
    {
        return (int)(get().initialMouseYPosition - get().mouseXPosition);
    }

    public static int getScrollX()
    {
        return (int)get().scrollX;
    }

    public static int getScrollY()
    {
        return (int)get().scrollY;
    } 

    public static boolean isDragging()
    {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button)
    {
        if (button < get().mouseButtonPressed.length)
            return get().mouseButtonPressed[button];
        else
            return false;
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