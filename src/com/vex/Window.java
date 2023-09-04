package com.vex;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window 
{
	private boolean debugMode = GlobalConstants.debugMode;
	private static Window window = null;
	private int width, height;
	private String title;
	private long windowId;

	public Window() 
	{
		this.width = 1920;
		this.height = 1080;
		this.title = "Vex";
	}

	public static Window get()
	{
		if (Window.window == null)
			Window.window = new Window();

		return Window.window;
	}

	public void run() 
    {
		if(debugMode)
			System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		initializeWindow();
		loop();

		glfwFreeCallbacks(windowId);
		glfwDestroyWindow(windowId);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void initializeWindow() 
    {
		GLFWErrorCallback.createPrint(System.err).set();

		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		        
		glfwDefaultWindowHints(); 
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); 
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

		windowId = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		if ( windowId == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		try ( MemoryStack stack = stackPush() ) 
        {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			glfwGetWindowSize(windowId, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				windowId,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		glfwSetCursorPosCallback(windowId, InputHandler::mousePositionCallback);
		glfwSetMouseButtonCallback(windowId, InputHandler::mouseButtonCallback);
		glfwSetScrollCallback(windowId, InputHandler::mouseScrollCallback);

		glfwMakeContextCurrent(windowId);
		glfwSwapInterval(1);

		GL.createCapabilities();
		glClearColor(1.0f, 0.0f, 1.0f, 1.0f);

		glfwShowWindow(windowId);
	}

	private void loop() 
    {
		Player player = new Player(windowId, 0, 0, 1);
        ArrayList<Entity> entityList = new ArrayList<>();
		entityList.add(player);

		InputHandler inputHandler = new InputHandler(windowId);
		EntityHandler entityHandler = new EntityHandler(windowId, entityList);

		while ( !glfwWindowShouldClose(windowId) ) 
        {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			glfwSwapBuffers(windowId); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();

            inputHandler.update();
			entityHandler.update();
		}
	}
}
