package com.junghyun.ai.engine;

import com.junghyun.data.Type;

public class Game {

    private Type.Color playerColor = Type.Color.BLACK;
    private Type.Color AIColor = Type.Color.WHITE;

    private int turns = 1;
    private Type.Color turnColor = Type.Color.BLACK;

    private Stone[][][] plate = new Stone[15][15][15]; //Z, X, Y

    public Game() {
        this.clearPlate();
    }

    public void resetAll() {
        this.turns = 0;
        this.turnColor = Type.Color.BLACK;
        this.clearPlate();
    }

    public Type.Color getPlayerColor() {
        return this.playerColor;
    }

    public Type.Color getAIColor() {
        return this.AIColor;
    }

    public void setPlayerColor(Type.Color color) {
        this.playerColor = color;
        this.AIColor = Type.flipColor(color);
    }

    public void clearPlate() {
        for (byte x = 0; x < 15 ; x++) {
            for (byte y = 0; y < 15 ; y++) {
                for (byte z = 0; z < 15; z++) {
                    this.plate[z][x][y] = new Stone(z, x, y);
                }
            }
        }
    }

    public void setPlate(Stone[][][] plate) {
        this.plate = plate;
    }

    public Stone[][][] getPlate() {
        return this.plate;
    }

    public int getTurns() {
        return this.turns;
    }

    public void setColor(Type.Color color) {
        this.turnColor = color;
    }

    public Type.Color getColor() {
        return this.turnColor;
    }

    public Row getXRow(int z, int x, int y) {
        return new Row(this.plate[z][x]);
    }

    public Row getYRow(int z, int x, int y) {
        Stone[] rows = new Stone[15];
        for (int i = 0; i < 15; i++) {
            rows[i] = this.plate[z][i][y];
        }
        return new Row(rows);
    }

    public Row getZRow(int z, int x, int y) {
        Stone[] rows = new Stone[15];
        for (int i = 0; i < 15; i++) {
            rows[i] = this.plate[i][x][y];
        }
        return new Row(rows);
    }

    public Row getXYRow(int z, int x, int y) {
        int temp_length = y-x;
        int rowx;
        int rowy;
        int length;

        if (temp_length > -1) { //양수
            rowx = 0;
            rowy = temp_length;
            length = 15-rowy;
        } else { //음수
            rowx = Math.abs(y-x);
            rowy = 0;
            length = 15-rowx;
        }

        Stone[] row = new Stone[length];

        for (int i = 0; i < length; i++) {
            row[i] = this.plate[z][rowx][rowy];
            rowx++;
            rowy++;
        }
        return new Row(row);
    }

    public Row getYXRow(int z, int x, int y) {
        int temp_length = y+x;
        int rowx;
        int rowy;
        int length;

        if (temp_length < 15) {
            rowx = temp_length;
            rowy = 0;
            length = Math.abs(temp_length)+1;
        } else {
            rowx = 14;
            rowy = Math.abs(temp_length-14);
            length = 15-rowy;
        }

        Stone[] row = new Stone[length];

        for (int i = 0; i < length; i++) {
            row[i] = this.plate[z][rowx][rowy];
            rowx--;
            rowy++;
        }
        return new Row(row);
    }

    public Row getZYRow(int z, int x, int y) {
        int temp_length = y-z;
        int rowx;
        int rowy;
        int length;

        if (temp_length > -1) { //양수
            rowx = 0;
            rowy = temp_length;
            length = 15-rowy;
        } else { //음수
            rowx = Math.abs(y-z);
            rowy = 0;
            length = 15-rowx;
        }

        Stone[] row = new Stone[length];

        for (int i = 0; i < length; i++) {
            row[i] = this.plate[rowx][x][rowy];
            rowx++;
            rowy++;
        }
        return new Row(row);
    }

