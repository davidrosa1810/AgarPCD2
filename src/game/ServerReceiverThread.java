package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;

import environment.Direction;
import gui.BoardJComponent;

public class ServerReceiverThread extends Thread{
    
    

    private Socket socket;
    private BufferedReader in;
    
    private Servidor serv;


    public ServerReceiverThread(Socket socket, Servidor serv) throws IOException {
	this.socket = socket;
	this.serv = serv;
	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
	while(true) {
	    try {
		String line = in.readLine();
		String[] elements = line.split(",");
		String direction = elements[0];
		String idString = elements[1];
		int id = Integer.parseInt(idString);
		Direction d = Direction.valueOf(direction);
		Player p = serv.getPlayerById(id);
		p.getCurrentCell().movePlayer(d);
		serv.game.notifyChange();
		try {
		    Thread.sleep(Game.REFRESH_INTERVAL);
		} catch (InterruptedException e) {
		}
	    } catch (IOException e) {

	    }
	}
    }
}
