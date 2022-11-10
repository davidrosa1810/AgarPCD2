package game;


import java.util.HashMap;
import java.util.Observable;
import environment.Cell;
import environment.Coordinate;
import environment.Direction;

public class Game extends Observable {

    public static final int DIMY = 30;
    public static final int DIMX = 30;
    public static final int NUM_PLAYERS = 90;
    private static final int NUM_FINISHED_PLAYERS_TO_END_GAME=3;

    public static final long REFRESH_INTERVAL = 400;
    public static final double MAX_INITIAL_STRENGTH = 3;
    public static final long MAX_WAITING_TIME_FOR_MOVE = 2000;
    public static final long INITIAL_WAITING_TIME = 10000;


    protected Cell[][] board;

    public Game() {
	board = new Cell[Game.DIMX][Game.DIMY];

	for (int x = 0; x < Game.DIMX; x++) 
	    for (int y = 0; y < Game.DIMY; y++)
		board[x][y] = new Cell(new Coordinate(x, y),this);
    }

    /** 
     * @param player 
     * @throws InterruptedException 
     */
    public void addPlayerToGame(Player player) throws InterruptedException {
	Cell initialPos=getRandomCell();
	initialPos.addPlayerToGame(player);


	// To update GUI
	notifyChange();

    }

    public Cell getCell(Coordinate at) {
	return board[at.x][at.y];
    }

    public Cell getCell(Player p) {
	for(Cell[] c2: board) {
	    for(Cell c: c2) {
		if(c.getPlayer() != null && c.getPlayer().equals(p)) return c;
	    }
	}
	return null;
    }

    public synchronized void movePlayer(Cell c, Direction d) {
	//	    lock.lock();
	//	System.out.println("entrei no movePlayer");
	//	    try {
	//		Thread.sleep(1000);
	//	    } catch (InterruptedException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	    }
	Coordinate newPosition = c.getPosition().translate(d.getVector());
	if(!newPosition.outOfBounds()) {
	    Cell newCell = getCell(newPosition);
	    if(newCell.isOcupied()) {	// new cell is ocupied
		if(newCell.getPlayer().getCurrentStrength() < c.getPlayer().getCurrentStrength()) {
		    c.getPlayer().addEnergy(newCell.getPlayer().getCurrentStrength());
		    newCell.getPlayer().getThread().interrupt();
		}
		else if(newCell.getPlayer().getCurrentStrength() > c.getPlayer().getCurrentStrength()) {
		    newCell.getPlayer().addEnergy(c.getPlayer().getCurrentStrength());
		    c.getPlayer().getThread().interrupt();
		}
		else {
		    if(Math.random() < 0.5) {
			c.getPlayer().addEnergy(newCell.getPlayer().getCurrentStrength());
			newCell.getPlayer().getThread().interrupt();
		    }
		    else {
			newCell.getPlayer().addEnergy(c.getPlayer().getCurrentStrength());
			c.getPlayer().getThread().interrupt();
		    }
		}
	    } else {
		newCell.setPlayer(c.getPlayer());
		c.disoccupy(); 
	    }
	}
	notifyAll();
	//	System.out.println("Acabou o moveplayr");
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
}
