package com.example.flashlight;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView switch_on_off;
    private Camera camera;
    Parameters params;
    public boolean isSupportFlash, isFlashLighOn;
    String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isSupportFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isSupportFlash) {
            Toast.makeText(getApplicationContext(), "Your device does not support flash. ", Toast.LENGTH_LONG).show();
        }
        switch_on_off = (ImageView) findViewById(R.id.switch_on_off);

        connectCameraService();
        switch_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashLighOn) {
                    offFlashLight();
                    switch_on_off.setImageResource(R.drawable.switch_off);
                } else {
                    onFlashLight();
                    switch_on_off.setImageResource(R.drawable.switch_on);
                }
            }
        });
    }

    public void broadcastMessage() {
        Intent intent = new Intent();
        intent.setAction("com.example.flashlight.LightWidgetReceiver.LIGHT_STATUS");
        intent.putExtra("Status",status);
        sendBroadcast(intent);
    }


    public void onFlashLight() {
        if (!isFlashLighOn) {
            status = "ON";
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashLighOn = true;
            switch_on_off.setImageResource(R.drawable.switch_on);
            broadcastMessage();

        }
    }

    public void offFlashLight() {
        if (isFlashLighOn) {
            status = "ON";
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashLighOn = false;
            switch_on_off.setImageResource(R.drawable.switch_off);
            broadcastMessage();


        }
    }

    public void connectCameraService() {
        if (camera == null) {
            camera = android.hardware.Camera.open();
            params = camera.getParameters();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        offFlashLight();
    }

    @Override
    protected void onPause() {
        super.onPause();
        offFlashLight();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (isSupportFlash) {
            onFlashLight();
        }*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectCameraService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
