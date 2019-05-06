package repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import db.Database;
import model.RFID;

public class RFIDRepository implements Repository<RFID> {

	private Database instance;

    public RFIDRepository() {
        try {
            this.instance = Database.getInstance();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	@Override
	public List<RFID> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(RFID t) {
		// TODO Auto-generated method stub
	}
	
	public boolean check(RFID rfid) {
		List<RFID> result = new ArrayList<>();
		String value = rfid.getKey();
        try {
            Statement st = this.instance.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM rfid");

            while (rs.next()) {
            	Long id = rs.getLong("id");
                String key = rs.getString("key");
                if (key.equals(value)) {
                	return true;
                }
                result.add(new RFID(id, key));
            }
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return false;
	}

}
