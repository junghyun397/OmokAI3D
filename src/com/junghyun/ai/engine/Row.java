package com.junghyun.ai.engine;

import com.junghyun.data.Type;

public class Row {

    private Stone[] row;

    private int length;

    private boolean passAll = false;

    Row(Stone[] row) {
        this.row = row;
        this.length = row.length;
        if (length < 5) {
            this.passAll = true;
        }
    }

    boolean findFive(Type.Color color) {
        if (this.passAll) return false;
        else {
            int matchLength = 0;
            for (int i = 0; i < this.length; i++) {
                if (color == this.row[i].getColor()) matchLength++;
                if (matchLength > 4) return true;
                if (color != this.row[i].getColor()) matchLength = 0;
            }
        }
        return false;
    }

    public Stone[] getRow() {
        return this.row;
    }

}
