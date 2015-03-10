package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import Client.Client;

public class Server {

	public static final int TCP_PORT = 8888;
	public static final int UDP_PORT = 6666;
	public static ServerSocket ss = null;
	public ArrayList<User> users = new ArrayList<User>();
	private int ID = 1;

	public void start() {
		new Thread(new UDPThread()).start();
		try {
			ss = new ServerSocket(TCP_PORT);
			while (true) {
				Socket s = ss.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String IP = s.getInetAddress().getHostAddress();
				int udpPORT = dis.readInt();
				users.add(new User(udpPORT, IP));
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeInt(ID++);
				System.out.println("a client connect, " + "PORT : " + udpPORT
						+ " ID: " + ID + " IP: " + IP);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new Server().start();

	}

	private class User {
		private String IP;
		public int udpPORT;

		public User(int PORT, String IP) {
			this.udpPORT = PORT;
			this.IP = IP;
		}

	}

	private class UDPThread implements Runnable {

		byte[] buf = new byte[1024];

		public void run() {
			DatagramSocket ds = null;
			try {
				ds = new DatagramSocket(UDP_PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
//			System.out.println("UDP thread started at port :" + UDP_PORT);
			while (ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
					for (int i = 0; i < users.size(); i++) { 
						User c = users.get(i);
						dp.setSocketAddress(new InetSocketAddress(c.IP,
								c.udpPORT));
						ds.send(dp);
//						System.out.println("UDPThread");
					}
//System.out.println("a packet received!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
