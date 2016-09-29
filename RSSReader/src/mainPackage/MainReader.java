package mainPackage;

// TODO Packages for the open URL inside a browser part
//import java.awt.Desktop;
//import java.net.URI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import GUI.FeedList;
import read.RSSFeedParser;
import rssModel.Feed;
import rssModel.FeedMessage;

public class MainReader {

	private static Vector feedList;
	
	public static void main(String[] args) {

		/*RSSFeedParser parser = new RSSFeedParser(
				"http://g1.globo.com/dynamo/carros/rss2.xml");
		Feed feed = parser.readFeed();
		System.out.println(feed);
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);

		}*/
		
		FeedList feedlist = new FeedList();
		feedlist.setVisible(true);
		
		/* TODO Open URL inside a browser part
		
		  try { Desktop desktop = java.awt.Desktop.getDesktop(); URI oURL = new
		  URI(feed.getMessages().get(0).getLink()); desktop.browse(oURL); }
		  catch (Exception e) { e.printStackTrace(); }
		 */
	}

	// TODO This method should read a text file and extract
	// feeds' URL's from it, storing them inside a Vector
	// and returning it.
	public static Vector getList() {
		
		feedList = new Vector();
		
		String content = readFile();
		List<String> urls = Arrays.asList(content.split("\n"));
		
		for(int i = 0; i < urls.size(); i++)
			feedList.add(urls.get(i));
		
		return feedList;
	}

	// Method to get the text file content
	private static String readFile() {
		
		File file = new File("feedsURLs.txt");
		Scanner scanner;
		
		try {
			if(file.exists()) {
				
				scanner = new Scanner(file).useDelimiter("\\Z");
			
				// In case the file is empty
				if(scanner.hasNext() == false)
					return "";
				
				String url = scanner.next();
				scanner.close();
				
				return url;
			}
			// In case file doesn't exist
			else
				file.createNewFile();
			return "";
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("File not found");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't create new file");
		}
	}
	
	// Method to write the new feed's URL in the text file
	public static void addURL(String url) {
		
		File file = new File("feedsURLs.txt");
		PrintWriter pw;
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// In case file is empty (to avoid extra line jump)
			if(reader.readLine() == null)
				pw = new PrintWriter(file);
			else {
				pw = new PrintWriter(new FileWriter(file, true));
				pw.print("\n");
				pw.print(url);
			}
			
			pw.print(url);
			reader.close();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("File nor found");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't open file");
		}
	}

	// Method to remove URL from the text file
	public static void removeURL(String url) {
		
		// I also create a temporary file that equals the original except not having the URL
		File inputFile = new File("feedsURLs.txt");
		File tempFile = new File("temp.txt");
		String currentLine;
		
		// I use this flag to evade extra line jumps
		boolean flag = true;

		BufferedReader reader;
		BufferedWriter writer;
		
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			writer = new BufferedWriter(new FileWriter(tempFile));
			
			// Until the end of the file
			while((currentLine = reader.readLine()) != null) {
				
				// Check and treat if the line has the URL I don't want
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(url) || trimmedLine.equals("")) continue;
			    
			    if(flag) {
			    	writer.write(currentLine);
			    	flag = false;
			    }
			    else
			    	writer.write(System.getProperty("line.separator") + currentLine);
			}
			
			reader.close();
			writer.close();
			
			// I also had to write from the temporary to the original files
			// TODO Reason: I'm not sure. The program didn't let me change the original file
			// (nor rename, nor delete). So in here I just rewrite the changes on the original
			flag = true;
			
			reader = new BufferedReader(new FileReader(tempFile));
			writer = new BufferedWriter(new FileWriter(inputFile));
			
			while((currentLine = reader.readLine()) != null) {
				
				if(flag) {
			    	writer.write(currentLine);
			    	flag = false;
			    }
			    else
			    	writer.write(System.getProperty("line.separator") + currentLine);
			}
			
			reader.close();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem at removing URL");
		}
		
		// Finishing by deleting the temporary file
		tempFile.delete();
	}

	// Method to get the feed from the URL
	public static Feed getFeed(String url) {

		RSSFeedParser parser = new RSSFeedParser(url);
		return parser.readFeed();
	}
}