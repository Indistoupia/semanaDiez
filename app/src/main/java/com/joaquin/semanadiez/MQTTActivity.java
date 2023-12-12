package com.joaquin.semanadiez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MQTTActivity extends AppCompatActivity {





    private MqttClient client;


    private MqttCallback callback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqttactivity);




    }

    public interface MqttCallback {
        void onSubscribeSuccess();
        void onSubscribeFailure(String errorMessage);
    }


    public void setCallback(MqttCallback callback) {
        this.callback = callback;
    }





    public void connect(String brokerUrl, String clientId) {
        try {
            // Set up the persistence layer
            MemoryPersistence persistence = new MemoryPersistence();

            // Initialize the MQTT client
            client = new MqttClient(brokerUrl, clientId, persistence);

            // Set up the connection options
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            // Connect to the broker
            client.connect(connectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {

        boolean success = true;

        if (success && callback != null) {
            callback.onSubscribeSuccess();
        } else if (!success && callback != null) {
            callback.onSubscribeFailure("Error al suscribirse");
        }

        /*try {



            if (client != null || client.isConnected()) {
                client.subscribe(topic, 0);
            } else {
                Toast.makeText(MainActivity.this, "No se ha podido suscribir con MQTT", Toast.LENGTH_SHORT);
            }


        } catch (MqttException e) {
            e.printStackTrace();
        }*/

    }



}