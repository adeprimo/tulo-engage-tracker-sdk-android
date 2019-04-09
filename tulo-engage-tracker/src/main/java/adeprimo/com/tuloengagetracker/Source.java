package adeprimo.com.tuloengagetracker;

import android.content.Context;

import java.sql.Ref;

public class Source {

    private Screen screen;
    private Browser browser;
    private Locale locale;
    private Url url;
    private Referrer referrer;

    public Source() {
    }

    /*public void setScreen(int width, int height, int colorDepth) {
        this.screen = new Screen();
        screen.height = height;
        screen.width = width;
        screen.colorDepth = colorDepth;
    }*/

    public Source screen(int width, int height, int colorDepth) {
        this.screen = new Screen();
        screen.height = height;
        screen.width = width;
        screen.colorDepth = colorDepth;
        return this;
    }

    public Source browser(String name, String platform, String ua, String version) {
        this.browser = new Browser();
        browser.name = name;
        browser.platform = platform;
        browser.ua = ua;
        browser.version = version;
        return this;
    }

    public Source locale(String language, String timezone_offset) {
        this.locale = new Locale();
        locale.language = language;
        locale.timezone_offset = timezone_offset;
        return this;
    }

    public Source url(String pathname) {
        this.url = new Url();
        url.pathname = pathname;
        return this;
    }

    public Source referrer(String pathname, String protocol) {
        this.referrer = new Referrer();
        referrer.pathname = pathname;
        referrer.protocol = protocol;
        return this;
    }

    public static class Screen {
        private Integer height;
        private Integer width;
        private Integer colorDepth;

        public Screen() {}
    }

    public static class Browser {
        private String name;
        private String platform;
        private String ua;
        private String version;

        public Browser() {}
    }

    public static class Locale {
        private String language;
        private String timezone_offset;

        public Locale() {}
    }

    public static class Url {
        private String pathname;

        public Url() {}
    }

    public static class Referrer {
        private String pathname;
        private String protocol;

        public Referrer() {}
    }
}
