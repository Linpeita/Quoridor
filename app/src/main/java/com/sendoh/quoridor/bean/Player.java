package com.sendoh.quoridor.bean;

/**
 * @auther Lin Peita <peitalin@imatrictech.com>
 * @date 2019/8/28 16:11
 * @describe Make some explanation.
 */
public class Player {
    private int number;
    private Chess chess;
    private int damBoardNumber = 0;
    private int colorType;

    public Player() {

    }

    public Player(int number, int x, int y, int colorType) {
        this.number = number;
        chess = new Chess(x, y);
        this.colorType = colorType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Chess getChess() {
        return chess;
    }

    public void setChess(Chess chess) {
        this.chess = chess;
    }

    public int getDamBoardNumber() {
        return damBoardNumber;
    }

    public void setDamBoardNumber(int damBoardNumber) {
        this.damBoardNumber = damBoardNumber;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }
}
