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


    public HumanPlayer(int id, Game game) {
	super(id, game, (byte) 5);
    }



    @Override
    public boolean isHumanPlayer() {
	return true;
    }

}
