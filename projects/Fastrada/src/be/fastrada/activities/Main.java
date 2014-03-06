package be.fastrada.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import be.fastrada.Dashboard;
import be.fastrada.Exception.FastradaException;
import be.fastrada.HoloCircularProgressBar;
import be.fastrada.R;
import be.fastrada.Speedometer;
import be.fastrada.networking.PacketListener;
import be.fastrada.networking.PacketListenerService;
import be.fastrada.packetmapper.PacketConfiguration;
import be.fastrada.packetmapper.PacketMapper;

import java.io.InputStream;

/**
 * Activity with all the visualized data.
 */
public class Main extends Activity {
    private Dashboard dashboard;
    private ProgressBar rpmIndicator;
    private HoloCircularProgressBar holoSpeedMeter, holoTempMeter;
    private Speedometer tempoMeter, speedoMeter;
    private TextView tvCurrentTemp, tvCurrentSpeed, tvGear;
    private PacketConfiguration packetConfiguration;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private Intent senderServiceIntent;

    private PacketMapper packetMapper;

    private SharedPreferences sharedPreferences;
    private boolean holoStyle;


    private Context context;
    public static Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = this.getApplicationContext();
        sharedPreferences = getSharedPreferences(UiConfig.PREFS_KEY, MODE_PRIVATE);
        rpmIndicator = (ProgressBar) findViewById(R.id.rpmIndicator);
        holoSpeedMeter = (HoloCircularProgressBar) findViewById(R.id.speedIndicator);
        holoTempMeter = (HoloCircularProgressBar) findViewById(R.id.thermometer);
        tvCurrentTemp = (TextView) findViewById(R.id.tvTemperature);
        tvCurrentSpeed = (TextView) findViewById(R.id.tvSpeed);
        tvGear = (TextView) findViewById(R.id.tvGear);
        tempoMeter = (Speedometer) findViewById(R.id.tempometer);
        speedoMeter =  (Speedometer) findViewById(R.id.speedometer);

        initialise();
        initDashboard();
        initHandler();
        /*
         * Initialise packetConfiguration for each packet to be received
         * Make sure that dashboard is initialised ( see initialise() )
         */
        InputStream res = context.getResources().openRawResource(R.raw.structure);
        try {
            packetConfiguration = new PacketConfiguration(res, "be.fastrada.packetmapper.PacketInterface", dashboard);  // Maar gij roept nu packetConfiguration aan e? ja
        } catch (FastradaException e) {
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(e.getMessage());
            toast.show();
        }
        // Init packetMapper after packetConfiguration
        packetMapper = new PacketMapper(packetConfiguration);

        final ImageView settings = (ImageView) findViewById(R.id.settings);
        final Context context = this.getBaseContext();
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, Settings.class);
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

                packetMapper.setContent(bytes);
                packetMapper.process();
            }
        };
    }

    public void initialise() {
        dashboard = new Dashboard(tvCurrentTemp, tvCurrentSpeed, holoTempMeter, holoSpeedMeter, rpmIndicator, speedoMeter, tempoMeter, tvGear);
        Dashboard.setMaxSpeed(sharedPreferences.getInt(UiConfig.PREFS_KEY_MAXSPEED, 300));
        Dashboard.setMaxRPM(sharedPreferences.getInt(UiConfig.PREFS_KEY_MAXRPM, 6000));
        Dashboard.setMaxTemperature(sharedPreferences.getInt(UiConfig.PREFS_KEY_MAXTEMP, 120));
        Dashboard.setAlarmingTemperature(sharedPreferences.getInt(UiConfig.PREFS_KEY_TEMP_ALARM, 90));

        rpmIndicator.setMax(dashboard.getMaxRPM());
        //senderServiceIntent = new Intent(this, PacketSenderService.class);
    }

    public void initDashboard() {
        holoSpeedMeter.setProgress(0.0f);
        holoTempMeter.setProgress(0.0f);
        rpmIndicator.setProgress(0);
        tempoMeter.setCurrentSpeed(0.0f);
        speedoMeter.setCurrentSpeed(0.0f);

        tvCurrentSpeed.setText(String.format("%d", 0));
        tvCurrentTemp.setText(String.format("%d", 0));
        tvGear.setText(String.format("%d", 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHandler();
        customVisibility();
    }

    public void sendData(){
        startService(senderServiceIntent);
    }

    public void onToggleClick(final View v){
        boolean on = ((ToggleButton) v).isChecked();
        final EditText input = new EditText(this);

        if(on){
            new AlertDialog.Builder(Main.this)
                    .setTitle(getString(R.string.dialogTitle))
                    .setMessage(getString(R.string.dialogMessage))
                    .setView(input)
                    .setPositiveButton(getString(R.string.dialogPosButton), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Editable value = input.getText();
                            sendData();
                            Toast.makeText(context, input.getText(), Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton(getString(R.string.dialogNegButton), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    ((ToggleButton) v).setChecked(false);
                }
            }).show();
        }
        else{
            stopSendingData();
        }
    }

    private void stopSendingData() {
        stopService(senderServiceIntent);
    }

    private void customVisibility() {
        RelativeLayout rlGear = (RelativeLayout) findViewById(R.id.rlGear);
        RelativeLayout holoSpeed = (RelativeLayout) findViewById(R.id.speedMeterHolo);
        RelativeLayout barsSpeed = (RelativeLayout) findViewById(R.id.speedMeterBars);
        RelativeLayout holoTemp = (RelativeLayout) findViewById(R.id.tempMeterHolo);
        RelativeLayout barsTemp = (RelativeLayout) findViewById(R.id.tempMeterBars);


        boolean showGear = sharedPreferences.getBoolean(UiConfig.PREFS_KEY_SHOWGEAR, false);
        holoStyle = sharedPreferences.getBoolean(UiConfig.PREFS_KEY_STYLE, true);


        if (showGear) {
            rlGear.setVisibility(View.VISIBLE);
        } else {
            rlGear.setVisibility(View.INVISIBLE);
        }

        if (holoStyle) {
            holoSpeed.setVisibility(View.VISIBLE);
            holoTemp.setVisibility(View.VISIBLE);
            barsSpeed.setVisibility(View.INVISIBLE);
            barsTemp.setVisibility(View.INVISIBLE);
            rpmIndicator.setProgressDrawable(getResources().getDrawable(R.drawable.rpmindicator));
        } else {
            holoSpeed.setVisibility(View.INVISIBLE);
            holoTemp.setVisibility(View.INVISIBLE);
            barsSpeed.setVisibility(View.VISIBLE);
            barsTemp.setVisibility(View.VISIBLE);
            rpmIndicator.setProgressDrawable(getResources().getDrawable(R.drawable.rpmindicator2));
        }

    }
}
