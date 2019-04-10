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

       User user = User.builder()
                .userId("123")
                .paywayId("001")
                .build();

        Tracker.instance().setUser(user,false);

        Tracker.instance().trackEvent("test", null, null);


    }
}
