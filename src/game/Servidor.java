package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;



public class Servidor {

    public static final int PORTO = 8080;
    
    private ArrayList<HumanPlayer> players = new ArrayList<HumanPlayer>();
    
    public Game game;
    
    public Servidor(Game game) {
	this.game = game;
    }
    
//    public static void main(String[] args) {
//	try {
//	    new Servidor().startServing();
//	} catch (IOException e) {
//	    // ...
//	}
//    }

    public void startServing() throws IOException {
	ServerSocket ss = new ServerSocket(PORTO);
	try {
	    ss.setSoTimeout(10000);
	    while(true){
		int id = Game.NUM_PLAYERS;
		Socket socket = ss.accept();
		HumanPlayer player = new HumanPlayer(id,game);
		players.add(player);
		game.addPlayerToGame(player);
		new ServerSenderThread(socket,id).start();
		new ServerReceiverThread(socket,this).start();
		id++;
	    }			
	} catch (IOException e) {
	    
	} finally {
	}
    }
    
    public HumanPlayer getPlayerById(int id) {
	for(HumanPlayer p: players) {
	    if(p.getIdentification() == id) return p;
	}
	return null;
    }

}
