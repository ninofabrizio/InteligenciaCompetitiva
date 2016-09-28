package rssModel;

import java.util.ArrayList;
import java.util.List;

public class Feed {

	// Here are the attributes of a feed
	private final String title;
	private final String link;
	private final String description;

	// My list of articles
	final List<FeedMessage> entries = new ArrayList<FeedMessage>();

	public Feed(String title, String link, String description) {
		this.title = title;
		this.link = link;
		this.description = description;
	}

	public List<FeedMessage> getMessages() {
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	// Just to see the info inside the object of the class
	@Override
	public String toString() {
		return "Feed [description=" + description + ", link=" + link
				+ ", title=" + title + "]";
	}
}