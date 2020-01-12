package mobi.cemalyilmaz.PushNotificationActionButton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationActionReceiver extends BroadcastReceiver {
    private final String TAG = "NotificationActionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "notification action received: " + intent.getStringExtra("param"));
        String actionName = intent.getStringExtra("action_name");
        NotificationActionButton action = NotificationActionButton.getAction(actionName);
        if (action != null) {
            action.perform();
        }
    }
}
