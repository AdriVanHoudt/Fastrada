package be.fastrada.packetmapper;

import be.fastrada.Dashboard;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: M
 * Date: 26/02/14
 * Time: 8:38
 * To change this template use File | Settings | File Templates.
 */
public class PacketConfiguration {
    private JSONObject configFile;
    private String classPath;
    private Dashboard classObject;

    public PacketConfiguration(InputStream packetMappingPath, String classPath, Dashboard classObject) {
        this.classPath = classPath;
        this.classObject = classObject;

        try {
            this.configFile = (JSONObject) new JSONParser().parse(new InputStreamReader(packetMappingPath));
        } catch (ParseException e) {
            throw new Error("parse error");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getConfigFile() {
        return configFile;
    }

    public String getClassPath() {
        return classPath;
    }

    public Object getClassObject() {
        return classObject;
    }
}
