package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable{

	ArrayList<String> scores;
	public Score() {
		scores=new ArrayList<>();
	}
	public ArrayList<String> getScores() {
		return scores;
	}
	public void setScores(ArrayList<String> scores) {
		this.scores = scores;
	}
	

}
