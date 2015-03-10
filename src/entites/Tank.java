package entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;

import controller.Direction;

public class Tank {
	
	public int TANK_SPEED = 5;
	public Direction DIR = Direction.D;
	public int x = 50;
	public int y = 50;
	public int TANK_SIZE  = 20;
	private Direction PT_DIR = Direction.D;
	private boolean life = true;
	
	public boolean isLife() {
		return life;
	}

	public void setLife(boolean life) {
		this.life = life;
	}
	
	private int ID;
	
	public Tank(){
		
	}
	
	public Tank(int x, int y,Direction dIR,int id) {
		DIR = dIR;
		this.x = x;
		this.y = y;
		this.ID = id;
	}

	public Direction getDIR() {
		return DIR;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public void move(){
		System.out.println("tank move");
		switch(DIR) {
		case L:
			x -= TANK_SPEED;
			break;
		case LU:
			x -= TANK_SPEED;
			y -= TANK_SPEED;
			break;
		case U:
			y -= TANK_SPEED;
			break;
		case RU:
			x += TANK_SPEED;
			y -= TANK_SPEED;
			break;
		case R:
			x += TANK_SPEED;
			break;
		case RD:
			x += TANK_SPEED;
			y += TANK_SPEED;
			break;
		case D:
			y += TANK_SPEED;
			break;
		case LD:
			x -= TANK_SPEED;
			y += TANK_SPEED;
			break;
		case STOP:
			break;
		}
		
		if(this.DIR != Direction.STOP) {
			this.PT_DIR = this.DIR;
		}
		
	}
	
	public void changerDir(Direction dir){
		this.DIR = dir;
	}
	
	public Missile fire(){
		System.out.println("tank fire");
		int x = this.x + TANK_SIZE/2 - Missile.MISSILE_SIZE/2;
		int y = this.y + TANK_SIZE/2 - Missile.MISSILE_SIZE/2;
		return new Missile(x, y,PT_DIR);
	}
	
	public void drawMe(Graphics g){
		
//		System.out.println("tank drawMe");
		Color c = g.getColor();
		g.fillOval(x, y, TANK_SIZE, TANK_SIZE);
		g.setColor(Color.RED);
		if( ID !=0 ){
			g.drawString("ID:" + ID, x, y - 2);
		}
		
		switch(PT_DIR) {
		case L:
			g.drawLine(x + TANK_SIZE/2, y + TANK_SIZE/2, x, y + TANK_SIZE/2);
			break;
		case LU:
			g.drawLine(x + TANK_SIZE/2, y + TANK_SIZE/2, x, y);
			break;
		case U:
			g.drawLine(x + TANK_SIZE/2, y + TANK_SIZE/2, x + TANK_SIZE/2, y);
			break;
		case RU:
			g.drawLine(x + TANK_SIZE/2, y + TANK_SIZE/2, x + TANK_SIZE, y);
			break;
		case R:
			g.drawLine(x + TANK_SIZE/2, y +  TANK_SIZE/2, x + TANK_SIZE, y + TANK_SIZE/2);
			break;
		case RD:
			g.drawLine(x + TANK_SIZE/2, y + TANK_SIZE/2, x + TANK_SIZE, y + TANK_SIZE);
			break;
		case D:
			g.drawLine(x + TANK_SIZE/2, y + TANK_SIZE/2, x + TANK_SIZE/2, y + TANK_SIZE);
			break;
		case LD:
			g.drawLine(x + TANK_SIZE/2, y + TANK_SIZE/2, x, y + TANK_SIZE);
			break;
		}
		g.setColor(c);
	}
	
	public void isHit(){
		System.out.println("tank isHit");
		
	}
}
