package org.AsyncTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import org.ecjtu.inteligentorder.R;

import java.util.HashMap;

/**
 * Created by KerriGan on 2015/8/4.
 */
public class ResourceCollection {

    private static ResourceCollection _instance=null;
    private static ImageContainer _imageContainer=null;
    private static DefaultResource _defautResource=null;
    private static Context _context;
    private ResourceCollection()
    {
//        initContext(_context);
//        _defautResource=new DefaultResource();
    }

//    public static ResourceCollection getInstance(Context context)
//    {
//        if(_context==null)
//            _context=context;
//
//        if(_instance==null)
//            _instance=new ResourceCollection();
//        if(_imageContainer==null)
//            _imageContainer=ImageContainer.getInstance();
//
//
//        return _instance;
//    }
    public static void initialize(Context context)
    {
        _context =context;
    }

    public static ResourceCollection getInstance()
    {
        if(_instance==null)
            _instance=new ResourceCollection();
        if(_imageContainer==null)
            _imageContainer=ImageContainer.getInstance();


        return _instance;
    }

    public void initContext(Context context)
    {
        _context=context;
    }

    public ImageContainer getImageContainer()
    {
        return _imageContainer;
    }

    public DefaultResource getDefaultResource()
    {
        if(_defautResource==null)
            _defautResource=new DefaultResource();
        return _defautResource;
    }

    public class DefaultResource
    {
        private HashMap<String,Bitmap> _bitmapMap;

        private DefaultResource()
        {
           initBitmapMap();
        }

        public HashMap<String,Bitmap> getBitmapMap()
        {
            return _bitmapMap;
        }


        public void initBitmapMap()
        {
            _bitmapMap = new HashMap<>();
            Bitmap bmp = BitmapFactory.decodeResource(_context.getResources(), R.drawable.default_img);
            _bitmapMap.put(String.valueOf(ResourceName.BITMAP_DEFAULT_IMAGE), bmp);
        };
    }

    public enum ResourceName
    {
        BITMAP_DEFAULT_IMAGE
    }

}
