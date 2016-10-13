package mainPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import GUI.FeedList;
import read.RSSFeedParser;
import rssModel.Feed;

public class MainReader {

	private static Vector<String> feedList;
	
	public static void main(String[] args) {
		
		FeedList feedlist = new FeedList();
		feedlist.setVisible(true);
	}

	// Method that reads the text file with the RSS links and populate a vector with them 
	public static Vector<String> getList() {
		
		feedList = new Vector<String>();
		
		String content = readFile();
		List<String> urls = Arrays.asList(content.split("\n"));
		
		for(int i = 0; i < urls.size(); i++)
			feedList.add(urls.get(i));
		
		return feedList;
	}
	
	// Method to get the feed from the URL
	public static Feed getFeed(String url) {

		RSSFeedParser parser = new RSSFeedParser(url);
		return parser.readFeed();
	}

	// Method to get the text file content
	private static String readFile() {
		
		File file = new File("feedsURLs.txt");
		Scanner scanner;
		
		try {
			if(file.exists()) {
				
				scanner = new Scanner(file);
				scanner.useDelimiter("\\Z");
			
				// In case the file is empty
				if(scanner.hasNext() == false) {
					scanner.close();
					return "";
				}
				
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
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
				pw.print("\r\n");
			}
			
			pw.print(url);
			reader.close();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
				
				// Check and treat if the line has the URL I don't want or has empty lines
			    if(currentLine.equals(url) || currentLine.equals(""))
			    	continue;
			    
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
		}
		
		// Finishing by deleting the temporary file
		tempFile.delete();
	}
}