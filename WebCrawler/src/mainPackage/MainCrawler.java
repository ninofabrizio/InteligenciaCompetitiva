package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import read.WebSiteParser;
import webSiteModel.WebSite;
import GUI.InputWindow;
import GUI.ProgressWindow;

public class MainCrawler extends Thread {

	private static int maxLinksNum;
	private static InputWindow iw;
	
	// My lists for visited sites and for sites to visit, respectively
	private static ArrayList<String> crawledSites;
    private static ArrayList<String> linkedSites;
	
    // A fake user agent I use to tell the servers this is a normal browser
 	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    
	public static void main(String[] args) {
		
		iw = new InputWindow();
		iw.setVisible(true);
	}

	// Method to get initial info from the frame
	public static void setInfo(int num, String link) {

		crawledSites = new ArrayList<String>();
	    linkedSites = new ArrayList<String>();
		
		maxLinksNum = num;
		addSite(link);
		
		iw.setTextEmpty();
		iw.setVisible(false);
		startCrawling();
	}

	// Method for the crawl process
	private static void startCrawling() {
		
		int i = 0;
		String link = null;
		ProgressWindow pw = new ProgressWindow(maxLinksNum);
		pw.setVisible(true);
		
		// Setting my user_agent for the site to know me, in this case I'm a (fake) browser
		System.setProperty("http.agent", USER_AGENT);
		
		// I also check if there are no more sites referenced
		while(i < maxLinksNum && (link = nextSite()) != null) {
			
			i++;
		    pw.updateBar(i);
			
			WebSite currentSite = getWebSite(link);
			
			if(currentSite == null)
			    continue;
			
			writeFiles(currentSite, i - 1);
			getReferencedSites(currentSite);
		    
		    // Waiting 10 seconds before the next 'crawl' to avoid getting 'hammered' by the sites
		    if(i < maxLinksNum && linkedSites.size() > 0) {
		    	try {
		    		sleep(10000);
		    	} catch (InterruptedException e) {
		    		e.printStackTrace();
		    	}
		    }
		}
		
		pw.showMessage(i);
		iw.setVisible(true);
	}
	
	// Method to write inside both files (the one containing links and the other the contents)
	private static void writeFiles(WebSite webSite, int siteNumber) {
		
		File referencedLinksFile = new File("referencedLinks.txt");
		File contentsFile = new File("contents.txt");
		
		BufferedReader referenceReader;
		BufferedWriter writer;
		PrintWriter pw;
		
		boolean alreadyReferenced = false;
		
		try {
			
			// Creating the files, if they don't exist
			if(!referencedLinksFile.exists())
				referencedLinksFile.createNewFile();
			if(!contentsFile.exists())
				contentsFile.createNewFile();
			
			writer = new BufferedWriter(new FileWriter(referencedLinksFile, true));
			
			pw = new PrintWriter(new FileWriter(contentsFile, true));

			String currentLine;
			
			// Dealing with the referenced links file first
			for(String site : webSite.getReferencedLinks()) {
				
				referenceReader = new BufferedReader(new FileReader(referencedLinksFile));
				
				// Checking if the link is already written in the file
				while((currentLine = referenceReader.readLine()) != null)
					if(currentLine.equals(site)) {
						alreadyReferenced = true;
						break;
					}
			    
				referenceReader.close();
				
				if(alreadyReferenced) {
					alreadyReferenced = false;
					continue;
				}
				
				writer.write(site + "\r\n");
			}
			
			writer.close();
			
			// Now dealing with the contents file
			pw.print("\r\nSITE NUMBER " + (siteNumber + 1) + "\r\nLINK: " + webSite.getLink());
			pw.print("\r\n\r\nLINKS IT REFERENCES:\r\n");
			for(String site : webSite.getReferencedLinks())
				pw.print(site + "\r\n");
			pw.print("\r\nCONTENT:\r\n" + webSite.getBody() + "\r\n\r\n\r\n\r\n\r\n\r\n==================== END OF LINK ====================\r\n\r\n\r\n\r\n\r\n\r\n");
			
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to get the sites referenced from a current one and to try to add to the 'crawling' line
	private static void getReferencedSites(WebSite currentSite) {
		
		// Sorting the order of the links before adding them, just to make things more interesting...
		Collections.shuffle(currentSite.getReferencedLinks());
		
		for(String site : currentSite.getReferencedLinks())
			addSite(site);
	}

	// Method to get the web site info from the link
	public static WebSite getWebSite(String url) {

		WebSiteParser parser = new WebSiteParser(url);
		
		if(parser.urlIsSet())
			return parser.readWebSite();
		
		// In case the url is not set
		return null;
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