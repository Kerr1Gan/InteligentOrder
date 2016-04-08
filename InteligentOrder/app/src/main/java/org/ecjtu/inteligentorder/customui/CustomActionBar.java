package org.ecjtu.inteligentorder.customui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.AsyncTask.AsyncImageTask;
import org.AsyncTask.Task;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.activity.SearchActivity;

/**
 * Created by KerriGan on 2016/1/12.
 */
public class CustomActionBar extends LinearLayout{
    private Context _context;
    public CustomActionBar(Context context) {
        this(context,null);
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context=context;
        init();
    }

    private ViewGroup _rootView;
    private ImageView _headImage;
    public void init()
    {
        _rootView= (ViewGroup) LayoutInflater.
                from(_context).inflate(R.layout.custom_action_bar,
                this);

        ((TextView)(_rootView.findViewById(R.id.text_view_id)))
                .setText("Hi ["
                        + AppInstance.getInstance().getID() + "]!");
        ((Button)_rootView.findViewById(R.id.button_search))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _context.startActivity(new Intent(_context
                                , SearchActivity.class));
                    }
                });
        _headImage= (ImageView) _rootView.findViewById(R.id.image_view);
        AsyncImageTask task=new AsyncImageTask();
        Task obj=new Task(_headImage);
        obj.setResPath(AppInstance.getInstance().getID()+"/HeadImage.jpg");
        task.addTask(obj);
    }

    public void updateHeadImage(Bitmap bm)
    {
        _headImage.setImageBitmap(bm);
    }
}
