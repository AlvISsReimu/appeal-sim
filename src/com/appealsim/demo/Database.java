package com.appealsim.demo;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static final String url = "jdbc:mysql://localhost:3306/cgss?user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
	
	
	public static void init(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void close(){
        try {  
            if (conn != null)  
                conn.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
	
	public static ResultSet selectSQL(String sql) {
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
	
	public synchronized static String[] selectTitleAndNameByYyknId(int yyknid) {
		String title = "";
		String name = "";
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select title, name from idol where yyknid = " + yyknid + ";");
			if (rs.next()){
				title = rs.getString("title");
				name = rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return new String[]{title, name};
    }
	
	public synchronized static String[] selectTitleAndNameById(int id) {
		String title = "";
		String name = "";
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select title, name from idol where id = " + id + ";");
			if (rs.next()){
				title = rs.getString("title");
				name = rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return new String[]{title, name};
    }
	
	public static int selectIDByNameAndTitle(String name, String title) {
		int id = 0;
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select id from idol where name = '" + name + "' and title = '" + title + "';");
		if (rs.next())
			id = rs.getInt("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return id;
    }
	
	public static ArrayList<Integer> selectIdByTendAndSkilltype(int tend, int skilltype) {
		ArrayList<Integer> idollist = new ArrayList<Integer>();
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select id from idol where skilltype = " + skilltype + " and tend = " + tend + ";");
		while (rs.next())
			idollist.add(rs.getInt("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return idollist;
    }
	
	public static ArrayList<Integer> selectIdByTypeAndSkilltype(int type, int skilltype) {
		ArrayList<Integer> idollist = new ArrayList<Integer>();
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select id from idol where skilltype = " + skilltype + " and type = " + type + ";");
		while (rs.next())
			idollist.add(rs.getInt("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return idollist;
    }
	
	public static ArrayList<Integer> selectIdByTendAndTypeAndSkilltype(int tend, int type, int skilltype) {
		ArrayList<Integer> idollist = new ArrayList<Integer>();
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select id from idol where skilltype = " + skilltype + " and type = " + type + " and tend = " + tend + ";");
		while (rs.next())
			idollist.add(rs.getInt("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return idollist;
    }
	
	public static int selectFreqById(int id) {
		int freq = 0;
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select freq from idol where id = " + id + ";");
		if (rs.next())
			freq = rs.getInt("freq");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return freq;
    }
	
	public static int selectTypeById(int id) {
		int type = 0;
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select type from idol where id = " + id + ";");
		if (rs.next())
			type = rs.getInt("type");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return type;
    }
	
	public static int selectYyknIdById(int id) {
		int yyknid = 0;
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select yyknid from idol where id = " + id + ";");
		if (rs.next())
			yyknid = rs.getInt("yyknid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return yyknid;
    }
	
	public static int selectIdByYyknId(int yyknid) {
		int id = 0;
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select id from idol where yyknid = " + yyknid + ";");
		if (rs.next())
			id = rs.getInt("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return id;
    }
	
	public static int selectSkilltypeById(int id) {
		int skilltype = 0;
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select skilltype from idol where id = " + id + ";");
		if (rs.next())
			skilltype = rs.getInt("skilltype");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return skilltype;
    }
	
	public static int selectTendById(int id) {
		int tend = 0;
		Database.init();
		try{
			ResultSet rs = Database.selectSQL("select tend from idol where id = " + id + ";");
		if (rs.next())
			tend = rs.getInt("tend");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return tend;
    }
	
	public static int selectTypenumByIds(int[] ids) {
		int typenum = 0;
		Database.init();
		try{
			String sql = "select count(distinct type) typenum from idol where ";
			for (int i = 0; i < ids.length; i++){
				sql += ("id = " + ids[i]);
				if (i != ids.length - 1)
					sql += " or ";
				else
					sql += ";";
			}
			ResultSet rs = Database.selectSQL(sql);
		if (rs.next())
			typenum = rs.getInt("typenum");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Database.close();
		return typenum;
    }
	
}
