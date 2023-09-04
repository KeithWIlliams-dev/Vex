package com.vex;

import java.util.ArrayList;

public class EntityHandler 
{
    private ArrayList<Entity> entityList;

    public EntityHandler(long window, ArrayList<Entity> entityList)
    {
        this.entityList = entityList;
    }

    public void update(InputHandler inputHandler)
    {
        for (Entity entity : entityList) 
        {
                switch (entity.getClass().getSimpleName())
                {
                    case "Player":
                        ((Player) entity).update(inputHandler);
                        break;
                }
        }
    }

}