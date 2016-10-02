package read;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import rssModel.Feed;
import rssModel.FeedMessage;

public class RSSFeedParser {

	// These I use to orient myself inside the XML file of the RSS link
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String LINK = "link";
	private static final String AUTHOR = "author";
	private static final String ITEM = "item";
	private static final String PUB_DATE = "pubDate";
	private static final String CATEGORY = "category";

	private final URL url;

	// Method to create the instance holding the URL directory info
	public RSSFeedParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Malformed URL");
		}
	}

	// Method to read the content of the XML file and extract what I need from it
	public Feed readFeed() {
		
		Feed feed = null;
		
		try {
			// Flags I use to make sure I take the right info
			boolean isFeedHeader = true;
			boolean isLink = true;
			boolean isTitle = true;
			
			// Setting these empty in case there're some of them missing inside
			// the XML file
			String description = "";
			String title = "";
			String link = "";
			String author = "";
			String pubdate = "";
			String category = "";
			
			// Creating what I need to start reading the XML file
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream in = read();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			// Reading the file. Here I pull info for each article inside the
			// RSS Feed (that's why a loop)
			while (eventReader.hasNext()) {
				
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName().getLocalPart();
					
					// Here I check the blocks that contain what I want inside the file
					switch (localPart) {
						// Regarding my feed
						case ITEM:
							if (isFeedHeader) {
								isFeedHeader = false;
								feed = new Feed(title, link, description);
								isLink = true;
								isTitle = true;
							}
							event = eventReader.nextEvent();
							break;
							
						case TITLE:
							if(isTitle) {
								isTitle = false;
								title = getCharacterData(event, eventReader);
							}
							break;
							
						case DESCRIPTION:
							description = getCharacterData(event, eventReader);
							break;
							
						case LINK:
							if(isLink) {
								isLink = false;
								link = getCharacterData(event, eventReader);
							}
							break;
							
						case AUTHOR:
							author = getCharacterData(event, eventReader);
							break;
							
						case PUB_DATE:
							pubdate = getCharacterData(event, eventReader);
							break;
							
						case CATEGORY:
							category = getCharacterData(event, eventReader);
							break;
					}
				} else if (event.isEndElement()) {
					
					// It got to the end of the XML (at least what interests me)
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						// Passing all the info I got to my objects
						FeedMessage message = new FeedMessage();
						message.setAuthor(author);
						message.setDescription(description);
						message.setLink(link);
						message.setTitle(title);
						message.setPublicationDate(pubdate);
						message.setCategory(category);
						feed.getMessages().add(message);
						
						event = eventReader.nextEvent();
						isLink = true;
						isTitle = true;
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem at reading XML");
		}
		return feed;
	}

	// Method to read and get the info from the event
	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
		
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	// Method to open the data stream from the URL address
	private InputStream read() {
		
		try {
			return url.openStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem at opening feed URL");
		}
	}
}