package adeprimo.com.engagetrackerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

import adeprimo.com.engageexample.R;
import adeprimo.com.tuloengagetracker.Content;
import adeprimo.com.tuloengagetracker.Tracker;
import adeprimo.com.tuloengagetracker.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Tracker engageTracker = ((EngageDemo) getApplication()).getTracker();
        engageTracker.setOptOut(false);
        Content myContent = new Content();
        myContent.state = "locked";

        User user = new User
                .UserBuilder("123","123").build();
        engageTracker.setUser(user,true);

        engageTracker.setContent(myContent);
        engageTracker.trackEvent("test","{\"data\": {\"first\": 1}}", null);*/

        Content content = Content.builder()
                .state("locked")
                .build();

        Tracker.instance().setContent(content);

        Tracker.instance().trackEvent("test", null, null);


    }
}
