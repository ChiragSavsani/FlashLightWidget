package com.example.flashlight;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by chirag.savsani on 9/21/2015.
 */
public class LightWidgetProvider extends AppWidgetProvider {

    public static String CLOCK_WIDGET_UPDATE = "CLOCK_WIDGET_UPDATE";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (CLOCK_WIDGET_UPDATE.equals(intent.getAction())) {
            Toast.makeText(context, "onReceiver()", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        Log.i("ExampleWidget", "Updating widgets " + Arrays.asList(appWidgetIds));
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, FlashLightReceiver.class);
            intent.setAction("FLASHLIGHT_STATUS");
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_file);
            views.setOnClickPendingIntent(R.id.widgetLinearLayout, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
}
