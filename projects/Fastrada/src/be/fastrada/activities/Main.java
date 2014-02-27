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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import be.fastrada.Dashboard;
import be.fastrada.HoloCircularProgressBar;
import be.fastrada.R;
import be.fastrada.networking.PacketListener;
import be.fastrada.networking.PacketListenerService;
import be.fastrada.packetmapper.Packet;
import be.fastrada.packetmapper.PacketConfiguration;

import java.io.InputStream;

/**
 * Activity with all the visualized data.
 */
public class Main extends Activity {
    private Dashboard dashboard;
    private ProgressBar rpmIndicator;
    private HoloCircularProgressBar speedMeter, tempMeter;
    private TextView tvCurrentTemp, tvCurrentSpeed;
    private PacketConfiguration packetConfiguration;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private Context context;
    public static Handler mHandler;

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
        initDashboard();
        initHandler();

        /*
         * Initialise packetConfiguration for each packet to be received
         * Make sure that dashboard is initialised ( see initialise() )
         */
        InputStream res = context.getResources().openRawResource(R.raw.structure);
        packetConfiguration = new PacketConfiguration(res, "be.fastrada.packetmapper.PacketInterface", dashboard);  // Maar gij roept nu packetConfiguration aan e? ja



        final ImageView settings = (ImageView) findViewById(R.id.settings);
        final Context context = this.getBaseContext();
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, Configuration.class);
                startActivity(intent);
            }
        });

        startService(new Intent(this, PacketListenerService.class));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                final Bundle bundle = msg.getData();
                final byte[] bytes = bundle.getByteArray(PacketListener.BUNDLE_BYTES_KEY);

                Packet packet = new Packet(bytes, packetConfiguration);
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

    public void initDashboard() {
        speedMeter.setProgress(0.0f);
        tempMeter.setProgress(0.0f);
        rpmIndicator.setProgress(0);

        tvCurrentSpeed.setText(String.format("%d", 0));
        tvCurrentTemp.setText(String.format("%d", 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHandler();
    }
}
