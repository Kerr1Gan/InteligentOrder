package org.Http.HttpUtils;

import org.apache.http.client.methods.HttpPost;
import org.ecjtu.inteligentorder.AppInstance;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by KerriGan on 2015/8/22.
 */
public class UploadImageProtocol extends ProtocolBase
{
    private File _imgFile;
    private String _resPath;
    public UploadImageProtocol()
    {
        _postRequest=new HttpPost(UploadUtil._imgRequestUrl);
        _postParameters=new ArrayList<>();
    }

    public void uploadImage(File file)
    {
        _imgFile=file;
//        this.addPostParameters(METHOD,"uploadImage");
//        this.addPostParameters("userName", ApplicationData.getInstance().getUser().getUserName());
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String result=UploadImageProtocol.this.request();
//                UploadUtil.uploadFile(_imgFile,UploadUtil._imgRequestUrl,
//                        ApplicationData.getInstance().getUser().getUserName());
            }
        }).start();
    }

    public void uploadHeadImage(final File file)
    {
        _imgFile=file;

        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadUtil.uploadFile(file,UploadUtil._imgRequestUrl,
                        AppInstance.getInstance().getID(),
                        "uploadHeadImage",null);
            }
        }).start();
    }

    public void uploadOrderItemImage(final File file, final String id)
    {
        _imgFile=file;
        new Thread(new Runnable() {
            @Override
            public void run() {
                UploadUtil.uploadFile(file,UploadUtil._imgRequestUrl,
                        id,
                        "uploadOrderItemImage",null);
            }
        }).start();
    }

    public void uploadImage(File file, final String userName)
    {
        _imgFile=file;
//        this.addPostParameters(METHOD,"uploadImage");
//        this.addPostParameters("userName", userName);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String result=UploadImageProtocol.this.request();
                UploadUtil.uploadFile(_imgFile,UploadUtil._imgRequestUrl,
                        userName,
                        "uploadImage",null);
            }
        }).start();
    }

    public void setResPath(String path)
    {
        _resPath= path;
    }
    public String getResPath()
    {
        return _resPath;
    }
}
