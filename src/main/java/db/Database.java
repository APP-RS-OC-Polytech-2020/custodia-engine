package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe permet la connexion à la base de données MySQL. <br>
 * Une seule instance de la classe est créée.
 *
 * @author Franck Battu
 * @version 1.0
 */
public class Database {

    private static Database instance;
    private Connection connection;
    private String url = "jdbc:mysql://tp-epu.univ-savoie.fr:3308/rsoc_20";
    private String username = "rsoc_20";
    private String password = "yd72vctb";
    
    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accès à l'instance de la base de données
     *
     * @return l'instance
     * @throws SQLException si l'accès de la base de données produit une erreur
     */
    public static Database getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
