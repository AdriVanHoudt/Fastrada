package be.fastrada.packetmapper;

/**
 * Created with IntelliJ IDEA.
 * User: M
 * Date: 26/02/14
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
public interface PacketInterface {
    public void setCurrentSpeed(int currentSpeed);
    public void setCurrentRPM(int currentRPM);
    public void setCurrentTemperature(int currentTemperature);
    public void setGear(int currentGear);
}
