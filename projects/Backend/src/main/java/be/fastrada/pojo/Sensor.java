package be.fastrada.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document
public class Sensor {

    @Id
    private String id;

    private String name;
    private String[] types;

    public Sensor(String name, String[] types) {
        this.name = name;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", types=" + Arrays.toString(types) +
                '}';
    }
}
