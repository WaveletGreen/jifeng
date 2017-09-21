package com.connor.jifeng.plm.jfom007;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
	public static void main(String[] args) {
		System.out.println(sdf.format(new Date()));
		
	}

}
