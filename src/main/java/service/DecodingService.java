package service;

import model.Laser;
import model.Sensor;
import model.RFID;
import org.json.JSONObject;

/**
 * Cette classe est utilisée pour décoder les messages JSON arrivant du serveur
 *
 * @author Franck Battu
 * @version 1.0
 */
public class DecodingService {

    private JSONObject jsonObject;

    public DecodingService(String message) {
        this.jsonObject = new JSONObject(message);
    }

    /**
     * Donne le type du message utile pour le traitement
     *
     * @return le type du message
     */
    public String haveType() {
        return this.jsonObject.getString("type");
    }

    /**
     * Donne l'addresse mail de l'utilisateur
     *
     * @return l'email du l'utilisateur
     */
    public String haveAddress() {
        JSONObject user = this.jsonObject.getJSONObject("user");
        return user.getString("email");
    }

    /**
     * Creation d'un capteur à partir des informations reçues (type sensors)
     *
     * @return capteur
     */
    public Sensor getCapteur() {
        JSONObject data = this.jsonObject.getJSONObject("data");

        double temperature = data.getDouble("temperature");
        double humidity = data.getDouble("humidity");
        double smoke = data.getDouble("smoke");
        String macAddress = this.jsonObject.getString("macAddress");
        String name = this.jsonObject.getString("name");

        return new Sensor(temperature, humidity, smoke, macAddress, name);
    }

    /**
     * Création d'un laser à partir des informations reçues (type alert)
     * @return laser
     */
    public Laser getLaser() {
        int trigger = this.jsonObject.getInt("trigger");
        String macAddress = this.jsonObject.getString("macAddress");
        String name = this.jsonObject.getString("name");

        return new Laser(trigger, macAddress, name);
    }
    
    /**
     * Création d'un rfid à partir des informations reçues
     * @return rfid
     */
    public RFID getRFID() {
    	String key = this.jsonObject.getString("key");
    	return new RFID(key);
    }
    
    public String haveAlert() {
    	return this.jsonObject.getString("alert");
    }
    
    public String haveState() {
    	return this.jsonObject.getString("state");
    }
}
