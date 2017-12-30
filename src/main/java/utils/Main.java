package main.java.utils;

import main.java.configuration.RepositoryConfiguration;
import main.java.service.MailService;
import main.java.service.ThreadService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Created by User on 15.11.2017.
 */
public class Main {

    public static void main(String[] args) throws SQLException, MessagingException {
        try {
            RepositoryConfiguration repositoryConfiguration = RepositoryConfiguration.getInstance();
            repositoryConfiguration.connection();
            repositoryConfiguration.createDBUserTable();
            repositoryConfiguration.createDBCategoriesTable();
            repositoryConfiguration.createDBProductTable();
            repositoryConfiguration.createDBOrdersTable();

            ServerSocket serverSoket = new ServerSocket(8080);

            System.out.println("Сервер готов к работе");

            while (true) {
                Socket socket = serverSoket.accept();
                System.out.println( "Подключился: "+socket.getInetAddress().getHostName());

                ThreadService thread = new ThreadService(socket);
                thread.start();

            }
        } catch (IOException e) {
            System.err.println(e);
            //System.out.println("Подключение");
        }
    }
}
