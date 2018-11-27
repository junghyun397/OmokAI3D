package com.junghyun.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AISetting {

    static int DEF_AI_POINT = 2; //AI 돌 근처에 부여되는 포인트
    static int DEF_PLAYER_POINT = 1; //플레이어 돌 근처에 부여되는 포인트


    static int WIN_5_POINT = 1000000; //놓는 즉시 승리
    static int LOSE_5_POINT = 100000; //상대가 놓는 즉시 패배, 5 생성

    static int PLAYER_MAKE_OPEN_4_POINT = 17000; //상대가 열린 4를 만들수 있는 포인트
    static int PLAYER_MAKE_CLOSE_4_POINT = 500; //상대가 닫힌 4를 만들수 있는 포인트
    static int PLAYER_MAKE_OPEN_3_POINT = 600; //상대가 열린 3을 만들수 있는 포인트

    static int PLAYER_MAKE_4_3_POINT = 1000; //상대가 4-3을 만들수 있는 포인트
    static int PLAYER_MAKE_4_4_POINT = 10000; //상대가 4-4을 만들수 있는 포인트

    static int MAKE_OPEN_4_POINT = 40000; //열린 4를 만들수 있는 포인트
    static int MAKE_CLOSE_4_POINT = 700; //닫힌 4를 만들수 있는 포인트
    static int MAKE_OPEN_3_POINT = 800; //열린 3을 만들수 있는 포인트

    static int MAKE_4_3_POINT = 18000; //4-3을 만들수 있는 포인트
    static int MAKE_4_4_POINT = 20000; //4-4을 만들수 있는 포인트



    final static int[] THREE_CASE_1 = {1, 1, 0, 0}; //●●○○

    final static int[] THREE_CASE_2 = {1, 0, 1, 0}; //●○●○

    final static int[] THREE_CASE_3 = {1, 0, 0, 1}; //●○○●


    final static int[] FOUR_CASE_1_L = {1, 1, 0, 0, 1}; //●●○○●
    final static int[] FOUR_CASE_1_R = {1, 0, 0, 1, 1}; //●○○●●

    final static int[] FOUR_CASE_2_L = {1, 1, 0, 1, 0}; //●●○●○
    final static int[] FOUR_CASE_2_R = {0, 1, 0, 1, 1}; //○●○●●

    final static int[] FOUR_CASE_3_L = {1, 1, 1, 0, 0}; //●●●○○
    final static int[] FOUR_CASE_3_R = {0, 0, 1, 1, 1}; //○○●●●

    final static int[] FOUR_CASE_4 = {1, 0, 1, 0, 1}; //●○●○●

    public static void readSetting() {
        System.out.print("평가치 설정 로딩중...\n");
        try (BufferedReader br = new BufferedReader(new FileReader("points.txt"))) {
            while (true) {
                String line = br.readLine();
                if ((line != null) && (!line.equals("END") && (!line.equals("")))) {

                    System.out.print("로딩: " + line + "\n");

                    String[] rs = line.split(" ");
                    String key = rs[0];
                    int value = Integer.parseInt(rs[1]);

                    switch (key) {
                        case "DEF_AI_POINT":
                            DEF_AI_POINT = value;
                            break;
                        case "DEF_PLAYER_POINT":
                            DEF_PLAYER_POINT = value;
                            break;
                        case "WIN_5_POINT":
                            WIN_5_POINT = value;
                            break;
                        case "LOSE_5_POINT":
                            LOSE_5_POINT = value;
                            break;
                        case "PLAYER_MAKE_OPEN_4_POINT":
                            PLAYER_MAKE_OPEN_4_POINT = value;
                            break;
                        case "PLAYER_MAKE_CLOSE_4_POINT":
                            PLAYER_MAKE_CLOSE_4_POINT = value;
                            break;
                        case "PLAYER_MAKE_OPEN_3_POINT":
                            PLAYER_MAKE_OPEN_3_POINT = value;
                            break;
                        case "PLAYER_MAKE_4_3_POINT":
                            PLAYER_MAKE_4_3_POINT = value;
                            break;
                        case "PLAYER_MAKE_4_4_POINT":
                            PLAYER_MAKE_4_4_POINT = value;
                            break;
                        case "MAKE_OPEN_4_POINT":
                            MAKE_OPEN_4_POINT = value;
                            break;
                        case "MAKE_CLOSE_4_POINT":
                            MAKE_CLOSE_4_POINT = value;
                            break;
                        case "MAKE_OPEN_3_POINT":
                            MAKE_OPEN_3_POINT = value;
                            break;
                        case "MAKE_4_3_POINT":
                            MAKE_4_3_POINT = value;
                            break;
                        case "MAKE_4_4_POINT":
                            MAKE_4_4_POINT = value;
                            break;
                    }
                } else if ("END".equals(line)) {
                    System.out.print("불러오기 성공");
                    return;
                }
            }
        } catch (IOException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.print("파일 불러오기 실패, 기본 설정 사용");
        }
    }

}
