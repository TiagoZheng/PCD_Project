package game;


import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.CountDownLatch;

import environment.Cell;
import environment.Coordinate;
import environment.Direction;

public class Game extends Observable {

	public static final int DIMY = 20;
	public static final int DIMX = 20;
	private static final int NUM_PLAYERS = 50 ;
	private static final int NUM_FINISHED_PLAYERS_TO_END_GAME=3;
	CountDownLatch latch = new CountDownLatch(NUM_FINISHED_PLAYERS_TO_END_GAME);

	private LinkedList<PlayerData> dataList = new LinkedList<PlayerData>();

	public static final long REFRESH_INTERVAL = 400;
	public static final double MAX_INITIAL_STRENGTH = 3;
	public static final long MAX_WAITING_TIME_FOR_MOVE = 2000;
	public static final long INITIAL_WAITING_TIME = 10000;

	public Player[] playerList = new AutomaticPlayer[NUM_PLAYERS];
	public LinkedList<HumanPlayer> humanPlayerList = new LinkedList<>();
	public Cell cell;
	protected Cell[][] board;
	Thread summary = new Thread() {
		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
			for(Player p : playerList) {
				p.interrupt();
				System.out.println(p + "interrupted");
			}
			for(HumanPlayer hp: humanPlayerList) {
				hp.interrupt();
			}
		}
	};

	public Game() {
		board = new Cell[Game.DIMX][Game.DIMY];

		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) 
				board[x][y] = new Cell(new Coordinate(x, y),this);
		summary.start();
	}

	public Game(LinkedList<PlayerData> dataList) {
		this.dataList = dataList;
		board = new Cell[Game.DIMX][Game.DIMY];
		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) 
				board[x][y] = new Cell(new Coordinate(x, y),this);

		for(PlayerData p: dataList) {
			if(p.isHumanPlayer()) {
				new HumanPlayer(p.getId(),this,p.getStrength());
			}else {
				new AutomaticPlayer(p.getId(),this,p.getStrength());
			}
		}
		this.notifyChange();
	}

	public Player[] getPlayerList() {
		return playerList;
	}

	public Cell getCell(Coordinate at) {
		return board[at.x][at.y];
	}

	public int getRandomNumber(int max, int min) {
		return (int)((Math.random()*(max)) +min);
	}

	public Direction getRandomDirection() {

		int randomNumber = getRandomNumber(4, 0);
		if(randomNumber==0) 
			return Direction.UP;
		if(randomNumber==1)
			return Direction.DOWN;
		if(randomNumber==2)
			return Direction.LEFT;
		if(randomNumber==3)
			return Direction.RIGHT;
		return null;
	}

	/**	
	 * Updates GUI. Should be called anytime the game state changes
	 */
	public void notifyChange() {
		setChanged();
		notifyObservers();
	}

	public Cell getRandomCell() {

		Cell newCell=getCell(new Coordinate((int)(Math.random()*Game.DIMX),(int)(Math.random()*Game.DIMY)));
		return newCell; 
	}

	public LinkedList<PlayerData> getPlayerData() {
		dataList.clear();
		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) 

				if(board[x][y].isOcupied()) {
					PlayerData data = new PlayerData(board[x][y].getPlayer().getIdentification(),board[x][y].getPlayer().getCurrentStrength(),board[x][y].getPosition(), board[x][y].getPlayer().isHumanPlayer());
					dataList.add(data);
				}

		return dataList;
	}

	public void addHumanPlayer(HumanPlayer hp) {
		humanPlayerList.add(hp);
	}

	/**
	 *  Creates all players and puts them in a Players List and starts all players as threads
	 */
	public void createAllPlayers() {
		for(int i = 0 ; i <= NUM_PLAYERS-1 ; i++) {
			Player player = new AutomaticPlayer(i , this ,(byte)getRandomNumber(3,1));
			playerList[i] = player;

			player.start();
		}
	}



	/** 
	 * @param player 
	 */
	public void addPlayerToGame(Player player) {
		Cell initialPos=getRandomCell();
		initialPos.addPlayerToCell(player);

		// To update GUI
		notifyChange();
	}

	public void update(LinkedList<PlayerData> dataList) {
		this.dataList = dataList;
		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++) 
				board[x][y].setPlayer(null);

		for(int i = 0; i < dataList.size();i++) {
			if(dataList.get(i).isHumanPlayer()) {
				HumanPlayer hp = new HumanPlayer(dataList.get(i).getId(),this,dataList.get(i).getStrength());
				getCell(dataList.get(i).getCoordinate()).addPlayerToCell(hp);
			}else {
				AutomaticPlayer ap = new AutomaticPlayer(dataList.get(i).getId(),this,dataList.get(i).getStrength());
				getCell(dataList.get(i).getCoordinate()).addPlayerToCell(ap);
			}


		}
		notifyChange();
	}


}
