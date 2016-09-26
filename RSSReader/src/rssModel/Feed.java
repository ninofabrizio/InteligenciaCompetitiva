package rssModel;

import java.util.ArrayList;
import java.util.List;

public class Feed {

        private final String title;
        private final String link;
        private final String description; // not necessary
        private final String language; // not necessary
        private final String copyright; // not necessary
        private final String pubDate;
        //private final String category;

        final List<FeedMessage> entries = new ArrayList<FeedMessage>();

        public Feed(String title, String link, String description, String language,
                        String copyright, String pubDate/*, String category*/) {
                this.title = title;
                this.link = link;
                this.description = description;
                this.language = language;
                this.copyright = copyright;
                this.pubDate = pubDate;
                //this.category = category;
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

        public String getLanguage() {
                return language;
        }

        public String getCopyright() {
                return copyright;
        }

        public String getPubDate() {
                return pubDate;
        }

        /*public String getCategory() {
        		return category;
        }*/
        
        @Override
        public String toString() {
                return "Feed [copyright=" + copyright + ", description=" + description
                                + ", language=" + language + ", link=" + link + ", pubDate="
                                + pubDate + ", title=" + title + /*", category=" + category +*/ "]";
        }
}