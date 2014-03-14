package be.fastrada.packetmapper;

import be.fastrada.Exception.FastradaException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PacketConfiguration {
    private JSONObject configFile;
    private String classPath;
    private PacketInterface classObject;

    public PacketConfiguration(InputStream packetMappingPath, String classPath, PacketInterface classObject) throws FastradaException {
        this.classPath = classPath;
        this.classObject = classObject;

        // Validate
        if (this.classObject == null) {
            final String message = "ClassObject can not be null!";
            throw new FastradaException(message);
        }

        try {

            this.configFile = (JSONObject) new JSONParser().parse(new InputStreamReader(packetMappingPath));
            /*this.configFile = (JSONObject) new JSONParser().parse("{\n" +
                    "    \"packets\": {\n" +
                    "        \"256\": {\n" +
                    "            \"struct\": [\n" +
                    "                {\n" +
                    "                    \"name\": \"setCurrentRPM\",\n" +
                    "                    \"size\": 16,\n" +
                    "                    \"factor\": \"1\",\n" +
                    "                    \"offset\": \"0\",\n" +
                    "                    \"type\": \"rpm\"\n" +
                    "\n" +
                    "                },\n" +
                    "                {\n"  +
                    "                    \"name\": \"setThrottlePos\",\n" +
                    "                    \"size\": 16,\n" +
                    "                    \"factor\": \"0.0015259\",\n" +
                    "                    \"offset\": \"0\",\n" +
                    "                    \"type\": \"throttle\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\": \"setCurrentTemperature\",\n" +
                    "                    \"size\": 16,\n" +
                    "                    \"factor\": \"0.0030518\",\n" +
                    "                    \"offset\": \"50\",\n" +
                    "                    \"type\": \"temperature\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"257\": {\n" +
                    "            \"struct\": [\n" +
                    "                {\n" +
                    "                    \"name\": \"setCurrentSpeed\",\n" +
                    "                    \"size\": 16,\n" +
                    "                    \"factor\": \"0.00549324\",\n" +
                    "                    \"offset\": \"0\",\n" +
                    "                    \"type\": \"speed\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"name\": \"setGear\",\n" +
                    "                    \"size\": 8,\n" +
                    "                    \"factor\": \"1\",\n" +
                    "                    \"offset\": \"0\",\n" +
                    "                    \"type\": \"gear\"\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    }\n" +
                    "}"); */
        } catch (ParseException e) {
            final String message = "Error parsing json";


            throw new FastradaException(message);
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

    public PacketInterface getClassObject() {
        return classObject;
    }
}
