package edu.inspire.messageparser;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;


public class MessageReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    public static final String EMER = "emer";
    public static final String HPY = "hpy";
    public static final String EMO = "emo";
    private static final int NOTIFID = 4040;
    MessageDBClass dbc;
    NotificationManager mgr;
    NotificationCompat.Builder notif;
    Intent msgIntent;
    PendingIntent pi;
    Context context;

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        dbc = new MessageDBClass(context);
        notif = new NotificationCompat.Builder(context);
        mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        this.context = context;
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            // String smsMessageStr = "";
            assert sms != null;
            for (Object sm : sms) {
                SmsMessage smsMessage = SmsMessage
                        .createFromPdu((byte[]) sm);

                String smsBody = smsMessage.getMessageBody();
                String address = smsMessage.getOriginatingAddress();
                Log.d("smsbody", smsBody);
                Log.d("sms address", address);
                msgIntent = new Intent(context, MessageViewActivity.class);
                msgIntent.putExtra("smsaddress", address);
                msgIntent.putExtra("smsbody", smsBody);
                pi = PendingIntent.getActivity(context, 1000, msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (smsBody.contains("urgent") || smsBody.contains("emergency") || smsBody.contains("urgently")
                        || smsBody.contains("problem") || smsBody.contains("need help")) {

                    dbc.insertIntoEmerTab(address, smsBody, "Unread");
                    generateNotification(EMER);
                } else if (smsBody.contains("happy") || smsBody.contains("yahoo") || smsBody.contains("surprise")
                        || smsBody.contains("joyful") || smsBody.contains("fantastic")) {
                    dbc.insertIntoHpyTab(address, smsBody, "Unread");
                    generateNotification(HPY);
                } else if (smsBody.contains("sad") || smsBody.contains("painful") || smsBody.contains("heart broke")
                        || smsBody.contains("grieve") || smsBody.contains("sorrow")) {
                    dbc.insertIntoEmoTab(address, smsBody, "Unread");
                    generateNotification(EMO);
                }


            }

        }

    }

    public  void  generateNotification(String tag){

        MessageDBClass db = new MessageDBClass(context);
        Cursor cursor =  db.getLastRowEmer();
        cursor.moveToFirst();
        String address = cursor.getString(cursor.getColumnIndex(MessageDBClass.ADDRESS));
        //String body = cursor.getString(cursor.getColumnIndex(MessageDBClass.SMSBODY));
        //int id = cursor.getInt(cursor.getColumnIndex(MessageDBClass.ID));
        //String status = cursor.getString(cursor.getColumnIndex(MessageDBClass.STATUS));

        notif.setContentIntent(pi);
        notif.setSmallIcon(R.mipmap.ic_launcher);
        notif.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        notif.setAutoCancel(true);

        switch (tag) {
            case EMER:

                notif.setContentText("Tap to Read");
                notif.setContentTitle("Message from " + address);
                notif.setOngoing(true);

                context.startService(new Intent(context, NotificationService.class));


                break;
            case HPY:
                notif.setContentText("Tap to Read");
                notif.setContentTitle("You have a Happy Message");
                notif.setOngoing(false);
                break;
            case EMO:
                notif.setContentText("Tap to Read");
                notif.setContentTitle("You have an Emotional Message");
                notif.setOngoing(false);
                break;
        }

        mgr.notify(NOTIFID, notif.build());

    }
}
