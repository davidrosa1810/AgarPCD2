package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class Servidor {

    public static final int PORTO = 8080;
    
    private ArrayList<HumanPlayer> players = new ArrayList<HumanPlayer>();
    
    public Game game;
    
    private ServerSocket ss;
    
    public Servidor(Game game) {
	this.game = game;
    }

    public void startServing() throws IOException {
	ss = new ServerSocket(PORTO);
	try {
	    ss.setSoTimeout(10000);
	    int id = Game.NUM_PLAYERS;
	    while(true){
		
		Socket socket = ss.accept();
		HumanPlayer player = new HumanPlayer(id,game);
		players.add(player);
		game.addPlayerToGame(player);
		Thread receiver = new ServerReceiverThread(socket,this);
		receiver.start();
		new ServerSenderThread(socket,id,game,player,receiver).start();
		
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
    
    public void closeServerSocket() throws IOException {
	ss.close();
    }

}
