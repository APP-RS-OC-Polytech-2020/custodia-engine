package service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

/**
 * Cette classe est utilisée pour la gestion des mails dans l'application
 * <p>Utilisation du protocole SMTP de Gmail</p>
 *
 * @author Franck Battu
 * @version 1.0
 */
public class MailService {

    private static final Log log = LogFactory.getLog(MailService.class);

    private static final String USERNAME = "apprsoc2020@gmail.com";
    private static final String PASSWORD = "6jaTPfb$FT|N";

    private Properties properties;

    public MailService() {
        this.properties = new Properties();
        this.properties.put("mail.smtp.host", "smtp.gmail.com");
        this.properties.put("mail.smtp.auth", "true");
        this.properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        this.properties.put("mail.smtp.port", "587");
        this.properties.put("mail.smtp.starttls.enable", "true");
    }

    /**
     * Envoie d'un mail
     * <p>L'heure est automatiquement ajoutée au sujet du message</p>
     * <p>Une exception <code>MessagingException</code> est gérée</p>
     *
     * @param to le destinataire du mail
     * @param subject le sujet du mail
     * @param content le contenu du mail
     */
    public void sendEmail(String to, String subject, String content) {

        Session session = Session.getInstance(this.properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String formatDateTime = now.format(formatter);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(MessageFormat.format("{0} - {1}", subject, formatDateTime));
            message.setSentDate(new Date());
            message.setContent(content, "text/html");
            Transport.send(message);
            log.info("The mail has been sent");
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
