package org.AsyncTask;

import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by KerriGan on 2015/8/4.
 */
public abstract class AsyncTaskBase implements AsyncTaskInterface{
    private Thread _thread;
    private boolean _isRunning;
    private ArrayList<Task> _taskQueue;
    private int _maxSynchronizeTask;
    private ExecutorService _fixedThreadPool;
    private static final int MSG_ASYNC_TASK_LOADING=0x00001;
    private static final int MSG_ASYNC_TASK_LOADED=0x00002;
    private static final int MSG_ASYNC_TASK_LAOD_FAILED=0x00003;


    public AsyncTaskBase()
    {
        _thread=new Thread(_runnable);
        _taskQueue=new ArrayList<>();
        _isRunning=true;
        _maxSynchronizeTask=1;
        _fixedThreadPool= Executors.newFixedThreadPool(1);
        _thread.start();
    }


    private Runnable _runnable=new Runnable() {
        @Override
        public void run() {
            while (_isRunning) {
                while (_taskQueue.size()>0)
                {
                    _fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            Task task=null;
                            if(_taskQueue.size()>0)
                            {
                                task=_taskQueue.remove(0);
                            }
                            else
                                return;

                            System.out.println("线程: "+this.toString()+": begin work.");

                            Message msg=new Message();
                            msg.what=MSG_ASYNC_TASK_LOADING;
                            msg.obj=task;
                            _handler.sendMessage(msg);
                            Object obj=doHttpRequestMethod(task);

                            if(obj!=null)
                            {
                                task.setObject(obj);
                                Message msg1=new Message();
                                msg1.what=MSG_ASYNC_TASK_LOADED;
                                msg1.obj=task;
                                _handler.sendMessage(msg1);
                            }
                            else
                            {
                                task.setObject(null);
                                Message msg2=new Message();
                                msg2.what=MSG_ASYNC_TASK_LAOD_FAILED;
                                msg2.obj=task;
                                _handler.sendMessage(msg2);
                            }

                            System.out.println("线程: "+this.toString()+": finished.");
                            }
                        });
                    }

                synchronized (AsyncTaskBase.this)
                {
                    if(_isRunning==false)
                    {
                        return;
                    }
                    try {
//                        _isRunning=false;
                        AsyncTaskBase.this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private Handler _handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_ASYNC_TASK_LOADING:
                    ((Task)msg.obj).setLoaded(false);
                    onLoading(((Task) msg.obj));
                    break;
                case MSG_ASYNC_TASK_LOADED:
                    ((Task)msg.obj).setLoaded(true);
                    onLoaded((Task) msg.obj);
                    break;
                case MSG_ASYNC_TASK_LAOD_FAILED:
                    onLoadFailed((Task)msg.obj);
                    break;
            }
        }
    };

    public void addTask(Task task)
    {
        _taskQueue.add(task);
        synchronized (AsyncTaskBase.this)
        {
            AsyncTaskBase.this.notifyAll();
            _isRunning = true;
        }
    }



    public Task getTask(int index)
    {
        return _taskQueue.get(index);
    }

    public ArrayList<Task> getTaskQueue()
    {
        return _taskQueue;
    }

    /**
     *  return the thread stat
     *
     */

    public boolean isRunning()
    {
        return _isRunning;
    }

    /**
     * child class overwrite this method to do custom things
     */
    @Override
    public Object doHttpRequestMethod(Task task)
    {
        return null;
    }

    @Override
    public void onLoading(Task task) {

    }

    @Override
    public void onLoaded(Task task) {

    }

    @Override
    public void onLoadFailed(Task task)
    {

    }

    public void startLoading()
    {
        synchronized (AsyncTaskBase.this)
        {
            if(_isRunning==false) {
                AsyncTaskBase.this.notifyAll();
                _isRunning = true;
            }
        }

    }
    public void destroy()
    {
        synchronized (AsyncTaskBase.this)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true)
                    {
                        if(_taskQueue.size()<=0)
                        {
                            AsyncTaskBase.this.notifyAll();
                            _isRunning=false;
                            break;
                        }
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    }

    public void httpRequestFinish(Task task)
    {
        synchronized (AsyncTaskBase.this)
        {
            AsyncTaskBase.this.notifyAll();
        }
    }

    public void setMaxSynchronizeTask(int num)
    {
        _maxSynchronizeTask=num;
        _fixedThreadPool=Executors.newFixedThreadPool(_maxSynchronizeTask);
    }

    public int getMaxSynchronizeTask()
    {
        return _maxSynchronizeTask;
    }
}
