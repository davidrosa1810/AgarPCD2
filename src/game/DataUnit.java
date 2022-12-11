package game;

import java.io.Serializable;

import environment.Cell;

public class DataUnit implements Serializable{

    private Cell[][] matrix;
    private int playerID;
    private Boolean gameIsOver;
    private Boolean playerIsInactive;

    public DataUnit(Cell[][] matrix,int playerID, Boolean gameIsOver, Boolean playerIsInactive) {
	this.matrix = matrix;
	this.playerID = playerID;
	this.gameIsOver = gameIsOver;
	this.playerIsInactive = playerIsInactive;
    }

    public Cell[][] getMatrix() {
	return matrix;
    }

    public int getPlayerID() {
	return playerID;
    }

    public Boolean getGameIsOver() {
	return gameIsOver;
    }

    public Boolean getPlayerIsInactive() {
	return playerIsInactive;
    }

}
