package game;



import java.io.Serializable;

import environment.Cell;

/**
 * Represents a player.
 * @author luismota
 *
 */
public abstract class Player  implements Serializable{

    

	protected  transient Game game;

	private int id;

	private byte currentStrength;
	protected byte originalStrength;
	
	private transient Thread thread;
	
	public boolean gameStarted = false;
	
	private boolean isActive = true;

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
	
	public static byte generateInitialEnergy() {
	    return (byte) (Math.random()*3+1);
	}
	
	public void addEnergy(byte energyAmount) {
	    currentStrength += energyAmount;
	    if(currentStrength >= 10) {
		currentStrength = 10;
		setInactive();
	    }
	}
	
	public void setEnergyToZero() {
	    currentStrength = 0;
	}
	
	public void setInactive() {
	    this.isActive = false;
	}
	
	public boolean isActive() {
	    return isActive;
	}
}
