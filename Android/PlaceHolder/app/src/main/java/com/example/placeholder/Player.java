package com.example.placeholder;

public class Player
{
    private String name;
    private int score;

    public Player()
    {

    }
    public Player(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    public String getName()
    {
        return name;
    }

    public int getScore()
    {
        return score;
    }

    public String getScoreString()
    {
        int i = getScore();
        return "" + i;
    }
}
