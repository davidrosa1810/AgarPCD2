package environment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.AutomaticPlayer;
import game.Game;
import game.Player;

public class Cell {
    private Coordinate position;
    private Game game;
    private Player player=null;

    private Lock lock = new ReentrantLock ();
    private Lock lock2 = new ReentrantLock();
    
    private Condition cellIsOcupied = lock2.newCondition();
    private Condition cellIsEmpty = lock2.newCondition();

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

    public void disoccupy() throws InterruptedException {
	lock.lock();
	lock2.lock();
	try {
	    while(!isOcupied()) {
		cellIsOcupied.await();
	    }
	    this.player=null;
	    cellIsEmpty.signalAll();
	} finally {
	    lock.unlock();
	    lock2.unlock();
	}
	
	//notifyAll();
	
    }


    public Player getPlayer() {
	return player;
    }

    public void setPlayer(Player player) {
	this.player=player;
    }

    public synchronized void movePlayer(Direction d) {

	Coordinate newPosition = getPosition().translate(d.getVector());
	if(!newPosition.outOfBounds()) {
	    Cell newCell = game.getCell(newPosition);
	    newCell.lock.lock();
	    if(newCell.isOcupied()) {	// new cell is ocupied
		if(!newCell.getPlayer().isActive()) {}
		else if(newCell.getPlayer().getCurrentStrength() == 0) {
		    if(getPlayer() instanceof AutomaticPlayer) {
			//bloquear
		    }
		}
		else {
		    if(newCell.getPlayer().getCurrentStrength() < getPlayer().getCurrentStrength()) {
			fight(getPlayer(), newCell.getPlayer());
		    }
		    else if(newCell.getPlayer().getCurrentStrength() > getPlayer().getCurrentStrength()) {
			fight(newCell.getPlayer(), getPlayer());
		    }
		    else {
			if(Math.random() < 0.5) {
			    fight(getPlayer(), newCell.getPlayer());
			}
			else {
			    fight(newCell.getPlayer(), getPlayer());
			}
		    }
		}

	    } else {
		newCell.setPlayer(getPlayer());
		try {
		    disoccupy();
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} 
	    }
	    newCell.lock.unlock();
	}

	//notifyAll();
	//	System.out.println("Acabou o moveplayr");
    }

    public synchronized void fight(Player strong, Player weak) {
	strong.addEnergy(weak.getCurrentStrength());
	weak.setEnergyToZero();
	weak.getThread().interrupt();
	if(strong.getCurrentStrength() == 10) {
	    strong.checkEndGame.countDown();
	    strong.getThread().interrupt();
	}
    }
























    // Should not be used like this in the initial state: cell might be occupied, must coordinate this operation
    public void addPlayerToGame(Player player) throws InterruptedException {
	lock2.lock();
	while(isOcupied()) {
	    System.out.println("Célula já ocupada; posição: " + position + ", jogador1: " + getPlayer().getIdentification() + ", jogador2: " + player.getIdentification());
	    System.out.println("A esperar");
	    player.gameStarted = true;
	    cellIsEmpty.await();
	    System.out.println("Acabou a espera");
	}

	this.player = player;
	cellIsOcupied.signalAll();
	game.notifyChange();
	lock2.unlock();

























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
