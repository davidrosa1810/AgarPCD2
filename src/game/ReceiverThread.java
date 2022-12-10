package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;

import gui.BoardJComponent;

public class ReceiverThread extends Thread{

    private Socket socket;
    private ObjectInputStream in;

    private JFrame frame;

    private Cliente cliente;

    public ReceiverThread(Socket socket, Cliente cliente) throws IOException {
	this.socket = socket;
	this.cliente = cliente;
	this.frame = cliente.getFrame();
	in = new ObjectInputStream (socket.getInputStream());
    }

    @Override
    public void run() {
	try {
	    DataUnit dadosIniciais = (DataUnit) in.readObject();
	    int id = dadosIniciais.getPlayerID();
	    cliente.setID(id);
	    notifyAll();
	} catch (ClassNotFoundException | IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	while(true) {
	    try {
		DataUnit data = (DataUnit) in.readObject();
		BoardJComponent board = data.getBoard();
		frame.removeAll();
		frame.add(board);
	    } catch (ClassNotFoundException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

}
