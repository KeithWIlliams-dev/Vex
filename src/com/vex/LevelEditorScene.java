package com.vex;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

import com.vex.util.GlobalConstants;

public class LevelEditorScene extends Scene
{
    private boolean isChangingScene = false;

    private String vertexShaderSource = "#version 330 core\n" +
                    "\n" +
                    "layout(location=0) in vec3 aPosition;\n" +
                    "layout(location=1) in vec4 aColor;\n" +
                    "\n" +
                    "out vec4 fColor;\n" +
                    "\n" +
                    "void main()\n" +
                    "{\n" +
                    "   fColor = aColor;\n" +
                    "   gl_Position = vec4(aPosition, 1.0);\n" +
                    "}\n";

    private String fragmentShaderSource = "#version 330 core\n" +
                    "\n" +
                    "in vec4 fColor;\n" +
                    "\n" +
                    "out vec4 color;\n" +
                    "\n" +
                    "void main()\n" +
                    "{\n" +
                    "    color = fColor;\n" +
                    "}\n";

    private int vertexID, fragmentID, shaderProgram;

    public LevelEditorScene()
    {
        if (GlobalConstants.debugMode)
            System.out.println("Insdie Level Editor Scene");
    }

    @Override
    public void init()
    {
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexID, vertexShaderSource);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE)
        {
            int glInfoLogLength = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: default.glsl: Vertex Shader Compilation Failed");
            System.out.println("<--------------------------------------------------------------->");
            System.out.println(glGetShaderInfoLog(vertexID, glInfoLogLength));
            assert false : "";
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragmentID, fragmentShaderSource);
        glCompileShader(fragmentID);

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE)
        {
            int glInfoLogLength = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: default.glsl: Fragment Shader Compilation Failed");
            System.out.println("<--------------------------------------------------------------->");
            System.out.println(glGetShaderInfoLog(fragmentID, glInfoLogLength));
            assert false : "";
        }
        
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE)
        {
            int glInfoLogLength = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("Error: default.glsl: Linking of Shaders Failed");
            System.out.println("<--------------------------------------------------------------->");
            System.out.println(glGetProgramInfoLog(shaderProgram, glInfoLogLength));
            assert false : "";
        }
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