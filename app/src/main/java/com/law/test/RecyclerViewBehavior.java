package com.law.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.liaoinstan.springview.widget.SpringView;

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<FrameLayout> {

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull FrameLayout child, @NonNull View dependency) {
//        return super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof FrameLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull FrameLayout child, @NonNull View dependency) {
//        return super.onDependentViewChanged(parent, child, dependency);
        //计算列表y坐标，最小为0
        float y = dependency.getHeight() + dependency.getTranslationY();
        Log.d("TAG", "onDependentViewChanged:y = " + y);
        Log.d("TAG", "RecyclerViewBehavior:child " + (child.getId() == R.id.fl) + "");
        Log.d("TAG", "RecyclerViewBehavior:dependency " + (dependency.getId() == R.id.head) + "");
        Log.d("TAG", "RecyclerViewBehavior:dependency.getHeight() = " + dependency.getHeight());
        Log.d("TAG", "RecyclerViewBehavior:dependency.getTranslationY() = " + dependency.getTranslationY());
        ViewPager viewPager = (ViewPager) child.findViewById(R.id.viewpage);
        SpringView springView = viewPager.getChildAt(viewPager.getCurrentItem()).findViewById(R.id.springview);
        if (dependency.getTranslationY() <= 0d) {
            springView.setEnableHeader(true);
            springView.setEnableFooter(false);
        } else if (dependency.getTranslationY() + dependency.getHeight() == 0d) {
            springView.setEnableHeader(false);
            springView.setEnableFooter(true);
        } else {
            springView.setEnableHeader(false);
            springView.setEnableFooter(false);
        }


        if (y < 0) {
            y = 0;
        }
        child.setY(y);
        return true;
    }

    //    @Override
//    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
//        return dependency instanceof TextView;
//    }
//
//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
//        //计算列表y坐标，最小为0
//        float y = dependency.getHeight() + dependency.getTranslationY();
//        if (y < 0) {
//            y = 0;
//        }
//        child.setY(y);
//        return true;
//    }
}