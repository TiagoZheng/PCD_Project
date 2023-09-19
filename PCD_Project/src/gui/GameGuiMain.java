package gui;

import java.util.Observable;
import java.util.Observer;

import game.Client;
import game.Game;
import game.HumanPlayer;

import javax.swing.JFrame;

public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;
	private static HumanPlayer humanPlayer;

	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);

		buildGui();

	}

	public GameGuiMain(Game game,Client c) {
		super();
		this.game = game;
		this.game.addObserver(this);

		buildGuiForClient(c);

	}

	public GameGuiMain(Game game) {
		super();
		this.game = game;
		this.game.addObserver(this);

		buildGui();
	}

	public void buildGui() {
		boardGui = new BoardJComponent(game);
		frame.add(boardGui);


		frame.setSize(800,800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void buildGuiForClient(Client c) {
		boardGui = new BoardJComponent(game,c);
		frame.add(boardGui);


		frame.setSize(800,800);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void init()  {
		frame.setVisible(true);

	}
	public BoardJComponent getBoardGui() {
		return boardGui;
	}

	public void initForServer()  {
		frame.setVisible(true);
		game.createAllPlayers();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) {

		GameGuiMain gameGui = new GameGuiMain();
		gameGui.init();
	}

}
