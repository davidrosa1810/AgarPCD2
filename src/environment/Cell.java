package environment;

import java.io.Serializable;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.AutomaticPlayer;
import game.Game;
import game.Player;
import game.ThreadAwakener;
import gui.GameGuiMain;

public class Cell implements Serializable{
    private Coordinate position;
    private transient Game game;
    private Player player=null;

    private transient Lock lock = new ReentrantLock ();
    private transient Lock lock2 = new ReentrantLock();

    private transient Condition cellIsOcupied = lock2.newCondition();
    private transient Condition cellIsEmpty = lock2.newCondition();

    private transient HashSet<Player> blockedPlayers = new HashSet<Player>();

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

	    if(newCell.isOcupied()) {
		if(newCell.getPlayer().isActive()) {
		    measureStrength(newCell.getPlayer(),getPlayer());
		}
		else {
		    if(getPlayer() instanceof AutomaticPlayer) {
			try {
			    ThreadAwakener a = new ThreadAwakener(this);
			    a.start();
			    wait();
			    movePlayer(Direction.getRandomDirection());
			} catch (InterruptedException e) {
			    getPlayer().getThread().interrupt();
			}
		    }
		}
	    }
	    else {
		newCell.setPlayer(getPlayer());
		try {
		    disoccupy();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		} 
	    }
	    newCell.lock.unlock();
	}
    }

    public synchronized void measureStrength(Player p1, Player p2) {
	if(p1.getCurrentStrength() < p2.getCurrentStrength()) {
	    fight(p2, p1);
	}
	else if(p1.getCurrentStrength() > p2.getCurrentStrength()) {
	    fight(p1, p2);
	}
	else {
	    if(Math.random() < 0.5) {
		fight(p2, p1);
	    }
	    else {
		fight(p1, p2);
	    }
	}
    }

    public synchronized void fight(Player strong, Player weak) {	
	strong.addEnergy(weak.getCurrentStrength());
	weak.setEnergyToZero();
	if(weak instanceof AutomaticPlayer) weak.getThread().interrupt();
	if(strong.getCurrentStrength() == 10) {
	    GameGuiMain.getInstance().checkEndGame.countDown();
	    if(strong instanceof AutomaticPlayer) strong.getThread().interrupt();
	}
    }


    public synchronized HashSet<Player> getBlockedPlayers(){
	return blockedPlayers;
    }

    public synchronized void removeAllBlockedPlayers(){
	blockedPlayers.removeAll(blockedPlayers);
    }

    public synchronized void removeBlockedPlayer(Player p) {
	blockedPlayers.remove(p);
    }

    public synchronized void addBlockedPlayer(Player p) {
	blockedPlayers.add(p);
    }


    public void addPlayerToGame(Player player) {
	lock2.lock();

	if(isOcupied() && getPlayer().getCurrentStrength() == 0) {
	    someoneDied(player);
	}
	else {


	    try {
		while(isOcupied()) {
		    addBlockedPlayer(player);
		    System.out.println("Célula já ocupada; posição: " + position + ", jogador1: " + getPlayer().getIdentification() + ", jogador2: " + player.getIdentification());
		    player.gameStarted = true;
		    cellIsEmpty.await();
		}
		removeBlockedPlayer(player);
		this.player = player;
		cellIsOcupied.signalAll();
		game.notifyChange();
		lock2.unlock();
	    } catch (InterruptedException e) {
		if(!GameGuiMain.getInstance().gameHasEnded) {
		    someoneDied(player);
		}
	    }
	}
    }

    public synchronized void someoneDied(Player player) {
	try {
	    ThreadAwakener a = new ThreadAwakener(this);
	    a.start();
	    wait();
	} catch (InterruptedException e) {
	}
	if(!GameGuiMain.getInstance().gameHasEnded) {
	    game.addPlayerToGame(player);
	}
    }



}
