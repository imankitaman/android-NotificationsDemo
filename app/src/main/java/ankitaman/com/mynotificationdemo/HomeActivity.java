package ankitaman.com.mynotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import java.util.Calendar;

/**
 * Created by ankit on 11/07/16.
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /**
     * basic notification using Notification
     *
     * @param
     */
    public void BasicNotify(View view) {
        Intent notificationIntent = new Intent(HomeActivity.this, NotificationView.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(HomeActivity.this);
        stackBuilder.addParentStack(NotificationView.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.this);
        Notification notification = builder.setContentTitle("title")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Big text"))
                .setAutoCancel(true)
                .setContentText("Hi this is context text")
                .setLargeIcon(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.ic_edit_white_24dp))
                .setSmallIcon(R.drawable.ic_edit_white_24dp)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager = (NotificationManager) HomeActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notification);
    }

    int numMessages = 0;
    public void indexStyle(View view) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've received new message.");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setSmallIcon(R.drawable.ic_edit_white_24dp);
        mBuilder.setNumber(++numMessages);
        mBuilder.setGroupSummary(true);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        events[0] = new String("This is first line....");
        events[1] = new String("This is second line...");
        events[2] = new String("This is third line...");
        events[3] = new String("This is 4th line...");
        events[4] = new String("This is 5th line...");
        events[5] = new String("This is 6th line...");

        inboxStyle.setBigContentTitle("Big Title Details:");
        for (String event : events) {
            inboxStyle.addLine(event);
        }
        mBuilder.setStyle(inboxStyle);

        Intent resultIntent = new Intent(this, NotificationView.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) HomeActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder.build());
    }


    public void buttonNotify(View view) {
        Intent intent = new Intent(this, NotificationView.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        if (Build.VERSION.SDK_INT >= 16) {
            Notification n = new Notification.Builder(this)
                    .setContentTitle("New mail from " + "test@gmail.com")
                    .setContentText("Subject")
                    .setSmallIcon(R.drawable.ic_edit_white_24dp)
            .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_edit_white_24dp, "Call", pIntent)
                    .addAction(R.drawable.ic_edit_white_24dp, "More", pIntent).build();
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(4, n);
        }
    }


    public void createLayoutNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent i = new Intent(this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);

        builder.setTicker("Custom layout notification");
        builder.setSmallIcon(R.drawable.ic_edit_white_24dp);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);
        final String text = "Hi this is sample text";
        contentView.setTextViewText(R.id.textView, text);
        notification.contentView = contentView;
        if (Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        }
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(5, notification);
    }


    public void createNotificationForL(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            Notification.Builder notificationBuilder = new Notification.Builder(HomeActivity.this)
                    .setSmallIcon(R.drawable.ic_edit_white_24dp)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setContentTitle("Sample Notification")
                    .setContentText("This is a normal notification.");
            Intent push = new Intent();
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            push.setClass(HomeActivity.this, NotificationView.class);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(HomeActivity.this, 0,
                    push, PendingIntent.FLAG_CANCEL_CURRENT);
            notificationBuilder
                    .setContentText("Heads-Up Notification on Android L or above.")
                    .setFullScreenIntent(fullScreenPendingIntent, true);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(6, notificationBuilder.build());
        }
    }

    /**
     *
     * @param view
     *
     * BIG PICTURE NOTIFICATION
     */
    public void notificationWithImage(View view) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.image)).build();

        Intent resultIntent = new Intent(this, NotificationView.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent piResult = PendingIntent.getActivity(this,
                (int) Calendar.getInstance().getTimeInMillis(), resultIntent, 0);

        if (Build.VERSION.SDK_INT >= 16) {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_edit_white_24dp)
                            .setContentTitle("Big picture notification")
                            .setContentText("This is test of big picture notification.")
                            .setStyle(bigPictureStyle)
                            .addAction(R.drawable.ic_edit_white_24dp, "show activity", piResult)
                            .addAction(R.drawable.ic_edit_white_24dp, "Share",
                                    PendingIntent.getActivity(getApplicationContext(), 0,
                                            getIntent(), 0, null));
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, builder.build());
        }

    }

}
