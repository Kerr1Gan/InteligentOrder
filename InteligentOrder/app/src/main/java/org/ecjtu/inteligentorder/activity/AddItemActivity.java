package org.ecjtu.inteligentorder.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.Http.HttpUtils.OrderItemRequestProtocol;
import org.Http.HttpUtils.UploadImageProtocol;
import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.customui.ModifyImageDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class AddItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        init();
    }

    private EditText _editTextName;
    private EditText _editTextDetail;
    private EditText _editTextPrice;
    private Button _buttonCommit;
    private Button _buttonExit;
    private ImageView _imageView;
    public void init() {
        _editTextName = (EditText) this.findViewById(R.id.edit_text_name);
        _editTextDetail = (EditText) this.findViewById(R.id.edit_text_detail);
        _editTextPrice = (EditText) this.findViewById(R.id.edit_text_price);
        _imageView= (ImageView) findViewById(R.id.image_view);

        _buttonCommit = (Button) this.findViewById(R.id.button_commit);
        _buttonExit = (Button) this.findViewById(R.id.button_exit);

        _buttonCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderItemData data = new OrderItemData();
                data.setItemDetail(_editTextDetail.getText().toString());
                data.setItemName(_editTextName.getText().toString());
                data.setPrice(_editTextPrice.getText().toString());
                data.setStyle("");
                String id = String.valueOf(
                        new Random(System.currentTimeMillis()).nextInt(100000));
                data.setID(id);
                data.setImagePath(_imagePath);

                new UploadImageProtocol().uploadOrderItemImage(
                        new File(_imagePath),id);

                if (data.getID() == "" || data.getID() == null
                        || data.getItemName() == "" || data.getItemName() == null)
                    return;
                new OrderItemRequestProtocol().addItemDataToServer(
                        data, new OrderItemRequestProtocol.OrderItemRequestCallBack() {
                            @Override
                            public void callBack(int code, ArrayList<OrderItemData>
                                    data) {
                                if (code ==
                                        OrderItemRequestProtocol.
                                                OrderItemRequestCallBack.FOUND_IN_SERVER) {
                                    Toast.makeText(AddItemActivity.this,
                                            "添加成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(AddItemActivity.this,
                                            "添加失败", Toast.LENGTH_LONG).show();
                                }
                                AddItemActivity.this.finish();
                            }
                        }
                );
            }
        });

        _buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemActivity.this.finish();
            }
        });

        _modifyImageButton= (Button) findViewById(R.id.button_modify_head);
        _modifyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _modifyImageDialog=new ModifyImageDialog(AddItemActivity.this);
                _modifyImageDialog.show();

                _modifyImageDialog.setImageCallBack(new ModifyImageDialog.
                        GetImageCallBack() {
                    @Override
                    public void ImageFromWareHouseCallBack(Bitmap big, String path) {

                        _imagePath=path;
                        _imageView.setImageBitmap(big);
                    }

                    @Override
                    public void ImageFromCameraCallBack(Bitmap small, Bitmap big, String path) {
                        _imagePath=path;
                        _imageView.setImageBitmap(big);
                    }
                });
            }
        });
    }

    private ModifyImageDialog _modifyImageDialog;
    private Button _modifyImageButton;
    private String _imagePath;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _modifyImageDialog.onActivityResult(requestCode,resultCode,data);
    }
}
