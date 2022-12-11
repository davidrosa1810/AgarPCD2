package game;

public class HumanPlayer extends Player{

    public HumanPlayer(int id, Game game) {
	super(id, game, (byte) 5);
    }

    @Override
    public boolean isHumanPlayer() {
	return true;
    }

}
