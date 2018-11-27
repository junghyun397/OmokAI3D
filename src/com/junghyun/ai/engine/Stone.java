package com.junghyun.ai.engine;

import com.junghyun.data.Type;

public class Stone {

    private int z;
    private int x;
    private int y;

    private int point = 0;

    private boolean isStoneAdded = false;

    private Type.Color color = Type.Color.NON;

    private int blackThreeCount = 0;
    private int blackFourCount = 0;

    private int whiteThreeCount = 0;
    private int whiteFourCount = 0;

    public Stone(int z, int x, int y) {
        this.z = z;
        this.x = x;
        this.y = y;
    }

    public boolean isStoneAdded() {
        return this.isStoneAdded;
    }

    public void setStone(Type.Color color) {
        this.isStoneAdded = true;
        this.color = color;
    }

    public Type.Color getColor() {
        return this.color;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void addThreeCount(Type.Color color) {
        if (color == Type.Color.BLACK) this.blackThreeCount++;
        else this.whiteThreeCount++;
    }

    public int getThreeCount(Type.Color color) {
        if (color == Type.Color.BLACK) return this.blackThreeCount;
        else return this.whiteThreeCount;
    }

    public void addFourCount(Type.Color color) {
        if (color == Type.Color.BLACK) this.blackFourCount++;
        else this.whiteFourCount++;
    }

    public int getFourCount(Type.Color color) {
        if (color == Type.Color.BLACK) return this.blackFourCount;
        else return this.whiteFourCount;
    }

    public void addPoint(int point) {
        this.point += point;
    }

    public void resetPoint() {
        this.blackThreeCount = 0;
        this.blackFourCount = 0;
        this.whiteThreeCount = 0;
        this.whiteFourCount = 0;

        this.point = 0;
    }

    public int getPoint() {
        return this.point;
    }

}
