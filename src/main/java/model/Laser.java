package model;

import java.sql.Timestamp;

/**
 * Cette classe représente un laser utilisé pour la DAO
 */
public class Laser {

    private Long id;
    private int trigger;
    private Timestamp captureTime;
    private String macAddress;
    private String name;

    
    public Laser(Long id, int trigger, Timestamp captureTime, String macAddress, String name) {
		super();
		this.id = id;
		this.trigger = trigger;
		this.captureTime = captureTime;
		this.macAddress = macAddress;
		this.name = name;
	}

	public Laser(int trigger, String macAddress, String name) {
        this(null, trigger, null, macAddress, name);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTrigger() {
		return trigger;
	}

	public void setTrigger(int trigger) {
		this.trigger = trigger;
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
	
	
}
