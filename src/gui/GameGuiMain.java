package gui;

import java.io.IOException;
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
    
    private Servidor servidor;
    
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
	checkEndGame = new CountDownLatch(Game.NUM_FINISHED_PLAYERS_TO_END_GAME);

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
	    servidor = new Servidor(game);
	    servidor.startServing();
	} catch (IOException e1) {
	    e1.printStackTrace();
	}

	for(int id = 0; id < Game.NUM_PLAYERS; id++) {
	    AutomaticPlayer a = new AutomaticPlayer(id, game, Player.generateInitialEnergy());
	    Thread t = new Thread(a);
	    automaticPlayers[id] = t;
	    a.setThread(t);
	}
	for(Thread t: automaticPlayers) {
	    t.start();
	}
	try {
	    checkEndGame.await();
	    endGame();
	    
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public synchronized void endGame() {
	for(Thread player : automaticPlayers) {
	    player.interrupt();
	}
	System.out.println("Jogo terminado");
	gameHasEnded = true;
	try {
	    servidor.closeServerSocket();
	} catch (IOException e) {
	    e.printStackTrace();
	}
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
