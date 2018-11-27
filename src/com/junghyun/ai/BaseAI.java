package com.junghyun.ai;

import com.junghyun.ai.engine.Game;
import com.junghyun.ai.engine.Stone;
import com.junghyun.data.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseAI {

    private Game game;

    public BaseAI(Game game) {
        this.game = game;
    }

    public Pos getAiPoint() {
        this.sumPoint();
        return this.getMax();
    }

    private void sumPoint() {
        game.resetAllPoint();
        this.sumDotPoints();
        this.sumRowPoints();
        this.sumOverlapPoint();
    }

    private void sumDotPoints() {
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                for (int z = 0; z < 15; z++) {
                    Stone stone = game.getPlate()[z][x][y];
                    if (stone.isStoneAdded()) {
                        if (stone.getColor() == game.getColor()) { //자신의 돌
                            this.addDotPoint(stone, AISetting.DEF_AI_POINT);
                        } else if (stone.getColor() != game.getColor()) { //상대의 돌
                            this.addDotPoint(stone, AISetting.DEF_PLAYER_POINT);
                        }
                    }
                }

            }
        }
    }

    private void sumRowPoints() {
        for (int layer = 0; layer < 15; layer++) {
            AIRow[] x_rows = new AIRow[15];
            AIRow[] y_rows = new AIRow[15];
            AIRow[] z_rows = new AIRow[15];

            AIRow[] xy_rows = new AIRow[29];
            AIRow[] yx_rows = new AIRow[29];

            AIRow[] zy_rows = new AIRow[29];
            AIRow[] yz_rows = new AIRow[29];

            AIRow[] xz_rows = new AIRow[29];
            AIRow[] zx_rows = new AIRow[29];

            for (int i = 0; i < 15; i++) {
                x_rows[i] = new AIRow(game.getXRow(layer, i, 0), game.getColor(), this.game);
                y_rows[i] = new AIRow(game.getYRow(layer, 0, i), game.getColor(), this.game);
                z_rows[i] = new AIRow(game.getZRow(i, layer, 0), game.getColor(), this.game);

                xy_rows[i] = new AIRow(game.getXYRow(layer, i, 0), game.getColor(), this.game);
                yx_rows[i] = new AIRow(game.getYXRow(layer, i, 0), game.getColor(), this.game);

                zy_rows[i] = new AIRow(game.getZYRow(i, layer, 0), game.getColor(), this.game);
                yz_rows[i] = new AIRow(game.getYZRow(i, layer, 0), game.getColor(), this.game);

                xz_rows[i] = new AIRow(game.getXZRow(i, layer, 0), game.getColor(), this.game);
                zx_rows[i] = new AIRow(game.getZXRow(i, layer, 0), game.getColor(), this.game);
            }

            int row_index = 14;
            for (int i = 0; i < 14; i++) {
                row_index++;
                xy_rows[row_index] = new AIRow(game.getXYRow(layer,0, i), game.getColor(), this.game);
                yx_rows[row_index] = new AIRow(game.getYXRow(layer,14, i), game.getColor(), this.game);

                zy_rows[row_index] = new AIRow(game.getZYRow(0,layer, i), game.getColor(), this.game);
                yz_rows[row_index] = new AIRow(game.getYZRow(14,layer, i), game.getColor(), this.game);

                zx_rows[row_index] = new AIRow(game.getZXRow(0, i, layer), game.getColor(), this.game);
                xz_rows[row_index] = new AIRow(game.getXZRow(14, i, layer), game.getColor(), this.game);
            }

            for (int i = 0; i < 15; i++) {
                x_rows[i].checkPoints();
                y_rows[i].checkPoints();
                z_rows[i].checkPoints();

                xy_rows[i].checkPoints();
                yx_rows[i].checkPoints();

                zy_rows[i].checkPoints();
                yz_rows[i].checkPoints();

                zx_rows[i].checkPoints();
                xz_rows[i].checkPoints();
            }

            for (int i = 16; i < 29; i++) {
                xy_rows[i].checkPoints();
                yx_rows[i].checkPoints();

                zy_rows[i].checkPoints();
                yz_rows[i].checkPoints();

                zx_rows[i].checkPoints();
                xz_rows[i].checkPoints();
            }
        }

    }

    private void sumOverlapPoint() {
        Stone[][][] plate = game.getPlate();
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                for (int z = 0; z < 15; z++) {
                    Stone stone = plate[z][x][y];
                    if (stone.getColor() == this.game.getPlayerColor()) { //플레이어
                        if ((stone.getFourCount(this.game.getPlayerColor()) > 0) && (stone.getThreeCount(this.game.getPlayerColor()) > 0)) { //4-3
                            game.addPoint(stone.getZ(), stone.getX(), stone.getY(), AISetting.PLAYER_MAKE_4_3_POINT);
                        } else if (stone.getFourCount(this.game.getPlayerColor()) > 1) { //4-4
                            game.addPoint(stone.getZ(), stone.getX(), stone.getY(), AISetting.PLAYER_MAKE_4_4_POINT);
                        }
                    } else { //인공지능
                        if ((stone.getFourCount(this.game.getAIColor()) > 0) && (stone.getThreeCount(this.game.getAIColor()) > 0)) { //4-3
                            game.addPoint(stone.getZ(), stone.getX(), stone.getY(), AISetting.MAKE_4_3_POINT);
                        } else if (stone.getFourCount(this.game.getAIColor()) > 1) { //4-4
                            game.addPoint(stone.getZ(), stone.getX(), stone.getY(), AISetting.MAKE_4_4_POINT);
                        }
                    }
                }

            }
        }
    }

    private void addDotPoint(Stone stone, int point) {
        Stone[][][] plate = game.getPlate();
        for (int in_x = 0; in_x < 3; in_x++) {
            for (int in_y = 0; in_y < 3; in_y++) {
                for (int in_z = 0; in_z < 3; in_z++) {
                    int n_x = stone.getX()+in_x-1;
                    int n_y = stone.getY()+in_y-1;
                    int n_z = stone.getZ()+in_z-1;
                    if (Pos.checkSize(n_z, n_x, n_y)) {
                        Stone n_stone = plate[n_z][n_x][n_y];
                        if (!n_stone.isStoneAdded()) {
                            n_stone.addPoint(point);
                            plate[n_z][n_x][n_y] = n_stone;
                        }
                    }
                }

            }
        }

        game.setPlate(plate);
    }

    private Pos getMax() {
        Stone[][][] plate = game.getPlate();

        int score_max = 0;
        List<Stone> max_stones = new ArrayList<>();

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                for (int z = 0; z < 15; z++) {
                    Stone stone = plate[z][x][y];
                    if (!stone.isStoneAdded()) {
                        if (stone.getPoint() > score_max) {
                            score_max = stone.getPoint();
                            max_stones.clear();
                            max_stones.add(stone);
                        } else if (stone.getPoint() == score_max) {
                            max_stones.add(stone);
                        }
                    }
                }
            }
        }

        Stone rs_stone;
        if (max_stones.size() == 1) {
            rs_stone = max_stones.get(0);
        } else {
            int rand = new Random().nextInt(max_stones.size());
            rs_stone = max_stones.get(rand);
        }
        return new Pos(rs_stone.getZ(), rs_stone.getX(), rs_stone.getY());
    }

}
