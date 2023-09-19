package game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class Client {

	private Socket socket;
	private String hostName;
	private ClientHandler handler;
	private int port, up, down, left, right;


	public Client(String host,int port,int up,int down,int left,int right) {
		hostName = host;
		this.port = port;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;

	}

	public void runClient() {
		connectToServer();
	}

	public void connectToServer() {
		try {
			socket = new Socket(InetAddress.getByName(hostName), port);
			handler = new ClientHandler(socket,this);
			handler.start();

		} catch (IOException e) {
			System.err.println("Error connection to server... aborting");
			System.exit(1);
		}
	}


	public int getUp() {
		return up;
	}

	public int getDown() {
		return down;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public static void main(String[] args) {
		Client app = new Client("localhost", 12006 ,KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT);
		app.runClient();
	}


}
