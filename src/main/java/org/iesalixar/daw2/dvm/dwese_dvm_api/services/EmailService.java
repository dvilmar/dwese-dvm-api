package org.iesalixar.daw2.dvm.dwese_dvm_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage; // Asegúrate de usar esta importación

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) throws MailException, MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new MailException("Error al enviar el correo", e);
        }
    }

    public void sendTicketDetails(String email, String ticketDetails) throws MailException, MessagingException {
        String subject = "Detalles de tu Ticket";
        String body = "<h1>Detalles de tu Ticket</h1><p>" + ticketDetails + "</p>";
        sendEmail(email, subject, body);
    }
}
