package entites;

import java.awt.Color;
import java.awt.Graphics;

import controller.Direction;

public class Missile {
	private int x;
	private int y;
	private Direction DIR;
	private int MISSILE_SPEED = 20;
	public static int MISSILE_SIZE = 10;
	public boolean life = true;
	
	public boolean isLife() {
		return life;
	}




	public void setLife(boolean life) {
		this.life = life;
	}




	public Missile(int x, int y,Direction dir) {
		this.x = x;
		this.y = y;
		this.DIR = dir;
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




	public  void move(){

		System.out.println("missile move");
		switch(DIR) {
		case L:
			x -= MISSILE_SPEED;
			break;
		case LU:
			x -= MISSILE_SPEED;
			y -= MISSILE_SPEED;
			break;
		case U:
			y -= MISSILE_SPEED;
			break;
		case RU:
			x += MISSILE_SPEED;
			y -= MISSILE_SPEED;
			break;
		case R:
			x += MISSILE_SPEED;
			break;
		case RD:
			x += MISSILE_SPEED;
			y += MISSILE_SPEED;
			break;
		case D:
			y += MISSILE_SPEED;
			break;
		case LD:
			x -= MISSILE_SPEED;
			y += MISSILE_SPEED;
			break;
		case STOP:
			break;
		}
	}
	
	public void drawMe(Graphics g){
		System.out.println("missile drawMe");
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, MISSILE_SIZE, MISSILE_SIZE);
		g.setColor(c);
		move();
	}
	
	public void isHit(){
		System.out.println("missile isHit");
		
	}
	
}
