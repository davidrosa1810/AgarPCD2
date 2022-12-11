package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JFrame;

import environment.Cell;
import gui.BoardJComponent;

public class ReceiverThread extends Thread{

	private ObjectInputStream in;

	private JFrame frame;

	private Cliente cliente;

	private BoardJComponent boardgui;

	private Thread senderThread;
	public ReceiverThread(Socket socket, Cliente cliente, Thread senderThread) throws IOException {
		this.cliente = cliente;
		this.senderThread = senderThread;
		in = new ObjectInputStream (socket.getInputStream());
	}

	@Override
	public synchronized void run() {
		Game game = new Game();
		try {
			DataUnit dadosIniciais = (DataUnit) in.readObject();
			int id = dadosIniciais.getPlayerID();
			cliente.setID(id);
			Cell[][] matrix = dadosIniciais.getMatrix();

			boardgui = new BoardJComponent(game,false);
			cliente.setBoard(boardgui);
			game.setMatrix(matrix);

			frame = new JFrame("cliente.io");

			frame.add(boardgui);
			frame.setSize(800,800);
			frame.setLocation(0, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frame.setVisible(true);

			senderThread.interrupt();
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		} 
		while(true) {
			DataUnit data;
			try {
				data = (DataUnit) in.readObject();
				Cell[][] matrix = data.getMatrix();
				game.setMatrix(matrix);
				boardgui.repaint();
				if(data.getGameIsOver()) {
				    senderThread.interrupt();
				    break;
				}
				if(data.getPlayerIsInactive()) senderThread.interrupt();			 
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
