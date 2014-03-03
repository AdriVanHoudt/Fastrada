package be.fastrada.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import be.fastrada.R;

/**
 * Activity for configuration.
 */
public class UiConfig extends Activity {
    public static final String PREFS_KEY = "be.fastrada.preferences";
    public static final String PREFS_KEY_MAXSPEED = "maxSpeed";
    public static final String PREFS_KEY_MAXTEMP = "maxTemp";
    public static final String PREFS_KEY_MAXRPM = "maxRpm";
    public static final String PREFS_KEY_TEMP_ALARM = "alarmTemp";
    public static final String PREFS_KEY_SHOWGEAR = "showGear";
    public static final String PREFS_KEY_STYLE = "style";

    private SharedPreferences sharedPreferences;
    private Switch gearChecked;
    private Switch holoStyle;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uiconfig);

        sharedPreferences = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        gearChecked = (Switch) findViewById(R.id.chkGear);
        holoStyle = (Switch) findViewById(R.id.chkStyle);

        initialise();

        final Button btnSave = (Button) findViewById(R.id.saveConfig);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfiguration();
            }
        });
    }

    public void initialise() {
        gearChecked.setChecked(sharedPreferences.getBoolean(UiConfig.PREFS_KEY_SHOWGEAR, true));
        holoStyle.setChecked(sharedPreferences.getBoolean(UiConfig.PREFS_KEY_STYLE, true));
    }

    public void saveConfiguration() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(UiConfig.PREFS_KEY_SHOWGEAR, gearChecked.isChecked());
            editor.putBoolean(UiConfig.PREFS_KEY_STYLE, holoStyle.isChecked());
            editor.commit();
            finish();
    }

    public void gearClicked(View v) {
        if (((Switch) v).isChecked()) {
            gearChecked.setChecked(true);
        } else {
            gearChecked.setChecked(false);
        }
    }

    public void StyleClicked(View v){
        if(((Switch) v).isChecked()){
           holoStyle.setChecked(true);
        } else {
            holoStyle.setChecked(false);
        }
    }

}