package be.fastrada.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import be.fastrada.R;

/**
 * Created with IntelliJ IDEA.
 * User: M
 * Date: 6/02/14
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class Configuration extends Activity {
    private SharedPreferences sharedPreferences;
    private EditText maxSpeed;
    private EditText maxTemperature;
    private EditText maxRPM;
    private EditText alarmingTemp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        sharedPreferences = getSharedPreferences("configuration", MODE_PRIVATE);
        maxSpeed = (EditText) findViewById(R.id.maxSpeed);
        maxTemperature = (EditText) findViewById(R.id.maxTemp);
        maxRPM = (EditText) findViewById(R.id.maxRPM);
        alarmingTemp = (EditText) findViewById(R.id.alarmTemp);

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
        maxSpeed.setText(sharedPreferences.getInt("maxSpeed", 300) + "");
        maxRPM.setText(sharedPreferences.getInt("maxRPM", 6000) + "");
        maxTemperature.setText(sharedPreferences.getInt("maxTemperature", 120) + "");
        alarmingTemp.setText(sharedPreferences.getInt("alarmingTemperature", 90) + "");
    }

    public void saveConfiguration() {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Context context = this.getBaseContext();

        if(Integer.parseInt(alarmingTemp.getText().toString()) <= Integer.parseInt(maxTemperature.getText().toString())){
            editor.putInt("maxSpeed", Integer.parseInt(maxSpeed.getText().toString()));
            editor.putInt("maxRPM", Integer.parseInt(maxRPM.getText().toString()));
            editor.putInt("maxTemperature", Integer.parseInt(maxTemperature.getText().toString()));
            editor.putInt("alarmingTemperature", Integer.parseInt(alarmingTemp.getText().toString()));
            editor.commit();

            startActivity(new Intent(context, Main.class));
            finish();
        } else {
            Toast.makeText(this, "Your alarming temperature must not be higher than the maximum temperature.", Toast.LENGTH_LONG).show();
        }


    }

}