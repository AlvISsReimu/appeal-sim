package com.appealsim.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExtr {
	
	public static int ExtScore(String html){
		Pattern pattern = Pattern.compile("最大スコア</th>\\s*<th>\\s*<font\\s*size=\"4%\">[0-9]+</font>\\s*</th>");
        Matcher matcher = pattern.matcher(html);
        String scorestring = "";
        if (matcher.find())
        	scorestring = matcher.group();
        pattern = Pattern.compile(">[0-9]+");
        matcher = pattern.matcher(scorestring);
        if (matcher.find())
        	scorestring = matcher.group();
        return Integer.parseInt(scorestring.substring(1));
	}
	
	public static int ExtAppeal(String html){
		Pattern pattern = Pattern.compile("総合アピール</th>\\s*<th>\\s*<font\\s*size=\"4%\">[0-9]+</font>\\s*</th>");
        Matcher matcher = pattern.matcher(html);
        String scorestring = "";
        if (matcher.find())
        	scorestring = matcher.group();
        pattern = Pattern.compile(">[0-9]+");
        matcher = pattern.matcher(scorestring);
        if (matcher.find())
        	scorestring = matcher.group();
        return Integer.parseInt(scorestring.substring(1));
	}
	
}
