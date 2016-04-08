package org.ecjtu.inteligentorder.customfragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.AsyncTask.AsyncImageTask;
import org.AsyncTask.ResourceCollection;
import org.AsyncTask.Task;
import org.Http.HttpUtils.OrderItemRequestProtocol;
import org.Http.HttpUtils.UserRequestProtocol;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.activity.OrderActivity;
import org.ecjtu.inteligentorder.customui.BottomMenu;
import org.ecjtu.inteligentorder.customui.CustomOrderItem;
import org.ecjtu.inteligentorder.customui.TopMenu;

import java.util.ArrayList;

/**
 * Created by KerriGan on 2016/1/5.
 */
public class MainMenuFragment extends Fragment{
    private ViewGroup _rootView;
    private ViewGroup _scrollViewContainer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        _rootView= (ViewGroup) inflater.inflate
                (R.layout.fragment_main_menu,container,false);

        TopMenu t= (TopMenu)_rootView.findViewById(R.id.top_title);
        t.setOnItemClickListener(new TopMenu.onItemClick() {
            @Override
            public void onClick(View v) {
                Log.i("msg", v.toString());
            }
        });

        _scrollViewContainer= (ViewGroup) _rootView.findViewById
                (R.id.advance_scroll_view_container);

        ResourceCollection.getInstance().initContext(this.getActivity());

        new OrderItemRequestProtocol().getAllItemData("", new
                OrderItemRequestProtocol.OrderItemRequestCallBack() {
                    @Override
                    public void callBack(int code, ArrayList<OrderItemData> data) {
                        if(code== OrderItemRequestProtocol.
                                OrderItemRequestCallBack.FOUND_IN_SERVER)
                        {
                            AppInstance.getInstance()._orderItemList=data;
                            synchronized (MainMenuFragment.this)
                            {
                                MainMenuFragment.this._loadedHandler.sendMessage(
                                        new Message()
                                );
                            }
                        }
                    }
                });

        _loadedHandler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                AsyncImageTask task=new AsyncImageTask();
                for(OrderItemData data:AppInstance.getInstance()._orderItemList)
                {
                    final CustomOrderItem item=new CustomOrderItem(MainMenuFragment.this
                            .getActivity());
                    _scrollViewContainer.addView(item);
                    item.setOrderItem(data);
                    Task obj=new Task(item.getImageView());
                    obj.setResPath(data.getImagePath());
                    task.addTask(obj);

                    item.setOnItemClickListener(new CustomOrderItem.
                            onItemClickListener() {
                        @Override
                        public void onClick(int index) {
                            if(index==0)
                            {
                                Intent i=new Intent(MainMenuFragment.this.getActivity()
                                        , OrderActivity.class);
                                i.putExtra("objID",item.getOrderItem().getID());

                                MainMenuFragment.this.startActivity(i);
                            }
                            else if(index==1)
                            {
                                new UserRequestProtocol().addItemToTableCart(
                                        AppInstance.getInstance().getID(),
                                        item.getOrderItem(),
                                        new UserRequestProtocol.ServerCallBack() {
                                            @Override
                                            public void getCallBackCode(int code, Object obj) {
                                                System.out.println("添加数据到"
                                                +"Cart成功!");
                                            }
                                        }
                                );
                            }
                            else if(index==2)
                            {
                                new UserRequestProtocol().addItemToTableCollect(
                                        AppInstance.getInstance().getID(),
                                        item.getOrderItem(),
                                        new UserRequestProtocol.ServerCallBack() {
                                            @Override
                                            public void getCallBackCode(int code, Object obj) {
                                                System.out.println("添加数据到"
                                                        +"Collect成功!");
                                            }
                                        }
                                );
                            }

                        }
                    });
                }


            }
        };

        return _rootView;
    }

    private Handler _loadedHandler;

}
