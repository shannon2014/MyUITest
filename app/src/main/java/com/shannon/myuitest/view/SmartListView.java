package com.shannon.myuitest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class SmartListView extends ListView {

    public SmartListView(Context context) {
        super(context);
    }

    public SmartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                ev.getPointerCount();

        }
        return super.onTouchEvent(ev);
    }
}
