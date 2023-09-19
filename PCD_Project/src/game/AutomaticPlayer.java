package game;


public class AutomaticPlayer extends Player{


	public AutomaticPlayer(int id, Game game, byte strength) {
		super(id, game, strength);
	}

	@Override
	public boolean isHumanPlayer() {
		return false;
	}


	public byte limitStrength(byte d) {
		if (d > 10) 
			return 10;
		return d;
	}


	@Override
	public void run() {
		game.addPlayerToGame(this);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		while(getCurrentStrength() < 10 && getCurrentStrength() > 0 ) {
			/**
			 *  Each player sleeps for a certain time depending on their initial strength
			 */
			int next_move_time = (int) (this.originalStrength * Game.REFRESH_INTERVAL);
			try {
				Thread.sleep(next_move_time);

				move(game.getRandomDirection());

			} catch (InterruptedException e) {
				break;
			}

		}

		if (this.getCurrentStrength() == 10 ) {
			this.isObstacle = true;
			game.latch.countDown();
			System.out.println("ola");
		}
		System.out.println(game.latch.getCount());

	}
}
