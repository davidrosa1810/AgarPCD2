package game;

import gui.BoardJComponent;

public class DataUnit {
    
    private BoardJComponent board;
    private int playerID;
    
    public DataUnit(BoardJComponent board,int playerID) {
	this.board = board;
	this.playerID = playerID;
    }

    public BoardJComponent getBoard() {
        return board;
    }

    public int getPlayerID() {
        return playerID;
    }
    
}
