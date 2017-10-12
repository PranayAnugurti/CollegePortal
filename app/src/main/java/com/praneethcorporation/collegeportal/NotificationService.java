package com.praneethcorporation.collegeportal;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/**
 * Created by PRANAYKUMAR on 12-10-2017.
 */
public class NotificationService extends FirebaseMessagingService {
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    Notification notification = new NotificationCompat.Builder(this)
        .setContentTitle(remoteMessage.getNotification().getTitle())
        .setContentText(remoteMessage.getNotification().getBody())
        .setSmallIcon(R.mipmap.ic_launcher)
        .setAutoCancel(true)
        .build();
    notification.contentIntent= PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), CurrentOpenings.class), 0);
    notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    notification.flags |= Notification.FLAG_AUTO_CANCEL;

    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
    manager.notify(123, notification);
  }
}