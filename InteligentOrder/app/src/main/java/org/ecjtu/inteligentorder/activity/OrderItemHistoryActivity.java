package org.ecjtu.inteligentorder.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ScrollView;

import org.AsyncTask.AsyncImageTask;
import org.AsyncTask.Task;
import org.Http.HttpUtils.UserRequestProtocol;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.customui.CustomOrderItem;

import java.util.ArrayList;

public class OrderItemHistoryActivity extends AppCompatActivity {
    public static final int COLLECT_ACTIVITY=0;
    public static final int PAY_ACTIVITY=1;
    public static final int CART_ACTIVITY=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_history);

        Bundle b=this.getIntent().getExtras();


        _scrollViewContent= (ViewGroup) findViewById(R.id.scroll_view_content);

        _uiHandler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                for(OrderItemData data:_itemList)
                {
                    CustomOrderItem item=new CustomOrderItem(
                            OrderItemHistoryActivity.this);
                    _scrollViewContent.addView(item);
                    item.setOrderItem(data);

                    AsyncImageTask task=new AsyncImageTask();
                    Task obj=new Task(item.getImageView());
                    obj.setResPath(data.getImagePath());
                    task.addTask(obj);
                }
            }
        };


        int model=b.getInt("model");

        switch (model)
        {
            case COLLECT_ACTIVITY:
                new UserRequestProtocol().getAllCollectItem(
                        AppInstance.getInstance().getID(),
                        new UserRequestProtocol.ServerCallBack() {
                            @Override
                            public void getCallBackCode(int code, Object obj) {
                                _itemList= (ArrayList<OrderItemData>) obj;
                                _uiHandler.sendMessage(new Message());
                            }
                        }
                );
                break;
            case PAY_ACTIVITY:
                new UserRequestProtocol().getAllPayedItem(
                        AppInstance.getInstance().getID(),
                        new UserRequestProtocol.ServerCallBack() {
                            @Override
                            public void getCallBackCode(int code, Object obj) {
                                _itemList= (ArrayList<OrderItemData>) obj;
                                _uiHandler.sendMessage(new Message());
                            }
                        }
                );
                break;
            case CART_ACTIVITY:
                new UserRequestProtocol().getAllCartItem(
                        AppInstance.getInstance().getID(),
                        new UserRequestProtocol.ServerCallBack() {
                            @Override
                            public void getCallBackCode(int code, Object obj) {
                                _itemList= (ArrayList<OrderItemData>) obj;
                                _uiHandler.sendMessage(new Message());
                            }
                        }
                );
                break;
        }
    }

    private ArrayList<OrderItemData> _itemList;
    private ViewGroup _scrollViewContent;
    private Handler _uiHandler;
}
