package game;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;

import gui.BoardJComponent;

public class Cliente {

    private ReceiverThread receiverThread;
    private SenderThread senderThread;

    private JFrame frame;

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    
    private int id = -1;
    

    public Cliente(String endereco, int porto, int up, int down, int left, int right) throws IOException {
	InetAddress address = InetAddress.getByName(endereco);
	socket = new Socket(address, porto);

	frame = new JFrame("pcd.io");
	
	
    }

    public static void main(String[] args) {
	try {
	    new Cliente(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]),Integer.parseInt(args[5])).runClient();
	    // new Cliente("localhost",8080,KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT).runClient();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void runClient() {
	try {
	    senderThread = new SenderThread(socket,this);
	    receiverThread = new ReceiverThread(socket,this);
	    senderThread.start();
	    receiverThread.start();
	    senderThread.join();
	    receiverThread.join();
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	try {
	    socket.close();
	} catch (IOException e) {//... 
	}
    }
    
    public JFrame getFrame() {
	return frame;
    }
    
    public int getID() {
	return id;
    }
    
    public void setID(int id) {
	this.id = id;
    }

}
