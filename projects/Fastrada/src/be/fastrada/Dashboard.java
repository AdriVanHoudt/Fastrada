package be.fastrada;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Class that represents the dashboard.
 */
public class Dashboard implements Serializable {
    private static int maxSpeed;
    private static int maxTemperature;
    private static int maxRPM;
    private static int alarmingTemperature;

    private TextView tvCurrentTemp, tvCurrentSpeed;
    private HoloCircularProgressBar tempMeter, speedMeter;
    private ProgressBar rpmIndicator;

    public Dashboard() {
    }

    public Dashboard(TextView tvCurrentTemp, TextView tvCurrentSpeed, HoloCircularProgressBar tempMeter, HoloCircularProgressBar speedMeter, ProgressBar rpmIndicator) {
        this.tvCurrentTemp = tvCurrentTemp;
        this.tvCurrentSpeed = tvCurrentSpeed;
        this.tempMeter = tempMeter;
        this.speedMeter = speedMeter;
        this.rpmIndicator = rpmIndicator;
    }

    public static void setMaxSpeed(int maxSpeed) {
        Dashboard.maxSpeed = maxSpeed;
    }

    public static void setMaxTemperature(int maxTemperature) {
        Dashboard.maxTemperature = maxTemperature;
    }

    public static void setMaxRPM(int maxRPM) {
        Dashboard.maxRPM = maxRPM;
    }

    public static void setAlarmingTemperature(int alarmingTemperature) {
        Dashboard.alarmingTemperature = alarmingTemperature;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getMaxRPM() {
        return maxRPM;
    }

    public int getAlarmingTemperature() {
        return alarmingTemperature;
    }

    public void setCurrentSpeed(int currentSpeed) {
        if (speedMeter != null) speedMeter.setProgress(((float) currentSpeed / (float) getMaxSpeed()));
        if (tvCurrentSpeed != null) tvCurrentSpeed.setText(String.format("%d", currentSpeed));
    }

    public void setCurrentRPM(int currentRPM) {
        rpmIndicator.setProgress(currentRPM);
    }

    public void setCurrentTemperature(int currentTemperature) {
        tempMeter.setProgress(((float) currentTemperature / (float) getMaxSpeed()));
        tvCurrentTemp.setText(String.format("%d", currentTemperature));
    }
}
