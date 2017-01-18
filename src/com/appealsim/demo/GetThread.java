package com.appealsim.demo;

import java.io.IOException;
import java.util.Random;

public class GetThread extends Thread{
	
	public int interval = 0;
	
	public GetThread(int interval){
		this.interval = interval;
	}
	
    public void run(){
    	try {
			for (int i = (0 + interval); i < SimMain.paramlist.size(); i += SimMain.threadnum){
				Thread.sleep(Math.abs(new Random().nextInt(1000)));
				YyknParam yyknparam = SimMain.paramlist.get(i);
				Team team = genTeam(yyknparam);
				if (team != null){
					SimMain.teamlist.add(team);
					SimMain.finished++;
					System.out.println(SimMain.finished + "/" + SimMain.paramlist.size());
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private Team genTeam(YyknParam p){
    	try{
        	String html = HttpRequest.sendGet("http://yykn.jp/ss", p.GenUrlParam());
    		Team t = new Team(RegExtr.ExtScore(html), RegExtr.ExtAppeal(html), null);
    		String[] titleandnames = new String[5];
    		for (int l = 0; l < 5; l++){
    			String[] titleandname = Database.selectTitleAndNameByYyknId(Integer.parseInt(p.i[l].substring(4)));
    			String ss = "[" + titleandname[0] + "]";
    			ss += titleandname[1];
    			int length = ss.getBytes("Shift_JIS").length;
    			for (int space = 0; space < (36 - length); space++)
    				ss += " ";
    			titleandnames[l] = ss;
    		}
    		t.titleandnames = titleandnames;
    		return t;
    	} catch (IOException e) {
			e.printStackTrace();
			for (int i=0;i<5;i++)
				System.out.println(p.i[i]);
			return null;
		}
    }
    
}