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
		this.cliente = cliente;
		id = cliente.getID();
		out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	}

	@Override
	public synchronized void run() {

		if(id == -1) {
			try {
				System.out.println("entrei");
				wait();
				System.out.println("sai");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		id = cliente.getID();
		BoardJComponent board = cliente.getBoard();
		while(true) {

			System.out.println("n devia eestar aqui");
			if(board.getLastPressedDirection() != null) { 
				Direction d = board.getLastPressedDirection();
				board.clearLastPressedDirection();
				System.out.println(id);
				out.println(d.toString() + "," + id);
			}

			
		}
	}

}
