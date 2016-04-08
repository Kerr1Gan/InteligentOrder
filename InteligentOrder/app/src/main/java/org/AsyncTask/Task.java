package org.AsyncTask;

import android.view.View;

import java.util.Objects;

/**
 * Created by KerriGan on 2015/8/4.
 */
public class Task {
    private String _resPath;
    private boolean _isLoaded;
    private View _contentView;
    private Object _obj;


    public void setResPath(String path)
    {
        _resPath=path;
    }
    public String getResPath()
    {
        return _resPath;
    }
    public void setLoaded(boolean t)
    {
        _isLoaded=t;
    }
    public Task()
    {
        _isLoaded=false;
        _contentView=null;
    }
    public Task(View view)
    {
        _isLoaded=false;
        _contentView=view;
    }
    public boolean isLoaded()
    {
        return _isLoaded;
    }
    public void setObject(Object obj)
    {
        _obj=obj;
    }
    public Object getObject()
    {
        return _obj;
    }
    public void setContentView(View view)
    {
        _contentView=view;
    }
    public View getContentView()
    {
        return _contentView;
    }
    public interface CallBack
    {
        public void LoadedCallBack(String resPath);
        public void LoadingCallBack(String resPath);
    }
}
