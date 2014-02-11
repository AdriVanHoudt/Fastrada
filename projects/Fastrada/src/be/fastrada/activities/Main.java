package be.fastrada.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.fastrada.Dashboard;
import be.fastrada.HoloCircularProgressBar;
import be.fastrada.R;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Dashboard dashboard;
    private ProgressBar rpmIndicator;
    private HoloCircularProgressBar speedMeter;
    private HoloCircularProgressBar tempMeter;
    private TextView tvCurrentTemp;
    private TextView tvCurrentSpeed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rpmIndicator = (ProgressBar) findViewById(R.id.rpmIndicator);
        speedMeter = (HoloCircularProgressBar) findViewById(R.id.speedIndicator);
        tempMeter = (HoloCircularProgressBar) findViewById(R.id.thermometer);
        tvCurrentTemp = (TextView) findViewById(R.id.tvTemperature);
        tvCurrentSpeed = (TextView) findViewById(R.id.tvSpeed);

        // initialise variables
        initialise();
        updateDashboard();

        final ImageView settings = (ImageView) findViewById(R.id.settings);
        final Context context = this.getBaseContext();
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Configuration.class));
                finish();
            }
        });

    }

    public void initialise() {
        final SharedPreferences sharedPreferences = getSharedPreferences("configuration", MODE_PRIVATE);
        dashboard = new Dashboard();
        dashboard.setMaxSpeed(sharedPreferences.getInt("maxSpeed", 300));
        dashboard.setMaxRPM(sharedPreferences.getInt("maxRPM", 6000));
        dashboard.setMaxTemperature(sharedPreferences.getInt("maxTemperature", 120));
        dashboard.setAlarmingTemperature(sharedPreferences.getInt("alarmingTemperature", 90));

        rpmIndicator.setMax(dashboard.getMaxRPM());
        /*voor percentage te berekenen in holoCircularProgressBar
            speedMeter.setProgress(currentSpeed / maxSpeed);
        */

    }

    public void updateDashboard() {
        int currentSpeed = 60;
        int currentTemp = 95;
        int currentRpm = 2000;
        speedMeter.setProgress((float) (currentSpeed / dashboard.getMaxSpeed()));
        tempMeter.setProgress((float) (currentTemp / dashboard.getMaxTemperature()));
        rpmIndicator.setProgress(currentRpm);

        tvCurrentSpeed.setText(currentSpeed + "");
        tvCurrentTemp.setText(currentTemp + "");

        if (currentTemp >= dashboard.getAlarmingTemperature()) {
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(800); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            tempMeter.startAnimation(anim);
        }
    }
}
