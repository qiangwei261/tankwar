package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import view.GamePanel;
import entites.Missile;
import entites.Tank;

public class Controller extends KeyAdapter {

	private Tank tank;
	private GamePanel gamepanel;
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	private boolean bL = false, bU = false, bR = false, bD = false;
	private int x;
	private int y;
	private Direction dir = Direction.STOP;
	private int tcpPORT;
	private String IP;
	private ClientNet cn = null;
	private ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	public void addTank(Tank tank){
		tanks.add(tank);
	}
	public Controller(Tank tank, GamePanel gamepanel, int x, int y, String IP,
			int tcpPORT) {
		this.tank = tank;
		this.gamepanel = gamepanel;
		this.x = x;
		this.y = y;
		this.IP = IP;
		this.tcpPORT = tcpPORT;
	}

	private class PaintThread implements Runnable {

		public void run() {
			while (true) {
				Controller.this.gamepanel.display(missiles, tanks,tank);
				Controller.this.isOutPanel();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void start() {
		tank.setX(x);
		tank.setY(y);
		new Thread(new PaintThread()).start();
	}

	public void isOutPanel() {

		if (tank.getX() > gamepanel.getWidth() - 20) {
			tank.setX(gamepanel.getWidth() - 20);
		} else if (tank.getY() > gamepanel.getHeight() - 20) {
			tank.setY(gamepanel.getHeight() - 20);
		} else if (tank.getX() < 0) {
			tank.setX(5);
		} else if (tank.getY() < 0) {
			tank.setY(25);
		}

		for (int i= 0;i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			if (m.getX() > gamepanel.getWidth()
					|| m.getY() > gamepanel.getHeight() || m.getX() < 0
					|| m.getY() < 0) {
				m.setLife(false);
			}
		}

	}

	public void isHit() {

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2:
			if (cn == null) {
				cn = new ClientNet(IP, tcpPORT,tank,this);
				cn.connect();
				tank.setID(cn.getID());
			}
			break;
		case KeyEvent.VK_CONTROL:
			missiles.add(tank.fire());
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		exChangeDir();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		exChangeDir();
	}

	public void exChangeDir() {

		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
		if (dir != null) {
			tank.changerDir(dir);
			tank.move();
		}
	}

}
