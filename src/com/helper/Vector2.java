package com.helper;

public class Vector2 {
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Vector2 setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2 setY(int y) {
        this.y = y;
        return this;
    }

    public Vector2 set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public int getArea() {
        return x * y;
    }
}
