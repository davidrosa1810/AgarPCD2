package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JFrame;

import environment.Direction;
import gui.BoardJComponent;

public class ServerReceiverThread extends Thread{
    
    

    private Socket socket;
    private BufferedReader in;


    public ServerReceiverThread(Socket socket) throws IOException {
	this.socket = socket;
	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
	while(true) {
	    try {
		String line = in.readLine();
		String[] elements = line.split(",");
		String direction = elements[0];
		String idString = elements[1];
		int id = Integer.parseInt(idString);
		Direction d = Direction.valueOf(direction);
		
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
}
