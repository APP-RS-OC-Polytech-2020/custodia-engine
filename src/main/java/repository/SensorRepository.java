package repository;

import model.Sensor;
import db.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est utilisée pour interagir avec la base de données avec le modèle Sensor
 *
 * @author Franck Battu
 * @version 1.0
 */
public class SensorRepository implements Repository<Sensor> {

    private final String FIND_ALL = "SELECT * FROM sensor";
    private final String INSERT = "INSERT INTO sensor (`temperature`, `humidity`, `smoke`, `macAddress`, `name`) VALUES (?, ?, ?, ?, ?)";

    private Database instance;

    public SensorRepository() {
        try {
            this.instance = Database.getInstance();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les sensors de la base de données
     * <p>Une exception <code>SQLException</code> est gérée</p>
     *
     * @return la <code>List</code> des sensors
     */
    @Override
    public List<Sensor> findAll() {
        List<Sensor> result = new ArrayList<>();
        try {
            Statement st = this.instance.getConnection().createStatement();
            ResultSet rs = st.executeQuery(this.FIND_ALL);

            while (rs.next()) {
                Long id = rs.getLong("id");
                double temperature = rs.getDouble("temperature");
                double humidity = rs.getDouble("humidity");
                double smoke = rs.getDouble("smoke");
                Timestamp captureTime = rs.getTimestamp("captureTime");
                String macAddress = rs.getString("macAddress");
                String name = rs.getString("name");

                result.add(new Sensor(id, temperature, humidity, smoke, captureTime, macAddress, name));
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Sauvegarde un nouveau Sensor dans la base de données
     * <p>Une exception <code>SQLException</code> est gérée</p>
     *
     * @param sensor le capteur à sauvegarder
     */
    @Override
    public void save(Sensor sensor) {
        try {
            PreparedStatement preparedStatement = this.instance.getConnection().prepareStatement(this.INSERT);
            preparedStatement.setDouble(1, sensor.getTemperature());
            preparedStatement.setDouble(2, sensor.getHumidity());
            preparedStatement.setDouble(3, sensor.getSmoke());
            preparedStatement.setString(4, sensor.getMacAddress());
            preparedStatement.setString(5, sensor.getName());

            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Retourne la localisation du Sensor
     * 
     * @param sensor le capteur à localiser
     * @return le nom de la localisation
     */
    public String getLocalisation(Sensor sensor) {
    	String result = "";
    	try {
            Statement st = this.instance.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM qrcode WHERE sensor = '" + sensor.getName() + "'");

            while (rs.next()) {
                result = rs.getString("localisation");
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return result;
    }
}
