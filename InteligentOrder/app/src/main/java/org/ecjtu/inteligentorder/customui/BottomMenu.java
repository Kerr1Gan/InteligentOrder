package org.ecjtu.inteligentorder.customui;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.Utils.MethodUtils;
import org.w3c.dom.NameList;

import java.util.Vector;

/**
 * Created by KerriGan on 2016/1/5.
 */
public class BottomMenu extends LinearLayout{

    private Context _context;
    private ViewGroup _rootView;

    public BottomMenu(Context context) {
        this(context, null);
    }

    public BottomMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context=context;
        initialize();
    }

    public void initialize()
    {
        _rootView= (ViewGroup) LayoutInflater.from(_context).
                inflate(R.layout.custom_bottom_menu,this);

        Vector<View> container=new Vector<>();
        MethodUtils.findAllChilds(_rootView, container);
        for(View v:container)
        {
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = -1;
                    if (_listener != null) {
                        switch (v.getId()) {
                            case R.id.button_left:
                                pos = 0;
                                break;
                            case R.id.button_mid:
                                pos = 1;
                                break;
                            case R.id.button_right:
                                pos = 2;
                                break;
                        }
                        _listener.itemClick(v, pos);
                    }
                }
            });
        }

    }

    public void setOnItemClickLitener(onItemClickListener lis)
    {
        _listener=lis;
    }

    private onItemClickListener _listener=null;
    public interface onItemClickListener
    {
        public void itemClick(View v,int pos);
    }

}
