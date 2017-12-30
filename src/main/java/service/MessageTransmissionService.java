package main.java.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by User on 16.11.2017.
 */
public class MessageTransmissionService {

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public MessageTransmissionService(ObjectOutputStream outputStream, ObjectInputStream inputStream){
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public void sendMessage(String key, String message) throws JSONException, IOException {
        this.outputStream.writeObject((new JSONObject()).put(key, message).toString());
    }

    public void sendMessage(JSONObject object) throws JSONException, IOException {
        this.outputStream.writeObject(object.toString());
    }

    public String getMessage(String key) throws JSONException, IOException, ClassNotFoundException {
        return (String)(new JSONObject((String) this.inputStream.readObject())).get(key);
    }

    public JSONObject getMessage() throws JSONException, IOException, ClassNotFoundException {
        return new JSONObject((String) this.inputStream.readObject());
    }
}
