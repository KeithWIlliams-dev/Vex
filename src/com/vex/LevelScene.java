package com.vex;

import static org.lwjgl.glfw.GLFW.*;

import com.vex.util.GlobalConstants;

public class LevelScene extends Scene
{
    private boolean isChangingScene = false;

    public LevelScene()
    {
        if (GlobalConstants.debugMode)
            System.out.println("Inside Level Scene");
    }

    @Override
    public void update(double deltaTime)
    {
        if (!isChangingScene && (InputHandler.isKeyHeld(GLFW_KEY_ESCAPE)))
            isChangingScene = true;
        else if (isChangingScene)
            Window.changeScene(0);
    }

}