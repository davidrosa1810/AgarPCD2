package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class HumanPlayer extends Player implements Serializable{

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public HumanPlayer(int id, Game game) {
	super(id, game, (byte) 5);
    }

    public void runClient() {
	try {
	    connectToServer();
	} catch (IOException e) {// ERRO...
	} finally {//a fechar...
	    try {
		socket.close();
	    } catch (IOException e) {//...
	    }
	}
    }

    void connectToServer() throws IOException {
	InetAddress endereco = InetAddress.getByName(null);
	System.out.println("Endereco:" + endereco);
	//socket = new Socket(endereco, SimpleServer.PORTO);
	System.out.println("Socket:" + socket);
	in = new BufferedReader(new InputStreamReader(
		socket.getInputStream()));
	out = new PrintWriter(new BufferedWriter(
		new OutputStreamWriter(socket.getOutputStream())),
		true);
    }


    @Override
    public boolean isHumanPlayer() {
	return true;
    }

}
