package org.ecjtu.inteligentorder.customui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.Vector;

/**
 * Created by KerriGan on 2016/1/5.
 */
public class AdvanceScrollView extends ScrollView{
    private Context _context;
    private ViewGroup _rootView;

    public AdvanceScrollView(Context context) {
        this(context, null);
    }

    public AdvanceScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvanceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context=context;
    }

    public void initialize() {
//        this.setOnTouchListener(this);
//
//        Vector<View> allChildren = new Vector<>();
//        TopMenu.findAllChilds(this, allChildren);
//
//        for (View v : allChildren)
//        {
//            v.setOnTouchListener(this);
//        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        _rootView= (ViewGroup) this.getChildAt(0);
        initialize();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


}
