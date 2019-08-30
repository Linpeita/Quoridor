package com.sendoh.quoridor.bean;

/**
 * @auther Lin Peita <peitalin@imatrictech.com>
 * @date 2019/8/26 15:48
 * @describe Make some explanation.
 */
public class Chess {
    public final static int DIRECTION_UP = 1;
    public final static int DIRECTION_DOWN = 2;
    public final static int DIRECTION_LEFT = 3;
    public final static int DIRECTION_RIGHT = 4;

    private int x;
    private int y;

    public Chess(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public void move(int direction) {
        switch (direction) {
            case DIRECTION_UP:
                y = y - 1;
                break;
            case DIRECTION_DOWN:
                y = y + 1;
                break;
            case DIRECTION_LEFT:
                x = x - 1;
                break;
            case DIRECTION_RIGHT:
                x = x + 1;
                break;
            default:
                break;
        }
    }

    public boolean overStep(int direction) {
        if ((direction == DIRECTION_UP && y <= 0)
                || (direction == DIRECTION_DOWN && y >= 8)
                || (direction == DIRECTION_LEFT && x <= 0)
                || (direction == DIRECTION_RIGHT && x >= 8))
            return true;
        return false;
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
}
