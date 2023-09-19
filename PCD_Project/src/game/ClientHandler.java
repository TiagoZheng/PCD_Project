package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

import gui.GameGuiMain;

public class ClientHandler extends Thread{
	private Socket socket;
	private ObjectInputStream input;
	private PrintWriter output;
	private MessageLinkedList clientData;
	private Game game;
	private GameGuiMain gameGui ;
	private Client c ;


	public LinkedList<PlayerData> getClientData() {
		return clientData.getDataList();
	}


	public ClientHandler(Socket s ,Client c) throws IOException {
		this.socket=s;
		this.c=c;

	}

	public void getStreams() throws IOException {
		//		input = new Scanner(connection.getInputStream()); ou entao ...
		output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
				true);
		input = new ObjectInputStream(socket.getInputStream());


	}

	public void processConnection() throws IOException {
		try {
			clientData = (MessageLinkedList) input.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		game = new Game(clientData.getDataList());
		gameGui = new GameGuiMain(game,c);
		gameGui.init();

		Thread sendDirection = new SendDirectionThread(gameGui,output);
		sendDirection.start();

		while (true) {
			try {
				clientData = (MessageLinkedList) input.readObject();
				game.update(clientData.getDataList());
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			getStreams();
			processConnection();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnection();
		}
	}
}
