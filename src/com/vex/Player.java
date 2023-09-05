package com.vex;

import static org.lwjgl.glfw.GLFW.*;

import com.vex.util.GlobalConstants;

public class Player extends Entity
{
    private int speedMultiplier;
    private Vector2D movementVector;

    public Player(long window, int xPosition, int yPosition, int speedMultiplier)
    {
        super(xPosition, yPosition);

        this.speedMultiplier = speedMultiplier;
        movementVector = new Vector2D(0, 0);
    }

    public int getSpeedMultiplier()
    {
        return speedMultiplier;
    }

    public Vector2D getMovementVector()
    {
        return movementVector;
    }

    public void setSpeedMultiplier(int speedMultiplier)
    {
        this.speedMultiplier = speedMultiplier;
    }

    public void setMovementVector(Vector2D movementVector)
    {
        this.movementVector = movementVector;
    }

    public void setMovementVector(int x, int y)
    {
        this.movementVector.setX(x);
        this.movementVector.setY(y);
    }

    public void setMovementVectorX(int x)
    {
        this.movementVector.setX(x);
    }

    public void setMovementVectorY(int y)
    {
        this.movementVector.setY(y);
    }

    private void move(Vector2D direction)
    {
        int deltaX = direction.getX() * speedMultiplier;
        int deltaY = direction.getY() * speedMultiplier;

        setXPosition(getXPosition() + deltaX);
        setYPosition(getYPosition() + deltaY);
    }

    public void update()
    {
        Vector2D playerPreviousPosition = new Vector2D(getXPosition(), getYPosition());

        if (InputHandler.isKeyPressed(GLFW_KEY_W) || InputHandler.isKeyHeld(GLFW_KEY_W))
            move(new Vector2D(0, 1*getSpeedMultiplier()));
        if (InputHandler.isKeyPressed(GLFW_KEY_S) || InputHandler.isKeyHeld(GLFW_KEY_S))
            move(new Vector2D(0, -1*getSpeedMultiplier()));
        if (InputHandler.isKeyPressed(GLFW_KEY_A) || InputHandler.isKeyHeld(GLFW_KEY_A))
            move(new Vector2D(-1*getSpeedMultiplier(), 0));
        if (InputHandler.isKeyPressed(GLFW_KEY_D) || InputHandler.isKeyHeld(GLFW_KEY_D))
            move(new Vector2D(1*getSpeedMultiplier(), 0));

        if (InputHandler.mouseButtonDown(0))
        {
            if (GlobalConstants.debugMode)
                System.out.println("Left Click");
        }
        if (InputHandler.mouseButtonDown(1))
        {
            if (GlobalConstants.debugMode)
                System.out.println("Right Click");
        }
        if (InputHandler.mouseButtonDown(2))
        {
            if (GlobalConstants.debugMode)
                System.out.println("Middle Mouse Button Clicked");
        }
        if (InputHandler.isDragging())
        {
            if (GlobalConstants.debugMode)
                System.out.println("Mouse is dragging");
        }
        if (GlobalConstants.debugMode && (playerPreviousPosition.getX() != getXPosition() || playerPreviousPosition.getY() != getYPosition()))
        {    
            if (GlobalConstants.debugMode)
                System.out.println("Player <"+getXPosition()+","+getYPosition()+">");
        }
    }

}