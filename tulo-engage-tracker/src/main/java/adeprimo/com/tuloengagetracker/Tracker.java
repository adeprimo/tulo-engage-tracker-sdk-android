package adeprimo.com.tuloengagetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import adeprimo.com.tuloengagetracker.utils.LogLevel;
import adeprimo.com.tuloengagetracker.utils.Logger;

import static java.util.UUID.randomUUID;

public class Tracker {

    private static final DateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);

    protected static final String PREFERENCE_FILE_NAME = "tuloengagetracker-preferences";

    protected static final String PREF_KEY_TRACKER_OPTOUT = "tracker.optout";
    protected static final String PREF_KEY_TRACKER_SESSIONID = "tracker.sessionid";
    protected static final String PREF_KEY_TRACKER_ROOTEVENTID = "tracker.rooteventid";
    protected static final String PREF_KEY_TRACKER_CLIENTID = "tracker.clientid";
    protected static final String PREF_KEY_TRACKER_USERID = "tracker.userid";
    protected static final String PREF_KEY_TRACKER_PAYWAYID = "tracker.paywayid";
    protected static final String PREF_KEY_TRACKER_STATES = "tracker.states";
    protected static final String PREF_KEY_TRACKER_PRODUCTS = "tracker.products";
    //protected static final String PREF_KEY_TRACKER_POSITIONLON = "tracker.positionlon";
    //protected static final String PREF_KEY_TRACKER_POSITIONLAT = "tracker.positionlat";
    protected static final String PREF_KEY_TRACKER_LOCATION = "tracker.location";

    private static Tracker engageTracker = null;
    private final static String TAG = Tracker.class.getSimpleName();


    public static Tracker init(Tracker tracker) {
        if(engageTracker == null) {
            engageTracker = tracker;
        }
        return instance();
    }

    public static Tracker instance() {
        if (engageTracker == null) {
            throw new IllegalStateException("FATAL: Tracker is not initialized");
        }
        return engageTracker;
    }

    private final Context context;
    private String organizationId;
    private String productId;
    private String eventUrl;
    private LogLevel logLevel;
    private boolean optOut;
    private SharedPreferences preferences;
    private final Dispatcher dispatcher;
    private Content content;
    private User user;
    private String url = "/";

    public static class TrackerBuilder {

        final String organizationId;
        String productId = null;
        final String eventUrl;
        final Context context;
        LogLevel logLevel = LogLevel.OFF;

        public TrackerBuilder(String organizationId, String eventUrl, Context context) {
            this.organizationId = organizationId;
            try {
                new URL(eventUrl);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            this.eventUrl = eventUrl;
            this.context = context;
        }

        public TrackerBuilder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public TrackerBuilder level(LogLevel log) {
            this.logLevel = log;
            return this;
        }

        public Tracker build() {
            return init(new Tracker(this));
        }

    }

    private Tracker(TrackerBuilder builder) {
        this.context = builder.context;
        this.organizationId = builder.organizationId;
        this.productId = builder.productId;
        this.eventUrl = builder.eventUrl;
        this.logLevel = builder.logLevel;
        this.optOut = getPreferences().getBoolean(PREF_KEY_TRACKER_OPTOUT, false);

        ISO_8601_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));

        String clientId = getPreferences().getString(PREF_KEY_TRACKER_CLIENTID, null);
        if (clientId == null) {
            clientId = randomUUID().toString();
            getPreferences().edit().putString(PREF_KEY_TRACKER_CLIENTID, clientId).apply();
        }

        user = User.builder()
                .userId(getPreferences().getString(PREF_KEY_TRACKER_USERID, null))
                .paywayId(getPreferences().getString(PREF_KEY_TRACKER_PAYWAYID, null))
                .states(loadArray(PREF_KEY_TRACKER_STATES))
                .products(loadArray(PREF_KEY_TRACKER_PRODUCTS))
                //.position(getPreferences().getString(PREF_KEY_TRACKER_POSITIONLON, null),getPreferences().getString(PREF_KEY_TRACKER_POSITIONLAT, null))
                .location(getPreferences().getString(PREF_KEY_TRACKER_LOCATION, null))
                .build();

        dispatcher = new Dispatcher(builder.eventUrl);
        newRootEventId();
        startSession();

        Logger.setLogLevel(builder.logLevel);
        Logger.verbose(TAG, "Tracker created.");
    }

    public SharedPreferences getPreferences() {
        return context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

   public void saveArray(String[] list, String key){
        SharedPreferences.Editor editor = getPreferences().edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public String[] loadArray(String key){
        Gson gson = new Gson();
        String json = getPreferences().getString(key, null);
        Type type = new TypeToken<String[]>() {}.getType();
        return gson.fromJson(json, type);
    }


    public void startSession() {
        String sessionId = randomUUID().toString();
        getPreferences().edit().putString(PREF_KEY_TRACKER_SESSIONID, sessionId).apply();

    }

    public void newRootEventId() {
        String rootEventId = randomUUID().toString();
        getPreferences().edit().putString(PREF_KEY_TRACKER_ROOTEVENTID, rootEventId).apply();

    }

    public void setOptOut(boolean optOut) {
        this.optOut = optOut;
        getPreferences().edit().putBoolean(PREF_KEY_TRACKER_OPTOUT, optOut).apply();
    }

    public void trackEvent (String name, @Nullable String customData, @Nullable String eventPrefix) {
         track(name, customData, eventPrefix,null, null, null, false, false);

    }

    public void trackEventWithContentData (String name, @Nullable String customData, @Nullable String url, @Nullable String referrer, @Nullable String referrerProtocol, @Nullable String eventPrefix) {
        track(name, customData, eventPrefix,url, referrer, referrerProtocol, true, true);

    }

    public void trackPageView (String url, @Nullable String referrer, @Nullable String referrerProtocol, @Nullable String eventPrefix) {
        track("pageview", null, eventPrefix,url, referrer, referrerProtocol, true, true);

    }

    public void trackArticleView (String customData, @Nullable String eventPrefix) {
        track("pageview", customData, eventPrefix,null, null, null, true, false);

    }

    public void trackArticleInteraction (String type, @Nullable String eventPrefix) {
        String data = String.format("{\"type\": \"%s\"}", type);
        track("article.interaction", data, eventPrefix,null, null, null, true, false);

    }

    public void trackArticlePurchase (String customData, @Nullable String eventPrefix) {
        track("article.purchase", customData, eventPrefix,null, null, null, true, false);

    }

    public void trackArticleActiveTime (Date startTime, Date endTime, @Nullable String eventPrefix) {
        String data = String.format("{\"activetime\": {\"startTime\": \"%s\",\"endTime\": \"%s\", \"secondsActive\": %s}}", ISO_8601_FORMAT.format(startTime), ISO_8601_FORMAT.format(endTime), Math.floor((endTime.getTime() - startTime.getTime()) / 1000));
        track("activetime", data, eventPrefix,null, null, null, true, false);

    }

    public void trackArticleRead (String customData, @Nullable String eventPrefix) {
        track("article.read", customData, eventPrefix,null, null, null, true, false);

    }


    private void track(String name, @Nullable String eventCustomData, @Nullable String eventPrefix, @Nullable String url, @Nullable String referrer, @Nullable String referrerProtocol, boolean trackData, boolean isNewRoot) {

        String eventType = eventPrefix == null ? "app:" + name : name;

        if (isNewRoot) {
            if (url != null && this.url != url) {
                newRootEventId();
                this.url = url;
            }
        }

        Event event = new Event.Builder(eventType,eventCustomData).context(this.organizationId,this.productId)
                .clientId(getPreferences().getString(PREF_KEY_TRACKER_CLIENTID,""))
                .sessionId(getPreferences().getString(PREF_KEY_TRACKER_SESSIONID, ""))
                .rootEventId(getPreferences().getString(PREF_KEY_TRACKER_ROOTEVENTID,""))
                .user(user)
                .build();

        if (trackData) {
            event.setContent(content);
        }

        Source source = new Source();
        int[] res = Helpers.getResolution(this.context);

        source.screen(res[0],res[1], Helpers.getDepth(this.context));

        source.browser(Helpers.getAppName(this.context),Helpers.getOSName(), Helpers.getUserAgent(this.context), Helpers.getAppVersionName(this.context));

        source.locale(Helpers.getLanguage(), Helpers.getTimezone());

        event.setSource(source);
        if (optOut) return;
        dispatcher.send(event);

    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void removeUser() {
        getPreferences().edit().remove(PREF_KEY_TRACKER_USERID).apply();
        getPreferences().edit().remove(PREF_KEY_TRACKER_PAYWAYID).apply();
        getPreferences().edit().remove(PREF_KEY_TRACKER_STATES).apply();
        getPreferences().edit().remove(PREF_KEY_TRACKER_PRODUCTS).apply();
        getPreferences().edit().remove(PREF_KEY_TRACKER_LOCATION).apply();
    }

    public void setUser(User user, boolean persist) {

        User.Builder builder = User.builder();

        //this.user = user;

        if (persist) {
            if (user.getUserId() != "") {
                getPreferences().edit().putString(PREF_KEY_TRACKER_USERID, user.getUserId()).apply();
            }
            if (user.getPaywayId() != "") {
                getPreferences().edit().putString(PREF_KEY_TRACKER_PAYWAYID, user.getPaywayId()).apply();

            }
            if (user.getStates() != null) {
                saveArray(user.getStates(), PREF_KEY_TRACKER_STATES);
            }
            if (user.getProducts() != null) {
                saveArray(user.getProducts(), PREF_KEY_TRACKER_PRODUCTS);
            }
            //getPreferences().edit().putString(PREF_KEY_TRACKER_POSITIONLON, user.getPositionLon()).apply();
            //getPreferences().edit().putString(PREF_KEY_TRACKER_POSITIONLAT, user.getPositionLat()).apply();
            getPreferences().edit().putString(PREF_KEY_TRACKER_LOCATION, user.getLocation()).apply();
        }
        builder.userId(user.getUserId() != "" ? user.getUserId() : this.user.getUserId());
        builder.paywayId(user.getPaywayId() != "" ? user.getPaywayId() : this.user.getPaywayId());
        builder.states((user.getStates() != null ? user.getStates() : this.user.getStates()));
        builder.products(user.getProducts() != null ? user.getProducts() : this.user.getProducts());
        builder.location(user.getLocation() != "" ? user.getLocation() : this.user.getLocation());

        this.user = builder.build();
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
