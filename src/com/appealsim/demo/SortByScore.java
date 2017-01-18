package com.appealsim.demo;

import java.util.Comparator;

public class SortByScore implements Comparator {
	
	public int compare(Object o1, Object o2) {
		Team t1 = (Team) o1;
		Team t2 = (Team) o2;
		if (t1.score < t2.score)
			return 1;
		return -1;
	}
	
}