package game;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import gui.GameGuiMain;


public class Server {
	private ServerSocket server;
	public Game game = new Game();
	private GameGuiMain gameGui = new GameGuiMain(game);

	public static final int PORT = 12006;

	public Server() {
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Cannot init server... aborting!");
			System.exit(1);
		}
	}
	public void runServer() {
		gameGui.initForServer();
		while(true) {
			try {
				waitForConnection();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void waitForConnection() throws IOException {
		System.out.println("Waiting for connection....");
		gameGui.init();
		Socket connection = server.accept();
		ServerHandler handler = new ServerHandler(connection,game);
		handler.start();
	}

	public static void main(String[] args) {
		Server app = new Server();
		app.runServer();
	}
}