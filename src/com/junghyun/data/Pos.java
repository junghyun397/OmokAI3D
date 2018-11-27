package com.junghyun.data;

public class Pos {

    private int compuZ = 0;
    private int compuX = 0;
    private int compuY = 0;

    private int humZ = 0;
    private char humX = 0;
    private int humY = 0;

    private String error_str = null;
    private boolean is_error = false;

    public Pos(int z, int x, int y) {
        this.compuX = x;
        this.compuY = y;
        this.compuZ = z;

        this.humX = Pos.intToEng(this.compuX);
        this.humY = this.compuY +1;
        this.humZ = this.compuZ +1;
    }

    public Pos(String str) {
        this.is_error = true;
        this.error_str = str;
    }

    //기능

    public static int engToInt(char str) {
        return str-97;
    }

    public static char intToEng(int inter) {
        return (char) ((char) inter+97);
    }

    public static boolean checkSize(int z, int x, int y) {
        return (x >= 0) && (x < 15) && (y >= 0) && (y < 15) && (z >= 0) && (z < 15);
    }

    //데이터 출력

    public int getCompuX() {
        return this.compuX;
    }

    public int getCompuY() {
        return this.compuY;
    }

    public int getCompuZ() {
        return this.compuZ;
    }

    public char getHumX() {
        return this.humX;
    }

    public int getHumY() {
        return this.humY;
    }

    public int getHumZ() {
        return this.humZ;
    }

    public boolean isError() {
        return this.is_error;
    }

    public String getErrorStr() {
        return this.error_str;
    }

}
