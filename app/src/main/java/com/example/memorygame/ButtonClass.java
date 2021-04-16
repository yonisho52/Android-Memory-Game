package com.example.memorygame;

public class ButtonClass
{
    private int id;
    private int arrId;

    ButtonClass(int id, int arrId)
    {
        this.id=id;
        this.arrId=arrId;
    }

    public int getId() { return id; }
    public int getArrId() { return arrId; }
}
