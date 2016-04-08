package org.inteligentorder.animation;

import android.util.Log;
import android.view.View;

/**
 * Created by KerriGan on 2016/1/4.
 */
public class LeftToRightAnimation extends AnimationBase{
    private float _beginX;
    private int _drawTimes;
    private double _offSetX;

    @Override
    protected void beforeStart() {
        super.beforeStart();
        _beginX=_targetView.getX();
        _targetView.setX(-_targetView.getWidth());
        _drawTimes= (int) (_duration/10);
        _offSetX=(_targetView.getWidth()+_beginX)*1.0/_drawTimes*1.0;
    }

    @Override
    protected void onAnimate(View obj) {
        super.onAnimate(obj);
//        Log.i("msg","x:"+obj.getX());
        double dis=obj.getX()+_offSetX;
//        Log.i("msg","dis:"+dis);
        _targetView.setX((float) (dis));
    }

    @Override
    protected void endAnimate(View obj) {
        super.endAnimate(obj);
        _targetView.setX(_beginX);
    }
}
