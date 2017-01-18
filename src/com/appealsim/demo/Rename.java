package com.appealsim.demo;

import java.io.File;

public class Rename {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("D:\\Downloads\\主文件夹");
		File flist[] = file.listFiles();
		for (File f: flist){
			if (f.isDirectory()){
				File flist2[] = f.listFiles();
				String path = f.getPath();
				for (File f2:flist2){
					if (!f2.isDirectory()){
						String name = flist2[0].getName();
						String newname = name.replaceAll("hack", "2d");
						File newfile = new File(path + "\\" + newname);
						f2.renameTo(newfile);
					}
				}
			}
		}
	}

}
