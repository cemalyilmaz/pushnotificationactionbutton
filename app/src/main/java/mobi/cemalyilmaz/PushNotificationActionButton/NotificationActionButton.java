package mobi.cemalyilmaz.PushNotificationActionButton;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class NotificationActionButton {
    private static final List<NotificationActionButton> actions = new ArrayList<>();

    private static final String guidKey = "mobi.cemalyilmaz.action.guid";
    private static final String intentActionName = "mobi.cemalyilmaz.action.name";

    private Context context;
    private String guid;
    private String buttonTitle;
    private NotificationActionButtonListener action;
    private Bundle extras;

    public void setButtonTitle(String buttonTitle) {
        this.buttonTitle = buttonTitle;
    }

    public void setExtras(Bundle extras) {
        this.extras = extras;
    }

    public void setAction(NotificationActionButtonListener action) {
        this.action = action;
    }

    public NotificationActionButton(Context context) {
        this(context, null, null, null);
    }

    public NotificationActionButton(Context context, String buttonTitle, Bundle extras, NotificationActionButtonListener action) {
        this.context = context;
        this.guid = UUID.randomUUID().toString();

        this.extras = extras;
        this.buttonTitle = buttonTitle;
        this.action = action;
    }

    public NotificationCompat.Action action() {
        this.registerAction();

        Intent intent = new Intent(context, NotificationActionReceiver.class);
        intent.setAction(intentActionName);
        if (extras != null) {
            intent.putExtras(extras);
        }
        intent.putExtra(guidKey, guid);

        Random r = new Random();
        int randomNumber = r.nextInt();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, randomNumber, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action.Builder(0, this.buttonTitle, pendingIntent).build();
    }

    void perform() {
        action.onClick(this.extras);
    }

    private void registerAction() {
        actions.add(this);
    }

    static NotificationActionButton getAction(String name) {
        for (NotificationActionButton action : actions) {
            if (action.guid.equalsIgnoreCase(name)) {
                return action;
            }
        }
        return null;
    }
}
