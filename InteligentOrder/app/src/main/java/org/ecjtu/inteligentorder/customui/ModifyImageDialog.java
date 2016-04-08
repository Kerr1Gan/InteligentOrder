package org.ecjtu.inteligentorder.customui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.Http.HttpUtils.UploadImageProtocol;
import org.ecjtu.inteligentorder.R;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by KerriGan on 2016/1/9.
 */
public class ModifyImageDialog extends CustomDialogBase{


    public ModifyImageDialog(Context context) {
        super(context);
        init();
    }

    protected void init()
    {
        super.init();

        ViewGroup root= (ViewGroup) LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_modify_image
                        , null);
        this.setView(root);
        this.setTitle("修改头像");

        _buttonSelectFromWareHouse= (Button) root.findViewById(
                R.id.button_select_from_warehouse);
        _buttonSelectFromCamera= (Button) root.findViewById(
                R.id.button_select_from_camera);

        _buttonSelectFromWareHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                ((Activity) ModifyImageDialog.this.getContext()).
                        startActivityForResult(intent, 1);

            }
        });

        _buttonSelectFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                _uri=cacheFileByUri();

                intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,_uri);

                ((Activity)ModifyImageDialog.this.getContext())
                        .startActivityForResult(intent, 2);

            }
        });
    }

    private GetImageCallBack _callBack;
    private Uri _uri;
    public void setImageCallBack(GetImageCallBack callBack)
    {
        _callBack=callBack;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        this.disMiss();
        if (resultCode == Activity.RESULT_OK&&requestCode==1) {
//            this.disMiss();
            Uri uri = data.getData();
            Log.e("URITAG", "Uri=" + uri);
            try {
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContext()
                        .getContentResolver().query(uri, pojo, null, null,null);
                if (cursor != null) {
                    ContentResolver cr = this.getContext()
                            .getContentResolver();
                    int colunmIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunmIndex);
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        File file=new File(path);
                        FileInputStream iStream=new FileInputStream(file);
                        int size=iStream.available();
                        BitmapFactory.Options opt=new BitmapFactory.Options();
                        opt.inSampleSize=2;
                        Bitmap bmp=null;
                        if(size>=1024*1024)
                        {
                            bmp=BitmapFactory.decodeStream(cr.openInputStream(
                                    uri),null,opt);
                        }
                        else {
                            bmp = BitmapFactory.
                                    decodeStream(cr.openInputStream(uri));
                        }

                        if(_callBack!=null)
                        {
                            _callBack.ImageFromWareHouseCallBack(bmp,
                                    path);
                        }

                    }
                }
            } catch (Exception e) {

            }
        }
        if(resultCode==Activity.RESULT_OK&&requestCode==2)
        {
//            Bundle bundle=data.getExtras();
//            this.disMiss();
//            Bitmap small=data.getParcelableExtra("data");
//            Bitmap bundlBmp= (Bitmap) bundle.get("data");

            if(_callBack!=null)
            {

                BitmapFactory.Options opts=new BitmapFactory.Options();
//                opts.inJustDecodeBounds=true;
//                Bitmap big=BitmapFactory.decodeFile(_uri.getPath());
                opts.inSampleSize=2;
                Bitmap big=BitmapFactory.decodeFile(_uri.getPath(),opts);

                opts.inSampleSize=4;
                Bitmap small=BitmapFactory.decodeFile(_uri.getPath(),opts);
//                if(big.getWidth()<big.getHeight())
//                {
//                    Matrix m=new Matrix();
//                    m.setRotate(90,big.getWidth()/2,big.getHeight()/2);
//                    big=Bitmap.createBitmap(big,0,0,big.getWidth(),big.getHeight(),m
//                            , true);
//                }
                _callBack.ImageFromCameraCallBack(small,big,
                        _uri.getPath());

                return;
            }
        }
    }

    public static Uri cacheFileByUri()
    {
        String tempPath= Environment.getExternalStorageDirectory().toString()
                +"/IntelligentOrder";
        File tFile=new File(tempPath);
        if(!tFile.exists() || !tFile.isDirectory())
            tFile.mkdir();

        File file=new File(tFile,System.currentTimeMillis()+".jpg");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri u=Uri.fromFile(file);

        return u;
    }

    public interface GetImageCallBack
    {
        void ImageFromWareHouseCallBack(Bitmap big,String path);
        void ImageFromCameraCallBack(Bitmap small,Bitmap big,String path);
    }

    private Button _buttonSelectFromWareHouse;

    private Button _buttonSelectFromCamera;
}
