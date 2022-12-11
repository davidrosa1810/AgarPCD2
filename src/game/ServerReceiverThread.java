package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import environment.Direction;

public class ServerReceiverThread extends Thread{
    
    private BufferedReader in;
    private Servidor serv;


    public ServerReceiverThread(Socket socket, Servidor serv) throws IOException {
	this.serv = serv;
	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
	while(true) {
	    String line;
	    try {
		line = in.readLine();
		String[] elements = line.split(",");
		String direction = elements[0];
		String idString = elements[1];
		int id = Integer.parseInt(idString);
		Direction d = Direction.valueOf(direction);
		Player p = serv.getPlayerById(id);
		p.getCurrentCell().movePlayer(d);
		serv.game.notifyChange();
	    } catch (IOException e) {
		break;
	    }
	}
    }
}
