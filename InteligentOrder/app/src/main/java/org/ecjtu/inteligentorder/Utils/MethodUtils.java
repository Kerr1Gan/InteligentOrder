package org.ecjtu.inteligentorder.Utils;

import android.view.View;
import android.view.ViewGroup;

import java.util.Vector;

/**
 * Created by KerriGan on 2016/1/7.
 */
public class MethodUtils {

    public static void findAllChilds(ViewGroup root,Vector<View> container) {
        View view;

        for (int i = 0; i < root.getChildCount(); i++) {
            view = root.getChildAt(i);
            if (view instanceof ViewGroup) {
                findAllChilds((ViewGroup) view, container);
            }

            if (view != null)
                container.add(view);
        }
    }
}
