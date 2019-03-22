package model;

public class PacMan {
	int rad;
	int posX;
	int posY;
	int waitTime;
	String dir;
	int rebounds;
	boolean dead;
	
	public PacMan(int rad, int posX, int posY, int waitTime, String dir, int rebounds, boolean dead) {
		super();
		this.rad = rad;
		this.posX = posX;
		this.posY = posY;
		this.waitTime = waitTime;
		this.dir = dir;
		this.rebounds = rebounds;
		this.dead = dead;
	}

	public int getRad() {
		return rad;
	}

	public void setRad(int rad) {
		this.rad = rad;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getRebounds() {
		return rebounds;
	}

	public void setRebounds(int rebounds) {
		this.rebounds = rebounds;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	
}
