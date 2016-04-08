package org.inteligentorder.animation;

import android.view.View;

/**
 * Created by KerriGan on 2016/1/4.
 */
public class First extends AnimationBase{
    @Override
    protected void onAnimate(View obj) {
        super.onAnimate(obj);

        obj.setAlpha((float) (obj.getAlpha()-0.01));
    }
}
