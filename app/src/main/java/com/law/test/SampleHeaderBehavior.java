package com.law.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.liaoinstan.springview.widget.SpringView;

public class SampleHeaderBehavior extends CoordinatorLayout.Behavior<FrameLayout> {

    // 界面整体向上滑动，达到列表可滑动的临界点
    private boolean upReach;
    // 列表向上滑动后，再向下滑动，达到界面整体可滑动的临界点
    private boolean downReach;
    // 列表上一个全部可见的item位置
    private int lastPosition = -1;

    public SampleHeaderBehavior() {
    }

    public SampleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, FrameLayout child, MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downReach = false;
                upReach = false;
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FrameLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FrameLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.d("TAG", "before:upReach = " + upReach + ",downReach = " + downReach + ",lastPosition = " + lastPosition);

        Log.d("TAG", (child.getId() == R.id.head) + "");
        if (target instanceof RecyclerView) {
//            FrameLayout frameLayout = (FrameLayout) target;


            RecyclerView list = (RecyclerView) target;
            // 列表第一个全部可见Item的位置
            int pos = ((LinearLayoutManager) list.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            Log.d("TAG", "pos == 0 && pos < lastPosition : " + (pos == 0 && pos < lastPosition));
            if (pos == 0 && pos < lastPosition) {
                downReach = true;
            }
            // 整体可以滑动，否则RecyclerView消费滑动事件
            Log.d("TAG", "canScroll = " + canScroll(child, dy) + ",pos = " + pos);
            if (canScroll(child, dy) && pos == 0) {
                float finalY = child.getTranslationY() - dy;
                Log.d("TAG", "finalY = " + finalY + ",child.getHeight() = " + child.getHeight());
                if (finalY < -child.getHeight()) {
                    finalY = -child.getHeight();
                    upReach = true;
                } else if (finalY > 0) {
                    finalY = 0;
                }
                Log.d("TAG", "mid:upReach = " + upReach + ",downReach = " + downReach + ",lastPosition = " + lastPosition);

                child.setTranslationY(finalY);
                // 让CoordinatorLayout消费滑动事件
                consumed[1] = dy;
            }
            lastPosition = pos;
        }
        Log.d("TAG", "after:upReach = " + upReach + ",downReach = " + downReach + ",lastPosition = " + lastPosition);
//        if (!downReach) {
//            springView.setEnableHeader(false);
//        } else {
//            springView.setEnableHeader(true);
//        }
    }

    private boolean canScroll(View child, float scrollY) {
        if (scrollY > 0 && child.getTranslationY() == -child.getHeight() && !upReach) {
            return false;
        }

        if (downReach) {
            return false;
        }
        return true;
    }
}
