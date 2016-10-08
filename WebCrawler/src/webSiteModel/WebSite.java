package webSiteModel;

import java.util.ArrayList;

public class WebSite {

	private ArrayList<String> ReferencedLinks = new ArrayList<String>();
	private String link;
	private String body;
	
	public ArrayList<String> getReferencedLinks() {
		
		return ReferencedLinks;
	}
	
	public void addReferencedLinks(String referencedLink) {
		
		if(!ReferencedLinks.contains(referencedLink) && !link.equals(referencedLink))
			ReferencedLinks.add(referencedLink);
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	// Just to see the info inside the object of the class
	@Override
	public String toString() {
		return "WebSite [link=" + link + "]";
	}
}