package game;

import java.io.Serializable;

import javax.swing.JComponent;

import environment.Cell;
import gui.BoardJComponent;

public class DataUnit implements Serializable{
    
    private final Cell[][] matrix;
    private final int playerID;
    
    public DataUnit(Cell[][] board,int playerID) {
	this.matrix = board;
	this.playerID = playerID;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public int getPlayerID() {
        return playerID;
    }
    
}
