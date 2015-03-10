package Client;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import view.GamePanel;
import controller.Controller;
import entites.Tank;

public class Client {
	public static void main(String[] args) {
		
		Tank tank = new Tank();
		GamePanel gamepanel = new GamePanel();
		
		Controller controller = new Controller(tank, gamepanel,30,30,"127.0.0.1",8888);
		gamepanel.setLocation(50, 50);
		gamepanel.setSize(800,600);
		gamepanel.setResizable(false);
		gamepanel.setBackground(Color.GREEN);
		gamepanel.addKeyListener(controller);
		gamepanel.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		gamepanel.setVisible(true);
		controller.start();
}
}
