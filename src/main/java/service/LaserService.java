package service;

import repository.Repository;
import model.Laser;
import repository.LaserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;

public class LaserService {

    private static final Log log = LogFactory.getLog(LaserService.class);

    private Laser laser;
    private Repository<Laser> laserRepository;
    private MailService mailService;
    private PrintWriter out;
    private String address;

    public LaserService(Laser laser, String address, PrintWriter out) {
        this.laser = laser;
        this.laserRepository = new LaserRepository();
        this.mailService = new MailService();
        this.address = address;
        this.out = out;
    }

    public void verify() {
        String content = "";

        if (this.laser.getTrigger() > 0) {
            content += "<p><strong>Le laser a détecté une intrusion</strong></p>";
        }

        if (content.length() != 0) {
            String subject = "Anomalie(s) détectée(s) chez vous sur le boitier : " + this.laser.getMacAddress();
            this.inform(subject, content);
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
        this.out.println("{\"type\": \"alert\", \"name\":"+ this.laser.getName() +", \"macAddress\":"+ this.laser.getMacAddress() +"}");
    }

    /**
     * Sauvegarde des informations des capteurs dans la base de données
     *
     * @see LaserRepository#save(Laser)
     */
    public void save() {
        this.laserRepository.save(this.laser);
        log.info("The laser has been saved");
    }
}
