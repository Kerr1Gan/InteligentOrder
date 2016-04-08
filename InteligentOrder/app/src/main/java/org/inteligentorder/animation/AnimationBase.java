package org.inteligentorder.animation;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * Created by KerriGan on 2016/1/4.
 */
public abstract class AnimationBase extends Handler implements Runnable{

    protected View _targetView;
    protected float _duration;
    private Thread _thread;
    private int _delayTime;

    private final int MESSAGE_DRAW=0x0001;
    private final int MESSAGE_END_DRAW=0x0002;
    private final int MESSAGE_START_DRAW=0x0003;

    public AnimationBase()
    {
        initialize();
    }

    private void initialize()
    {
        _duration=0;
        _targetView=null;
        _thread=new Thread(this);
    }

    public void setDuration(float duratin)
    {
        _duration=duratin;
    }

    public void setTargetView(View obj)
    {
        _targetView =obj;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        Runnable runDraw=new Runnable() {
            @Override
            public void run() {
                onAnimate(_targetView);
            }
        };

        Runnable runStartDraw=new Runnable() {
            @Override
            public void run() {
                _targetView.setVisibility(View.VISIBLE);
                beforeStart();
                _thread.start();
            }
        };

        Runnable runEndDraw=new Runnable() {
            @Override
            public void run() {
                endAnimate(_targetView);
            }
        };
        switch (msg.what)
        {
            case MESSAGE_DRAW:
//                _targetView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        onAnimate(_targetView);
//                    }
//                });
                _targetView.post(runDraw);
                break;
            case MESSAGE_END_DRAW:
//                _targetView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        endAnimate(_targetView);
//                    }
//                });
                _targetView.post(runEndDraw);
                break;
            case MESSAGE_START_DRAW:
//                _targetView.post(new Runnable() {
//                    @Override
//                    public void run() {
////                        Log.i("Animation",AnimationBase.this._targetView.toString()
////                                +"Animation was started!");
//                        _targetView.setVisibility(View.VISIBLE);
//                        beforeStart();
//                        _thread.start();
//                    }
//                });
                _targetView.post(runStartDraw);
                break;
        }

    }
    public void runAnimation()
    {
        runAnimation(0);
    }

    public void runAnimation(float delay)
    {
        _delayTime= (int) delay;
        synchronized (AnimationBase.this)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int timeCount=0;
                    while (true)
                    {
                        if((_targetView.getWidth()!=0
                                || _targetView.getHeight()!=0)&&
                                timeCount>= _delayTime)
                        {
                            Message m=new Message();
                            m.what=MESSAGE_START_DRAW;
                            AnimationBase.this.sendMessage(m);
                            break;
                        }
                        try {
                            timeCount+=10;
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    protected void onAnimate(View obj)
    {
    }

    protected void endAnimate(View obj)
    {

    }

    protected void beforeStart()
    {
    }

    @Override
    public void run() {
            float startTime=0;
            while(true)
            {
                if(_targetView==null)
                {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                synchronized (this)
                {
                    Message m=new Message();
                    m.what=MESSAGE_DRAW;
                    AnimationBase.this.handleMessage(m);
                }

                try {
                    startTime+=10;
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(startTime>=_duration)
                {
//                    Log.i("Animation",this._targetView.toString()+"Animation was ended!");
                    Message m=new Message();
                    m.what=MESSAGE_END_DRAW;
                    AnimationBase.this.sendMessage(m);
                    break;
                }
            }
        }
}

