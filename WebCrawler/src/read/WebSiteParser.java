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

	private final URL siteURL;
	
	public WebSiteParser(String url) {
		
		try {
			siteURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Malformed URL");
		}
	}

	// Method to read the URL content and separate what I want from the rest
	public WebSite readWebSite() {
		
		WebSite site = new WebSite();
		Reader reader;
		StringBuilder contentBuilder = new StringBuilder();
		boolean isTag = false; // I use this flag to ignore some tags
		String str;
		
		site.setLink(siteURL.toString());
		
		try {
			
			URLConnection conn = siteURL.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    
		    while ((str = in.readLine()) != null) {
		    	
		    	// Here I get ready to extract links
		    	if(str.contains("http://")) {
		    		
					reader = new InputStreamReader((InputStream) siteURL.getContent());
					
					new ParserDelegator().parse(reader, new HTMLEditorKit.ParserCallback() {
						
						// Doing an override of a class method to be able to extract info from HTML tag
					    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
					    	
					        if (t == HTML.Tag.A) {
					        	
					            String link = null;
					            Enumeration<?> attributeNames = a.getAttributeNames();
					            
					            if (attributeNames.nextElement().equals(HTML.Attribute.HREF)) {
					                link = a.getAttribute(HTML.Attribute.HREF).toString();
					                
					                // I decided to only include the ones that end with '.html' to get
					                // closer to a more precise group of related links to the original
					                if(link.endsWith(".html"))
					                	site.addReferencedLinks(link);
					            }
					            
					        }
					    }
					}, true);
		    		
					reader.close();
		    	}
		    	
		    	// Here I ignore lines that contains (usually) javascript tags
		    	if(str.contains("<script") && str.contains("</script>"))
		    		str = str.replaceAll("<script.*</script>", "");
		    	
		    	// In case the tags listed don't end in the same line
		    	else if((str.contains("<script") && str.contains("</script>") == false)
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
			throw new RuntimeException("Problem at reading URL");
		}
		
		// Removing the rest of the tags
		String content = contentBuilder.toString().replaceAll("\\<[^>]*>", "");
		
		site.setBody(content);
		
		// TODO Tests
		//for(String link : site.getReferencedLinks())
		//	System.out.println(link);
		
		//System.out.println(content);
		
		return site;
	}
}