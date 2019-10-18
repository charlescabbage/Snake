package com.fucker.snake;

import android.graphics.*;
import android.view.*;

public class Game {
	
	public static Snake snake;
	public static Food food;
	
	public static Rect grid;
	public static Rect keypad;
	public static Rect pauseBtn;
	
	public static boolean keyleft;
	public static boolean keyup;
	public static boolean keyright;
	public static boolean keydown;
	public static boolean keypause;
	
	public static boolean gameWin;
	public static boolean gamePause;
	public static boolean gameOver;
	
	public static final int GRID_COL = 20;
	public static final int GRID_ROW = 20;
	public static final int GRID_WIDTH = 30;
	public static final int GRID_HEIGHT = 30;
	
	public static void setup(int width, int height) {
		int centerx = (width / 2);
		int halfGrid = (GRID_WIDTH * (GRID_COL / 2));
		int left = centerx - halfGrid;
		int right = centerx + halfGrid;
		grid = new Rect(left, 150, right, 150 + GRID_HEIGHT * GRID_ROW);
		keypad = new Rect(centerx - 150, grid.bottom + 100, centerx + 150, grid.bottom + 400);
		pauseBtn = new Rect(grid.right - 100, grid.bottom + 50, grid.right, grid.bottom + 125);
	}
	
	public static void update() {
		
		if (gamePause) {
			return;
		}
		
		snake.update();
		
		if (snake.tailColliding()) {
			over();
		}
		
		snake.eat(food);
		
		if (snake.tail.size() == GRID_COL * GRID_ROW - 1) {
			win();
		}
	}
	
	public static void start() {
		gameWin = false;
		gameOver = false;
		snake = new Snake();
		food = new Food();
		food.place(snake);
	}
	
	public static void pause() {
		gamePause = true;
	}
	
	public static void resume() {
		gamePause = false;
	}
	
	public static void over() {
		gameOver = true;
	}
	
	public static void win() {
		gameWin = true;
	}
	
	public static void drawGrid(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(2.0F);
		
		canvas.drawColor(Color.DKGRAY);
		
		// grid background
		paint.setColor(Color.rgb(159, 172, 136));
		Rect bg = new Rect(grid.left - 4, grid.top - 50, grid.right + 4, grid.bottom + 4);
		canvas.drawRect(bg, paint);
		
		if (gameWin) {
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setTextSize(72);
			paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
			canvas.drawText("You Won", grid.left + 150, grid.top + 300, paint);
			paint.setTextSize(36);
			canvas.drawText("Touch to continue...", grid.left + 150, grid.top + 350, paint);
		}
		else if (gameOver) {
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setTextSize(72);
			paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
			canvas.drawText("Game Over", grid.left + 130, grid.top + 300, paint);
			paint.setTextSize(36);
			canvas.drawText("Touch to continue...", grid.left + 150, grid.top + 350, paint);
		}
		else {
			paint.setColor(Color.rgb(150, 163, 127));
			for (int row = grid.top; row <= grid.bottom; row += GRID_HEIGHT) {
				paint.setStyle(Paint.Style.STROKE);
				canvas.drawLine(grid.left, row, grid.right, row, paint);
				for (int col = grid.left; col <= grid.right; col += GRID_WIDTH) {
					canvas.drawLine(col, grid.top, col, grid.bottom, paint);
					if (row != grid.bottom && col != grid.right) {
						paint.setStyle(Paint.Style.FILL);
						canvas.drawRect(col + 4, row + 4, col + GRID_WIDTH - 4, row + GRID_HEIGHT - 4, paint);
					}
				}
			}
		}
		
		// grid border
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(grid, paint);
		
		// score
		paint.setTextSize(30);
		paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
		canvas.drawText("Score: " + snake.score, grid.left + 10, grid.top - 15, paint);
	}
	
