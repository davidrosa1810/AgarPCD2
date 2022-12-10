package game;

import environment.Cell;

public class ThreadAwakener extends Thread {

    private Cell c;
    private Player player;

    public ThreadAwakener(Cell c, Player player) {
	this.c = c;
	this.player = player;
    }

    @Override
    public synchronized void run() {
	try {
	    Thread.sleep(2000);
	    if(c.getPlayer().getIdentification() != player.getIdentification()) { //jogador a esperar numa celula com um jogador morto
		throw new InterruptedException();
	    }
	    else { //jogador automatico bloquado por movimentacao contra obstaculo
		synchronized(c) {
		    c.notify();
		}
	    }

	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }



}
