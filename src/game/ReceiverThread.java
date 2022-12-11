package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JComponent;
import javax.swing.JFrame;

import environment.Cell;
import gui.BoardJComponent;

public class ReceiverThread extends Thread{

    private Socket socket;
    private ObjectInputStream in;

    private JFrame frame;

    private Cliente cliente;

    private BoardJComponent boardgui;

    public ReceiverThread(Socket socket, Cliente cliente) throws IOException {
	this.socket = socket;
	this.cliente = cliente;
	this.frame = cliente.getFrame();
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
	    game.setMatrix(matrix);
	    boardgui = new BoardJComponent(game,false);
	    

	    
	    
	    frame.add(boardgui);
	    frame.setSize(800,800);
	    frame.setLocation(0, 150);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    game.notifyChange();
	    boardgui.repaint();

	    frame.setVisible(true);
	    notifyAll();
	} catch (ClassNotFoundException | IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	while(true) {
	    try {
		DataUnit data = (DataUnit) in.readObject();
		frame.removeAll();
		Cell[][] matrix = data.getMatrix();
		game.setMatrix(matrix);
		boardgui.repaint();
		//frame.add(board);
	    } catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}
