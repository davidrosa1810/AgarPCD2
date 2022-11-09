package environment;

import game.Game;
import game.Player;

public class Cell {
    private Coordinate position;
    private Game game;
    private Player player=null;

    public Cell(Coordinate position,Game g) {
	super();
	this.position = position;
	this.game=g;
    }

    public Coordinate getPosition() {
	return position;
    }

    public boolean isOcupied() {
	return player!=null;
    }


    public Player getPlayer() {
	return player;
    }

    // Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
    public synchronized void setPlayer(Player player) throws InterruptedException {
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	if(!isOcupied() && player.getCurrentCell() != null) {
//	    Cell c = player.getCurrentCell();
//	    this.player = player;
//	    c.setPlayer(null);
//	    return;
//	}
//	if(player == null) {
//	    this.player=null;
//	    notifyAll();
//	    return;
//	}
//	while(isOcupied() && player.getCurrentCell() == null) {
//	    System.out.println("ciclo while");
//	    System.out.println("Célula já ocupada; posição: " + position + ", jogador1: " + getPlayer().getIdentification() + ", jogador2: " + player.getIdentification());
//	    wait();
//	    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//	}
//	if(isOcupied() && player.getCurrentCell() != null) {
//	    System.out.println("interação");
//	    System.out.println("Célula já ocupada; posição: " + position + ", jogador1: " + getPlayer().getIdentification() + ", jogador2: " + player.getIdentification());
//	    if(player.getCurrentStrength() < getPlayer().getCurrentStrength()) {
//		System.out.println("abababagdsagsa");
//		player.getCurrentCell().setPlayer(null);
//		player.getThread().interrupt();
//		return;
//	    }
//	    else if(player.getCurrentStrength() > getPlayer().getCurrentStrength()) {
//		getPlayer().getCurrentCell().setPlayer(null);
//		getPlayer().getThread().interrupt();
//		this.player = player;
//		return;
//	    }
//	    else {
//		if(Math.random() < 0.5) {
//		    player.getCurrentCell().setPlayer(null);
//		    player.getThread().interrupt();
//		    return;
//		}
//		else {
//		    getPlayer().getCurrentCell().setPlayer(null);
//		    getPlayer().getThread().interrupt();
//		    this.player = player;
//		    return;
//		}
//	    }
//	}
//	if(!isOcupied() && player.getCurrentCell() == null) {
//	    this.player = player;
//	    return;
//	}
//	System.out.println("posiçao_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa: " + position);
//	this.player = player;
	//notifyAll();	    
    }



}
