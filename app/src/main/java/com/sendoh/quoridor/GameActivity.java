package com.sendoh.quoridor;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.sendoh.quoridor.bean.DamBoard;
import com.sendoh.quoridor.presenter.GamePresenter;
import com.sendoh.quoridor.presenter.IGamePresenter;
import com.sendoh.quoridor.view.GameView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @auther Lin Peita <peitalin@imatrictech.com>
 * @date 2019/8/26 17:07
 * @describe Make some explanation.
 */
public class GameActivity extends AppCompatActivity {
    private GameView mGameView;
    private IGamePresenter iGamePresenter;
    private MaterialButton mbtnChess1, mbtnDBHor1, mbtnDBVer1;
    private MaterialButton mbtnChess2, mbtnDBHor2, mbtnDBVer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        mGameView = (GameView) findViewById(R.id.gv_game);

        mbtnChess1 = findViewById(R.id.mbtn_chess1);
        mbtnDBHor1 = findViewById(R.id.mbtn_damboard_horizontal1);
        mbtnDBVer1 = findViewById(R.id.mbtn_damboard_vertical1);
        mbtnChess2 = findViewById(R.id.mbtn_chess2);
        mbtnDBHor2 = findViewById(R.id.mbtn_damboard_horizontal2);
        mbtnDBVer2 = findViewById(R.id.mbtn_damboard_vertical2);

        iGamePresenter = new GamePresenter();
        iGamePresenter.initGame(2);

        mGameView.setPresenter(iGamePresenter);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mbtn_chess1:
                iGamePresenter.switchPlayer(GamePresenter.NUMBER_ONE);
                iGamePresenter.switchActionType(GamePresenter.ACTION_CHESS);

                mbtnChess1.setIconTintResource(R.color.customred);
                mbtnDBHor1.setIconTintResource(R.color.lightgray);
                mbtnDBVer1.setIconTintResource(R.color.lightgray);
                break;

            case R.id.mbtn_damboard_horizontal1:
                iGamePresenter.switchPlayer(GamePresenter.NUMBER_ONE);
                iGamePresenter.switchActionType(GamePresenter.ACTION_DAMBOARD);
                iGamePresenter.switchDamBoard(DamBoard.LANDSCAPE);

                mbtnChess1.setIconTintResource(R.color.lightgray);
                mbtnDBHor1.setIconTintResource(R.color.customred);
                mbtnDBVer1.setIconTintResource(R.color.lightgray);
                break;

            case R.id.mbtn_damboard_vertical1:
                iGamePresenter.switchPlayer(GamePresenter.NUMBER_ONE);
                iGamePresenter.switchActionType(GamePresenter.ACTION_DAMBOARD);
                iGamePresenter.switchDamBoard(DamBoard.VERTICAL);

                mbtnChess1.setIconTintResource(R.color.lightgray);
                mbtnDBHor1.setIconTintResource(R.color.lightgray);
                mbtnDBVer1.setIconTintResource(R.color.customred);
                break;

            case R.id.mbtn_chess2:
                iGamePresenter.switchPlayer(GamePresenter.NUMBER_TWO);
                iGamePresenter.switchActionType(GamePresenter.ACTION_CHESS);

                mbtnChess2.setIconTintResource(R.color.royalblue);
                mbtnDBHor2.setIconTintResource(R.color.lightgray);
                mbtnDBVer2.setIconTintResource(R.color.lightgray);
                break;

            case R.id.mbtn_damboard_horizontal2:
                iGamePresenter.switchPlayer(GamePresenter.NUMBER_TWO);
                iGamePresenter.switchActionType(GamePresenter.ACTION_DAMBOARD);
                iGamePresenter.switchDamBoard(DamBoard.LANDSCAPE);

                mbtnChess2.setIconTintResource(R.color.lightgray);
                mbtnDBHor2.setIconTintResource(R.color.royalblue);
                mbtnDBVer2.setIconTintResource(R.color.lightgray);
                break;

            case R.id.mbtn_damboard_vertical2:
                iGamePresenter.switchPlayer(GamePresenter.NUMBER_TWO);
                iGamePresenter.switchActionType(GamePresenter.ACTION_DAMBOARD);
                iGamePresenter.switchDamBoard(DamBoard.VERTICAL);

                mbtnChess2.setIconTintResource(R.color.lightgray);
                mbtnDBHor2.setIconTintResource(R.color.lightgray);
                mbtnDBVer2.setIconTintResource(R.color.royalblue);
                break;
        }
    }
}
