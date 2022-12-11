package game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gui.GameGuiMain;

public class ServerSenderThread extends Thread{
    private ObjectOutputStream out;
    private GameGuiMain guiMain;
    private Game game;
    private int id;
    private Player player;
    private Thread serverReceiver;

    public ServerSenderThread(Socket socket, int id, Game game, Player player, Thread serverReceiver) throws IOException {
	out = new ObjectOutputStream(socket.getOutputStream());
	guiMain = GameGuiMain.getInstance();
	this.id = id;
	this.game = game;
	this.player = player;
	this.serverReceiver = serverReceiver;
    }

    @Override
    public void run() {

	while(true) {
	    try {
		boolean gameHasEnded = guiMain.gameHasEnded;
		DataUnit d = new DataUnit(game.getBoard(),id, gameHasEnded, !player.isActive());
		out.writeObject(d);
		out.reset();

		if(gameHasEnded) {
		    serverReceiver.interrupt();
		    break;
		}
		Thread.sleep(Game.REFRESH_INTERVAL);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

}
