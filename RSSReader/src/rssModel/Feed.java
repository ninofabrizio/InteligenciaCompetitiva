package rssModel;

import java.util.ArrayList;
import java.util.List;

public class Feed {

        private final String title;
        private final String link;
        private final String description;

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

        /*public String getCategory() {
        		return category;
        }*/
        
        @Override
        public String toString() {
                return "Feed [description=" + description
                                + ", link=" + link + ", title=" + title + "]";
        }
}