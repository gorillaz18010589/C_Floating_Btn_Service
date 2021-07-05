package com.example.c_floating_btn_service;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private FloatingMediaButton floatingMediaButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        showBtnFullScreenExit(2);
        initFloatingButtonService();
    }


    @SuppressLint("NewApi")
    private void initFloatingButtonService(){
        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            if (!FloatingMediaButtonService.isStarted) {
                Intent serviceIntent = new Intent(this, FloatingMediaButtonService.class);
                serviceIntent.putExtra("isWorkout", false);
                startService(serviceIntent);
                //    startSleepTimer();
            }
        }
    }





    private Button btnFullScreenExit;
    private WindowManager windowManager_exitButton;
    private WindowManager.LayoutParams layoutParams;
    public boolean floatButtonIsStarted;
    private boolean m_bOnClick;
    private long m_lStartTime;






    @SuppressLint("ClickableViewAccessibility")
    public void showBtnFullScreenExit(int type) {


        Log.d("HHHHHH", "showBtnFullScreenExit: " + type);
        floatButtonIsStarted = true;
        windowManager_exitButton = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = 172;
        layoutParams.height = 172;
        layoutParams.x = 1000;
        layoutParams.y = 0;

        btnFullScreenExit = new Button(getApplicationContext());
        btnFullScreenExit.setAlpha(0.9f);
        btnFullScreenExit.setBackgroundResource(R.drawable.icon_media_fullscreen);
        windowManager_exitButton.addView(btnFullScreenExit, layoutParams);
        btnFullScreenExit.setOnTouchListener(new FloatingOnTouchListener());
        btnFullScreenExit.setOnClickListener(v -> {
            //點擊離開全螢幕的float按鈕

        });
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    m_bOnClick = false;
                    m_lStartTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    m_bOnClick = true;
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    //  windowManager_exitButton.updateViewLayout(view, layoutParams);

                    try {
                        windowManager_exitButton.updateViewLayout(view, layoutParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    long m_lEndTime = System.currentTimeMillis();
                    m_bOnClick = (m_lEndTime - m_lStartTime) > 0.1 * 5000L;
                    break;
                default:
                    break;
            }
            return m_bOnClick;
        }
    }

}









/*
private Button btnFullScreenExit;
    private WindowManager windowManager_exitButton;
    private WindowManager.LayoutParams layoutParams;
    public boolean floatButtonIsStarted;
    private boolean m_bOnClick;
    private long m_lStartTime;

    @SuppressLint("ClickableViewAccessibility")
    public void showBtnFullScreenExit(int type) {

        if (floatButtonIsStarted) return;
        try {
            FloatingDashboardService.isStarted = false;
            stopService(new Intent(getInstance(), FloatingDashboardService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("HHHHHH", "showBtnFullScreenExit: " + type);
        floatButtonIsStarted = true;
        windowManager_exitButton = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = 172;
        layoutParams.height = 172;
        layoutParams.x = 1000;
        layoutParams.y = 0;

        btnFullScreenExit = new Button(getApplicationContext());
        btnFullScreenExit.setAlpha(0.9f);
        btnFullScreenExit.setBackgroundResource(R.drawable.icon_media_fullscreen);
        windowManager_exitButton.addView(btnFullScreenExit, layoutParams);
        btnFullScreenExit.setOnTouchListener(new FloatingOnTouchListener());
        btnFullScreenExit.setOnClickListener(v -> {
            //點擊離開全螢幕的float按鈕
            if (CommonUtils.isFastClick()) return;

            try {

                startFloatingDashboard(type);

                new RxTimer().timer(200, number -> {
                    try {
                        windowManager_exitButton.removeView(btnFullScreenExit);
                        floatButtonIsStarted = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    m_bOnClick = false;
                    m_lStartTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    m_bOnClick = true;
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    //  windowManager_exitButton.updateViewLayout(view, layoutParams);

                    try {
                        windowManager_exitButton.updateViewLayout(view, layoutParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    long m_lEndTime = System.currentTimeMillis();
                    m_bOnClick = (m_lEndTime - m_lStartTime) > 0.1 * 5000L;
                    break;
                default:
                    break;
            }
            return m_bOnClick;
        }
    }*/