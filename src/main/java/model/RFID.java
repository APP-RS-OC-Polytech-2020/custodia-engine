package model;

import java.sql.Timestamp;

/**
 * Cette classe représente un RFID utilisé pour la DAO
 */
public class RFID {
	
	private Long id;
	private String key;
	
	public RFID(Long id, String key) {
		this.id = id;
		this.key = key;
	}
	
	public RFID(String key) {
		this(null, key);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
