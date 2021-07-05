package com.example.c_floating_btn_service;

import android.content.Context;
import android.os.Build;
import android.transition.TransitionManager;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ConstraintUtils {
    private ConstraintLayout mContainer;
    private ConstraintSet mConstraintSet;
    private Context mContent;


    public ConstraintUtils(Context context ,ConstraintLayout constraintLayout) {
        this.mContainer = constraintLayout;
        this.mContent = context;
        this.mConstraintSet  = new ConstraintSet();
        this.mConstraintSet.clone(constraintLayout);
    }

    public ConstraintSet setImageIcon(ImageView imageIcon, int drawableId){
        if(mConstraintSet != null){
            imageIcon.setImageDrawable(mContent.getResources().getDrawable(drawableId));
        }
        return mConstraintSet;
    }

    public void setBarHeight(int viewid,float percent){
        if(mConstraintSet != null){
            mConstraintSet.constrainPercentHeight(viewid,percent);
            mConstraintSet.applyTo(mContainer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(mContainer);
            }
        }
    }

    public void setVerticalBias(int viewid,float percent){
        if(mConstraintSet != null){
            mConstraintSet.setVerticalBias(viewid,percent);
            mConstraintSet.applyTo(mContainer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(mContainer);
            }
        }
    }




    public void setWidth(int viewid,int width){
        if(mConstraintSet != null){
            mConstraintSet.constrainWidth(viewid,width);
            mConstraintSet.applyTo(mContainer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(mContainer);
            }
        }
    }


    public void setHorizontalBias(int viewid,float bias){
        if(mConstraintSet != null){
            mConstraintSet.setHorizontalBias(viewid,bias);
            mConstraintSet.applyTo(mContainer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(mContainer);
            }
        }
    }

//    public void softKeyBoardShowVerticalBias(){
//        setBarHeight(R.id.fContainer, 0.95f);
//        setVerticalBias(R.id.fContainer, 0.1f);
//    }
//
//
//    public void softKeyBoardNoShowVerticalBias(){
//        setBarHeight(R.id.fContainer, 0.57f);
//        setVerticalBias(R.id.fContainer, 0.9f);
//    }



    public void clean(int viewid){
        if(mConstraintSet != null){
            mConstraintSet.clear(viewid);
            mConstraintSet.applyTo(mContainer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(mContainer);
            }
        }
    }

    public void setMargin(int viewId, int anchor, int value){
        if(mConstraintSet != null){
            mConstraintSet.setMargin(viewId,anchor,value);
            mConstraintSet.applyTo(mContainer);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(mContainer);
            }
        }
    }
}
