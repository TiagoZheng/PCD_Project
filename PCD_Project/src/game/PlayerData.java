package game;

import java.io.Serializable;

import environment.Cell;
import environment.Coordinate;

public class PlayerData implements Serializable{
	
	private int id;
	private byte strength;
	private Coordinate cd;
	private boolean humanPlayer;
	
	public PlayerData(int id, byte strength, Coordinate cd, boolean humanPlayer) {
		this.id = id;
		this.strength = strength;
		this.cd = cd;
		this.humanPlayer = humanPlayer;
		
	}

	public int getId() {
		return id;
	}

	public byte getStrength() {
		return strength;
	}

	public Coordinate getCoordinate() {
		return cd;
	}

	public boolean isHumanPlayer() {
		return humanPlayer;
	}
}
