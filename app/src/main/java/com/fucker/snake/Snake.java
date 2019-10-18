package com.fucker.snake;

import android.graphics.*;
import java.util.*;

public class Snake {
	
	public int x;
	public int y;
	public int width;
	public int height;
	public int xspeed;
	public int yspeed;
	public int min;
	public int max;
	public ArrayList<Point> tail;
	public int score;
	
	public Snake() {
		width = 30;
		height = 30;
		min = 5;
		xspeed = 1;
		tail = new ArrayList<Point>(Collections.nCopies(min, new Point()));
		
		for (int i = 0; i < tail.size(); i++) {
			tail.set(i, new Point(Game.grid.left + Game.GRID_WIDTH * (min - (i + 1)), Game.grid.top));
		}
		
		x = Game.grid.left + Game.GRID_WIDTH * min;
		y = Game.grid.top;
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(2.0F);
		
		// Draw head
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(x, y, x + width, y + height, paint);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(x + 4, y + 4, x + width - 4, y + height - 4, paint);
		
		// Draw tail
		for (Point p : tail) {
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawRect(p.x, p.y, p.x + width, p.y + height, paint);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawRect(p.x + 4, p.y + 4, p.x + width - 4, p.y + height - 4, paint);
		}
	}
	
	public void update() {
		if (tail.size() > 0) {
			for (int i = tail.size() - 1; i > 0; i--) {
				tail.set(i, tail.get(i - 1));
			}
			tail.set(0, new Point(x, y));
		}
		
		x += xspeed * Game.GRID_WIDTH;
		y += yspeed * Game.GRID_HEIGHT;
		
		if (x < Game.grid.left)
			x = Game.grid.right - Game.GRID_WIDTH;
		else if (x >= Game.grid.right)
			x = Game.grid.left;
		if (y < Game.grid.top)
			y = Game.grid.bottom - Game.GRID_HEIGHT;
		else if (y >= Game.grid.bottom)
			y = Game.grid.top;
	}
	
	public boolean eat(Food food) {
		if ((x + width) - food.x > 0 && (food.x + food.width) - x > 0 &&
			(y + height) - food.y > 0 && (food.y + food.height) - y > 0) {
			tail.add(new Point(x, y));
			score += food.points;
			food.place(this);
			return true;
		}
		
		return false;
	}
	
	public boolean tailColliding() {
		for (Point p : tail) {
			if ((x + width) - p.x > 0 && (p.x + width) - x > 0 &&
				(y + height) - p.y > 0 && (p.y + height) - y > 0) {
				return true;
			}
		}
		return false;
	}
	
	public void dir(int x, int y) {
		if (Game.gamePause) {
			return;
		}
		
		xspeed = x;
		yspeed = y;
	}
}
