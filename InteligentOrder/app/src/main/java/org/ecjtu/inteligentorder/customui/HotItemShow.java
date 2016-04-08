package org.ecjtu.inteligentorder.customui;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.AsyncTask.AsyncImageTask;
import org.AsyncTask.ResourceCollection;
import org.AsyncTask.Task;
import org.ecjtu.inteligentorder.R;

import java.util.ArrayList;

/**
 * Created by KerriGan on 2016/1/8.
 */
public class HotItemShow extends LinearLayout{
    public HotItemShow(Context context) {
        this(context, null);
    }

    public HotItemShow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotItemShow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context=context;
        _rootView= (ViewGroup) LayoutInflater.from(_context).inflate(
                R.layout.custom_hot_item_show
                ,this);

        init();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        _animThread.stop();
    }

    private Context _context;
    private ViewGroup _rootView;
    private ViewPager _viewPager;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void init()
    {
        ResourceCollection.getInstance().initContext(this.getContext());
        _viewPager= (ViewPager) _rootView.findViewById(R.id.view_pager);

        final ArrayList<ImageView> list=new ArrayList<>();
        final ImageView img=new ImageView(_context);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
//        img.setBackground(_context.getResources().getDrawable
//                (R.drawable.head_logo));
        img.setScaleType(ImageView.ScaleType.FIT_XY);

        final ImageView img1=new ImageView(_context);
        img1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
//        img1.setBackground(_context.getResources().getDrawable
//                (R.drawable.ico_action_bar));
        img1.setScaleType(ImageView.ScaleType.FIT_XY);

        final ImageView img2=new ImageView(_context);
        img2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT));
//        img2.setBackground(_context.getResources().getDrawable
//                (R.drawable.mainsweety2));
        img2.setScaleType(ImageView.ScaleType.FIT_XY);

        list.add(img);
        list.add(img1);
        list.add(img2);

        AsyncImageTask task=new AsyncImageTask();
        Task obj=new Task(img);
        obj.setResPath("HotItem/" + "1.jpg");
        task.addTask(obj);
        Task obj1=new Task(img1);
        obj1.setResPath("HotItem/"+"2.jpg");
        task.addTask(obj1);
        Task obj2=new Task(img2);
        obj2.setResPath("HotItem/"+"3.jpg");
        task.addTask(obj2);



        _viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(View container, int position) {
                ((ViewPager) container).addView(list.get(position));
                return list.get(position);
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView(list.get(position));
            }
        });

        _animThread=new Thread(new Runnable() {
            @Override
            public void run() {

                int pos=0;
                boolean toRight=true;
                while (true)
                {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(toRight)
                        pos++;
                    else
                        pos--;
                    if(pos>list.size()-1)
                    {
                        pos=list.size()-1;
                        toRight=false;
                    }
                    else if(pos<0)
                    {
                        pos=0;
                        toRight=true;
                    }
                    final int fpos=pos;
                    _viewPager.post(new Runnable() {
                        @Override
                        public void run() {
                            _viewPager.setCurrentItem(fpos);
                        }
                    });
                }
            }
        });
        _animThread.start();

        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int _lastPos = -1;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private Thread _animThread;
}
