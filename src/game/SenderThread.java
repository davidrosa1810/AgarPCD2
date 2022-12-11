package game;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import environment.Direction;
import gui.BoardJComponent;

public class SenderThread extends Thread{

    private PrintWriter out;

    private Cliente cliente;

    private int id;

    public SenderThread(Socket socket, Cliente cliente) throws IOException {
	this.cliente = cliente;
	id = cliente.getID();
	out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
    }

    @Override
    public synchronized void run() {

	if(id == -1) {
	    try {
		wait();
	    } catch (InterruptedException e) {
		id = cliente.getID();
	    }
	}
	
	BoardJComponent board = cliente.getBoard();
	while(true) {
	    if(board.getLastPressedDirection() != null) { 
		Direction d = board.getLastPressedDirection();
		board.clearLastPressedDirection();
		out.println(d.toString() + "," + id);
		
	    }
	    try {
		    Thread.sleep(Game.REFRESH_INTERVAL);
		} catch (InterruptedException e) {
		    break;
		}
	}
    }
}
