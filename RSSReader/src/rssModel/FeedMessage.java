package rssModel;

public class FeedMessage {

    private String title;
    private String description;
    private String link;
    private String author;
    private String publicationDate;
	private String category;
    //private String body;

    public String getTitle() {
            return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
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
    
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	/*public String getBody() {
			return body;
	}

	public void setBody(String body) {
			this.body = body;
	}*/
	
    @Override
    public String toString() {
            return "FeedMessage [title=" + title + ", description=" + description
                            + ", link=" + link + ", author=" + author
                            + ", publication date=" + publicationDate
                            + ", category=" + category
                            /*+ ", body=" + body*/ + "]";
    }
}