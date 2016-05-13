package com.andela.checksmarter.utilities.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.andela.checksmarter.R;
import com.andela.checksmarter.utilities.MsgBox;

/**
 * Created by CodeKenn on 04/05/16.
 */
public class CheckSmarterWidget extends AppWidgetProvider {
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        MsgBox.INSTANCE.show(context, "Removed");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        MsgBox.INSTANCE.show(context, "Added");

        for (int currentWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_checksmarter);

            Intent intent = new Intent(context, OnWidgetReceiver.class);
            PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            views.setOnClickPendingIntent(R.id.widgetButton, pIntent);
            appWidgetManager.updateAppWidget(currentWidgetId, views);
        }
    }
}
