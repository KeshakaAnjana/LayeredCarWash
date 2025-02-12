package com.ijse.gdse.carwash.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


public class NotificationController {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtSubject;

    @Setter
    private String customerEmail;

    @FXML
    public void sendUsingGmailOnAction(ActionEvent actionEvent) {
        if (customerEmail == null) {
            return;
        }
        final String FROM = "anjanakeshaka2@gmail.com";

        String subject = txtSubject.getText();
        String body = txtBody.getText();

        if (subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Subject and body must not be empty!").show();
            return;
        }
        sendEmailWithGmail(FROM, customerEmail, subject, body);

    }

    private void sendEmailWithGmail(String from, String to, String subject, String messageBody) {
        String PASSWORD = "dpnb edzc udzl dopi";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.host", "smtp.gmail.com");

        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject(subject);

            message.setText(messageBody);

            Transport.send(message);

            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully!").show();
        } catch (MessagingException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send email.").show();
        }
    }

    public void sendUsingWhatsappOnAction(ActionEvent actionEvent) {
    }
}
