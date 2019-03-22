package Threads;

import application.PlaygroundController;
import javafx.geometry.Bounds;

public class PacsThread extends Thread{
	
	PlaygroundController pac;
	int index;
	int moveH;
	int moveV;
	Bounds bound;
	
	public PacsThread(PlaygroundController pac, int index) {
		this.pac = pac;
		this.index = index;
		moveH=(pac.getLevel().getPacs().get(index).getDir().equals("RIGHT"))?(1):
			(pac.getLevel().getPacs().get(index).getDir().equals("LEFT"))?(-1):0;
		moveV=(pac.getLevel().getPacs().get(index).getDir().equals("UP"))?(-1):
			(pac.getLevel().getPacs().get(index).getDir().equals("DOWN"))?(1):0;
		bound= pac.getPlayArea().getBoundsInLocal();
	}


	private void move() {
		pac.getLevel().movePac(index, moveH, moveV);
		try {
			sleep(pac.getLevel().getPacs().get(index).getWaitTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean hitHorizontalBounds() {
		return (pac.getLevel().getPacs().get(index).getPosX()>=
				(bound.getMaxX()-pac.getLevel().getPacs().get(index).getRad()))||
				(pac.getLevel().getPacs().get(index).getPosX()<=
				(bound.getMinX()+pac.getLevel().getPacs().get(index).getRad()));
	}
	private boolean hitVerticalBounds() {
		return (pac.getLevel().getPacs().get(index).getPosY()-pac.getLevel().getPacs().get(index).getRad()<=bound.getMinY()) 
				|| (pac.getLevel().getPacs().get(index).getPosY()+pac.getLevel().getPacs().get(index).getRad()>=bound.getMaxY());
	}

	@Override
	public void run() {
		while(!pac.getLevel().getPacs().get(index).isDead()) {
			for (int i=0;i<pac.getLevel().getPacs().size();i++) {
				//check collision with other pac-mans
				if (pac.getLevel().collisionPacMan(index,i)&&index!=i) {
					moveH=moveH*-1;
					moveV=moveV*-1;
				}
				
			}
			//check rebound
			if(hitVerticalBounds()||hitHorizontalBounds()) {
				moveH=moveH*-1;
				moveV=moveV*-1;
				pac.getLevel().getPacs().get(index).setRebounds(pac.getLevel().getPacs().get(index).getRebounds()+1);
			}
			//move
			move();
		}
	}

}
