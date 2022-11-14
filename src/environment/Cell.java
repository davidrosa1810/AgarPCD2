package environment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.Game;
import game.Player;

public class Cell {
    private Coordinate position;
    private Game game;
    private Player player=null;

    private Lock lock = new ReentrantLock ();

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

    public synchronized void disoccupy() {
	lock.lock();
	this.player=null;
	notifyAll();
	lock.unlock();
    }


    public Player getPlayer() {
	return player;
    }

    public void setPlayer(Player player) {
	this.player=player;
    }

    public synchronized void movePlayer(Direction d) {
	//	    lock.lock();
	//	System.out.println("entrei no movePlayer");
	//	    try {
	//		Thread.sleep(1000);
	//	    } catch (InterruptedException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	    }
	Coordinate newPosition = getPosition().translate(d.getVector());
	if(!newPosition.outOfBounds()) {
	    Cell newCell = game.getCell(newPosition);
	    newCell.lock.lock();
	    if(newCell.isOcupied()) {	// new cell is ocupied
		if(newCell.getPlayer().getCurrentStrength() < getPlayer().getCurrentStrength()) {
		    getPlayer().addEnergy(newCell.getPlayer().getCurrentStrength());
		    newCell.getPlayer().getThread().interrupt();
		}
		else if(newCell.getPlayer().getCurrentStrength() > getPlayer().getCurrentStrength()) {
		    newCell.getPlayer().addEnergy(getPlayer().getCurrentStrength());
		    getPlayer().getThread().interrupt();
		}
		else {
		    if(Math.random() < 0.5) {
			getPlayer().addEnergy(newCell.getPlayer().getCurrentStrength());
			newCell.getPlayer().getThread().interrupt();
		    }
		    else {
			newCell.getPlayer().addEnergy(getPlayer().getCurrentStrength());
			getPlayer().getThread().interrupt();
		    }
		}
	    } else {
		newCell.setPlayer(getPlayer());
		disoccupy(); 
	    }
	    newCell.lock.unlock();
	}

	notifyAll();
	//	System.out.println("Acabou o moveplayr");
    }

    // Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
    public synchronized void addPlayerToGame(Player player) throws InterruptedException {
	while(isOcupied()) {
	    System.out.println("Célula já ocupada; posição: " + position + ", jogador1: " + getPlayer().getIdentification() + ", jogador2: " + player.getIdentification());
	    System.out.println("A esperar");
	    player.gameStarted = true;
	    wait();
	    System.out.println("Acabou a espera");
	}

	this.player = player;
	game.notifyChange();

























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
	//	notifyAll();	    
    }



}
