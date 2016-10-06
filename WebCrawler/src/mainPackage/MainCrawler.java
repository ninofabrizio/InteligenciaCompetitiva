package mainPackage;

import java.util.ArrayList;

import read.WebSiteParser;
import webSiteModel.WebSite;
import GUI.InputWindow;
import GUI.ProgressWindow;

public class MainCrawler {

	private static int maxLinksNum;
	
	// My lists for visited sites and for sites to visit, respectively
	private static ArrayList<String> crawledSites = new ArrayList<String>();
    private static ArrayList<String> linkedSites = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		InputWindow iw = new InputWindow();
		iw.setVisible(true);
	}

	// Method to get initial info from the frame
	public static void setInfo(int num, String link) {

		maxLinksNum = num;
		addSite(link);

		// System.out.println("Number " + maxLinksNum + "\nLink " + originalLink);
		
		startCrawling();
	}

	// Method for the crawl process
	private static void startCrawling() {
		
		int i = 0;
		ProgressWindow pw = new ProgressWindow(maxLinksNum);
		pw.setVisible(true);
		
		// I also check if there are no more sites referenced
		while(i < maxLinksNum && linkedSites.size() > 0) {
			
			String link = nextSite();
			WebSite currentSite = getWebSite(link);
			
			writeFiles(currentSite);
			getReferencedSites(currentSite);
			
			i++;
		    pw.updateBar(i);
		}
		
		pw.showMessage(i);
	}
	
	private static void writeFiles(WebSite webSite) {
		// TODO Auto-generated method stub
		
	}
	
	private static void getReferencedSites(WebSite currentSite) {
		// TODO Auto-generated method stub
		
	}

	// Method to get the web site info from the link
	public static WebSite getWebSite(String url) {

		WebSiteParser parser = new WebSiteParser(url);
		return parser.readWebSite();
	}

	// Method to add a link that hasn't been crawled yet
	private static void addSite(String site) {
				
		if (!crawledSites.contains(site))
			linkedSites.add(site);
	}

	// Method to get next link to crawl
	private static String nextSite() {
		
		if (linkedSites.size() == 0)
			return null;
		
		String site = linkedSites.get(0);
		linkedSites.remove(0);
		crawledSites.add(site);
		return site;
	}
}