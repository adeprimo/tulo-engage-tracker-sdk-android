package adeprimo.com.tuloengagetracker;

import java.util.Date;

public class Content {
    private final String state;
    private final String type;
    private final String articleId;
    private final Date publishDate;
    private final String title;
    private final String section;
    private final String[] keywords;
    private final String[] authorId;
    private final String articleLon;
    private final String articleLat;

    private Content(Builder builder) {
        this.state = builder.state;
        this.type = builder.type;
        this.articleId = builder.articleId;
        this.publishDate = builder.publishDate;
        this.title = builder.title;
        this.section = builder.section;
        this.keywords = builder.keywords;
        this.authorId = builder.authorId;
        this.articleLon = builder.articleLon;
        this.articleLat = builder.articleLat;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String state;
        private String type;
        private String articleId;
        private Date publishDate;
        private String title;
        private String section;
        private String[] keywords;
        private String[] authorId;
        private String articleLon;
        private String articleLat;

        private Builder() {}

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder articleId(String articleId) {
            this.articleId = articleId;
            return this;
        }

        public Builder publishDate(Date publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder section(String section) {
            this.section = section;
            return this;
        }

        public Builder keywords(String[] keywords) {
            this.keywords = keywords;
            return this;
        }

        public Builder authorId(String[] authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder articleLon(String articleLon) {
            this.articleLon = articleLon;
            return this;
        }

        public Builder articleLat(String articleLat) {
            this.articleLat = articleLat;
            return this;
        }

        public Content build() {
            return new Content(this);
        }
    }
}