	public static void drawButtons(Canvas canvas) {
		Paint paint = new Paint();
		
		int w = (keypad.right - keypad.left) / 3;
		int h = (keypad.bottom - keypad.top) / 3;
		Rect left = new Rect(keypad.left, keypad.top + h, keypad.left + w, keypad.bottom - h);
		Rect top = new Rect(keypad.left + w, keypad.top, keypad.right - w, keypad.top + h);
		Rect right = new Rect(keypad.right - w, keypad.top + h, keypad.right, keypad.bottom - h);
		Rect bottom = new Rect(keypad.left + w, keypad.bottom - h, keypad.right - w, keypad.bottom);
		
		if (keyleft)
			left.top += 10;
		else if (keyup)
			top.top += 10;
		else if (keyright)
			right.top += 10;
		else if (keydown)
			bottom.top += 10;
		
		paint.setColor(Color.rgb(255, 165, 0));
		canvas.drawRect(left, paint);
		canvas.drawRect(top, paint);
		canvas.drawRect(right, paint);
		canvas.drawRect(bottom, paint);
		
		paint.setColor(Color.rgb(255, 140, 0));
		paint.setStrokeWidth(10.0F);
		canvas.drawLine(left.left, left.bottom - 5, left.right, left.bottom - 5, paint);
		canvas.drawLine(top.left, top.bottom - 5, top.right, top.bottom - 5, paint);
		canvas.drawLine(right.left, right.bottom - 5, right.right, right.bottom - 5, paint);
		canvas.drawLine(bottom.left, bottom.bottom - 5, bottom.right, bottom.bottom - 5, paint);
		
		if (keypause) {
			pauseBtn.top = grid.bottom + 60;
		}
		else {
			pauseBtn.top = grid.bottom + 50;
		}
		
		paint.setColor(Color.rgb(255, 165, 0));
		canvas.drawRect(pauseBtn, paint);
		
		paint.setColor(Color.WHITE);
		
		if (gamePause) {
			float [] verts = new float[] {
				pauseBtn.left + 30, pauseBtn.top + 10,
				pauseBtn.right - 30, pauseBtn.top + 32,
				pauseBtn.left + 30, pauseBtn.bottom - 20,
				pauseBtn.left + 30, pauseBtn.top + 10,
			};             

			canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 8, verts, 0, null, 0, null, 0, null, 0, 0, paint);
			Path path = new Path();
			path.moveTo(verts[0], verts[1]);
			path.lineTo(verts[2], verts[3]);
			path.lineTo(verts[4], verts[5]);
			canvas.drawPath(path,paint);
		}
		else {
			paint.setStrokeWidth(20.0F);
			canvas.drawLine(pauseBtn.left + 30, pauseBtn.top + 10, pauseBtn.left + 30, pauseBtn.bottom - 20, paint);
			canvas.drawLine(pauseBtn.right - 30, pauseBtn.top + 10, pauseBtn.right - 30, pauseBtn.bottom - 20, paint);
		}
		
		paint.setColor(Color.rgb(255, 140, 0));
		paint.setStrokeWidth(10.0F);
		canvas.drawLine(pauseBtn.left, pauseBtn.bottom - 5, pauseBtn.right, pauseBtn.bottom - 5, paint);
	}
	
	public static void processInput(MotionEvent e) {
		int x = Math.round(e.getX());
		int y = Math.round(e.getY());
		int w = (keypad.right - keypad.left) / 3;
		int h = (keypad.bottom - keypad.top) / 3;
		Rect left = new Rect(keypad.left, keypad.top + h, keypad.left + w, keypad.bottom - h);
		Rect top = new Rect(keypad.left + w, keypad.top, keypad.right - w, keypad.top + h);
		Rect right = new Rect(keypad.right - w, keypad.top + h, keypad.right, keypad.bottom - h);
		Rect bottom = new Rect(keypad.left + w, keypad.bottom - h, keypad.right - w, keypad.bottom);
		
		if (keyleft = left.contains(x, y)) {
			if (snake.xspeed != 1) {
				snake.dir(-1, 0);
			}
		}
		else if (keyup = top.contains(x, y)) {
			if (snake.yspeed != 1) {
				snake.dir(0, -1);
			}
		}
		else if (keyright = right.contains(x, y)) {
			if (snake.xspeed != -1) {
				snake.dir(1, 0);
			}
		}
		else if (keydown = bottom.contains(x, y)) {
			if (snake.yspeed != -1) {
				snake.dir(0, 1);
			}
		}
		
		keypause = pauseBtn.contains(x, y);
		
		switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (gameOver || gameWin) {
					start();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				if (keypause && gamePause) {
					resume();
				}
				else if (keypause && !gamePause) {
					pause();
				}
				keyleft = keyup = keyright = keydown = keypause = false;
				break;
		}
	}
}
