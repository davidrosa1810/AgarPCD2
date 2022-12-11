package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import game.AutomaticPlayer;
import game.CountDownLatch;
import game.Game;
import game.Player;
import game.Servidor;

public class GameGuiMain implements Observer {
    private JFrame frame = new JFrame("pcd.io");
    private static BoardJComponent boardGui;
    private Game game;
    public CountDownLatch checkEndGame;
    Thread[] automaticPlayers = new Thread[Game.NUM_PLAYERS];
    
    public boolean gameHasEnded = false;
    
    private static GameGuiMain INSTANCE = null;
    
    public static GameGuiMain getInstance() {
	if(INSTANCE == null) {
	    INSTANCE = new GameGuiMain();
	}
	return INSTANCE;
    }

    private GameGuiMain() {
	super();
	game = new Game();
	game.addObserver(this);
	checkEndGame = new CountDownLatch(game.NUM_FINISHED_PLAYERS_TO_END_GAME);

	buildGui();

    }

    private void buildGui() {
	boardGui = new BoardJComponent(game, false);
	frame.add(boardGui);

	frame.setSize(800,800);
	frame.setLocation(0, 150);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init()  {
	frame.setVisible(true);
	
	try {
	    Servidor servidor = new Servidor(game);
	    servidor.startServing();
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	for(int id = 0; id < Game.NUM_PLAYERS; id++) {
	    AutomaticPlayer a = new AutomaticPlayer(id, game, Player.generateInitialEnergy());
	    Thread t = new Thread(a);
	    automaticPlayers[id] = t;
	    a.setThread(t);
	    //game.addPlayerToGame(a);
	}
	for(Thread t: automaticPlayers) {
	    t.start();
	}
	try {
	    checkEndGame.await();
	    endGame();
	    
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	//		while(true) {
	//			try {
	//				Thread.sleep(1000);
	//				int playerCount = 0;
	//				for(int x = 0; x < 30; x++) {
	//					for(int y = 0; y < 30; y++) {
	//						if(game.getCell(new Coordinate(x,y)).isOcupied()) playerCount++;
	//					}
	//				}
	//				System.out.println("Player count: " + playerCount);
	//			} catch (InterruptedException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			
	//		}
	//		game.addPlayerToGame(new PhoneyHumanPlayer(1, game, (byte)3));
	//		game.addPlayerToGame(new PhoneyHumanPlayer(2, game, (byte)2));
	//		game.addPlayerToGame(new PhoneyHumanPlayer(3, game, (byte)1));
    }

    public synchronized void endGame() {
	for(Thread player : automaticPlayers) {
	    player.interrupt();
	}
	System.out.println("Jogo terminado");
	gameHasEnded = true;
    }
    
    public synchronized BoardJComponent getBoard() {
	return boardGui;
    }


    @Override
    public void update(Observable o, Object arg) {
	boardGui.repaint();
    }

    public static void main(String[] args) {
	GameGuiMain game = GameGuiMain.getInstance();
	game.init();
    }

}
