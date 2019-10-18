package com.fucker.snake;

import android.graphics.*;
import java.util.*;

public class Food {
	
	public int x;
	public int y;
	public int width;
	public int height;
	public int points;
	
	public Food() {
		width = 30;
		height = 30;
		points = 5;
	}
	
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.rgb(128, 56, 47));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2.0F);
		canvas.drawRect(x, y, x + width, y + height, paint);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(x + 4, y + 4, x + width - 4, y + height - 4, paint);
	}
	
	public void place(Snake snake) {
		Random rand = new Random();
		Rect rFood;
		boolean onSnake = false;
		do {
			x = Game.grid.left + rand.nextInt(Game.GRID_COL) * Game.GRID_WIDTH;
			y = Game.grid.top + rand.nextInt(Game.GRID_ROW) * Game.GRID_HEIGHT;
			rFood = new Rect(x, y, x + width, y + height);
			// Check if food is on the tail
			for (Point p : snake.tail) {
				if (onSnake = rFood.contains(p.x, p.y)) {
					break;
				}
			}
			// If food is not on the tail, check if it is on the head
			if (!onSnake) {
				onSnake = rFood.contains(snake.x, snake.y);
			}
		} while (onSnake);
	}
}
