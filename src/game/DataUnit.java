package game;

import java.io.Serializable;

import javax.swing.JComponent;

import gui.BoardJComponent;

public class DataUnit implements Serializable{
    
    private final JComponent board;
    private final int playerID;
    
    public DataUnit(JComponent board,int playerID) {
	this.board = board;
	this.playerID = playerID;
    }

    public JComponent getBoard() {
        return board;
    }

    public int getPlayerID() {
        return playerID;
    }
    
}
