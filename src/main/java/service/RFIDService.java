package service;

import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import model.RFID;
import repository.RFIDRepository;
import repository.Repository;

public class RFIDService {

	private static final Log log = LogFactory.getLog(LaserService.class);

    private RFID rfid;
    private Repository<RFID> rfidRepository;
    private MailService mailService;
    private PrintWriter out;
    private String address;

    public RFIDService(RFID rfid, String address, PrintWriter out) {
        this.rfid = rfid;
        this.rfidRepository = new RFIDRepository();
        this.mailService = new MailService();
        this.address = address;
        this.out = out;
    }
    
    /**
     * Vérification que la clé RFID soit valide
     */
    public void verify() {
    	if (((RFIDRepository) this.rfidRepository).check(rfid)) {
    		this.out.println("{\"type\": \"rfid-validation\", \"keyValid\": \"1\"}");
    	}
    	else {
    		this.out.println("{\"type\": \"rfid-validation\", \"keyValid\": \"0\"}");
    	}
    }
    
    /**
     * Envoie les informations relatives à l'instrusion détectée au serveur par JSON et à l'utilisateur par email.
     *
     * @param subject le sujet du message envoyé
     * @param content le contenu du message envoyé
     */
    private void inform(String subject, String content) {
        this.mailService.sendEmail(this.address, subject, content);
        this.out.println("{\"type\": \"alert-rfid\"}");
    }
    
    public void check(String alert, String state) {
    	if (alert.equals("1")) {
    		String subject = "Anomalies détectées chez vous";
    		String content = "Quelqu'un a essayé de rentrer chez vous";
    		this.inform(subject, content);
    	}
    }
}
