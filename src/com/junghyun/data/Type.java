package com.junghyun.data;

public class Type {

    public enum Color {WHITE, BLACK, NON}

    public static Color flipColor(Color color) {
        if (color == Color.WHITE) return Color.BLACK;
        else return Color.WHITE;
    }

}
