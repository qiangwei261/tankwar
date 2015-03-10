package view;

import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import entites.Missile;
import entites.Tank;

public class GamePanel extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tank tank;
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	private ArrayList<Tank> tanks = new ArrayList<Tank>();
	

	public void display(ArrayList<Missile> missiles,ArrayList tanks,Tank tank) {
//		System.out.println("gamepanel display");
		this.missiles = missiles;
		this.tanks = tanks;
		this.tank = tank;
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
//		System.out.println("gamepanel paintComponent");
		tank.drawMe(g);
		for(int i = 0; i< missiles.size();i++){
			Missile missile = missiles.get(i);
			if( missile.isLife()){
					missile.drawMe(g);
//					System.out.println("______________missile draw" + missiles.size());
			}
			if( !(missile.isLife())){
				missiles.remove(missile);
//				System.out.println("_______________remove missile" + missiles.size());
			}
		}
		for(int i = 0; i< tanks.size();i++){
			Tank tank = tanks.get(i);
			if( tank.isLife()){
					tank.drawMe(g);
//					System.out.println("______________tank draw" + tanks.size());
			}
			if( !(tank.isLife())){
				tanks.remove(tank);
//				System.out.println("_______________remove tank" + tanks.size());
			}
		}
	}
}
