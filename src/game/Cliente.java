package game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import gui.BoardJComponent;

public class Cliente {

	private ReceiverThread receiverThread;
	private SenderThread senderThread;
	
	private boolean alternativeKeys;

	private Socket socket;

	private BoardJComponent board;

	private int id = -1;


	public Cliente(String endereco, int porto, boolean alternativeKeys) throws IOException {
		InetAddress address = InetAddress.getByName(endereco);
		socket = new Socket(address, porto);
		this.alternativeKeys = alternativeKeys;

	}

	public static void main(String[] args) {
		try {
			new Cliente(args[0],Integer.parseInt(args[1]),Boolean.parseBoolean(args[2])).runClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runClient() {
		try {
			senderThread = new SenderThread(socket,this);
			receiverThread = new ReceiverThread(socket,this,senderThread);
			senderThread.start();
			receiverThread.start();
			senderThread.join();
			receiverThread.join();
			socket.close();
		} catch (InterruptedException |IOException e1) {
			e1.printStackTrace();

		}
		
	}


	public BoardJComponent getBoard() {
		return board;
	}

	public void setBoard(BoardJComponent board) {
		this.board = board;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public boolean getAlternativeKeys() {
	    return alternativeKeys;
	}

}
