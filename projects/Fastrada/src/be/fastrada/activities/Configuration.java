package be.fastrada.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import be.fastrada.Dashboard;
import be.fastrada.R;

/**
 * Created with IntelliJ IDEA.
 * User: M
 * Date: 6/02/14
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class Configuration extends Activity {
    public static final String PREFS_KEY = "be.fastrada.preferences";
    public static final String PREFS_KEY_MAXSPEED = "maxSpeed";
    public static final String PREFS_KEY_MAXTEMP = "maxTemp";
    public static final String PREFS_KEY_MAXRPM = "maxRpm";
    public static final String PREFS_KEY_TEMP_ALARM = "alarmTemp";

    private SharedPreferences sharedPreferences;
    private EditText etMaxSpeed;
    private EditText etMaxTemperature;
    private EditText etMaxRPM;
    private EditText etAlarmingTemp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        sharedPreferences = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        etMaxSpeed = (EditText) findViewById(R.id.maxSpeed);
        etMaxTemperature = (EditText) findViewById(R.id.maxTemp);
        etMaxRPM = (EditText) findViewById(R.id.maxRPM);
        etAlarmingTemp = (EditText) findViewById(R.id.alarmTemp);

        // initialise
        initialise();

        final Context context = this.getBaseContext();

        // Save configuration
        final Button btnSave = (Button) findViewById(R.id.saveConfig);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfiguration();

            }
        });

        // Cancel configuration
        final Button btnCancel = (Button) findViewById(R.id.cancelConfig);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, be.fastrada.activities.Main.class));
                finish();
            }
        });

    }

    public void initialise() {
        etMaxSpeed.setText(sharedPreferences.getInt(Configuration.PREFS_KEY_MAXSPEED, 300) + "");
        etMaxRPM.setText(sharedPreferences.getInt(Configuration.PREFS_KEY_MAXRPM, 6000) + "");
        etMaxTemperature.setText(sharedPreferences.getInt(Configuration.PREFS_KEY_MAXTEMP, 120) + "");
        etAlarmingTemp.setText(sharedPreferences.getInt(Configuration.PREFS_KEY_TEMP_ALARM, 90) + "");
    }

    public void saveConfiguration() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Context context = this.getBaseContext();

        final int alarmTemp = Integer.parseInt(etAlarmingTemp.getText().toString());
        final int maxTemp = Integer.parseInt(etMaxTemperature.getText().toString());
        final int maxSpeed = Integer.parseInt(etMaxSpeed.getText().toString());
        final int maxRpm = Integer.parseInt(etMaxRPM.getText().toString());

        if (alarmTemp <= maxTemp) {
            editor.putInt(Configuration.PREFS_KEY_MAXSPEED, maxSpeed);
            editor.putInt(Configuration.PREFS_KEY_MAXRPM, maxRpm);
            editor.putInt(Configuration.PREFS_KEY_MAXTEMP, maxTemp);
            editor.putInt(Configuration.PREFS_KEY_TEMP_ALARM, alarmTemp);
            editor.commit();

            Dashboard.setAlarmingTemperature(alarmTemp);
            Dashboard.setMaxTemperature(maxTemp);
            Dashboard.setMaxSpeed(maxSpeed);
            Dashboard.setMaxRPM(maxRpm);

            startActivity(new Intent(context, Main.class));
            finish();
        } else {
            Toast.makeText(this, "Your alarming temperature must not be higher than the maximum temperature.", Toast.LENGTH_LONG).show();
        }


    }

}