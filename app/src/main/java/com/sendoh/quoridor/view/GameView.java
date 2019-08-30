package com.sendoh.quoridor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sendoh.quoridor.R;
import com.sendoh.quoridor.bean.Chess;
import com.sendoh.quoridor.bean.DamBoard;
import com.sendoh.quoridor.bean.Player;
import com.sendoh.quoridor.presenter.GamePresenter;
import com.sendoh.quoridor.presenter.IGamePresenter;

import java.util.Iterator;
import java.util.Map;

/**
 * @auther Lin Peita <peitalin@imatrictech.com>
 * @date 2019/8/26 16:50
 * @describe Make some explanation.
 */
public class GameView extends View {
    private IGamePresenter iGamePresenter;

    private Paint backgroundPaint, blockPaint, chessPaint, damBoarddPaint;
    private Rect srcRect, destRectRed, destRectBlue;
    private Bitmap bitmapChessRed, bitmapChessBlue;

    private int blockSize = 50;
    private int blockPadding = 10;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setPresenter(IGamePresenter iGamePresenter) {
        this.iGamePresenter = iGamePresenter;
    }

    public void init(Context context) {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(this.getResources().getColor(R.color.white));

        blockPaint = new Paint();
        blockPaint.setAntiAlias(true);
        blockPaint.setDither(true);
        blockPaint.setStrokeCap(Paint.Cap.ROUND);
        blockPaint.setStrokeJoin(Paint.Join.ROUND);
        blockPaint.setStrokeWidth(8.0f);
        blockPaint.setShadowLayer(8.0f, 2.0f, 2.0f, this.getResources().getColor(R.color.saddlebrown));
        blockPaint.setColor(this.getResources().getColor(R.color.saddlebrown));

        damBoarddPaint = new Paint();
        damBoarddPaint.setAntiAlias(true);
        damBoarddPaint.setDither(true);
        damBoarddPaint.setStyle(Paint.Style.STROKE);
        damBoarddPaint.setStrokeCap(Paint.Cap.ROUND);
        damBoarddPaint.setStrokeWidth(8.0f);
        damBoarddPaint.setShadowLayer(8.0f, 2.0f, 2.0f, this.getResources().getColor(R.color.goldenrod));
        damBoarddPaint.setColor(this.getResources().getColor(R.color.goldenrod));

        chessPaint = new Paint();
        chessPaint.setAntiAlias(true);
        chessPaint.setDither(true);
        chessPaint.setStrokeCap(Paint.Cap.ROUND);
        chessPaint.setStrokeJoin(Paint.Join.ROUND);
        chessPaint.setStrokeWidth(8.0f);
        chessPaint.setShadowLayer(8.0f, 2.0f, 2.0f, this.getResources().getColor(R.color.customred));
        chessPaint.setColor(this.getResources().getColor(R.color.customred));

//        bitmapChessRed = BitmapFactory.decodeResource(context.getResources(), R.drawable.chess_red);
//        bitmapChessBlue = BitmapFactory.decodeResource(context.getResources(), R.drawable.chess_blue);

//        srcRect = new Rect(0, 0, bitmapChessRed.getWidth(), bitmapChessRed.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(new Rect(0, 0, this.getWidth(), this.getHeight()), backgroundPaint);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawRect(i * blockSize + blockPadding, j * blockSize + blockPadding,
                        (i + 1) * blockSize - blockPadding, (j + 1) * blockSize - blockPadding, blockPaint);
            }
        }

        Player[] players = iGamePresenter.getPlayers();
        if (players != null && players.length > 0) {
            for (int i = 0; i < players.length; i++) {
                Player player = players[i];
                if (player != null) {
                    switchPaintColor(chessPaint, player.getColorType());
                    int x = player.getChess().getX();
                    int y = player.getChess().getY();
                    canvas.drawCircle(x * blockSize + blockSize / 2, y * blockSize + blockSize / 2,
                            blockSize / 2 - (2 * blockPadding), chessPaint);
                }
            }
        }

        Map<Integer, DamBoard> placedDamBoards = iGamePresenter.getDamBoards();
        if (placedDamBoards != null) {
            Iterator<Map.Entry<Integer, DamBoard>> iterator = placedDamBoards.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, DamBoard> entry = iterator.next();
                DamBoard damBoard = entry.getValue();
                if (damBoard != null && damBoard.isPlaced()) {
                    int orientation = damBoard.getOrientation();
                    int startX = 0;
                    int startY = 0;
                    int stopX = 0;
                    int stopY = 0;
                    if (orientation == DamBoard.LANDSCAPE) {
                        startX = damBoard.getX() * blockSize - blockSize + blockPadding;
                        startY = damBoard.getY() * blockSize;
                        stopX = damBoard.getX() * blockSize + blockSize - blockPadding;
                        stopY = startY;
                    } else if (orientation == DamBoard.VERTICAL) {
                        startX = damBoard.getX() * blockSize;
                        startY = damBoard.getY() * blockSize - blockSize + blockPadding;
                        stopX = startX;
                        stopY = damBoard.getY() * blockSize + blockSize - blockPadding;
                    }

                    int colorType = getColorType(damBoard.getWhichPlayer());
                    switchPaintColor(damBoarddPaint, colorType);

                    canvas.drawLine(startX, startY, stopX, stopY, damBoarddPaint);
                }
            }
        }

        DamBoard moveDamBoard = iGamePresenter.getMoveDamBoard();
        if (moveDamBoard != null && moveDamBoard.isPlaced()) {
            int orientation = moveDamBoard.getOrientation();
            int startX = 0;
            int startY = 0;
            int stopX = 0;
            int stopY = 0;
            if (orientation == DamBoard.LANDSCAPE) {
                startX = moveDamBoard.getX() - blockSize;
                startY = moveDamBoard.getY();
                stopX = moveDamBoard.getX() + blockSize;
                stopY = startY;
            } else if (orientation == DamBoard.VERTICAL) {
                startX = moveDamBoard.getX();
                startY = moveDamBoard.getY() - blockSize;
                stopX = startX;
                stopY = moveDamBoard.getY() + blockSize;
            }

            int colorType = getColorType(-1);
            switchPaintColor(damBoarddPaint, colorType);
            canvas.drawLine(startX, startY, stopX, stopY, damBoarddPaint);
        }
    }

    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        if (iGamePresenter.getActionType() == GamePresenter.ACTION_CHESS) {
            if (action == MotionEvent.ACTION_DOWN) {
                downX = x;
                downY = y;
            } else if (action == MotionEvent.ACTION_MOVE) {

            } else if (action == MotionEvent.ACTION_UP) {
                float offsetX = x - downX;
                float offsetY = y - downY;

                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (offsetX > 0)
                        iGamePresenter.moveChess(Chess.DIRECTION_RIGHT);
                    else
                        iGamePresenter.moveChess(Chess.DIRECTION_LEFT);
                } else if (Math.abs(offsetX) < Math.abs(offsetY)) {
                    if (offsetY > 0)
                        iGamePresenter.moveChess(Chess.DIRECTION_DOWN);
                    else
                        iGamePresenter.moveChess(Chess.DIRECTION_UP);
                }
                this.invalidate();
            }
        } else if (iGamePresenter.getActionType() == GamePresenter.ACTION_DAMBOARD) {
            if (action == MotionEvent.ACTION_DOWN) {
                iGamePresenter.moveDamBoard((int) x, (int) y);
            } else if (action == MotionEvent.ACTION_MOVE) {
                iGamePresenter.moveDamBoard((int) x, (int) y);
            } else if (action == MotionEvent.ACTION_UP) {
                if (x > 0 && x < this.getWidth() && y > 0 && y < this.getHeight()) {
                    int upX = (int) (x / blockSize + 0.5f);
                    int upY = (int) (y / blockSize + 0.5f);
                    if (upX <= 0)
                        upX = 1;
                    if (upX >= 9)
                        upX = 8;
                    if (upY <= 0)
                        upY = 1;
                    if (upY >= 9)
                        upY = 8;

                    iGamePresenter.placedDamBoard(upX, upY);
                } else {
                    iGamePresenter.placedDamBoard(-1, -1);
                }
            }
            this.invalidate();
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        blockSize = width / 9;
        int width9 = blockSize * 9;
        setMeasuredDimension(width9, width9);
    }

    private void switchPaintColor(Paint paint, int colorType) {
        if (paint == null)
            return;
        paint.setShadowLayer(8.0f, 2.0f, 2.0f, this.getResources().getColor(colorType));
        paint.setColor(this.getResources().getColor(colorType));
    }

    private int getColorType(int playerNumber) {
        int colorType = R.color.customred;
        Player player = null;
        if (playerNumber == -1)
            player = iGamePresenter.getCurrentPlayer();
        else
            player = iGamePresenter.getPlayer(playerNumber);
        if (player != null) {
            colorType = player.getColorType();
        }
        return colorType;
    }
}
