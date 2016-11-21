package com.mascot.common;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.core.NestedExceptionUtils;

import java.util.Properties;
import java.util.concurrent.Executors;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Николай on 20.11.2016.
 */
public class MailSender {
    private static Logger logger = Logger.getLogger("email");

    public static void sendErrorAsync(Throwable throwable) {
        Executors.newSingleThreadExecutor().execute(() -> send("Error from Furniture", ExceptionUtils.getStackTrace(throwable)));
    }

    public static void sendErrorAsync(String message, Throwable throwable) {
        Executors.newSingleThreadExecutor().execute(() -> send("Error from Furniture",
                message + "\n" + ExceptionUtils.getStackTrace(throwable))
        );
    }

    public void sendAsync(String emailBody) {
        Executors.newSingleThreadExecutor().execute(() -> send("Furniture", emailBody));
    }

    public static void send(String emailBody) {
        send("Error from furniture", emailBody);
    }

    public static void send(String subject, String emailBody) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.yandex.ru");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("nikolaypro", "070902prokopov");
                        }
                    }
            );

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nikolaypro@ya.ru", "From furniture"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("nikolaypro@ya.ru"));
            message.setSubject(subject);
            message.setText(emailBody);

            Transport.send(message);
            logger.info("Sent email success");
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static void main(String[] args) {
//        System.setProperty("javax.net.ssl.trustStore", "D:\\projects\\JavaTest\\template\\src\\src\\tomcat-conf\\bin\\keystore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "qwerty");
        new MailSender().sendAsync("This message from test");
    }

}
