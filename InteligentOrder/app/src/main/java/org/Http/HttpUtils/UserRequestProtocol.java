package org.Http.HttpUtils;


import android.os.Looper;

import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KerriGan on 2016/1/6.
 */
public class UserRequestProtocol extends ProtocolBase{

    public void isUserInServer(String id,String code,ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters("method","isUserInServer");
        this.addPostParameters("id",id);
        this.addPostParameters("code", code);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                JSONObject root=null;
                int code=-1;
                try {
                    if(result==null)
                        result="{}";
                    root=new JSONObject(result);
                    code=root.getInt(CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(result!=null || !result.equals("{}"))
                {
                    if(_callBack!=null)
                    {
                        _callBack.getCallBackCode(code
                        ,null);
                    }
                }
                else
                {
                    if(_callBack!=null)
                        _callBack.
                                getCallBackCode(ServerCallBack.NOT_SUCCESED
                                ,null);
                }

                Looper.loop();
            }
        }).start();
    }

    public void registerUser(String id,String code,ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters("method","registerUser");
        this.addPostParameters("id",id);
        this.addPostParameters("code", code);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                JSONObject root=null;
                int code=-1;
                try {
                    root=new JSONObject(result);
                    code=root.getInt(CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(result!=null || !result.equals("{}"))
                {
                    if(_callBack!=null)
                    {
                        _callBack.getCallBackCode(code
                                ,null);
                    }
                }
                else
                {
                    if(_callBack!=null)
                        _callBack.
                                getCallBackCode(ServerCallBack.NOT_SUCCESED
                                        ,null);
                }

                Looper.loop();
            }
        }).start();
    }

    public void getAllPayedItem(String id, final ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters("method","getAllPayedItem");
        this.addPostParameters("id",id);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                try {
                    JSONObject root=new JSONObject(result);
                    int code=root.getInt("code");
                    JSONArray array=root.getJSONArray("jsonArray");
                    ArrayList<OrderItemData> list=new ArrayList<OrderItemData>();
                    for(int i=0;i<array.length();i++)
                    {
                        OrderItemData data=new OrderItemData();
                        data.setID(array.getJSONObject(i).getString("id"));
                        data.setItemName(array.getJSONObject(i).getString("name"));
                        data.setItemDetail(array.getJSONObject(i).getString("detail"));
                        data.setStyle(array.getJSONObject(i).getString("style"));
                        data.setImagePath(array.getJSONObject(i).getString("img_path"));
                        data.setPrice(array.getJSONObject(i).getString("price"));
                        data.setRestaurant(array.getJSONObject(i).getString("restaurant"));
                        list.add(data);
                    }
                    callBack.getCallBackCode(code,list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    public void getAllCollectItem(String id, final ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters(METHOD,"getAllCollectItem");
        this.addPostParameters("id",id);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                try {
                    JSONObject root=new JSONObject(result);
                    int code=root.getInt("code");
                    JSONArray array=root.getJSONArray("jsonArray");
                    ArrayList<OrderItemData> list=new ArrayList<OrderItemData>();
                    for(int i=0;i<array.length();i++)
                    {
                        OrderItemData data=new OrderItemData();
                        data.setID(array.getJSONObject(i).getString("id"));
                        data.setItemName(array.getJSONObject(i).getString("name"));
                        data.setItemDetail(array.getJSONObject(i).getString("detail"));
                        data.setStyle(array.getJSONObject(i).getString("style"));
                        data.setImagePath(array.getJSONObject(i).getString("img_path"));
                        data.setPrice(array.getJSONObject(i).getString("price"));
                        data.setRestaurant(array.getJSONObject(i).getString("restaurant"));
                        list.add(data);
                    }
                    callBack.getCallBackCode(code,list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    public void getAllCartItem(String id, final ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters(METHOD,"getAllCartItem");
        this.addPostParameters("id",id);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                try {
                    JSONObject root=new JSONObject(result);
                    int code=root.getInt("code");
                    JSONArray array=root.getJSONArray("jsonArray");
                    ArrayList<OrderItemData> list=new ArrayList<OrderItemData>();
                    for(int i=0;i<array.length();i++)
                    {
                        OrderItemData data=new OrderItemData();
                        data.setID(array.getJSONObject(i).getString("id"));
                        data.setItemName(array.getJSONObject(i).getString("name"));
                        data.setItemDetail(array.getJSONObject(i).getString("detail"));
                        data.setStyle(array.getJSONObject(i).getString("style"));
                        data.setImagePath(array.getJSONObject(i).getString("img_path"));
                        data.setPrice(array.getJSONObject(i).getString("price"));
                        data.setRestaurant(array.getJSONObject(i).getString("restaurant"));
                        list.add(data);
                    }
                    callBack.getCallBackCode(code,list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    public void addItemToTablePay(String id,OrderItemData data, final ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters(METHOD,"addItemToTablePay");
        this.addPostParameters("id", id);

        JSONObject root=new JSONObject();
        try {
            root.put("name",data.getItemName());
            root.put("detail",data.getItemDetail());
            root.put("price",data.getPrice());
            root.put("style",data.getStyle());
            root.put("img_path",data.getImagePath());
            root.put("id",data.getID());
            root.put("restaurant",data.getRestaurant());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.addPostParameters("jsonOrderItemData",root.toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                try {
                    JSONObject root=new JSONObject(result);
                    int code=root.getInt("code");
                    callBack.getCallBackCode(code,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Looper.loop();
            }
        }).start();

    }

    public void addItemToTableCollect(String id,OrderItemData data, final ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters(METHOD,"addItemToTableCollect");
        this.addPostParameters("id",id);

        JSONObject root=new JSONObject();
        try {
            root.put("name",data.getItemName());
            root.put("detail",data.getItemDetail());
            root.put("price",data.getPrice());
            root.put("style",data.getStyle());
            root.put("img_path",data.getImagePath());
            root.put("id",data.getID());
            root.put("restaurant",data.getRestaurant());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.addPostParameters("jsonOrderItemData",root.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                try {
                    JSONObject root=new JSONObject(result);
                    int code=root.getInt("code");
                    callBack.getCallBackCode(code,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();

    }

    public void addItemToTableCart(String id,OrderItemData data, final ServerCallBack callBack)
    {
        _callBack=callBack;
        this.addPostParameters(METHOD,"addItemToTableCart");
        this.addPostParameters("id",id);
        JSONObject root=new JSONObject();
        try {
            root.put("name",data.getItemName());
            root.put("detail",data.getItemDetail());
            root.put("price",data.getPrice());
            root.put("style",data.getStyle());
            root.put("img_path",data.getImagePath());
            root.put("id",data.getID());
            root.put("restaurant",data.getRestaurant());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.addPostParameters("jsonOrderItemData",root.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result=UserRequestProtocol.this.request();
                try {
                    JSONObject root=new JSONObject(result);
                    int code=root.getInt("code");
                    callBack.getCallBackCode(code,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();

    }



    private ServerCallBack _callBack=null;
    public static final String CODE="code";
    public interface ServerCallBack
    {
        static final int NOT_SUCCESED=0;
        static final int NOT_FOUND_IN_SERVER=1;
        static final int SUCCESED =2;

        void getCallBackCode(int code,Object obj);
    }

}
