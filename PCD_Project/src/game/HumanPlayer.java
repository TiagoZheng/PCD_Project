package game;


import environment.Direction;

/**
 * Class to demonstrate a player being added to the game.
 * @author luismota
 *
 */

public class HumanPlayer extends Player {

	private Direction lastDirection = null;
	public HumanPlayer (int id, Game game, byte strength) {
		super(id, game, strength);

	}

	public boolean isHumanPlayer() {
		return true;
	}
	public void setLastDirection(Direction d) {
		lastDirection = d;
	}
	@Override
	public void run() {
		while(getCurrentStrength() < 10 && getCurrentStrength() > 0) {
			int next_move_time = (int) (this.originalStrength * Game.REFRESH_INTERVAL);
			try {

				Thread.sleep(next_move_time);
			} catch (InterruptedException e) {
				break;
			}
			if(lastDirection != null) {
				move(lastDirection);
				lastDirection = null;
			}
		}
		if (this.getCurrentStrength() == 10 ) {
			this.isObstacle = true;
			game.latch.countDown();
		}
	}
}
