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

		// TODO Test
		//System.out.println("Number " + maxLinksNum + "\nLink " + originalLink);
		
		startCrawling();
	}

	// Method for the crawl process
	private static void startCrawling() {
		
		int i = 0;
		String link = null;
		ProgressWindow pw = new ProgressWindow(maxLinksNum);
		pw.setVisible(true);
		
		// I also check if there are no more sites referenced
		while(i < maxLinksNum && (link = nextSite()) != null) {
			
			//link = nextSite();
			WebSite currentSite = getWebSite(link);
			
			writeFiles(currentSite, i);
			getReferencedSites(currentSite);
			
			i++;
		    pw.updateBar(i);
		}
		
		pw.showMessage(i);
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
			
			referenceReader = new BufferedReader(new FileReader(referencedLinksFile));
			writer = new BufferedWriter(new FileWriter(referencedLinksFile, true));
			
			pw = new PrintWriter(new FileWriter(contentsFile, true));

			String currentLine;
			
			// Dealing with the referenced links file first
			for(String site : webSite.getReferencedLinks()) {
				// Checking if the link is already written in the file
				while((currentLine = referenceReader.readLine()) != null)
					if(currentLine.equals(site)) {
						alreadyReferenced = true;
						break;
					}
			    
				if(alreadyReferenced) {
					alreadyReferenced = false;
					continue;
				}
				
				writer.write(site + "\n");
			}

			referenceReader.close();
			writer.close();
			
			// Now dealing with the contents file
			pw.print("\nSITE NUMBER " + (siteNumber + 1) + "\nLINK: " + webSite.getLink());
			pw.print("\n\nLINKS IT REFERENCES:\n");
			for(String site : webSite.getReferencedLinks())
				pw.print(site + "\n");
			pw.print("\nCONTENT:\n" + webSite.getBody() + "\n\n==================== END OF LINK ====================\n\n");
			
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to get the sites referenced from a current one and to try to add to the 'crawling' line
	private static void getReferencedSites(WebSite currentSite) {
		
		for(String site : currentSite.getReferencedLinks())
			addSite(site);
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