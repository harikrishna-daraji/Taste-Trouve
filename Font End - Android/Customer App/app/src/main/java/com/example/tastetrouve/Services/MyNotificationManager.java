package com.example.tastetrouve.Services;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.tastetrouve.R;

import java.util.*;

public class MyNotificationManager {
    //the method will show a big notification with an image
    //parameters are title for message title, message for message text, url of the big image and an intent that will open
    //when you will tap on the notification
Context context;

    public  MyNotificationManager(Context context){
        this.context=context;
    }


    public void showNotificationOreo( String title,  String message, Intent intent) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 234,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = context.getString(R.string.channel);
            String channelName = "TastyTrouve";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.canShowBadge();
            notificationChannel.setShowBadge(true);


            notificationManager.createNotificationChannel(notificationChannel);

            Notification notification = new Notification.Builder(context, channelId)
                    .setSmallIcon(R.drawable.logo)
                    .setColor(ContextCompat.getColor(context, R.color.red))
                    .setTicker(title)
                    .setWhen(0)
                    .setStyle( new Notification.BigTextStyle().bigText(message))
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.logo))
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(message)
                    .build();

            notificationManager.notify((int) Calendar.getInstance().getTimeInMillis(), notification);

        }
    }



}
