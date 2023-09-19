package game;



import environment.Cell;
import environment.Coordinate;
import environment.Direction;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player  extends Thread{

	protected  Game game;

	private int id;
	private Cell currentCell;


	private byte currentStrength;
	protected byte originalStrength;

	public boolean isObstacle;

	public Cell getCurrentCell() {
		return currentCell;
	}

	public void setCurrentCell(Cell cell) {
		currentCell = cell;
	}

	public Player(int id, Game game, byte strength) {
		super();
		this.id = id;
		this.game=game;
		currentStrength=strength;
		originalStrength=strength;

	}

	public abstract boolean isHumanPlayer();


	public void move( Direction direction) {
		if(getCurrentStrength()==10) {
			this.setObstacle();

		}
		Cell currentCell = this.getCurrentCell();
		Coordinate nextCellPos = currentCell.getPosition().translate(direction.getVector());

		/**
		 *  Checks for out of bounds so that the players does not go out of the board
		 */
		if (nextCellPos.x < 0 || nextCellPos.x > game.DIMX -1 || nextCellPos.y < 0 || nextCellPos.y > game.DIMY-1) {
			return;
		}else {

			/**
			 * Checks if the position that he is moving to is available or not, if not occupied moves
			 */
			Cell nextCell = game.getCell(nextCellPos);
			nextCell.movePlayer(this);

		}
		game.notifyChange();
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", currentStrength=" + currentStrength + ", getCurrentCell()=" + getCurrentCell()
		+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public byte getCurrentStrength() {
		return currentStrength;
	}

	public int getIdentification() {
		return id;
	}

	public void setCurrentStrength(byte b) {
		currentStrength = b;

	}

	public void setObstacle() {
		this.isObstacle = true;
	}
}
