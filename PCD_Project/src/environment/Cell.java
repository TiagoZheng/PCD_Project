package environment;

import java.util.Random;

import game.Game;
import game.Player;

public class Cell {
	private Coordinate position;
	private Player player=null;
	private Game game;

	public Cell(Coordinate position,Game g) {
		super();
		this.position = position;
		this.game=g;
	}

	public synchronized void addPlayerToCell(Player player) {
		while(this.isOcupied()) {
			try {
				wait();
				System.out.println(player.getIdentification() + " is waiting to join...");
			} catch (InterruptedException e) {
				System.out.println("You Lost! Looser");
			}
		}
		setPlayer(player);
		player.setCurrentCell(this);

	}


	public synchronized void removePlayerFromCell(Player player) {
		setPlayer(null);
		notifyAll();
	}

	public synchronized void movePlayer(Player player) {
		if( player.isObstacle) {

		}else {
			if(!this.isOcupied()) {
				player.getCurrentCell().removePlayerFromCell(player);
				addPlayerToCell(player);

			} else {

				if(player.getCurrentStrength() < this.getPlayer().getCurrentStrength() && player.isObstacle == false && this.getPlayer().isObstacle == false) {
					byte sumStrength = limitStrength( (byte) (player.getCurrentStrength() + this.getPlayer().getCurrentStrength()) );
					player.setCurrentStrength((byte)0);
					player.setObstacle();
					this.getPlayer().setCurrentStrength(sumStrength);

				} else if (player.getCurrentStrength() > this.getPlayer().getCurrentStrength() && player.isObstacle == false && this.getPlayer().isObstacle == false) {
					byte sumStrength = limitStrength( (byte) (player.getCurrentStrength() + this.getPlayer().getCurrentStrength()) );
					this.getPlayer().setCurrentStrength((byte)0);
					this.getPlayer().setObstacle();
					player.setCurrentStrength(sumStrength);

					/**
					 *  If any they have the same energy any of them wins.	
					 */
				} else if (player.getCurrentStrength() == this.getPlayer().getCurrentStrength() && player.isObstacle == false && this.getPlayer().isObstacle == false) {

					Random random = new Random();
					if(random.nextBoolean()) {
						byte sumStrength = limitStrength( (byte) (player.getCurrentStrength() + this.getPlayer().getCurrentStrength()) );
						player.setCurrentStrength((byte)0);
						player.setObstacle();
						this.getPlayer().setCurrentStrength(sumStrength);
					}else {
						byte sumStrength = limitStrength( (byte) (player.getCurrentStrength() + this.getPlayer().getCurrentStrength()) );
						this.getPlayer().setCurrentStrength((byte)0);
						this.getPlayer().setObstacle();
						player.setCurrentStrength(sumStrength);
					}
				}
			}
		}

	}


	public byte limitStrength(byte d) {
		if (d > 10) 
			return 10;
		return d;
	}

	public Coordinate getPosition() {
		return position;
	}

	public boolean isOcupied() {
		return player!=null;
	}


	public Player getPlayer() {
		return player;
	}

	// Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
	public void setPlayer(Player player) {
		this.player = player;
	}



}
