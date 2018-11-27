package com.junghyun.ai;

import com.junghyun.ai.engine.Stone;

public class SavedPlate {

    private Stone[][][] plate;

    private int length = 0;

    public SavedPlate(Stone[][][] arry) {
        this.plate = arry;

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                for (int z = 0; z < 15; z++) {
                    if (arry[z][x][y].isStoneAdded()) {
                        this.length++;
                    }
                }

            }
        }
    }

    public Stone[][][] get_plate() {
        return plate;
    }

    public int get_length() {
        return this.length;
    }
}
