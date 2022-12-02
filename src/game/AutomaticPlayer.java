package game;

import environment.Direction;

public class AutomaticPlayer extends Player implements Runnable {


    public AutomaticPlayer(int id, Game game, byte strength, CountDownLatch checkEndGame) {
	super(id, game, strength, checkEndGame);
    }

    @Override
    public boolean isHumanPlayer() {
	return false;
    }

    @Override
    public void run() {

	try {
	    game.addPlayerToGame(this);
	    if(!gameStarted) Thread.sleep(2000);
	    else Thread.sleep(game.REFRESH_INTERVAL);
	    while(true) {
		Direction d = Direction.values()[(int)(Math.random()*Direction.values().length)];
		getCurrentCell().movePlayer(d);
		game.notifyChange();
		Thread.sleep(originalStrength*game.REFRESH_INTERVAL/3);
	    }
	} catch (InterruptedException e1) {
	    if(getCurrentStrength() == 0) {
		setInactive();
		game.notifyChange();
	    }
	}	
    }

}
