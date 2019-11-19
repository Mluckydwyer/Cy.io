package com.cyio.backend.model;

import java.awt.*;
import java.util.Random;

public class Entity {
    private int size;
    private String color;
    private int xPos;
    private int yPos;
    private int scoreValue;

    public Entity() {
        setSize((int) Math.round(Math.random() * 3));
        setxPos((int) Math.round(Math.random() * 1000));
        setyPos((int) Math.round(Math.random() * 1000));
        setScoreValue((int) Math.round(Math.random() * 200));
        setColor(getRandomColor());
    }

    private String getRandomColor() {
        return String.format("#%06x", new Random().nextInt(0xffffff + 1));
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }
}
