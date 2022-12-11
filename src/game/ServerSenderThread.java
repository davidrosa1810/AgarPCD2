package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import environment.Direction;
import gui.GameGuiMain;

public class ServerSenderThread extends Thread{
    
    private Socket socket;
    private ObjectOutputStream out;
    
    private GameGuiMain guiMain;
    
    private Game game;
    
    private int id;


    public ServerSenderThread(Socket socket, int id, Game game) throws IOException {
	this.socket = socket;
	out = new ObjectOutputStream(socket.getOutputStream());
	guiMain = GameGuiMain.getInstance();
	this.id = id;
	this.game = game;
    }
    
    @Override
    public void run() {
	while(true) {
	    try {
		out.writeObject(new DataUnit(game.getBoard(),id));
		Thread.sleep(Game.REFRESH_INTERVAL);
	    } catch (InterruptedException e) {
		
	    } catch (IOException e) {
		
	    }
	    
	}
    }

}
