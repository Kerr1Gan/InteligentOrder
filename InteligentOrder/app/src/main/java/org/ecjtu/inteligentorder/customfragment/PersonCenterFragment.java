package org.ecjtu.inteligentorder.customfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.AsyncTask.AsyncImageTask;
import org.AsyncTask.ResourceCollection;
import org.AsyncTask.Task;
import org.Http.HttpUtils.UploadImageProtocol;
import org.Http.HttpUtils.UserRequestProtocol;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.activity.OrderItemHistoryActivity;
import org.ecjtu.inteligentorder.customui.ModifyImageDialog;

import java.io.File;

/**
 * Created by KerriGan on 2016/1/7.
 */
public class PersonCenterFragment extends Fragment{
    private ViewGroup _rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView= (ViewGroup) inflater.
                inflate(R.layout.fragment_person_center,container,false);

        init();
        return _rootView;
    }

    private Button _buttonCollect;
    private Button _buttonCart;
    private Button _buttonPay;
    private Button _buttonModifyHead;
    private ModifyImageDialog _modifyImageDialog;

    public void init() {


        _buttonModifyHead= (Button) _rootView.findViewById(R.id.button_modify_head);


        _buttonModifyHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyImageDialog dialog=new ModifyImageDialog(
                        PersonCenterFragment.this.getActivity());
                _modifyImageDialog=dialog;
                PersonCenterFragment.this._modifyImageDialog.show();

                PersonCenterFragment.this._modifyImageDialog.setImageCallBack(
                        new ModifyImageDialog.GetImageCallBack() {
                            public void ImageFromWareHouseCallBack(Bitmap big, String path) {
                                ((ImageView) _rootView.findViewById(R.id.image_view_head_logo
                                )).setImageBitmap(big);
//                                Toast.makeText(PersonCenterFragment.this.getActivity()
//                                        , "bmp:" + big.toString(), Toast.LENGTH_LONG).show();

                                ((ImageView) _rootView.findViewById(R.id.image_view_head_logo
                                )).setImageBitmap(big);
                                AppInstance.getInstance().getMainActivityActionBar()
                                        .updateHeadImage(big);
                                new UploadImageProtocol().uploadHeadImage
                                        (new File(path));
                                ResourceCollection.getInstance().getImageContainer()
                                        .remove(AppInstance.getInstance().getID()+"/HeadImage.jpg");
                            }

                            @Override
                            public void ImageFromCameraCallBack(Bitmap small, Bitmap big, String path) {
//
                                ((ImageView) _rootView.findViewById(R.id.image_view_head_logo
                                )).setImageBitmap(big);
                                AppInstance.getInstance().getMainActivityActionBar()
                                        .updateHeadImage(big);
                                new UploadImageProtocol().uploadHeadImage
                                        (new File(path));
                                ResourceCollection.getInstance().getImageContainer()
                                        .remove(AppInstance.getInstance().getID()
                                                + "/HeadImage.jpg");
                            }
                        }
                );
            }
        });

        ImageView img= (ImageView) _rootView.findViewById(
                R.id.image_view_head_logo);

        AsyncImageTask task=new AsyncImageTask();
        Task obj=new Task(img);
        obj.setResPath(AppInstance.getInstance().getID()+"/HeadImage.jpg");
        task.addTask(obj);


        _buttonCart= (Button) _rootView.findViewById(R.id.button_search_cart);
        _buttonCollect= (Button) _rootView.findViewById(R.id.button_collect);
        _buttonPay= (Button) _rootView.findViewById(R.id.button_pay);

        _buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PersonCenterFragment.this.getActivity(),
                        OrderItemHistoryActivity.class);
                i.putExtra("model", OrderItemHistoryActivity.PAY_ACTIVITY);
                PersonCenterFragment.this.startActivity(i);
            }
        });

        _buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PersonCenterFragment.this.getActivity(),
                        OrderItemHistoryActivity.class);
                i.putExtra("model", OrderItemHistoryActivity.CART_ACTIVITY);
                PersonCenterFragment.this.startActivity(i);
            }
        });

        _buttonCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PersonCenterFragment.this.getActivity(),
                        OrderItemHistoryActivity.class);
                i.putExtra("model", OrderItemHistoryActivity.COLLECT_ACTIVITY);
                PersonCenterFragment.this.startActivity(i);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _modifyImageDialog.onActivityResult(requestCode,resultCode,
                data);
    }



}
