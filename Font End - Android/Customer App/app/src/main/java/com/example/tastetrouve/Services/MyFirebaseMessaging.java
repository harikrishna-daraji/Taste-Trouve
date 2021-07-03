package com.example.tastetrouve.Services;

import com.example.tastetrouve.Activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {


        if (!remoteMessage.getData().isEmpty()) {


            JSONObject  json = new JSONObject(remoteMessage.getData());
            sendNotification(json);


        }

    }

    private void sendNotification(JSONObject json){

        try {

            String title = json.getString("title");
            String message = json.getString("message");

            //creating MyNotificationManager object
            MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());

            Intent intent = new Intent(MyFirebaseMessaging.this, MainActivity.class);


                myNotificationManager.showNotificationOreo(title, message, intent);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    }

