package org.ecjtu.inteligentorder.customui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.ecjtu.inteligentorder.R;

import java.util.Vector;

/**
 * Created by KerriGan on 2016/1/5.
 */
public class TopMenu extends LinearLayout implements View.OnClickListener{

    private Context _context;
    private ViewGroup _rootView;
    public TopMenu(Context context) {
        this(context, null);
    }

    public TopMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context=context;
        initialize();
    }

    protected void initialize()
    {
        _rootView= (ViewGroup) LayoutInflater.from(_context).
                inflate(R.layout.custom_top_menu,this);

        ViewGroup g= (ViewGroup) _rootView.findViewById
                (R.id.scroll_view_container);

        Vector<View> allChilds=new Vector<>();
        findAllChilds(this, allChilds);
        for(View v:allChilds)
        {
            v.setOnClickListener(this);
        }
    }

    private View _lastTouch=null;


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

    public void setOnItemClickListener(onItemClick callback)
    {
        _itemClickCallBack=callback;
    }

    private onItemClick _itemClickCallBack;
    @Override
    public void onClick(View v) {
        if(_itemClickCallBack!=null)
            _itemClickCallBack.onClick(v);
    }


    public interface onItemClick{
        void onClick(View v);
    }
}
