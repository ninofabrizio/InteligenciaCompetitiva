package mainPackage;

import rssModel.Feed;
import rssModel.FeedMessage;
import read.RSSFeedParser;

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