package be.fastrada;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: M
 * Date: 6/02/14
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class Dashboard implements Serializable{
    private double maxSpeed;
    private double maxTemperature;
    private int maxRPM;
    private int alarmingTemperature;

    private int currentSpeed;
    private int currentRPM;
    private int currentTemperature;

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }


    public void setMaxRPM(int maxRPM) {
        this.maxRPM = maxRPM;
    }

    public void setAlarmingTemperature(int alarmingTemperature) {
        this.alarmingTemperature = alarmingTemperature;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public int getMaxRPM() {
        return maxRPM;
    }

    public int getAlarmingTemperature() {
        return alarmingTemperature;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setCurrentRPM(int currentRPM) {
        this.currentRPM = currentRPM;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }
}
