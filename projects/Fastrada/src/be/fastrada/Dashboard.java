package be.fastrada;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;

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
        if (tvCurrentSpeed != null && currentSpeed <= maxSpeed) tvCurrentSpeed.setText(String.format("%d", currentSpeed));
        else if(tvCurrentSpeed != null && currentSpeed > maxSpeed) tvCurrentSpeed.setText(String.format("%d", maxSpeed));
    }

    public void setCurrentRPM(int currentRPM) {
        if(rpmIndicator != null && currentRPM <= maxRPM) rpmIndicator.setProgress(currentRPM);
        else if (rpmIndicator != null && currentRPM > maxRPM) rpmIndicator.setProgress(maxRPM);
    }

    public void setCurrentTemperature(int currentTemperature) {
        if(tempMeter != null) tempMeter.setProgress(((float) currentTemperature / (float) getMaxSpeed()));
        if(tvCurrentTemp != null && currentTemperature <= maxTemperature) tvCurrentTemp.setText(String.format("%d", currentTemperature));
        else if(tvCurrentTemp != null && currentTemperature > maxTemperature)  tvCurrentTemp.setText(String.format("%d", maxTemperature));
    }
}
