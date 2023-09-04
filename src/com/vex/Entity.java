package com.vex;

public class Entity 
{
    int entityXPosition;
    int entityYPosition;

    public Entity(int entityXPosition, int entityYPosition)
    {
        this.entityXPosition = entityXPosition;
        this.entityYPosition = entityYPosition;
    }

    public int getXPosition()
    {
        return entityXPosition;   
    }

    public int getYPosition()
    {
        return entityYPosition;
    }

    public void setXPosition(int xPosition)
    {
        entityXPosition = xPosition;
    }

    public void setYPosition(int yPosition)
    {
        entityYPosition = yPosition;
    }

}

