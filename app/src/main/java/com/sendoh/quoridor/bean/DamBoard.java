package com.sendoh.quoridor.bean;

import com.sendoh.quoridor.presenter.GamePresenter;

/**
 * @auther Lin Peita <peitalin@imatrictech.com>
 * @date 2019/8/26 15:50
 * @describe Make some explanation.
 */
public class DamBoard {
    public static final int LANDSCAPE = 0;
    public static final int VERTICAL = 1;

    private int x;
    private int y;
    private boolean placed = false;
    private int orientation = LANDSCAPE;
    private int whichPlayer = GamePresenter.NUMBER_ONE;

    public DamBoard(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getWhichPlayer() {
        return whichPlayer;
    }

    public void setWhichPlayer(int whichPlayer) {
        this.whichPlayer = whichPlayer;
    }
}
