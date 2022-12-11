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
import environment.Coordinate;
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
	//this.frame = cliente.getFrame();
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
		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++)
				if(matrix[x][y].getPlayer() != null)
				System.out.print("1 ");
		
	    boardgui = new BoardJComponent(game,false);
	   

	    frame = new JFrame("cliente.io");
	    
	    frame.add(boardgui);
	    frame.setSize(800,800);
	    frame.setLocation(0, 150);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame.setVisible(true);
	    
	    game.setMatrix(matrix);
	 //   game1.notifyChange();
	   // boardgui.repaint();

	    
	    notifyAll();
	} catch (IOException | ClassNotFoundException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} 
	while(true) {
	    try {
		DataUnit data = (DataUnit) in.readObject();
		Cell[][] matrix = data.getMatrix();

		for (int x = 0; x < Game.DIMX; x++) 
			for (int y = 0; y < Game.DIMY; y++)
				if(matrix[x][y].getPlayer() != null)
				System.out.print("1 ");
		System.out.println();
		game.setMatrix(matrix);
		boardgui.repaint();
		game.notifyChange();
		//frame.add(board);
	    } catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}
