package adeprimo.com.tuloengagetracker;

import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static java.util.UUID.randomUUID;

public class Event {

    private static final DateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

    private String clientId;
    private final String clientTimestamp;
    private String rootEventId;
    private String sessionId;
    private final String eventId;
    private final String eventType;
    private final Integer eventTypeVersion = 1;
    private Map<String, Object> eventCustomData;
    private Context context;
    private Source source;
    private Content content;
    private User user;

    private Event(String eventType) {
        this.eventType = eventType;
        eventId = randomUUID().toString();
        ISO_8601_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        clientTimestamp = ISO_8601_FORMAT.format(new Date());
    }

    /*public Context context(String organizationId, String productId) {
        //this.context.put("organizationId", organizationId);
        //this.context.put("productId", productId);
        this.context = new Context(organizationId,productId);
        //return this;
    }*/

    public static class Context {
        private final String organizationId;
        private final String productId;

        Context(String organizationId, String productId) {
            this.organizationId = organizationId;
            this.productId = productId;
        }
    }

    public void setSource(Source source) {
        this.source = source;
    }

    /*public void setEventCustomData(String json) {
        GsonToJsonConverter jsonConverter = new GsonToJsonConverter();
        this.eventCustomData = jsonConverter.deserialize(json);
    }*/

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Builder {

        private String clientId;
        private String sessionId;
        private String rootEventId;
        private String eventType;
        private Context context;
        private Content content;
        private User user;
        private Map<String, Object> eventCustomData;

        public Builder(String eventType, String eventCustomData) {
            this.eventType = eventType;
            if (eventCustomData != null) {
                GsonToJsonConverter jsonConverter = new GsonToJsonConverter();
                this.eventCustomData = jsonConverter.deserialize(eventCustomData);
            }
        }

        public Event build() {
            Event event = new Event(eventType);
            event.context = context;
            event.content = content;
            event.user = user;
            event.clientId = clientId;
            event.sessionId = sessionId;
            event.rootEventId = rootEventId;
            event.eventCustomData = eventCustomData;
            return event;
        }

        public Builder context(String organizationId, String productId) {
            Context context = new Context(organizationId,productId);
            this.context = context;
            return this;
        }

        public Builder content(Content content) {
            this.content = content;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder rootEventId(String rootEventId) {
            this.rootEventId = rootEventId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

    }

}
