package mainPackage;

import GUI.InputWindow;

public class MainReader {

	private static int maxLinksNum;
	
	public static void main(String[] args) {
		
		InputWindow iw = new InputWindow();
		iw.setVisible(true);
	}

	public static void setMaxLinksNum(int num) {
		
		maxLinksNum = num;
		//System.out.println(maxLinksNum);
	}
}