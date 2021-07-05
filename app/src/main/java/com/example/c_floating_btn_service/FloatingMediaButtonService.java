package com.example.c_floating_btn_service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class FloatingMediaButtonService extends Service  {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    public static boolean isStarted = false;
    private ConstraintLayout viewTop;
    private ConstraintLayout viewBottom;
    private ConstraintLayout viewButton;
    private boolean isToggleShow;



    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;


        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        initView();

    }


    private void initView() {
//        viewTop = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.service_floating_dashboard_top, null);
//        viewBottom = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.service_floating_dashboard_bottom, null);
        viewButton = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.service_floating_media_button, null);
//
//        viewTop.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
//                        View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//
//        viewBottom.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
//                        View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        viewButton.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

//        layoutParams.gravity = Gravity.TOP;
//        windowManager.addView(viewTop, layoutParams);
//
//        layoutParams.gravity = Gravity.BOTTOM;
//        windowManager.addView(viewBottom, layoutParams);



        layoutParams.gravity = Gravity.CENTER;
        windowManager.addView(viewButton, layoutParams);


//        LogUtils.d("FloatingDashboardService ->" + "initView()");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }


    Handler handler = new Handler();

    private void showFloatingWindow() {
        initFloatingButtonView();




        conCheckBox.setOnClickListener( v ->{
            isToggleShow = !isToggleShow;
            showToggle(isToggleShow);
        } );






    }

    private void showToggle(boolean isShow) {
        ConstraintUtils conFloatingContainerSet = new ConstraintUtils(FloatingMediaButtonService.this,conFloatingContainer);
        if(isShow){
            conFloatingContainerSet.setWidth(R.id.conFloatingButton,280);
        }else {
            conFloatingContainerSet.setWidth(R.id.conFloatingButton,104);
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ConstraintUtils constraintUtils = new ConstraintUtils(FloatingMediaButtonService.this,container);
                        if(isShow){
                            constraintUtils.setWidth(R.id.conFloatingContainer,312);
                        }else {
                            constraintUtils.setWidth(R.id.conFloatingContainer,136);

                        }
                    }
                });
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ConstraintUtils constraintUtils = new ConstraintUtils(FloatingMediaButtonService.this,conAppsButton);
                        if(isShow){
                            constraintUtils.setHorizontalBias(R.id.imgAppsIcon,0.1f);
                        }else {
                            constraintUtils.setHorizontalBias(R.id.imgAppsIcon,0.5f);
                        }

                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ConstraintUtils constraintUtils = new ConstraintUtils(FloatingMediaButtonService.this,conTogglePanelContainer);
                        if(isShow){
                            constraintUtils.setHorizontalBias(R.id.imgTogglePlaneIcon,0.1f);
                            constraintUtils.setHorizontalBias(R.id.tvTogglePlaneTitle,0.6f);
                        }else {
                            constraintUtils.setHorizontalBias(R.id.imgTogglePlaneIcon,0.5f);
                        }
                    }
                });
            }
        }).start();

        if(isShow){
            tvAppsTitle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvAppsTitle.setVisibility(View.VISIBLE);
                }
            },100);


            tvTogglePlaneTitle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTogglePlaneTitle.setVisibility(View.VISIBLE);
                }
            },100);
        }else {
            tvAppsTitle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvAppsTitle.setVisibility(View.GONE);
                }
            },100);


            tvTogglePlaneTitle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tvTogglePlaneTitle.setVisibility(View.GONE);
                }
            },100);
        }
    }


    ConstraintLayout container;
    ConstraintLayout conFloatingContainer;
    ConstraintLayout conCheckBox;
    ConstraintLayout conFloatingButton;
    ConstraintLayout conAppsBar;
    ConstraintLayout conAppsButton;
    TextView tvAppsTitle;

    ConstraintLayout conTogglePanelContainer;
    ImageView imgTogglePlaneIcon;
    TextView tvTogglePlaneTitle;
    private boolean m_bOnClick;
    private long m_lStartTime;

    private void initFloatingButtonView() {
        container = viewButton.findViewById(R.id.container);
        conFloatingContainer = viewButton.findViewById(R.id.conFloatingContainer);
        conCheckBox = viewButton.findViewById(R.id.conCheckBox);
        conFloatingButton = viewButton.findViewById(R.id.conFloatingButton);
        conAppsBar = viewButton.findViewById(R.id.conAppsBar);

        conAppsButton = viewButton.findViewById(R.id.conAppsButton);
        tvAppsTitle = viewButton.findViewById(R.id.tvAppsTitle);

        conTogglePanelContainer = viewButton.findViewById(R.id.conTogglePanelContainer);
        imgTogglePlaneIcon = viewButton.findViewById(R.id.imgTogglePlaneIcon);
        tvTogglePlaneTitle = viewButton.findViewById(R.id.tvTogglePlaneTitle);

        container.setOnTouchListener(floatingOnTouchListener);
    }


    View.OnTouchListener floatingOnTouchListener = new View.OnTouchListener() {
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
                        windowManager.updateViewLayout(view, layoutParams);
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
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        exitDashboard();

        viewTop = null;
        viewBottom = null;
        viewButton = null;
    }

    private void exitDashboard() {
        try {
            isStarted = false;
            windowManager.removeView(viewTop);
            windowManager.removeView(viewBottom);
            stopService(new Intent(this, FloatingMediaButtonService.class));
            windowManager = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}