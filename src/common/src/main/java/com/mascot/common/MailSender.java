package com.mascot.common;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Николай on 20.11.2016.
 */
public class MailSender {
    private final Properties mailServerProperties;

    public MailSender(Properties mailServerProperties) {
        this.mailServerProperties = mailServerProperties;
    }

    public MailSender() {
// Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        this.mailServerProperties = new Properties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");
    }

    public void send(String message) {
        send("Error from furniture", message);
    }

    public void send(String subject, String emailBody) {
        try {
            // Step2
            System.out.println("\n\n 2nd ===> get Mail Session..");
            Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
            MimeMessage generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("nikolaypro@yandex.ru"));
//        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("test2@crunchify.com"));
            generateMailMessage.setSubject(subject);
            generateMailMessage.setContent(emailBody, "text/html");
            System.out.println("Mail Session has been created successfully..");

            // Step3
            System.out.println("\n\n 3rd ===> Get Session and Send mail");
            Transport transport = getMailSession.getTransport("smtp");

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect("smtp.gmail.com", "pro.nikolay", "prokopov200713");
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "F:\\work\\furniture\\src\\src\\tomcat-conf\\bin\\keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "qwerty");
        new MailSender().send("This message from test");
    }

}
