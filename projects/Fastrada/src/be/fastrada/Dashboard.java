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
    private double currentSpeed;
    private double maxTemperature;
    private double currentTemperature;
    private int maxRPM;
    private int currentRPM;

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }


    public void setMaxRPM(int maxRPM) {
        this.maxRPM = maxRPM;
    }

    public void setCurrentRPM(int currentRPM) {
        this.currentRPM = currentRPM;
    }
}
