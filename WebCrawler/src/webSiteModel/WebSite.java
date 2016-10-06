package webSiteModel;

import java.util.ArrayList;

public class WebSite {

	private ArrayList<String> ReferencedLinks;
	private String title;
	private String link;
	private String author;
	private String publicationDate;
	private String body;
	
	public ArrayList<String> getReferencedLinks() {
		return ReferencedLinks;
	}
	
	public void setReferencedLinks(ArrayList<String> referencedLinks) {
		ReferencedLinks = referencedLinks;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublicationDate() {
		return publicationDate;
	}
	
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
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
		return "WebSite [title=" + title + ", link=" + link + ", author=" + author
				+ ", publication date=" + publicationDate + "]";
	}
}