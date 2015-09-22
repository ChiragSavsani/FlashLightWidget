package com.example.flashlight;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.widget.RemoteViews;


/**
 * Created by chirag.savsani on 9/21/2015.
 */
public class FlashLightReceiver extends BroadcastReceiver {

    static Camera camera = null;
    Camera.Parameters params;
    public static boolean isFlashLighOn;

    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_file);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //connectCameraService();
        //appWidgetManager.updateAppWidget(new ComponentName(context, LightWidgetProvider.class), views);
        if (isFlashLighOn) {
            offFlashLight();
            views.setImageViewResource(R.id.widgetImgOnOff, R.drawable.widget_light_off);
            views.setTextViewText(R.id.widgetTxtOnOff, "OFF");
            appWidgetManager.updateAppWidget(new ComponentName(context, LightWidgetProvider.class), views);
        } else {
            onFlashLight();
            views.setImageViewResource(R.id.widgetImgOnOff, R.drawable.widget_light_on);
            views.setTextViewText(R.id.widgetTxtOnOff, "ON");
            appWidgetManager.updateAppWidget(new ComponentName(context, LightWidgetProvider.class), views);
        }
    }

    public void onFlashLight() {
        if(camera == null){
            camera = Camera.open();
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashLighOn = true;

    }

    public void offFlashLight() {
        if(camera == null){
            camera = Camera.open();
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashLighOn = false;
    }

    public void connectCameraService() {
        if (camera == null) {
            camera = Camera.open();
            params = camera.getParameters();
        }
    }
}

