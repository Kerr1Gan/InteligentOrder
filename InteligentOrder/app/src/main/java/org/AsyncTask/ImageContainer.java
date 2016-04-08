package org.AsyncTask;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by KerriGan on 2015/8/4.
 */
public class ImageContainer {
    private HashMap<String, SoftReference<Bitmap>> _map;
    private static ImageContainer _instance;

    private ImageContainer() {
        _map = new HashMap<>();
    }

    public static ImageContainer getInstance() {
        if (_instance == null)
            _instance = new ImageContainer();
        return _instance;
    }

    public HashMap<String, SoftReference<Bitmap>> getContainer() {
        return _map;
    }

    public void add(String resPath, Bitmap bmp) {
        if (isContainsInContainer(resPath))
        {
            bmp.recycle();
            return;
        }

        _map.put(resPath, new SoftReference<Bitmap>(bmp));
    }

    public Bitmap get(String resPath) {
        if(_map.get(resPath)==null || _map.get(resPath).get()==null)
            return null;

        Bitmap bmp = _map.get(resPath).get();
        return bmp;
    }

    public Bitmap remove(String resPath) {
        return _map.remove(resPath).get();
    }

    public boolean isContainsInContainer(String resPath) {
        boolean flag = false;
        for (int i = 0; i < _map.size(); i++) {
            if (_map.get(resPath) != null && _map.get(resPath).get()!=null)
                flag = true;
        }
        return flag;
    }
}
