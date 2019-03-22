package Threads;

import application.PlaygroundController;

public class ReboundThread extends Thread{
	
	PlaygroundController cont;
	boolean running;
	int rebounds;
	
	public ReboundThread(PlaygroundController cont) {
		this.cont = cont;
		running=true;
		rebounds=0;
	}

	public void run() {
		while(running) {
			rebounds=0;
			for (int i=0;i<cont.getLevel().getPacs().size()&&running;i++) {
				if(!cont.getLevel().getPacs().get(i).isDead())
					rebounds+=cont.getLevel().getPacs().get(i).getRebounds();
			}
			cont.getLevel().setRebounds(rebounds);
			//cont.updateRebounds();
			if (rebounds>100)
				running=false;
		}
		
	}
}
