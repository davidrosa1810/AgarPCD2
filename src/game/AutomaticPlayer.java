package game;

import environment.Direction;
import gui.GameGuiMain;
import java.io.Serializable;
public class AutomaticPlayer extends Player implements Runnable, Serializable{


    public AutomaticPlayer(int id, Game game, byte strength) {
	super(id, game, strength);
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
	    if(!GameGuiMain.getInstance().gameHasEnded) {
		while(true) {
		    Direction d = Direction.getRandomDirection();
		    getCurrentCell().movePlayer(d);
		    game.notifyChange();
		    Thread.sleep(originalStrength*game.REFRESH_INTERVAL);
		}
	    }
	} catch (InterruptedException e1) {
	    if(getCurrentStrength() == 0) {
		game.notifyChange();
		for(Player p: getCurrentCell().getBlockedPlayers()) {
		    p.getThread().interrupt();
		}
		getCurrentCell().removeAllBlockedPlayers();
	    }
	}	
    }

}
