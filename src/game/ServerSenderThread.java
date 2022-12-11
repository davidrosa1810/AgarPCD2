package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import environment.Cell;
import environment.Direction;
import gui.GameGuiMain;

public class ServerSenderThread extends Thread{
    
    private Socket socket;
    private ObjectOutputStream out;
    
    private GameGuiMain guiMain;
    
    private Game game;
    
    private int id;
    
    private Player player;


    public ServerSenderThread(Socket socket, int id, Game game, Player player) throws IOException {
	this.socket = socket;
	out = new ObjectOutputStream(socket.getOutputStream());
	guiMain = GameGuiMain.getInstance();
	this.id = id;
	this.game = game;
	this.player = player;
    }
    
    @Override
    public void run() {

    	while(true) {
	    try {

	    DataUnit d = new DataUnit(game.getBoard(),id, guiMain.gameHasEnded, player.getCurrentStrength() == 0);
	    out.writeObject(d);
	    out.reset();
		
		Thread.sleep(Game.REFRESH_INTERVAL);
	    } catch (InterruptedException e) {
		System.out.println("SAIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
	    e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	System.out.println("SAIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
	    }
	    

	    
	}
    }

}
