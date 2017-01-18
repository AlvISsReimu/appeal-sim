package com.appealsim.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class SimMain {
	
	public static ArrayList<Team> teamlist = new ArrayList<Team>();
	public static ArrayList<YyknParam> paramlist = new ArrayList<YyknParam>();
	public static int finished = 0;
	public static int threadnum = 8;
	
	private static int tend = 0;//0 vo; 1 da; 2 vi
	private static int guest = 0;
	private static int music = 38;
	private static int dif = 4;
	private static int back = 111390;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		String filename = "";
		boolean is3cb = true;
		int idlist[] = new int[5];
		int nng_utsuki_id = Database.selectIDByNameAndTitle("島村卯月", "ピースフルデイズ");
		int nng_rin_id = Database.selectIDByNameAndTitle("島村卯月", "ピースフルデイズ");
		int nng_mio_id = Database.selectIDByNameAndTitle("本田未央", "ワンダーエンターテイナー");
		boolean isnng = false;
		boolean onlysametend = false;
		int centerskilltype = -1;
		int centertype = -1;
		int centertend = -1;
		
		try {
			BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
			String str = "";
			
			System.out.print("请选择队伍种类(0 nng三色队; 1 单色队): ");
			if (Integer.parseInt(strin.readLine()) == 0){
				System.out.print("请输入倾向(0 vo; 1 da; 2 vi): ");
				str = strin.readLine();
				tend = Integer.parseInt(str);
				isnng = true;
				if (tend == 0)
					idlist[0] = nng_rin_id;
				else if (tend == 1)
					idlist[0] = nng_mio_id;
				else
					idlist[0] = nng_utsuki_id;
			}
			else{
				System.out.print("请输入center的ID: ");
				str = strin.readLine();
				idlist[0] = Database.selectIdByYyknId(Integer.parseInt(str));
				isnng = false;
				String[] titleandname = Database.selectTitleAndNameById(idlist[0]);
				System.out.println("[" + titleandname[0] + "]" + titleandname[1]);
				
				System.out.print("是否只使用同倾偶像（可能无结果）(y/n): ");
				str = strin.readLine();
				if (str.equals("y"))
					onlysametend = true;
			}
			
			centerskilltype = Database.selectSkilltypeById(idlist[0]);
			centertype = Database.selectTypeById(idlist[0]);
			centertend = Database.selectTendById(idlist[0]);
			
			System.out.print("请输入guest的ID(为空默认center): ");
			str = strin.readLine();
			if (!str.equals(""))
				guest = Integer.parseInt(str);
			
			System.out.print("请输入歌曲的ID(为空默认38 M@GIC): ");
			str = strin.readLine();
			if (!str.equals(""))
				music = Integer.parseInt(str);
			
			System.out.print("请输入歌曲难度(为空默认4 Master): ");
			str = strin.readLine();
			if (!str.equals(""))
				dif = Integer.parseInt(str);
			
			System.out.print("请输入后援表现值(为空默认111390): ");
			str = strin.readLine();
			if (!str.equals(""))
				back = Integer.parseInt(str);
			
			System.out.print("请输入文件名(为空默认倾向名): ");
			str = strin.readLine();
			if (!str.equals(""))
				filename = str;
			else{
				if (tend == 0)
					filename = "vo";
				else if (tend == 1)
					filename = "da";
				else
					filename = "vi";
			}
			
			System.out.print("是否允许3cb（y/n）: ");
			str = strin.readLine();
			is3cb = str.equals("n")? false: true;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (centerskilltype == 1){
			ArrayList<Integer> cblist = null;
			ArrayList<Integer> sclist = null;
			if (isnng){
				cblist = Database.selectIdByTendAndSkilltype(tend, 1);
				sclist = Database.selectIdByTendAndSkilltype(tend, 0);
			}
			else{
				if (!onlysametend){
					cblist = Database.selectIdByTypeAndSkilltype(centertype, 1);
					sclist = Database.selectIdByTypeAndSkilltype(centertype, 0);
				}
				else{
					cblist = Database.selectIdByTendAndTypeAndSkilltype(centertend, centertype, 1);
					sclist = Database.selectIdByTendAndTypeAndSkilltype(centertend, centertype, 0);
				}
			}
			
			for (int idindex = 0; idindex < cblist.size(); idindex++){
				int id = cblist.get(idindex);
				if (id == idlist[0])
					continue;
				idlist[1] = id;
				ArrayList<Integer> cbfreqlist = new ArrayList<Integer>();
				cbfreqlist.add(Database.selectFreqById(idlist[0]));
				int f = Database.selectFreqById(idlist[1]);
				if (cbfreqlist.contains(f))
					continue;
				cbfreqlist.add(f);
				
				for (int i = 0; i < sclist.size(); i++){
					idlist[2] = sclist.get(i);
					ArrayList<Integer> scfreqlist = new ArrayList<Integer>();
					scfreqlist.add(Database.selectFreqById(idlist[2]));
					for (int j = i + 1; j < sclist.size(); j++){
						f = Database.selectFreqById(sclist.get(j));
						if (scfreqlist.contains(f))
							continue;
						scfreqlist.add(f);
						idlist[3] = sclist.get(j);
						for (int k = j + 1; k < sclist.size(); k++){
							f = Database.selectFreqById(sclist.get(k));
							if (scfreqlist.contains(f))
								continue;
							idlist[4] = sclist.get(k);
							
							int typenum = Database.selectTypenumByIds(idlist);
							if (typenum != 3 && isnng)
								continue;
							addIdList2ParamList(idlist);
						}
						scfreqlist.remove(scfreqlist.size() - 1);
					}
				}
				
				if (is3cb){
					for (int idindex2 = idindex + 1; idindex2 < cblist.size(); idindex2++){
						int id2 = cblist.get(idindex2);
						f = Database.selectFreqById(id2);
						if (cbfreqlist.contains(f))
							continue;
						cbfreqlist.add(f);
						idlist[2] = id2;
						
						for (int i = 0; i < sclist.size(); i++){
							idlist[3] = sclist.get(i);
							ArrayList<Integer> scfreqlist = new ArrayList<Integer>();
							scfreqlist.add(Database.selectFreqById(idlist[3]));
							for (int j = i + 1; j < sclist.size(); j++){
								f = Database.selectFreqById(sclist.get(j));
								if (scfreqlist.contains(f))
									continue;
								idlist[4] = sclist.get(j);
								
								int typenum = Database.selectTypenumByIds(idlist);
								if (typenum != 3 && isnng)
									continue;
								addIdList2ParamList(idlist);
							}
						}
					}
				}
			}
		}
		else{
			ArrayList<Integer> cblist = null;
			ArrayList<Integer> sclist = null;
			
			if (!onlysametend){
				cblist = Database.selectIdByTypeAndSkilltype(centertype, 1);
				sclist = Database.selectIdByTypeAndSkilltype(centertype, 0);
			}
			else{
				cblist = Database.selectIdByTendAndTypeAndSkilltype(centertend, centertype, 1);
				sclist = Database.selectIdByTendAndTypeAndSkilltype(centertend, centertype, 0);
			}
			
			for (int idindex = 0; idindex < sclist.size(); idindex++){
				int id = sclist.get(idindex);
				if (id == idlist[0])
					continue;
				idlist[1] = id;
				ArrayList<Integer> scfreqlist = new ArrayList<Integer>();
				scfreqlist.add(Database.selectFreqById(idlist[0]));
				int f = Database.selectFreqById(idlist[1]);
				if (scfreqlist.contains(f))
					continue;
				scfreqlist.add(f);
				
				for (int i = 0; i < cblist.size(); i++){
					idlist[3] = cblist.get(i);
					ArrayList<Integer> cbfreqlist = new ArrayList<Integer>();
					cbfreqlist.add(Database.selectFreqById(idlist[3]));
					for (int j = i + 1; j < cblist.size(); j++){
						f = Database.selectFreqById(cblist.get(j));
						if (cbfreqlist.contains(f))
							continue;
						idlist[4] = cblist.get(j);
						
						for (int idindex2 = idindex + 1; idindex2 < sclist.size(); idindex2++){
							int id2 = sclist.get(idindex2);
							f = Database.selectFreqById(id2);
							if (scfreqlist.contains(f))
								continue;
							scfreqlist.add(f);
							idlist[2] = id2;
							addIdList2ParamList(idlist);
						}
						if (is3cb){
							for (int k = j + 1; k < cblist.size(); k++){
								f = Database.selectFreqById(cblist.get(k));
								if (cbfreqlist.contains(f))
									continue;
								idlist[2] = cblist.get(k);
								addIdList2ParamList(idlist);
							}
						}
					}
				}
			}
		}
		
		System.out.println("共" + paramlist.size() + "条结果。");
		
		File file = new File(filename + ".txt");
		
		try{
	        if (file.exists())
	        	file.delete();
	        file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			
			GetThread[] threads = new GetThread[threadnum];
			for (int i = 0; i < threadnum ; i++){
				threads[i] = new GetThread(i);
				threads[i].start();
			}
			for (int i = 0; i < threadnum ; i++){
				threads[i].join();
			}
			
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			Collections.sort(teamlist, new SortByScore());
			for (Team t: teamlist){
				bw.write(t.toString());
				bw.newLine();
			}
			bw.close();
			System.out.println("已导出为" + filename + ".txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	private static void addIdList2ParamList(int[] idlist){
		int[] yyknidlist = new int[5];
		for (int l = 0; l < 5; l++)
			yyknidlist[l] = Database.selectYyknIdById(idlist[l]);
		YyknParam yyknparam = new YyknParam(
				"ssr_" + yyknidlist[0],
				"ssr_" + yyknidlist[1],
				"ssr_" + yyknidlist[2],
				"ssr_" + yyknidlist[3],
				"ssr_" + yyknidlist[4],
				10, 10, 10, 10, 10, guest, music, dif, back);
		paramlist.add(yyknparam);
	}
	
}
