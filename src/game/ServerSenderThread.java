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
    
    private int id;


    public ServerSenderThread(Socket socket, int id) throws IOException {
	this.socket = socket;
	out = new ObjectOutputStream(socket.getOutputStream());
	guiMain = GameGuiMain.getInstance();
	this.id = id;
    }
    
    @Override
    public void run() {
	while(true) {
	    try {
		Thread.sleep(Game.REFRESH_INTERVAL);
		out.writeObject(new DataUnit(guiMain.getBoard(),id));
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	}
    }

}