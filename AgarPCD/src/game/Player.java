package game;



import environment.Cell;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player  {


	protected  Game game;

	private int id;

	private byte currentStrength;
	protected byte originalStrength;
	
	private Thread thread;

	public Cell getCurrentCell() {
		return game.getCell(this);
	}

	public Player(int id, Game game, byte strength) {
		super();
		this.id = id;
		this.game=game;
		currentStrength=strength;
		originalStrength=strength;
	}
	
	public void setThread(Thread t) {
	    this.thread = t;
	}
	
	public Thread getThread() {
	    return thread;
	}

	public abstract boolean isHumanPlayer();
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", currentStrength=" + currentStrength + ", getCurrentCell()=" + getCurrentCell()==null?"":getCurrentCell()
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
	
	public static int generateInitialEnergy() {
	    Double r = Math.random();
	    if(r<0.33) return 1;
	    else if(r>=0.33 && r<0.66) return 2;
	    else return 3;
	}
}
