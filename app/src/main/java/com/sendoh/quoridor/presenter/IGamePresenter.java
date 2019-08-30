package com.sendoh.quoridor.presenter;

import com.sendoh.quoridor.bean.DamBoard;
import com.sendoh.quoridor.bean.Player;

import java.util.Map;

/**
 * @auther Lin Peita <peitalin@imatrictech.com>
 * @date 2019/8/26 16:02
 * @describe Make some explanation.
 */
public interface IGamePresenter {

    void initGame(int playerNumber);

    void switchPlayer(int playerNumber);

    void switchDamBoard(int orientation);

    void switchActionType(int actionType);

    void moveChess(int direction);

    void moveDamBoard(int rawX, int rawY);

    void placedDamBoard(int x, int y);

    Player[] getPlayers();

    Player getPlayer(int number);

    Player getCurrentPlayer();

    Map<Integer, DamBoard> getDamBoards();

    DamBoard getMoveDamBoard();

    int getActionType();
}
