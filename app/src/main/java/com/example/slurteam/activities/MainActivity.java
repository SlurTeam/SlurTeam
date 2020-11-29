package com.example.slurteam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.slurteam.R;
import com.example.slurteam.adapter.MainAdapter;
import com.example.slurteam.decoration.LayoutMarginDecoration;
import com.example.slurteam.model.ModelMain;
import com.example.slurteam.utils.Tools;

import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.onSelectData {

    RecyclerView rvMainMenu;
    LayoutMarginDecoration gridMargin;
    ModelMain mdlMainMenu;
    List<ModelMain> lsMainMenu = new ArrayList<>();
    public static final long INTERVAL_1MENIT = 60000L;

    private static final int NOTIFICATION_ID = 0;

    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        rvMainMenu = findViewById(R.id.rvMainMenu);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2,
                RecyclerView.VERTICAL, false);
        rvMainMenu.setLayoutManager(mLayoutManager);
        gridMargin = new LayoutMarginDecoration(2, Tools.dp2px(this, 4));
        rvMainMenu.addItemDecoration(gridMargin);
        rvMainMenu.setHasFixedSize(true);

        //get Time Now

        setMenu();
        mNotificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) getSystemService
                (ALARM_SERVICE);

        alarmToggle.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged
                            (CompoundButton buttonView, boolean isChecked) {
                        String toastMessage;
                        if (isChecked) {

                            long repeatInterval = MainActivity.INTERVAL_1MENIT;

                            long triggerTime = SystemClock.elapsedRealtime()
                                    + repeatInterval;

                            if (alarmManager != null) {
                                alarmManager.setInexactRepeating
                                        (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                                triggerTime, repeatInterval,
                                                notifyPendingIntent);
                            }

                            toastMessage = getString(R.string.alarm_on_toast);

                        } else {

                            mNotificationManager.cancelAll();

                            if (alarmManager != null) {
                                alarmManager.cancel(notifyPendingIntent);
                            }
                            toastMessage = getString(R.string.alarm_off_toast);

                        }

                        Toast.makeText(MainActivity.this, toastMessage,
                                Toast.LENGTH_SHORT).show();
                    }
                });

        createNotificationChannel();
    }


    public void createNotificationChannel () {

        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to " +
                    "stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }


    private void setMenu () {
        mdlMainMenu = new ModelMain("Wisata", R.drawable.logo_purwakarta);
        lsMainMenu.add(mdlMainMenu);
        mdlMainMenu = new ModelMain("About", R.drawable.ic_cafe);
        lsMainMenu.add(mdlMainMenu);

        MainAdapter myAdapter = new MainAdapter(lsMainMenu, this);
        rvMainMenu.setAdapter(myAdapter);

    }

    @Override
    public void onSelected (ModelMain mdlMain) {
        switch (mdlMain.getTxtName()) {
            case "Wisata":
                startActivityForResult(new Intent(MainActivity.this, com.example.slurteam.activities.WisataActivity.class), 1);
                break;
            case "About":
                startActivityForResult(new Intent(MainActivity.this, com.example.slurteam.activities.About.class), 1);
                break;
        }
    }

    //set Transparent Status bar
    public static void setWindowFlag (Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}

