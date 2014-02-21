package be.fastrada.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import be.fastrada.Dashboard;
import be.fastrada.HoloCircularProgressBar;
import be.fastrada.R;
import be.fastrada.networking.Server;
import be.fastrada.packetmapper.Packet;

import org.apache.commons.codec.binary.Hex;

import java.io.InputStream;
import java.net.SocketException;

public class Main extends Activity {
    private Dashboard dashboard;
    private ProgressBar rpmIndicator;
    private HoloCircularProgressBar speedMeter, tempMeter;
    private TextView tvCurrentTemp, tvCurrentSpeed;

    private Context context;
    public static Handler mHandler;
    private Server server;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = this.getApplicationContext();
        rpmIndicator = (ProgressBar) findViewById(R.id.rpmIndicator);
        speedMeter = (HoloCircularProgressBar) findViewById(R.id.speedIndicator);
        tempMeter = (HoloCircularProgressBar) findViewById(R.id.thermometer);
        tvCurrentTemp = (TextView) findViewById(R.id.tvTemperature);
        tvCurrentSpeed = (TextView) findViewById(R.id.tvSpeed);

        initialise();
        updateDashboard();
        initHandler();

        final ImageView settings = (ImageView) findViewById(R.id.settings);
        final Context context = this.getBaseContext();
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, Configuration.class);
                startActivity(intent);
            }
        });

        try {
            server = new Server();
        } catch (SocketException e) {
            Toast.makeText(this, getString(R.string.serverStartError), Toast.LENGTH_LONG).show();
        }
    }

    private void initHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                final Bundle bundle = msg.getData();
                final byte[] bytes = bundle.getByteArray(Server.BUNDLE_BYTES_KEY);

                String content = Hex.encodeHexString(bytes); //hex string uit byte array van 10 groot

                InputStream res = context.getResources().openRawResource(R.raw.structure);
                Packet packet = new Packet("00 00 13 88", res, dashboard);
                packet.process();
                packet.setContent("00 01 00 78");
                packet.process();
                packet.setContent("00 02 00 64");
                packet.process();
            }
        };
    }


    public void initialise() {
        final SharedPreferences sharedPreferences = getSharedPreferences(Configuration.PREFS_KEY, MODE_PRIVATE);

        dashboard = new Dashboard(tvCurrentTemp, tvCurrentSpeed, tempMeter, speedMeter, rpmIndicator);
        Dashboard.setMaxSpeed(sharedPreferences.getInt(Configuration.PREFS_KEY_MAXSPEED, 300));
        Dashboard.setMaxRPM(sharedPreferences.getInt(Configuration.PREFS_KEY_MAXRPM, 6000));
        Dashboard.setMaxTemperature(sharedPreferences.getInt(Configuration.PREFS_KEY_MAXTEMP, 120));
        Dashboard.setAlarmingTemperature(sharedPreferences.getInt(Configuration.PREFS_KEY_TEMP_ALARM, 90));

        rpmIndicator.setMax(dashboard.getMaxRPM());

    }

    public void updateDashboard() {
        final int currentSpeed = 60;
        final int currentTemp = 95;
        final int currentRpm = 2000;

        speedMeter.setProgress((float) (currentSpeed / dashboard.getMaxSpeed()));
        tempMeter.setProgress((float) (currentTemp / dashboard.getMaxTemperature()));
        rpmIndicator.setProgress(currentRpm);

        tvCurrentSpeed.setText(String.format("%d", currentSpeed));
        tvCurrentTemp.setText(String.format("%d", currentTemp));

        if (currentTemp >= dashboard.getAlarmingTemperature()) {
            final Animation anim = new AlphaAnimation(0.0f, 1.0f);

            anim.setDuration(800);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            tempMeter.startAnimation(anim);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHandler();
    }
}
