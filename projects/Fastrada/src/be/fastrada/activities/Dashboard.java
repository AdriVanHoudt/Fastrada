package be.fastrada.activities;

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
    private int currentRPM;

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }


    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMaxRPM(int maxRPM) {
        this.maxRPM = maxRPM;
    }

    public void setCurrentRPM(int currentRPM) {
        this.currentRPM = currentRPM;
    }
}
