package com.fyp.melody.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fyp.melody.R;
import com.fyp.melody.Restaurant;

/**
 * Created by Hananideen on 1/6/2015.
 */
public class SplashScreen extends Activity {

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scrren);
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 2000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this, RestaurantsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.animator.fade_in, R.animator.hold);
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();
    }

}