    public Row getYZRow(int z, int x, int y) {
        int temp_length = z+y;
        int rowx;
        int rowy;
        int length;

        if (temp_length < 15) {
            rowx = temp_length;
            rowy = 0;
            length = Math.abs(temp_length)+1;
        } else {
            rowx = 14;
            rowy = Math.abs(temp_length-14);
            length = 15-rowy;
        }

        Stone[] row = new Stone[length];

        for (int i = 0; i < length; i++) {
            row[i] = this.plate[rowx][x][rowy];
            rowx--;
            rowy++;
        }
        return new Row(row);
    }

    public Row getZXRow(int z, int x, int y) {
        int temp_length = z-x;
        int rowx;
        int rowy;
        int length;

        if (temp_length > -1) { //양수
            rowx = 0;
            rowy = temp_length;
            length = 15-rowy;
        } else { //음수
            rowx = Math.abs(z-x);
            rowy = 0;
            length = 15-rowx;
        }

        Stone[] row = new Stone[length];

        for (int i = 0; i < length; i++) {
            row[i] = this.plate[rowx][rowy][y];
            rowx++;
            rowy++;
        }
        return new Row(row);
    }

    public Row getXZRow(int z, int x, int y) {
        int temp_length = z+x;
        int rowx;
        int rowy;
        int length;

        if (temp_length < 15) {
            rowx = temp_length;
            rowy = 0;
            length = Math.abs(temp_length)+1;
        } else {
            rowx = 14;
            rowy = Math.abs(temp_length-14);
            length = 15-rowy;
        }

        Stone[] row = new Stone[length];

        for (int i = 0; i < length; i++) {
            row[i] = this.plate[rowx][rowy][y];
            rowx--;
            rowy++;
        }
        return new Row(row);
    }

    public boolean canSetStone(int z, int x, int y) {
        return ((x >= 0) && (x < 15) && (y >= 0) && (y < 15) && (z >= 0) && (z < 15)) && !this.plate[z][x][y].isStoneAdded();
    }

    public boolean isWin(int z, int x, int y, Type.Color color) {
        return this.getXRow(z, x, y).findFive(color) || this.getYRow(z, x, y).findFive(color) || this.getZRow(z, x, y).findFive(color)
                || this.getXYRow(z, x, y).findFive(color) || this.getYXRow(z, x, y).findFive(color)
                || this.getYZRow(z, x, y).findFive(color) || this.getZYRow(z, x, y).findFive(color)
                || this.getXZRow(z, x, y).findFive(color) || this.getZXRow(z, x, y).findFive(color);
    }

    public void setStone(int z, int x, int y) {
        this.setStone(z, x, y, Type.flipColor(this.turnColor));
    }

    public void setStone(int z, int x, int y, Type.Color color) {
        Stone stone = this.plate[z][x][y];
        stone.setStone(color);
        this.plate[z][x][y] = stone;

        this.turns++;

        this.setColor(color);
    }

    public void addThreePoint(int z, int x, int y, Type.Color color) {
        Stone stone = this.plate[z][x][y];
        stone.addThreeCount(color);
        this.plate[z][x][y] = stone;
    }

    public void addFourPoint(int z, int x, int y, Type.Color color) {
        Stone stone = this.plate[z][x][y];
        stone.addFourCount(color);
        this.plate[z][x][y] = stone;
    }

    public int getPoint(int z, int x, int y) {
        Stone stone = this.plate[z][x][y];
        return stone.getPoint();
    }

    public void addPoint(int z, int x, int y, int point) {
        Stone stone = this.plate[z][x][y];
        stone.addPoint(point);
        this.plate[z][x][y] = stone;
    }

    public void resetPoint(int z, int x, int y) {
        Stone stone = this.plate[z][x][y];
        stone.resetPoint();
        this.plate[z][x][y] = stone;
    }

    public void resetAllPoint() {
        for (byte x = 0; x < 15 ; x++) {
            for (byte y = 0; y < 15 ; y++) {
                for (int z = 0; z < 15; z++) {
                    this.resetPoint(z, x, y);
                }
            }
        }
    }

    public boolean isFull() {
        int count = 0;
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                for (int z = 0; z < 15; z++) {
                    if (this.plate[z][x][y].isStoneAdded()) {
                        count++;
                    }
                }
            }
        }
        return count == 3375;
    }

}
