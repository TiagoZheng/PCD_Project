package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import environment.Direction;

public class ServerHandler extends Thread{

	private Socket connection;
	private BufferedReader input;
	private ObjectOutputStream output;
	private HumanPlayer hp;
	private Game game;

	public ServerHandler(Socket connection , Game game) {
		this.connection = connection;
		this.game=game;
	}

	public void getStream() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}

	public void processConection() throws IOException {
		System.out.println("Connection accepted.. processing now...");
		hp = new HumanPlayer(game.getPlayerData().size(),game,(byte)5);
		game.addHumanPlayer(hp);
		game.addPlayerToGame(hp);
		hp.start();
		game.getPlayerData().add(new PlayerData(hp.getIdentification(),hp.getCurrentStrength(),hp.getCurrentCell().getPosition(),true));

		while(true) {
			new Thread() {
				@Override
				public void run() {
					try {
						String direction = input.readLine();
						Direction d = Direction.stringToDirection(direction);
						hp.move(d);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();

			try {
				MessageLinkedList message = new MessageLinkedList(game.getPlayerData());
				output.writeObject(message);
				output.reset();
				Thread.sleep(Game.REFRESH_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		try {
			input.close();
			output.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			getStream();
			processConection();

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			closeConnection();
		}
	}
}