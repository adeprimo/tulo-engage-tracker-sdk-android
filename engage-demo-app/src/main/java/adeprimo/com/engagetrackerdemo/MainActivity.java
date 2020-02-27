package adeprimo.com.engagetrackerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

import adeprimo.com.engageexample.R;
import adeprimo.com.tuloengagetracker.Content;
import adeprimo.com.tuloengagetracker.Tracker;
import adeprimo.com.tuloengagetracker.User;

public class MainActivity extends AppCompatActivity {

    Date start = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] products = {"product1", "product2"};

        String[] states = {"logged_in"};

       User user = User.builder()
                .userId("123")
                .paywayId("001")
                .products(products)
                .states(states)
                .build();

        Tracker.instance().setUser(user,false);

        Tracker.instance().trackEvent("test", null, null);


    }

    public void buttonClick(View view) {
        Tracker.instance().trackArticleActiveTime(start,new Date(), null);
    }
}
