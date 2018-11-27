package com.junghyun.ui;

import com.junghyun.ai.AISetting;
import com.junghyun.ai.BaseAI;
import com.junghyun.ai.engine.Game;
import com.junghyun.data.Link;
import com.junghyun.data.Pos;
import com.junghyun.data.Type;
import com.junghyun.graphics.Drawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TUI {

    private BufferedReader bufferedReader;

    public TUI() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private Game game = new Game();
    private Link link = new Link();

    private String getInput() {
        try {
            return this.bufferedReader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void startInput() {
        this.clearScreen();

        AISetting.readSetting();

        System.out.print(Drawer.getLogoText());
        System.out.print("Z좌표 X좌표 Y좌표 를 입력해 착수, 뒤에 point 를 붙일시 평가치 출력\n");
        System.out.print("G 키를 AI vs 플레이어 대결, A 키를 눌러 인공지능 시뮬레이션 시작 : ");

        String rs;
        while (true) {
            rs = this.getInput();
            assert rs != null;
            switch (rs) {
                case "g":
                    this.choiceColor();
                    return;
                case "a":
                    this.choiceLoops();
                    return;
                default:
                    this.clearScreen();
                    System.out.print(Drawer.getLogoText() + "\n");
                    System.out.print("G 또는 A 키를 눌러서 시작 : ");
                    break;
            }
        }
    }

    private void choiceColor() {
        boolean choice_loop = true;
        Type.Color color = Type.Color.NON;

        this.clearScreen();

        System.out.print(Drawer.getLogoText());
        System.out.print("\n흑/ 백 선택, 1 을 누를시 흑, 2를 누를시 백 선택\n");
        System.out.print("1 또는 2를 눌러서 시작 : ");

        while (choice_loop) {
            String input = this.getInput();
            assert input != null;
            switch (input) {
                case "1":
                    choice_loop = false;

                    color = Type.Color.BLACK;

                    break;
                case "2":
                    choice_loop = false;

                    color = Type.Color.WHITE;

                    break;
                default:
                    this.clearScreen();
                    System.out.print(Drawer.getLogoText() + "\n");
                    System.out.print("1 또는 2를 눌러서 시작 : ");
                    break;
            }
        }

        if (color == Type.Color.WHITE) { //AI 흑
            this.game.setStone(7, 7, 7, Type.Color.BLACK);
            this.link.setLastPoint(new Pos(7, 7, 7));
        }

        this.game.setPlayerColor(color);

        this.clearScreen();

        this.gameLoop();
    }

    private void choiceLoops() {
        this.clearScreen();

        System.out.print(Drawer.getLogoText());
        System.out.print("\n\n시뮬레이션 할 횟수를 선택\n");
        System.out.print("시뮬레이션 할 회수를 숫자로 입력 : ");

        while (true) {
            String input = this.getInput();
            int loops;
            try {
                assert input != null;
                loops = Integer.parseInt(input);
                this.link.setAiLoopCount(loops);
                this.aiEnd();
                this.aiLoop();
                return;
            } catch (NumberFormatException e) {
                this.clearScreen();
                System.out.print(Drawer.getLogoText());
                System.out.print("\n\n\n숫자로 입력 : ");
            }
        }
    }

    private void aiLoop() {
        this.link.addAiLoops();
        while (true) {

            if (this.game.isFull()) {
                this.link.addAiDraw();
                this.aiEnd();
                this.aiLoop();
                return;
            }

            Pos ai_pos = new BaseAI(this.game).getAiPoint();
            this.game.setStone(ai_pos.getCompuZ(), ai_pos.getCompuX(), ai_pos.getCompuY());
            this.link.setLastPoint(ai_pos);

            if (this.game.isWin(ai_pos.getCompuZ(), ai_pos.getCompuX(), ai_pos.getCompuY(), Type.flipColor(this.game.getColor()))) { //승리 판정
                this.printAiInfo();
                if (this.game.getColor() == Type.Color.WHITE) {
                    this.link.addAiBlackWin();
                } else {
                    this.link.addAiWhiteWin();
                }
                this.aiEnd();
                break;
            }

            this.game.setPlayerColor(Type.flipColor(this.game.getAIColor()));
        }

        if (this.link.getAiLoops() < this.link.getAiLoopCount()) {
            this.aiLoop();
        } else {
            this.clearScreen();
            System.out.print("인공지능 흑" + this.link.getAiBlackWin() + "승, 백 " + this.link.getAiWhiteWin()+"승, 무승부 " + this.link.getAiDraw()+"건, 동형반복 "+ this.link.getSameCount() +"건\n" );
            System.out.print("엔터키를 눌러서 이어서 진행...");
            this.getInput();

            this.link = new Link();
            this.startInput();
        }
    }

    private void aiEnd() {

        if (this.link.checkupSavedPlate(this.game.getPlate())) {
            this.link.addSameCount();
            this.link.delAiLoopCount();
        }

        this.game.resetAll();
        this.game.setPlayerColor(Type.Color.BLACK);
        this.link.setLastPoint(new Pos(7,7,7));
        this.game.setStone(7,7,7, Type.Color.BLACK);
    }

    private void gameLoop() {
        String error_str = null;

        while (true) {

            this.printPlayerInfo(error_str);
            error_str = null;

            String cmd = this.getInput();
            assert cmd != null;
            Pos player_pos = this.getPlayerPoint(cmd);

            if (!player_pos.isError()) {
                if (this.game.canSetStone(player_pos.getCompuZ(), player_pos.getCompuX(), player_pos.getCompuY())) {
                    //오류없음, 착수
                    this.game.setStone(player_pos.getCompuZ(), player_pos.getCompuX(), player_pos.getCompuY());
                    this.link.setLastPoint(player_pos);

                    if (this.game.isWin(player_pos.getCompuZ(), player_pos.getCompuX(), player_pos.getCompuY(), this.game.getPlayerColor())) { //승리 판정
                        this.onWin();
                        break;
                    }

                    //AI 연산
                    Pos ai_pos = new BaseAI(this.game).getAiPoint();
                    this.game.setStone(ai_pos.getCompuZ(), ai_pos.getCompuX(), ai_pos.getCompuY());
                    this.link.setLastPoint(ai_pos);

                    if (this.game.isWin(ai_pos.getCompuZ(), ai_pos.getCompuX(), ai_pos.getCompuY(), (this.game.getAIColor()))) { //승리 판정
                        this.onLose();
                        break;
                    }

                } else {
                    error_str = String.valueOf(player_pos.getHumX()) + " " + player_pos.getHumY() + " 는 비어있는 자리가 아닙니다.";
                }
            } else {
                error_str = player_pos.getErrorStr();
            }
        }

        this.askRestart();
    }

    private void askRestart() {
        System.out.print("다시 하려면 r 키를 눌러주세요 : ");
        String input = this.getInput();
        assert input != null;
        if (input.equals("r")) {
            this.game.resetAll();
            this.choiceColor();
        } else {
            this.clearScreen();
            System.out.print(Drawer.getLogoText());
            System.out.print("게임이 종료 되었습니다. 최종 점수 플레이어:인공지능 - " + this.link.getWinPlayer()+ ":"+ this.link.getWinAi()+"\n");
        }
    }

    private void onWin() {
        this.clearScreen();
        System.out.print(Drawer.getGraphics(this.game, this.link));
        System.out.print("\n");
        System.out.print(this.getTurnInfo());
        System.out.print(", 플레이어가 오목을 만들었습니다. 플레이어 승리!\n");

        this.link.addWinPlayer();
    }

    private void onLose() {
        this.clearScreen();
        System.out.print(Drawer.getGraphics(this.game, this.link));
        System.out.print("\n");
        System.out.print(this.getTurnInfo());
        System.out.print(", 인공지능이 오목을 만들었습니다. 인공지능 승리!\n");

        this.link.addWinAi();
    }

    private Pos getPlayerPoint(String cmd) {

        String[] index_string = cmd.split(" ");
        int pos_x;
        int pos_y;
        int pos_z;

        try {
            pos_x = Pos.engToInt(index_string[1].toCharArray()[0]);
            pos_y = Integer.parseInt(String.valueOf(index_string[2]))-1;
            pos_z = Integer.parseInt(String.valueOf(index_string[0]))-1;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | NullPointerException e) {
            return new Pos("[숫자 알파벳 숫자 ] 형식으로 적어 주세요. 예시 - 8 h 8");
        }

        if (!Pos.checkSize(pos_z, pos_x, pos_y)) {
            return new Pos("1~15, A~O, 1~15 사이의 숫자와 알파벳을 입력 해주세요." + pos_z);
        }

        try {
            if (index_string[3].equals("point")) {
                return new Pos((pos_z+1) + " " + Pos.intToEng(pos_x) + " " + (pos_y+1) + " 의 평가치는 " + this.game.getPoint(pos_z, pos_x, pos_y) + "입니다.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return new Pos(pos_z, pos_x, pos_y);
        }
        return new Pos(pos_z, pos_x, pos_y);
    }

    private void clearScreen() {
        System.out.print(Drawer.getClearString());
    }

    private void printPlayerInfo(String error_str) {
        this.clearScreen();
        System.out.print(Drawer.getGraphics(this.game, this.link));

        System.out.print("\n");

        if (error_str != null) {
            System.out.print(error_str+"\n");
        }

        System.out.print(this.getTurnInfo());
        System.out.print(", [ 숫자 알파벳 숫자 ] 로 착수 위치 지정 :  ");
    }

    private void printAiInfo() {
        this.clearScreen();
        System.out.print(Drawer.getGraphics(this.game, this.link));
        System.out.print(this.link.getAiLoops() + " 번째 시뮬레이션...");
    }

    private String getTurnInfo() {
        String turn;
        if (this.game.getColor() == Type.Color.BLACK) {
            turn = "흑";
        } else {
            turn = "백";
        }
        return turn+" 제 "+ this.game.getTurns()+"수 ";
    }

}
