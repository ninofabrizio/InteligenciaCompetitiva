package mainPackage;

import read.RSSFeedParser;
import rssModel.Feed;
import rssModel.FeedMessage;

public class MainReader {
	
        public static void main(String[] args) {
                RSSFeedParser parser = new RSSFeedParser(
                                "http://g1.globo.com/dynamo/carros/rss2.xml");
                Feed feed = parser.readFeed();
                System.out.println(feed);
                for (FeedMessage message : feed.getMessages()) {
                        System.out.println(message);

                }
        }
}