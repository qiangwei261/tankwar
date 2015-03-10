package controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import entites.Tank;

public class ClientNet {
	
	private String IP;
	private int TCP_PORT;
	private int ID;
	private Controller controller;
	private Tank tank;
	private static int udpPORT = 4237;
	DatagramSocket ds = null;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public ClientNet(String iP, int tcppORT,Tank tank ,Controller controller) {
		IP = iP;
		TCP_PORT = tcppORT;
		this.controller = controller;
		this.tank = tank;
		try {
			ds = new DatagramSocket(udpPORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void connect( ){
		try {
			Socket s = new Socket(IP , TCP_PORT);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPORT);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			ID= dis.readInt();
			System.out.println("connect server");
		} catch (IOException e) {
			e.printStackTrace();
		}

		new Thread(new UDPRecvThread()).start();

	}
	
	
	public void send () {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeInt(tank.getID());
			dos.writeInt(tank.getX());
			dos.writeInt(tank.getY());
			dos.writeInt(tank.getDIR().ordinal());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] buf = baos.toByteArray();
		
		try {
			DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, 6666));
			ds.send(dp);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class UDPRecvThread implements Runnable {
			
			byte[] buf = new byte[1024];
			
			public void run() {
				
				while(ds != null){
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					try {

						send();
						ds.receive(dp);
						parse(dp);
	System.out.println("a packet received from server!");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
			
	public void parse(DatagramPacket dp) {
		ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
		DataInputStream dis = new DataInputStream(bais);
				try {
					int id = dis.readInt();
					if(tank.getID()!= id) {
						int x = dis.readInt();
						int y = dis.readInt();
						Direction dir = Direction.values()[dis.readInt()];
	System.out.println("id:" + id + "-x:" + x + "-y:" + y + "-dir:" + dir);
						Tank t = new Tank(x, y,dir,id);
						controller.addTank(t);
					}


				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
