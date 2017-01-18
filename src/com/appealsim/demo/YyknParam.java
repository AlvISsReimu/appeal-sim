package com.appealsim.demo;

public class YyknParam {
	
	public String[] i;
	public int[] lv;
	public int guest;
	public int music;
	public int dif;
	public int back;
	
	public YyknParam(String i_1, String i_2, String i_3, String i_4, String i_5, int lv_1, int lv_2, int lv_3, int lv_4, int lv_5, int guest, int music, int dif, int back){
		this.i = new String[]{i_1, i_2, i_3, i_4, i_5};
		this.lv = new int[]{lv_1, lv_2, lv_3, lv_4, lv_5};
		this.guest = guest;
		this.music = music;
		this.dif = dif;
		this.back = back;
	}
	
	public YyknParam(String[] i, int[] lv, int guest, int music, int dif, int back){
		this.i = i;
		this.lv = lv;
		this.guest = guest;
		this.music = music;
		this.dif = dif;
		this.back = back;
	}
	
	public String GenUrlParam(){
		String urlparam = "";
		for (int l = 0; l < 5; l++){
			urlparam += ("i_" + (l + 1) + "=" + i[l] + "&");
			urlparam += ("lv_" + (l + 1) + "=" + lv[l] + "&");
		}
		urlparam += ("guest=" + this.guest + "&");
		urlparam += ("music=" + this.music + "&");
		urlparam += ("dif=" + this.dif + "&");
		urlparam += ("back=" + this.back + "&");
		urlparam += ("p1_da=0&p1_vi=0&p1_vo=0&");
		urlparam += ("p2_da=0&p2_vi=0&p2_vo=0&");
		urlparam += ("p3_da=0&p3_vi=0&p3_vo=0&");
		urlparam += ("p4_da=0&p4_vi=0&p4_vo=0&");
		urlparam += ("p5_da=0&p5_vi=0&p5_vo=0&");
		urlparam += ("p6_da=-1&p6_vi=-1&p1_vo=-1");
		return urlparam;
	}
	
}
