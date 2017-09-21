package com.connor.jifeng.plm.jfom016;

import java.io.IOException;

public class test {
	public static void main(String[] args) {
		String proj_file_name = "F:/INFO/123(saf    ).txt";
		String file_url = proj_file_name.replace("(", "^(");

		System.out.println("path :" + file_url);
		String cmd = "cmd  /c \"" + file_url + "\"";
		try {
			System.out.println("cmd = " + cmd);
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
