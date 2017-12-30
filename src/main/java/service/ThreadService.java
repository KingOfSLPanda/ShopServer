package main.java.service;

import main.java.configuration.RepositoryConfiguration;
import main.java.controller.MainController;
import main.java.model.User;
import main.java.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.MessagingException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by User on 15.11.2017.
 */

public class ThreadService extends Thread {

    private ConnectionService connectionService;
    private MessageTransmissionService messageTransmissionService;


    public ThreadService(Socket soket) throws IOException {
        this.connectionService = new ConnectionService();
        this.connectionService.connect(soket);
        this.messageTransmissionService  = new MessageTransmissionService(
                this.connectionService.getOutputStream(),
                this.connectionService.getInputStream()
        );

    }

    public void run() {
        try {
            System.out.println("Thread start!");
                MainController mainController = new MainController(this.messageTransmissionService);
                mainController.loginMenu();

        }   catch (SocketException e){

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public void disconnect() {
        try {
            connectionService.disconnect();
            System.out.println("Закончил работу: "+connectionService.getSocket().getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }
}
