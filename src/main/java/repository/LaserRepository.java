package repository;

import model.Laser;
import db.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est utilisée pour interagir avec la base de données avec le modèle Laser
 *
 * @author Franck Battu
 * @version 1.0
 */
public class LaserRepository implements Repository<Laser> {

    private final String FIND_ALL = "SELECT * FROM laser";
    private final String INSERT = "INSERT INTO laser(`trigger`, `macAddress`, `name`) VALUES (?, ?, ?)";

    private Database instance;

    public LaserRepository() {
        try {
            this.instance = Database.getInstance();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les lasers de la base de données
     * <p>Une exception <code>SQLException</code> est gérée</p>
     *
     * @return la <code>List</code> des lasers
     */
    @Override
    public List<Laser> findAll() {
        List<Laser> result = new ArrayList<>();
        try {
            Statement st = this.instance.getConnection().createStatement();
            ResultSet rs = st.executeQuery(this.FIND_ALL);

            while (rs.next()) {
                Long id = rs.getLong("id");
                int trigger = rs.getInt("trigger");
                Timestamp captureTime = rs.getTimestamp("captureTime");
                String macAddress = rs.getString("macAddress");
                String name = rs.getString("name");

                result.add(new Laser(id, trigger, captureTime, macAddress, name));
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Sauvegarde un nouveau Laser dans la base de données
     * <p>Une exception <code>SQLException</code> est gérée</p>
     *
     * @param laser le laser à sauvegarder
     */
    @Override
    public void save(Laser laser) {
        try {
            PreparedStatement preparedStatement = this.instance.getConnection().prepareStatement(this.INSERT);
            preparedStatement.setInt(1, laser.getTrigger());
            preparedStatement.setString(2, laser.getMacAddress());
            preparedStatement.setString(3, laser.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
