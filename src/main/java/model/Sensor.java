package model;

import java.sql.Timestamp;

/**
 * Cette classe représente un capteur (Sensor) utilisée pour la DAO
 *
 * @author Franck Battu
 * @version 1.0
 */
public class Sensor {

    private Long id;
    private double temperature;
    private double humidity;
    private double smoke;
    private Timestamp captureTime;
    private String macAddress;
    private String name;

    
    public Sensor(Long id, double temperature, double humidity, double smoke, Timestamp captureTime, String macAddress,
			String name) {
		this.id = id;
		this.temperature = temperature;
		this.humidity = humidity;
		this.smoke = smoke;
		this.captureTime = captureTime;
		this.macAddress = macAddress;
		this.name = name;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getTemperature() {
		return temperature;
	}


	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}


	public double getHumidity() {
		return humidity;
	}


	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}


	public double getSmoke() {
		return smoke;
	}


	public void setSmoke(double smoke) {
		this.smoke = smoke;
	}


	public Timestamp getCaptureTime() {
		return captureTime;
	}


	public void setCaptureTime(Timestamp captureTime) {
		this.captureTime = captureTime;
	}


	public String getMacAddress() {
		return macAddress;
	}


	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Sensor(double temperature, double humidity, double smoke, String macAddress, String name) {
        this(null, temperature, humidity, smoke, null, macAddress, name);
    }
}
