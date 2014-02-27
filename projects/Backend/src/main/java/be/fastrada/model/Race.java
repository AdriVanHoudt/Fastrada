package be.fastrada.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Race {

    @Id
    private String id;

    private String name;
    private DateTime dateTime;

    private Sensor[] sensors;

    public Race(String name, DateTime dateTime, Sensor[] sensors) {
        this.name = name;
        this.dateTime = dateTime;
        this.sensors = sensors;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Sensor[] getSensors() {
        return sensors;
    }

    public void setSensors(Sensor[] sensors) {
        this.sensors = sensors;
    }
}