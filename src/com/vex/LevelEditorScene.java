package com.vex;

import static org.lwjgl.glfw.GLFW.*;

import com.vex.util.GlobalConstants;

public class LevelEditorScene extends Scene
{
    private boolean isChangingScene = false;

    public LevelEditorScene()
    {
        if (GlobalConstants.debugMode)
            System.out.println("Insdie Level Editor Scene");
    }

    @Override
    public void update(double deltaTime) 
    {
        if (GlobalConstants.fpsCounter)
            System.out.println(""+(1.0f / deltaTime)+" FPS");

        if (!isChangingScene && (InputHandler.isKeyHeld(GLFW_KEY_ESCAPE)))
            isChangingScene = true;
        else if (isChangingScene)
            Window.changeScene(1);
    }
}