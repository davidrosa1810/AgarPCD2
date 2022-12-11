package game;

import java.io.Serializable;

import javax.swing.JComponent;

import environment.Cell;
import gui.BoardJComponent;

public class DataUnit implements Serializable{
    
    private Cell[][] matrix;
    private int playerID;
    private Boolean gameIsOver;
    private Boolean playerIsDead;
    
    public DataUnit(Cell[][] matrix,int playerID, Boolean gameIsOver, Boolean playerIsDead) {
	this.matrix = matrix;
	this.playerID = playerID;
	this.gameIsOver = gameIsOver;
	this.playerIsDead = playerIsDead;
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

	public Boolean getPlayerIsDead() {
		return playerIsDead;
	}
    
}
