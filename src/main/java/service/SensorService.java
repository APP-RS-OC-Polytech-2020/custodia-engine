package service;

import model.Sensor;
import repository.Repository;
import repository.SensorRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;

/**
 * Cette classe est utilisée pour gérer les différentes fonctionnalités liées aux capteurs.
 *
 * @author Franck Battu
 * @version 1.0
 * @see Sensor
 * @see SensorRepository
 */
public class SensorService {

    private static final Log log = LogFactory.getLog(SensorService.class);

    private Sensor sensor;
    private MailService mailService;
    private String address;
    private Repository<Sensor> sensorRepository;
    private PrintWriter out;

    public SensorService(Sensor sensor, String address, PrintWriter out) {
        this.sensor = sensor;
        this.mailService = new MailService();
        this.address = address;
        this.out = out;
        this.sensorRepository = new SensorRepository();
    }

    /**
     * Vérification des informations renvoyées par les capteurs et envoie d'alertes aux clients
     * <p>Une alerte est levée si la température n'est pas comprise si :</p>
     * <ul>
     * <li>la température n'est pas compris entre 10 et 45°C</li>
     * <li>de la fumée est détectée</li>
     * <li>l'humiditée est supérieure à 20%</li>
     * </ul>
     *
     * @see SensorService#inform(String, String)
     */
    public void verify() {
        String content = "";
    	String localisation = ((SensorRepository) this.sensorRepository).getLocalisation(this.sensor);

        if (this.sensor.getSmoke() > 600) {
            content += "<p><strong>Fumée détectée</strong></p>";
        }
        if (this.sensor.getTemperature() > 45) {
            content += "<p><strong>Température trop élevée</strong> : " + this.sensor.getTemperature() + " °C</p>";
        }
        if (this.sensor.getTemperature() < 10) {
            content += "<p><strong>Température trop basse</strong> : " + this.sensor.getTemperature() + " °C</p>";
        }
        if (this.sensor.getHumidity() > 100) {
            content += "<p><strong>Humidité trop élevée</strong> : " + this.sensor.getHumidity() + " %</p>";
        }

        if (content.length() != 0) {
            String subject = "Anomalie(s) détectée(s) chez vous sur le boitier : " + this.sensor.getMacAddress() + " à la place : " + localisation;
            this.inform(subject, content, localisation);
        }
    }

    /**
     * Sauvegarde des informations des capteurs dans la base de données
     *
     * @see SensorRepository#save(Sensor)
     */
    public void save() {
        this.sensorRepository.save(this.sensor);
        log.info("The data has been saved");
    }

    /**
     * Envoie les informations relatives aux anomalies détectés au serveur par JSON et à l'utilisateur par email.
     *
     * @param subject le sujet du message envoyé
     * @param content le contenu du message envoyé
     */
    private void inform(String subject, String content, String localisation) {
        this.mailService.sendEmail(this.address, subject, content);
        this.out.println("{\"type\": \"alert\", \"data\":{\"temperature\":" + this.sensor.getTemperature() + ", \"humidity\":" + this.sensor.getHumidity() + ", \"smoke\":" + this.sensor.getSmoke() + "}, \"macAddress\":\"" + this.sensor.getMacAddress() + "\", \"name\":\""+ this.sensor.getName() +"\", \"qrcode\":\"" + localisation + "\"}");
    }
}
