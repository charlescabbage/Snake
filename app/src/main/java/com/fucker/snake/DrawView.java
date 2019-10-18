package com.fucker.snake;

import android.content.Context;
import android.graphics.*;
import android.view.*;

public class DrawView extends View {
	
	private boolean once;
	
	private final long SECOND = 1000;
	private final long TARGET_FPS = 10;
	private final long FRAME_PERIOD = SECOND / TARGET_FPS;

	private long time = System.currentTimeMillis();
	
    public DrawView(Context context) {
        super(context);
    }
	
    @Override
    protected void onDraw(Canvas canvas) {
		if (!once) {
			Game.setup(getWidth(), getHeight());
			Game.start();
			once = true;
		}
		
		long startTime = System.currentTimeMillis();
		Game.update();
		doFpsCheck(startTime);

		Game.drawGrid(canvas);
		Game.drawButtons(canvas);
		
		if (!Game.gameOver && !Game.gameWin) {
			Game.snake.draw(canvas);
			Game.food.draw(canvas);
		}
		
		invalidate();
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		Game.processInput(e);
		return true;
	}
	
	public boolean doFpsCheck(long startTime) {
		if (System.currentTimeMillis() - time >= SECOND) {
			time = System.currentTimeMillis();
		}

		long sleepTime = FRAME_PERIOD - (System.currentTimeMillis() - startTime);

		if (sleepTime >= 0) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO handle this properly
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
}
