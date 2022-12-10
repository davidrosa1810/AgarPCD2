package game;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;

import environment.Direction;
import gui.BoardJComponent;

public class SenderThread extends Thread{

    private Socket socket;
    private PrintWriter out;

    private JFrame frame;

    private Cliente cliente;

    private int id;

    public SenderThread(Socket socket, Cliente cliente) throws IOException {
	this.socket = socket;
	this.frame = cliente.getFrame();
	this.cliente = cliente;
	id = cliente.getID();
	out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
    }

    @Override
    public void run() {
	if(id == -1) {
	    try {
		wait();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	while(true) {
	    BoardJComponent board = (BoardJComponent) frame.getComponent(0);
	    Direction d = board.getLastPressedDirection();
	    board.clearLastPressedDirection();
	    out.println(d.toString() + "," + id);
	}
    }

}
