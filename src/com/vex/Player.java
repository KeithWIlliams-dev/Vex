package com.vex;

import static org.lwjgl.glfw.GLFW.*;

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

    public void update(InputHandler inputHandler)
    {
        Vector2D playerPreviousPosition = new Vector2D(getXPosition(), getYPosition());

        if (inputHandler.isKeyPressed(GLFW_KEY_W) || inputHandler.isKeyHeld(GLFW_KEY_W))
            move(new Vector2D(0, 1*getSpeedMultiplier()));
        if (inputHandler.isKeyPressed(GLFW_KEY_S) || inputHandler.isKeyHeld(GLFW_KEY_S))
            move(new Vector2D(0, -1*getSpeedMultiplier()));
        if (inputHandler.isKeyPressed(GLFW_KEY_A) || inputHandler.isKeyHeld(GLFW_KEY_A))
            move(new Vector2D(-1*getSpeedMultiplier(), 0));
        if (inputHandler.isKeyPressed(GLFW_KEY_D) || inputHandler.isKeyHeld(GLFW_KEY_D))
            move(new Vector2D(1*getSpeedMultiplier(), 0));

        if (GlobalConstants.debugMode && (playerPreviousPosition.getX() != getXPosition() || playerPreviousPosition.getY() != getYPosition()))    
            System.out.println("Player <"+getXPosition()+","+getYPosition()+">");
    }

}