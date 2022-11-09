package gui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import game.AutomaticPlayer;
import game.Game;
import game.PhoneyHumanPlayer;
import game.Player;

import javax.swing.JFrame;

public class GameGuiMain implements Observer {
	private JFrame frame = new JFrame("pcd.io");
	private BoardJComponent boardGui;
	private Game game;

	public GameGuiMain() {
		super();
		game = new Game();
		game.addObserver(this);

		buildGui();

	}

	private void buildGui() {
		boardGui = new BoardJComponent(game);
		frame.add(boardGui);

		frame.setSize(800,800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void init()  {
		frame.setVisible(true);
		
		ArrayList<Thread> automaticPlayers = new ArrayList<>();

		// Demo players, should be deleted
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int id = 0; id < Game.NUM_PLAYERS; id++) {
		    AutomaticPlayer a = new AutomaticPlayer(id, game, (byte)Player.generateInitialEnergy());
		    Thread t = new Thread(a);
		    automaticPlayers.add(t);
		    a.setThread(t);
		    //game.addPlayerToGame(a);
		}
		for(Thread t: automaticPlayers) {
		    t.start();
		}
//		game.addPlayerToGame(new PhoneyHumanPlayer(1, game, (byte)3));
//		game.addPlayerToGame(new PhoneyHumanPlayer(2, game, (byte)2));
//		game.addPlayerToGame(new PhoneyHumanPlayer(3, game, (byte)1));
	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public static void main(String[] args) {
		GameGuiMain game = new GameGuiMain();
		game.init();
	}

}
