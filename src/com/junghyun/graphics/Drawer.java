package com.junghyun.graphics;

import com.junghyun.ai.engine.Game;
import com.junghyun.ai.engine.Stone;
import com.junghyun.data.Link;
import com.junghyun.data.Type;

public class Drawer {

    private final static String WHITE = "●";
    private final static String BLACK = "○";

    private final static String LAST_WHITE = "◆";
    private final static String LAST_BLACK = "◇";

    private final static String CORNER_T_L = "┌";
    private final static String CORNER_T_R = "┐";
    private final static String CORNER_B_L = "└";
    private final static String CORNER_B_R = "┘";

    private final static String CORNER_T_U = "┬";
    private final static String CORNER_T_D = "┴";

    private final static String CORNER_L = "├";
    private final static String CORNER_R = "┤";

    private final static String CROSS = "┼";

    private final static String BLANK = "　";

    private final static String[] FIXED_NUM = {"０", "　１", "　２", "　３", "　４", "　５", "　６", "　７", "　８", "　９", "１０", "１１", "１２", "１３", "１４", "１５"};
    private final static String[] FIXED_ENG = {"Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ"};

    private final static String LOGO_TEXT = "  /$$$$$$                          /$$        /$$$$$$  /$$$$$$$ \n" +
            " /$$__  $$                        | $$       /$$__  $$| $$__  $$\n" +
            "| $$  \\ $$ /$$$$$$/$$$$   /$$$$$$ | $$   /$$|__/  \\ $$| $$  \\ $$\n" +
            "| $$  | $$| $$_  $$_  $$ /$$__  $$| $$  /$$/   /$$$$$/| $$  | $$\n" +
            "| $$  | $$| $$ \\ $$ \\ $$| $$  \\ $$| $$$$$$/   |___  $$| $$  | $$\n" +
            "| $$  | $$| $$ | $$ | $$| $$  | $$| $$_  $$  /$$  \\ $$| $$  | $$\n" +
            "|  $$$$$$/| $$ | $$ | $$|  $$$$$$/| $$ \\  $$|  $$$$$$/| $$$$$$$/\n" +
            " \\______/ |__/ |__/ |__/ \\______/ |__/  \\__/ \\______/ |_______/ \n";

    private final static String CLEAR_STRING = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    private static String graphics_string = null;

    public static String getGraphics(Game game, Link link) {
        Drawer.updateGraphics(game.getPlate(), link);
        return graphics_string;
    }

    private static void updateGraphics(Stone[][][] plate, Link link) {
        StringBuilder result = new StringBuilder();
        for (int law = 0; law < 3; law++) {

            //상단 알파벳열 그리기
            result.append("　　");
            for (int xn = 0; xn < 5; xn++) {
                for (int x = 0; x < 15; x++) {
                    result.append(FIXED_ENG[x]);
                }
                result.append("　　　");
            }
            result.append("\n");

            //오목판 그리기
            for (int y = 0; y < 15; y++) {
                for (int xn = 0; xn < 5; xn++) {
                    result.append(FIXED_NUM[y + 1]);
                    for (int x = 0; x < 15; x++) {
                        Stone pro_stone = plate[law*5 + xn][x][y];
                        if (pro_stone.isStoneAdded()) {
                            if (pro_stone.getColor() == Type.Color.BLACK) {
                                if ((pro_stone.getX() == link.getLastPoint().getCompuX()) && (pro_stone.getY() == link.getLastPoint().getCompuY()) && (pro_stone.getZ() == link.getLastPoint().getCompuZ())) {
                                    result.append(LAST_BLACK);
                                } else {
                                    result.append(BLACK);
                                }
                            } else {
                                if ((pro_stone.getX() == link.getLastPoint().getCompuX()) && (pro_stone.getY() == link.getLastPoint().getCompuY()) && (pro_stone.getZ() == link.getLastPoint().getCompuZ())) {
                                    result.append(LAST_WHITE);
                                } else {
                                    result.append(WHITE);
                                }
                            }
                        } else {
                            result.append(Drawer.getEmptyCode(x, y));
                        }
                    }
                    result.append(BLANK);
                }
                result.append("\n");
            }
            result.append("\n");
        }

        Drawer.graphics_string = result.toString();
    }

    private static String getEmptyCode(int x, int y) {

        if (y == 0) {
            switch (x) {
                case 0:
                    return CORNER_T_L;
                case 14:
                    return CORNER_T_R;
            }
            return CORNER_T_U;
        } else if (y == 14) {
            switch (x) {
                case 0:
                    return CORNER_B_L;
                case 14:
                    return CORNER_B_R;
            }
            return CORNER_T_D;
        } else if (x == 0) {
            return CORNER_L;
        } else if (x == 14) {
            return CORNER_R;
        }
        return CROSS;
    }

    public static String getClearString() {
        return CLEAR_STRING;
    }

    public static String getLogoText() {
        return LOGO_TEXT;
    }

}
