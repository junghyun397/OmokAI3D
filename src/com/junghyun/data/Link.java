package com.junghyun.data;

import com.junghyun.ai.SavedPlate;
import com.junghyun.ai.engine.Stone;
import com.junghyun.data.Pos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Link {

    private Pos lastPoint = null;

    private int winPlayer = 0;
    private int winAi = 0;

    private int aiBlackWin = 0;
    private int aiWhiteWin = 0;
    private int aiDraw = 0;

    private int aiLoopCount = 0;
    private int aiLoops = 0;

    private int sameCount = 0;

    private List<SavedPlate> plates_500 = new ArrayList<>();
    private List<SavedPlate> plates_2000 = new ArrayList<>();
    private List<SavedPlate> plates_3000 = new ArrayList<>();

    public Pos getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(Pos point) {
        this.lastPoint = point;
    }

    public int getWinPlayer() {
        return this.winPlayer;
    }

    public void addWinPlayer() {
        this.winPlayer++;
    }

    public int getWinAi() {
        return this.winAi;
    }

    public void addWinAi() {
        this.winAi++;
    }

    public void addAiBlackWin() {
        this.aiBlackWin++;
    }

    public void addAiWhiteWin() {
        this.aiWhiteWin++;
    }

    public void addAiDraw() {
        this.aiDraw++;
    }

    public void addAiLoops() {
        this.aiLoops++;
    }

    public void delAiLoopCount() {
        this.aiLoops--;
    }

    public void setAiLoopCount(int loops) {
        this.aiLoopCount = loops;
    }

    public int getAiLoopCount() {
        return this.aiLoopCount;
    }

    public int getAiBlackWin() {
        return this.aiBlackWin;
    }

    public int getAiWhiteWin() {
        return this.aiWhiteWin;
    }

    public int getAiDraw() {
        return this.aiDraw;
    }

    public int getAiLoops() {
        return this.aiLoops;
    }

    public void addSameCount() {
        this.sameCount++;
    }

    public int getSameCount() {
        return this.sameCount;
   }

    public boolean checkupSavedPlate(Stone[][][] plate) {
        SavedPlate p1 = new SavedPlate(plate);
        if (p1.get_length() < 500) {
            for (SavedPlate aPlates_50 : plates_500) {
                if (aPlates_50.get_length() == p1.get_length()) {
                    if (Arrays.equals(aPlates_50.get_plate(), p1.get_plate())) {
                        return true;
                    }
                }
            }
            plates_500.add(p1);
        } else if (p1.get_length() < 2000) {
            for (SavedPlate aPlates_100 : plates_2000) {
                if (aPlates_100.get_length() == p1.get_length()) {
                    if (Arrays.equals(aPlates_100.get_plate(), p1.get_plate())) {
                        return true;
                    }
                }
            }
            plates_2000.add(p1);
        } else {
            for (SavedPlate aPlates_225 : plates_3000) {
                if (aPlates_225.get_length() == p1.get_length()) {
                    if (Arrays.equals(aPlates_225.get_plate(), p1.get_plate())) {
                        return true;
                    }
                }
            }
            plates_3000.add(p1);
        }
        return false;
    }

}
