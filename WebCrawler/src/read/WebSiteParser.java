package read;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import webSiteModel.WebSite;

public class WebSiteParser {

	private URL siteURL;
	
	public WebSiteParser(String url) {

		try {
			siteURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	// Method to check if the URL has been set
	public boolean urlIsSet() {
		
		return (siteURL != null);
	}

	// Method to read the URL content and separate what I want from the rest
	public WebSite readWebSite() {
		
		WebSite site = new WebSite();
		Reader reader;
		StringBuilder contentBuilder = new StringBuilder();
		boolean isTag = false; // I use this flag to ignore some tags
		String str;
		
		site.setLink(siteURL.toString());
		System.out.println("\n\nCrawling: " + site.getLink());
		
		try {
			
			URLConnection conn = siteURL.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    
			reader = new InputStreamReader((InputStream) siteURL.getContent());
			
			// Here I get ready to extract the links
			new ParserDelegator().parse(reader, new HTMLEditorKit.ParserCallback() {
				
				// Doing an override of a class method to be able to extract info from HTML tag
			    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			    	
			        if (t == HTML.Tag.A) {
			        	
			            String link = null;
			            Enumeration<?> attributeNames = a.getAttributeNames();
			            
			            while(attributeNames.hasMoreElements()) {
			            	// Checking if is a link and if the class is not of the following (note that there's no pattern in the names, so depending of the HTML some may still pass)
			            	if (attributeNames.nextElement().equals(HTML.Attribute.HREF)
			            			&& (a.getAttribute(HTML.Attribute.CLASS) == null || (a.getAttribute(HTML.Attribute.CLASS) != null && !a.getAttribute(HTML.Attribute.CLASS).toString().equals("image")))) {
			            	
			            		link = a.getAttribute(HTML.Attribute.HREF).toString().trim();
			                
			            		// Checking that the links prefix or suffix are valid
			            		if(link.startsWith("http://") || link.startsWith("https://"))
			            			site.addReferencedLinks(link);
			            	}
			            }
			        }
			    }
			}, true);
    		
			reader.close();

			// Now I extract the content
		    while ((str = in.readLine()) != null) {
		    	
		    	// Here I ignore lines that contains (usually) javascript tags or style format
		    	if(str.contains("<script") && str.contains("</script>")
		    		|| str.contains("<style") && str.contains("</style>")) {
		    		str = str.replaceAll("<script.*</script>", "");
		    		str = str.replaceAll("<style.*</style>", "");
		    	}
		    	
		    	// In case the tags listed don't end in the same line
		    	if((str.contains("<script") && str.contains("</script>") == false)
		    			|| (str.contains("<style") && str.contains("</style>") == false))
		    		isTag = true;
		    	
		    	// Don't include the line in the group if it's from a tag
		    	if(isTag == false)
		    		contentBuilder.append(str);
		    	
		    	// I do this after including the line to avoid adding lines from tags
		    	if((str.contains("<script") == false && str.contains("</script>"))
		    		|| (str.contains("<style") == false && str.contains("</style>")))
		    		isTag = false;
		    }
		    
		    in.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Removing the rest of the tags
		String content = contentBuilder.toString().replaceAll("\\<[^>]*>", "");
		
		site.setBody(content);
		
		return site;
	}
}