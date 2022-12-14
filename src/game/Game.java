package game;


import java.util.Observable;

import environment.Cell;
import environment.Coordinate;

public class Game extends Observable {

    public static final int DIMY = 30;
    public static final int DIMX = 30;
    public static final int NUM_PLAYERS = 400;
    public static final int NUM_FINISHED_PLAYERS_TO_END_GAME=15;

    public static final long REFRESH_INTERVAL = 400;
    public static final double MAX_INITIAL_STRENGTH = 3;
    public static final long MAX_WAITING_TIME_FOR_MOVE = 2000;
    public static final long INITIAL_WAITING_TIME = 10000;
    


    protected Cell[][] matrix;

    public Game() {
	matrix = new Cell[Game.DIMX][Game.DIMY];

	for (int x = 0; x < Game.DIMX; x++) 
	    for (int y = 0; y < Game.DIMY; y++)
		matrix[x][y] = new Cell(new Coordinate(x, y),this);
    }

    /** 
     * @param player 
     * @throws InterruptedException 
     */
    public void addPlayerToGame(Player player) {
	Cell initialPos=getRandomCell();
	initialPos.addPlayerToGame(player);

	notifyChange();

    }

    public Cell getCell(Coordinate at) {
	return matrix[at.x][at.y];
    }

    public synchronized Cell getCell(Player p) {
	for(Cell[] c2: matrix) {
	    for(Cell c: c2) {
		if(c.getPlayer() != null && c.getPlayer().equals(p)) return c;
	    }
	}
	return null;
    }

    public Cell getCell(int id) {
	for(Cell[] c2: matrix) {
	    for(Cell c: c2) {
		if(c.getPlayer() != null && c.getPlayer().getIdentification() == id) return c;
	    }
	}
	return null;
    }

    public void notifyChange() {
	setChanged();
	notifyObservers();
    }

    public Cell getRandomCell() {
	Cell newCell=getCell(new Coordinate((int)(Math.random()*Game.DIMX),(int)(Math.random()*Game.DIMY)));
	return newCell; 
    }
    
    public Cell[][] getBoard(){
	return matrix;
    }
    
    public void setMatrix(Cell[][] matrix) {
	this.matrix = matrix;
    }



}
