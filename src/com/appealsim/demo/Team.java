package com.appealsim.demo;

public class Team {
	
	public int score;
	public int appeal;
	public String[] titleandnames;
	
	public Team(int score, int appeal, String[] titleandnames){
		this.score = score;
		this.appeal = appeal;
		this.titleandnames = titleandnames;
	}
	
	@Override
	public String toString(){
		String s = "";
		s += (score + "  " + appeal + "  ");
		for (int l = 0; l < 5; l++)
			s += titleandnames[l];
		return s;
	}
	
}
