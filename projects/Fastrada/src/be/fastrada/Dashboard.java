package be.fastrada;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
    private int currentTemperature;

    private TextView tvCurrentTemp, tvCurrentSpeed;
    private HoloCircularProgressBar tempMeter, speedMeter;
    private ProgressBar rpmIndicator;
    private Animation blinkAnimation;
    private boolean effectOn;

    public Dashboard() {

    }


    public Dashboard(TextView tvCurrentTemp, TextView tvCurrentSpeed, HoloCircularProgressBar tempMeter, HoloCircularProgressBar speedMeter, ProgressBar rpmIndicator) {
        this();
        this.blinkAnimation = new AlphaAnimation(0.0f, 1.0f);
        blinkAnimation.setDuration(800);
        blinkAnimation.setStartOffset(20);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        blinkAnimation.setRepeatCount(Animation.INFINITE);

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

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentSpeed(int currentSpeed) {
        if (speedMeter != null) {
            speedMeter.setProgress(((float) currentSpeed / (float) getMaxSpeed()));
        }
        if (tvCurrentSpeed != null && currentSpeed <= maxSpeed) {
            tvCurrentSpeed.setText(String.format("%d", currentSpeed));
        } else if (tvCurrentSpeed != null && currentSpeed > maxSpeed) {
            tvCurrentSpeed.setText(String.format("%d", maxSpeed));
        }
    }

    public void setCurrentRPM(int currentRPM) {
        if (rpmIndicator != null && currentRPM <= maxRPM) {
            rpmIndicator.setProgress(currentRPM);
        } else if (rpmIndicator != null && currentRPM > maxRPM) {
            rpmIndicator.setProgress(maxRPM);
        }
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;

        if (tempMeter != null) {
            tempMeter.setProgress(((float) currentTemperature / (float) getMaxSpeed()));
        }
        if (tvCurrentTemp != null && currentTemperature <= maxTemperature) {
            tvCurrentTemp.setText(String.format("%d", currentTemperature));
        } else if (tvCurrentTemp != null && currentTemperature > maxTemperature) {
            tvCurrentTemp.setText(String.format("%d", maxTemperature));
        }

        checkForExceedingTemp();
    }

    public void checkForExceedingTemp() {
        if (getCurrentTemperature() >= getAlarmingTemperature() && !effectOn) {
            effectOn = true;
            tempMeter.startAnimation(blinkAnimation);
        } else if (getCurrentTemperature() < getAlarmingTemperature()) {
            effectOn = false;
            tempMeter.clearAnimation();
        }
    }
}
