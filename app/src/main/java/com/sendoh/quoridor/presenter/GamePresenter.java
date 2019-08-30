package com.sendoh.quoridor.presenter;

import com.sendoh.quoridor.R;
import com.sendoh.quoridor.bean.Chess;
import com.sendoh.quoridor.bean.DamBoard;
import com.sendoh.quoridor.bean.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther Lin Peita <peitalin@imatrictech.com>
 * @date 2019/8/26 16:03
 * @describe Make some explanation.
 */
public class GamePresenter implements IGamePresenter {
    public static final int NUMBER_ONE = 0;
    public static final int NUMBER_TWO = 1;
    public static final int NUMBER_THREE = 2;
    public static final int NUMBER_FOUR = 3;

    public static final int ACTION_CHESS = 1;
    public static final int ACTION_DAMBOARD = 2;

    private int currentPlayer = NUMBER_ONE;
    private int currentOrientation = DamBoard.LANDSCAPE;
    private int currentAction = ACTION_CHESS;

    private Player[] players;
    private Map<Integer, DamBoard> placedDamBoards;
    private DamBoard moveDamBoard;

    public GamePresenter() {

    }

    @Override
    public void initGame(int playerNumber) {
        players = new Player[playerNumber];
        placedDamBoards = new HashMap<Integer, DamBoard>();
        moveDamBoard = new DamBoard(0, 0);

        if (playerNumber == 2) {
            players[0] = new Player(NUMBER_ONE, 4, 8, R.color.customred);
            players[1] = new Player(NUMBER_TWO, 4, 0, R.color.royalblue);
//            players[2] = new Player(NUMBER_THREE, 0, 4, R.color.seagreen);
//            players[3] = new Player(NUMBER_FOUR, 8, 4, R.color.goldenrod);
        }
    }

    @Override
    public void switchPlayer(int playerNumber) {
        currentPlayer = playerNumber;
    }

    @Override
    public void switchDamBoard(int orientation) {
        currentOrientation = orientation;
    }

    @Override
    public void switchActionType(int actionType) {
        currentAction = actionType;
    }

    @Override
    public void moveChess(int direction) {
        Chess chess = players[currentPlayer].getChess();
        if (chess == null)
            return;
        if (chess.overStep(direction) || !permitMove(chess.getX(), chess.getY(), direction))
            return;

        chess.move(direction);

        if (existChess(chess.getX(), chess.getY())) {
            moveChess(direction);
        }
    }

    private boolean existChess(int x, int y) {
        boolean exist = false;
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            if (player.getNumber() == currentPlayer)
                continue;
            Chess chessOther = player.getChess();
            if (chessOther.getX() == x && chessOther.getY() == y) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    private boolean permitMove(int chessX, int chessY, int direction) {
        boolean permit = true;
        int db1X = 0;
        int db1y = 0;
        int db2X = 0;
        int db2y = 0;
        int orientation = DamBoard.LANDSCAPE;

        if (direction == Chess.DIRECTION_UP) {
            db1X = chessX;
            db1y = chessY;
            db2X = chessX + 1;
            db2y = chessY;
            orientation = DamBoard.LANDSCAPE;
        } else if (direction == Chess.DIRECTION_DOWN) {
            db1X = chessX;
            db1y = chessY + 1;
            db2X = chessX + 1;
            db2y = chessY + 1;
            orientation = DamBoard.LANDSCAPE;
        } else if (direction == Chess.DIRECTION_LEFT) {
            db1X = chessX;
            db1y = chessY;
            db2X = chessX;
            db2y = chessY + 1;
            orientation = DamBoard.VERTICAL;
        } else if (direction == Chess.DIRECTION_RIGHT) {
            db1X = chessX + 1;
            db1y = chessY;
            db2X = chessX + 1;
            db2y = chessY + 1;
            orientation = DamBoard.VERTICAL;
        }

        DamBoard damBoard1 = placedDamBoards.get(GamePresenter.getDamBoardKey(db1X, db1y));
        DamBoard damBoard2 = placedDamBoards.get(GamePresenter.getDamBoardKey(db2X, db2y));
        if (damBoard1 != null && damBoard1.getOrientation() == orientation
                || damBoard2 != null && damBoard2.getOrientation() == orientation)
            permit = false;

        return permit;
    }

    @Override
    public void moveDamBoard(int rawX, int rawY) {
        moveDamBoard.setPlaced(true);
        moveDamBoard.setOrientation(currentOrientation);
        moveDamBoard.setX(rawX);
        moveDamBoard.setY(rawY);
    }

    @Override
    public void placedDamBoard(int x, int y) {
        moveDamBoard.setPlaced(false);

        if (x < 0 || y < 0)
            return;

        if (!permitPlaced(x, y, currentOrientation))
            return;

        DamBoard damBoard = new DamBoard(x, y);
        damBoard.setPlaced(true);
        damBoard.setOrientation(currentOrientation);
        damBoard.setWhichPlayer(currentPlayer);
        placedDamBoards.put(getDamBoardKey(x, y), damBoard);
    }

    private boolean permitPlaced(int x, int y, int currentOrientation) {
        boolean permit = true;

        boolean landCenter = false;
        boolean landLeft = false;
        boolean landRight = false;
        boolean verCenter = false;
        boolean verUp = false;
        boolean verDown = false;

        DamBoard centerDB = placedDamBoards.get(getDamBoardKey(x, y));
        DamBoard leftDB = placedDamBoards.get(getDamBoardKey(x - 1, y));
        DamBoard rightDB = placedDamBoards.get(getDamBoardKey(x + 1, y));
        DamBoard upDB = placedDamBoards.get(getDamBoardKey(x, y - 1));
        DamBoard downDB = placedDamBoards.get(getDamBoardKey(x, y + 1));

        if (centerDB != null && centerDB.getOrientation() == DamBoard.LANDSCAPE)
            landCenter = true;
        if (leftDB != null && leftDB.getOrientation() == DamBoard.LANDSCAPE)
            landLeft = true;
        if (rightDB != null && rightDB.getOrientation() == DamBoard.LANDSCAPE)
            landRight = true;
        if (centerDB != null && centerDB.getOrientation() == DamBoard.VERTICAL)
            verCenter = true;
        if (upDB != null && upDB.getOrientation() == DamBoard.VERTICAL)
            verUp = true;
        if (downDB != null && downDB.getOrientation() == DamBoard.VERTICAL)
            verDown = true;

        if (currentOrientation == DamBoard.LANDSCAPE
                && (landCenter || landLeft || landRight || verCenter || (verUp && verDown))
                || currentOrientation == DamBoard.VERTICAL
                && (verCenter || verUp || verDown || landCenter || (landLeft && landRight)))
            permit = false;

        return permit;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public Player getPlayer(int number) {
        return players[number];
    }

    @Override
    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    @Override
    public Map<Integer, DamBoard> getDamBoards() {
        return placedDamBoards;
    }

    @Override
    public DamBoard getMoveDamBoard() {
        return moveDamBoard;
    }

    @Override
    public int getActionType() {
        return currentAction;
    }

    public static int getDamBoardKey(int x, int y) {
        return x * 10 + y;
    }
}
