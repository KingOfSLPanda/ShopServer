package main.java.service;

import main.java.model.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by User on 05.12.2017.
 */
public class MailService {

    public static void send(String adress, String info) throws IOException, MessagingException {
        final Properties properties = new Properties();
        properties.load(MailService.class.getClassLoader().getResourceAsStream("main/java/mail.properties"));

        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("mail"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(adress));
        message.setSubject("shop");
        message.setText(info);

        Transport tr = mailSession.getTransport();
        tr.connect(null, "pass");
        tr.sendMessage(message, message.getAllRecipients());
        tr.close();
    }

    public static boolean sendShares() throws SQLException {
        List<User> users = (new UserService()).getUsersFromRepository();
        for (User user : users){
            String message = "Уважаемый, " + user.getFirstName() + " " + user.getLastName() +"." +
                    " Вас приветствует интернет-магазин BPShop! Ранее вы интересовались мобильными телефонами и " +
                    "поэтому мы спешим сообщить вам о 10% скидке на все телефоны SAMSUNG.";
            try {
                send(user.getEmail(), message);
            } catch (IOException e) {
                return false;
            } catch (MessagingException e) {
                return false;
            }

        }

        return true;
    }
}
