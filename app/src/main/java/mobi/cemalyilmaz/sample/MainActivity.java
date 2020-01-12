package mobi.cemalyilmaz.sample;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import mobi.cemalyilmaz.PushNotificationActionButton.NotificationActionButton;
import mobi.cemalyilmaz.PushNotificationActionButton.NotificationActionButtonListener;
import mobi.cemalyilmaz.PushNotificationActionButton.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPushNotification();
            }
        });
    }

    private void showPushNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "channelId";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId);
        createNotificationChannel(notificationManager, channelId);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Default notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setAutoCancel(true)
                .setContentInfo("Info");

        addActionButtons(notificationBuilder);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void createNotificationChannel(NotificationManager notificationManager, String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "My Notifications", NotificationManager.IMPORTANCE_MAX);

            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void addActionButtons(NotificationCompat.Builder notificationBuilder) {

        Bundle prevActionBundle = new Bundle();
        prevActionBundle.putString("param", "Prev");

        NotificationActionButton prevActionButton = new NotificationActionButton(this, "PREV", prevActionBundle, new NotificationActionButtonListener() {
            @Override
            public void onClick(Bundle bundle) {
                Log.v("NotificationActionButton", "Action param:" + bundle.getString("param"));
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                nm.cancel(1);
            }
        });

        Bundle nextActionBundle = new Bundle();
        nextActionBundle.putString("param", "Next");

        NotificationActionButton nextActionButton = new NotificationActionButton(this, "NEXT", nextActionBundle, new NotificationActionButtonListener() {
            @Override
            public void onClick(Bundle bundle) {
                Log.v("NotificationActionButton", "Action param:" + bundle.getString("param"));
            }
        });

        Bundle otherBundle = new Bundle();
        otherBundle.putString("param", "OTHER");

        NotificationActionButton sampleButton = new NotificationActionButton(this);
        sampleButton.setButtonTitle("Other");
        sampleButton.setExtras(otherBundle);
        sampleButton.setAction(new NotificationActionButtonListener() {
            @Override
            public void onClick(Bundle bundle) {
                Log.v("NotificationActionButton", "Action param: " + bundle.getString("param"));
            }
        });

        notificationBuilder.addAction(nextActionButton.action());
        notificationBuilder.addAction(prevActionButton.action());
        notificationBuilder.addAction(sampleButton.action());
    }
}
