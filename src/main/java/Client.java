import model.Laser;
import model.RFID;
import model.Sensor;
import service.DecodingService;
import service.LaserService;
import service.RFIDService;
import service.SensorService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import db.Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Cette classe est le point d'entrée de l'application Engine
 *
 * <p>Engine est une application intégrée au projet Custodia. Elle permet d'effectuer tous les traitements des informations
 * échangées dans le projet.</p>
 * <p>Les traitements effectués sont divers : sauvegarde dans la base de données, envoie d'alertes si des données anormales
 * sont reçus</p>
 *
 * @author Franck Battu
 * @version 1.0
 */
@SuppressWarnings("InfiniteLoopStatement")
public class Client {

    private static final Log log = LogFactory.getLog(Client.class);

    private PrintWriter out;
    private BufferedReader in;
    private String message;

    private Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String[] args) throws IOException, SQLException {
    	Client client = new Client("193.48.125.70", 50008);
        client.start();
    }

    /**
     * Reception des messages entrants du serveur et application d'un traitement dessus
     * <p>La méthode tourne en continu</p>
     *
     * @throws IOException Une exception I/O peut être levée
     */
    private void start() throws IOException {
        out.println("{\"type\": \"init\", \"clientType\": \"SensorsDatabase\"}");
        this.message = in.readLine();
        while (true) {
            while ((this.message = in.readLine()) != null) {
            	System.out.println(this.message);
                this.treatment(this.message);
            }
        }
    }

    /**
     * Traitement suivant le type de message reçu
     *
     * @param messageFromServer Le message entrant du serveur
     *
     * @see SensorService#verify()
     * @see SensorService#save()
     * @see LaserService#verify()
     * @see LaserService#save()
     * @see RFIDService#verify()
     */
    private void treatment(String messageFromServer) {
        DecodingService decodingService = new DecodingService(messageFromServer);
        String type = decodingService.haveType();
        String address = decodingService.haveAddress();

        switch (type) {
            case "sensors":
                Sensor sensor = decodingService.getCapteur();
                SensorService sensorService = new SensorService(sensor, address, out);
                sensorService.verify();
                sensorService.save();
                break;
            case "laser":
                Laser laser = decodingService.getLaser();
                LaserService laserService = new LaserService(laser, address, out);
                laserService.verify();
                laserService.save();
                break;
            case "rfid-key":
            	RFID rfid = decodingService.getRFID();
            	RFIDService rfidService = new RFIDService(rfid, address ,out);
            	rfidService.verify();
            	break;
            case "rfid-system":
            	RFID rfid2 = decodingService.getRFID();
            	RFIDService rfidService2 = new RFIDService(rfid2, address ,out);
            	String alert = decodingService.haveAlert();
            	String state = decodingService.haveState();
            	rfidService2.check(alert, state); 
            default:
                // log.warn("Unknown type");
                break;
        }
    }
}
