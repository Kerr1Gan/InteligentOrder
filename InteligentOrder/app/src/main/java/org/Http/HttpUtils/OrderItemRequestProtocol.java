package org.Http.HttpUtils;

import android.os.Looper;

import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KerriGan on 2016/1/7.
 */
public class OrderItemRequestProtocol extends ProtocolBase {

    public void getAllItemData(String style, final OrderItemRequestCallBack callBack) {
        this.addPostParameters(METHOD, "getAllItemData");
        this.addPostParameters("style", style);
        final ArrayList<OrderItemData> resultList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result = OrderItemRequestProtocol.this.request();

                try {
                    JSONObject root = new JSONObject(result);
                    JSONArray array = root.getJSONArray("jsonArray");
                    int code=root.getInt("code");
                    for (int i = 0; i < array.length(); i++) {
                        OrderItemData data = new OrderItemData();
                        data.setPrice(array.getJSONObject(i).getString("price"));
                        data.setStyle(array.getJSONObject(i).getString("style"));
                        data.setID(array.getJSONObject(i).getString("id"));
                        data.setItemName(array.getJSONObject(i).getString("name"));
                        data.setItemDetail(array.getJSONObject(i).getString("detail"));
                        data.setImagePath(array.getJSONObject(i).getString("img_path"));
                        data.setRestaurant(array.getJSONObject(i).getString("restaurant"));
                        resultList.add(data);
                    }
                    callBack.callBack(code, resultList);
                    Looper.loop();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void addItemDataToServer(OrderItemData data, final OrderItemRequestCallBack
            callBack) {
        this.addPostParameters(METHOD, "addItemDataToServer");

        JSONObject root = new JSONObject();
        try {
            root.put("id", data.getID());
            root.put("name", data.getItemName());
            root.put("detail", data.getItemDetail());
            root.put("price", data.getPrice());
            root.put("style", data.getStyle());
            root.put("img_path",data.getImagePath());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.addPostParameters("jsonOrderItemData", root.toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String result = OrderItemRequestProtocol.this.request();
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.getInt("code");
                    callBack.callBack(code, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    public void getOrderItemsByRestaurant(String id,String restaurant,
                                          OrderItemRequestCallBack callBack)
    {
        //// FIXME: 2016/1/12
    }

    public interface OrderItemRequestCallBack {
        static final int NOT_SUCCESED = 0;
        static final int NOT_FOUND_IN_SERVER = 1;
        static final int FOUND_IN_SERVER = 2;

        void callBack(int code, ArrayList<OrderItemData> data);
    }


}
