package com.connor.jifeng.plm.util;

public class test {

	public static void main(String[] args) {

		String strs = "123123";
		System.out.println(strs.substring(0, 3));

		String path = "asfasfa.aasf.bmp";

		System.out.println(path.substring(path.lastIndexOf(".") + 1,
				path.length()));

		/*
		 * ExcelUtil excel03 = new ExcelUtil(); String path
		 * ="C:\\Users\\Administrator\\Desktop\\数据发放记录.xls"; String path2
		 * ="C:\\Users\\Administrator\\Desktop\\数据发放记录112.xls"; try { File file
		 * = new File(path); String fileName = file.getName();
		 * System.out.println(fileName); String fileDix =
		 * fileName.substring(fileName.lastIndexOf("."),fileName.length());
		 * 
		 * System.out.println(fileDix); List<List<String>> strList =
		 * excel03.readExcel(path);
		 * 
		 * for(List<String> strL : strList){ for(String str : strL){
		 * System.out.print(str+" | "); } System.out.println(""); }
		 * 
		 * ArrayList<JFomPasteBean> ls= new ArrayList<JFomPasteBean>();
		 * ls.add(new JFomPasteBean("1","","","c:\\IMG_0197.JPG","","",""));
		 * ls.add(new JFomPasteBean("2","","","c:\\IMG_0197.JPG","","",""));
		 * ls.add(new JFomPasteBean("3","","","c:\\IMG_0197.JPG","","",""));
		 * ls.add(new JFomPasteBean("4","","","c:\\IMG_0197.JPG","","",""));
		 * ls.add(new JFomPasteBean("5","","","c:\\IMG_0197.JPG","","",""));
		 * ls.add(new JFomPasteBean("6","","","c:\\IMG_0197.JPG","","",""));
		 * //ls.add(new JFomPasteBean("111","","","","","","")); //ls.add(new
		 * JFomPasteBean("111","","","","","",""));
		 * excel03.writeExcel(path,path2,ls,3); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
	}

}
