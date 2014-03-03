package be.fastrada.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import be.fastrada.Dashboard;
import be.fastrada.R;

/**
 * @author: Shana Steenssens
 * @version: 1.0 2/03/14 20:06
 */
public class GeneralConfig extends Activity {
    private EditText etMaxSpeed;
    private EditText etMaxTemperature;
    private EditText etMaxRPM;
    private EditText etAlarmingTemp;
    private SharedPreferences sharedPreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generalconfig);

        sharedPreferences = getSharedPreferences(UiConfig.PREFS_KEY, MODE_PRIVATE);
        etMaxSpeed = (EditText) findViewById(R.id.maxSpeed);
        etMaxTemperature = (EditText) findViewById(R.id.maxTemp);
        etMaxRPM = (EditText) findViewById(R.id.maxRPM);
        etAlarmingTemp = (EditText) findViewById(R.id.alarmTemp);

        initialise();

        final Button btnSave = (Button) findViewById(R.id.saveConfig);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfiguration();
            }
        });
    }

    private void initialise() {
        etMaxSpeed.setText(sharedPreferences.getInt(UiConfig.PREFS_KEY_MAXSPEED, 300) + "");
        etMaxRPM.setText(sharedPreferences.getInt(UiConfig.PREFS_KEY_MAXRPM, 6000) + "");
        etMaxTemperature.setText(sharedPreferences.getInt(UiConfig.PREFS_KEY_MAXTEMP, 120) + "");
        etAlarmingTemp.setText(sharedPreferences.getInt(UiConfig.PREFS_KEY_TEMP_ALARM, 90) + "");
    }

    public void saveConfiguration() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final int alarmTemp = Integer.parseInt(etAlarmingTemp.getText().toString());
        final int maxTemp = Integer.parseInt(etMaxTemperature.getText().toString());

        if (alarmTemp <= maxTemp) {
            final int maxSpeed = Integer.parseInt(etMaxSpeed.getText().toString());
            final int maxRpm = Integer.parseInt(etMaxRPM.getText().toString());

            editor.putInt(UiConfig.PREFS_KEY_MAXSPEED, maxSpeed);
            editor.putInt(UiConfig.PREFS_KEY_MAXRPM, maxRpm);
            editor.putInt(UiConfig.PREFS_KEY_MAXTEMP, maxTemp);
            editor.putInt(UiConfig.PREFS_KEY_TEMP_ALARM, alarmTemp);
            editor.commit();

            Dashboard.setAlarmingTemperature(alarmTemp);
            Dashboard.setMaxTemperature(maxTemp);
            Dashboard.setMaxSpeed(maxSpeed);
            Dashboard.setMaxRPM(maxRpm);

            finish();
        } else {
            Toast.makeText(this, getString(R.string.alarmTempTooHigh), Toast.LENGTH_LONG).show();
        }
    }
}