# Tulo Engage Tracker - SDK Android
Tulo Engage Tracker SDK for Android is used for tracking events in Tulo Engage Data Platform.

## Installation
The Tulo Engage Tracker SDK supports projects with minimum SDK version 19 (Android 4.4+).
### Gradle

Add jcenter() to build.gradle and add the tracker as a dependency to your project. Replace <latest-version> with the latest release.

```
repositories {
  jcenter()
  }
```

```
dependencies {
  implementation 'com.adeprimo.tuloengage:tracker:<latest-version>'
  }
```

## Usage
### Permissions
In order to be able to send tracking events you must allow netork access in your AndroidManifest.xml.

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```
### Setup
In order to be able to send tracking events you must initialize the tracker with TrackerBuilder. You will need the organizationId, productId and the URL to the event service.
Afterwards the tracker is available by calling Tracker.instance().
It is recommended to create the tracker in the Application class.

```
public class MyApp extends Application {
  @Override
      public void onCreate() {
          super.onCreate();

          Tracker.init(new Tracker.TrackerBuilder("myorganisation", "http://user-event-service.com/api/v1/events", this.getApplicationContext())
                .productId("MYPRODUCT")
                .level(LogLevel.VERBOSE)
                .build()
        );
      }
```
Do not forget to add the application name (in this example MyApp) to your AndroidManifest.xml.
```
<application android:name=".MyApp">
        <!-- activities goes here -->
    </application>
```

### Session
A new session is started every time the app is started. If you want to start a new session, e.g. when the app enters foreground you can use the startSession function.
```
Tracker.instance().startSession();
```
### Opt Out
If the user of the app does not want to be tracked you can set optOut on Tulo Engage Tracker. If set, the tracker won't send any events.
```
Tracker.instance().setOptOut(true);
```

### Tracking
All tracking in Tulo Engage Tracker is done by sending different events. You can either use predefined or custom events. In addition to the data sent together with the event you can enrich the data by setting user and/or content data. The events are sent straight away and no queuing is implemented at the moment.

### Setting User Data
When set, the user data is always sent together with all events. User data contains the following properties: userId, paywayId, states, products, positionLon, positionLat, location. If you want to save and persist the user data properties in your app so it is available after restarting the app, you can do so by setting persist to true. To delete a property you can set it to null.
```Swift
// set user properties and save them in app
engageTracker.setData(userId: "123456", products: ["product1", "product2"], persist: true)

// delete a property and save it
engageTracker.setData(products: nil, persist: true)
```

### Setting Content Data
Content data is used to provide more data related to the article that triggers the event. When set, the content data is always sent together with all predefined events, except when sending a custom event. If you want to send content data with a custom event you can do so by using trackEventWithContentData.

It is important to always set the content data when the content changes, e.g. when an article is shown. The properties are: state, type, articleId, publishDate, title, section, keywords, authorId, articleLon, articleLat. To delete a property you can set it to null.
```Swift
// the article has loaded so set the content and send a pageview event
engageTracker.setContent(state: "open", type: "free", articleId: "123", title: "My first article", section: "News", keywords: ["news"], authorId: ["John Doe", "Jane Doe"])
engageTracker.trackPageView(url: "/news")
```
### Events
All events are prefixed with app: but if you for some reason want to specify a different prefix you can do so by setting the eventPrefix when sending the event.
#### Page Views
Tracks an app:pageview event. Input is url with optional referrer, referrerProtocol
```Swift
engageTracker.trackPageView(url: "/sports/football")
```
#### Article Events
Predefined article related events

Tracks an app:pageview event. Input is customData formatted as JSON
```Swift
engageTracker.trackArticleView(customData: "{\"category\": \"sports/football\"}")
```
Tracks an app:article.interaction of the specified type. Input type of interaction
```Swift
engageTracker.trackArticleInteraction(type: "open_image")
```
Tracks an app:article.purchase. Input is customData formatted as JSON
```Swift
engageTracker.trackArticlePurchase(customData: "{\"transaction\": {\"id\": \"abc123\",\"revenue\": \"99.90\"}}")
```
Tracks an app:activetime event. Input is startTime and endTime formatted as Date
```Swift
engageTracker.trackArticleActiveTime(startTime: Date(), endTime: Date().addingTimeInterval(TimeInterval(5.0 * 60.0)))
```
Tracks an app:article.read event. Input is customData formatted as JSON
```Swift
engageTracker.trackArticleRead(customData: "{\"category\": \"sports/football\"}")
```
#### Custom Events
Custom events can be used to track e.g. features or actions in the app

Tracks an event of type name. Input name and optional customData formatted as JSON
```Swift
engageTracker.trackEvent(name: "screenshot", customData: "{\"article\": \"123\"})
```
Tracks an event of type name and includes content data if set. Input name and optional customData formatted as JSON, url, referrer, referrerProtocol
```Swift
engageTracker.trackEventWithContentData(name: "screenshot",url: "/sports/football")
```
