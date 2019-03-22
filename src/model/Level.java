package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.geometry.Bounds;

public class Level {
	int levelNum;
	int rebounds;
	ArrayList<PacMan> pacs;
	Score scores;

	public Level() {
		pacs = new ArrayList<>();
		loadScores();
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	
	public ArrayList<PacMan> getPacs() {
		return pacs;
	}

	public void setPacs(ArrayList<PacMan> pacs) {
		this.pacs = pacs;
	}

	public int getRebounds() {
		return rebounds;
	}

	public void setRebounds(int rebounds) {
		this.rebounds = rebounds;
	}
	//Methods

	public void loadScores() {
		File file = new File("../Pac-Man/scores.dat");
		
		if(file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				scores= (Score) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			scores = new Score();
		}
	}
	public void saveScores() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("../Pac-Man/scores.dat"));
			oos.writeObject(scores);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void load(File loadData) {
		try {
			FileReader read = new FileReader(loadData);
			BufferedReader fileRead = new BufferedReader(read);
			String line="";
			int lineNum=0;
			while((line=fileRead.readLine())!=null) {
				if (!(line.charAt(0)=='#')) {
					line.trim();
					if(lineNum==1) {
						levelNum=Integer.parseInt(line);
					}else {
						String[] info = line.split("\t");
						pacs.add(new PacMan(Integer.parseInt(info[0]),Integer.parseInt(info[1]),Integer.parseInt(info[2]),Integer.parseInt(info[3]),
								info[4],Integer.parseInt(info[5]),(info[6].equals("false")?false:true)));
					}
				}
				lineNum++;
			}
			fileRead.close();
			read.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void save(File saveData) {
			try {
				if(!saveData.exists())
					saveData.createNewFile();
				PrintWriter out = new PrintWriter(saveData);
				out.println("#Level \n"+levelNum);
				out.println("#radius\tposX\tposY\twait\tdirection\trebounds\tdead");
				for(int i=0;i<pacs.size();i++)
					out.println(pacs.get(i).getRad()+"\t"+pacs.get(i).getPosX()+"\t"+pacs.get(i).getPosY()+"\t"+
							pacs.get(i).getWaitTime()+"\t"+pacs.get(i).getDir()+"\t"+pacs.get(i).getRebounds()+"\t"+pacs.get(i).isDead());
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public String displayScore() {
		String msg="";
		for(String a:scores.getScores()) {
			msg+=a+"\n";
		}
		return msg;
	}
	public void submitScore(String name) {
		boolean betterPlay=false;
		int index=0;
		for (int i=0;i<scores.getScores().size();i++) {
			String[] comp = scores.getScores().get(i).split(":");
			betterPlay=(Integer.parseInt(comp[1])>rebounds)?true:false;
			if(betterPlay)
				index=i;
		}
		if(betterPlay) {
			scores.getScores().remove(index);
			scores.getScores().add(rebounds+":"+name);
		}
		//save file
		saveScores();
	}
	public void movePac(int index, int moveH, int moveV) {
		
		pacs.get(index).setPosX(pacs.get(index).getPosX()+moveH);
		pacs.get(index).setPosY(pacs.get(index).getPosY()+moveV);
	}
	public boolean collisionPacMan(int index, int index2) {
		int cx1 = pacs.get(index).getPosX();
		int cy1 = pacs.get(index).getPosY();
		int r1 = pacs.get(index).getRad();
		int cx2 = pacs.get(index2).getPosX();
		int cy2 = pacs.get(index2).getPosY();
		int r2 = pacs.get(index2).getRad();
		double distance = Math.sqrt((double)(cx1 - cx2)*(cx1 - cx2) + (cy1 - cy2)*(cy1 - cy2));
		boolean ret=(distance<r1+r2)?true:false;
		return ret;
	}
	public boolean killPacMan(int index, double playerPosX, double playerPosY) {
		int cx1 = pacs.get(index).getPosX();
		int cy1 = pacs.get(index).getPosY();
		int r1 = pacs.get(index).getRad();
		double distance = Math.sqrt((double)(cx1 - playerPosX)*(cx1 - playerPosX) + (cy1 - playerPosY)*(cy1 - playerPosY));
		boolean ret=(distance<r1)?true:false;
		return ret;
	}
	
}
