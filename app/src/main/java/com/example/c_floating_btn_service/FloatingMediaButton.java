package com.example.c_floating_btn_service;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FloatingMediaButton extends ConstraintLayout  {
    private ConstraintLayout mConCheckBox;
    private ImageView mImgCheckBoxIcon;

    public FloatingMediaButton(@NonNull Context context) {
        super(context);
    }

    public FloatingMediaButton(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingMediaButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view  = LayoutInflater.from(context).inflate(R.layout.service_floating_media_button,this);
        mConCheckBox = view.findViewById(R.id.conCheckBox);
        mImgCheckBoxIcon = view.findViewById(R.id.imgCheckBoxIcon);
//        mConCheckBox.setOnClickListener(onClickListener);
        mConCheckBox.setOnClickListener( v ->{
            Log.d("hank","conCheckBox ->");
        });

        mImgCheckBoxIcon.setOnClickListener(v -> {
            Log.d("hank","mImgCheckBoxIcon ->");
        });
    }


}
