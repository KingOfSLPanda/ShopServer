package main.java.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by User on 16.11.2017.
 */
public class ConnectionService {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public void connect() throws IOException {
        this.socket = new Socket(InetAddress.getLocalHost(), 8080);
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream()); //создание потока вывода
        this.inputStream = new ObjectInputStream(this.socket.getInputStream());   //создание потока ввода
    }

    public void connect(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream()); //создание потока вывода
        this.inputStream = new ObjectInputStream(this.socket.getInputStream());   //создание потока ввода
    }

    public void disconnect() throws IOException {
        if (this.outputStream != null) {
            this.outputStream.close();
        }
        if (this.inputStream != null) {
            this.inputStream.close();
        }
    }


    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(ObjectOutputStream oStream) {
        this.outputStream = oStream;
    }

    public ObjectInputStream getInputStream() {
        return this.inputStream;
    }

    public void setInputStream(ObjectInputStream iStream) {
        this.inputStream = iStream;
    }
}
