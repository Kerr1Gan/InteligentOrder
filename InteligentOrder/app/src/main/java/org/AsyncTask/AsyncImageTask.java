package org.AsyncTask;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.Http.HttpUtils.HttpUtil;

import java.util.HashMap;



/**
 * Created by KerriGan on 2015/8/4.
 */
public class AsyncImageTask extends AsyncTaskBase implements AsyncTaskInterface{
    private HashMap<String,Bitmap> _bitmapMap;
    private ImageContainer _imageContainer;
    private Bitmap _defaultBitmap;
    public AsyncImageTask()
    {
        _bitmapMap=new HashMap<>();
        _imageContainer=ImageContainer.getInstance();
        _defaultBitmap=ResourceCollection.getInstance().getDefaultResource().
                getBitmapMap().get(String.valueOf(ResourceCollection.ResourceName.BITMAP_DEFAULT_IMAGE));
    }

    public HashMap<String,Bitmap> getContainer()
    {
        return _bitmapMap;
    }



    @Override
    public void onLoading(Task task)
    {
    }
    @Override
    public void onLoaded(Task task)
    {
        _imageContainer.add(task.getResPath(), (Bitmap) task.getObject());
        System.out.println("客户端请求下载图片成功!! "+ task.getResPath());
        if(task.getContentView()!=null)
        {
            ((ImageView)task.getContentView()).setImageBitmap(_imageContainer.get(task.getResPath()));
        }
    }
    @Override
    public void onLoadFailed(Task task)
    {
//        _imageContainer.add(task.getResPath(),null);
    }
    @Override
    public Object doHttpRequestMethod(Task task)
    {
        Bitmap bmp= HttpUtil.getUrlImage(task.getResPath());
        return bmp;
    }
    @Override
    public void addTask(Task task)
    {
        if(ResourceCollection.getInstance().getImageContainer().get(task.getResPath())!=null)
        {
            System.out.println("客户端请求下载的图片已存在于内存中!! 请求地址:"+task.getResPath());
            Bitmap bmp= ResourceCollection.getInstance().getImageContainer().get(task.getResPath());
            ((ImageView)task.getContentView()).setImageBitmap(bmp);
            return;
        }

        ((ImageView)task.getContentView()).setImageBitmap(_defaultBitmap);

        super.addTask(task);
    }

    public void setDefautltBitmap(ResourceCollection.ResourceName name)
    {
        _defaultBitmap=ResourceCollection.getInstance().getDefaultResource().
                getBitmapMap().get(String.valueOf(name));
    }

    public Bitmap getDefaultBitmap()
    {
        return _defaultBitmap;
    }
}
