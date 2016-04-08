package org.Http.HttpUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by KerriGan on 2015/7/22.
 */
public class HttpUtil {
    protected final static String _requestUrl="http://192.168.191.1:8080/IntelligentOrder/servlet/MainServlet";
    protected final static String _baseResourceUrl="http://192.168.191.1:8080/IntelligentOrder/Resource/";

//    protected final static String _requestUrl="http://xiangqian.wicp.net/InviteWebService/servlet/MainServlet";
//    protected final static String _baseResourceUrl="http://xiangqian.wicp.net/InviteWebService/Resource/";


    public static HttpGet getHttpGet(String url)
    {
        HttpGet request=new HttpGet(url);
        return request;
    }

    public static HttpPost getHttpPost(String url)
    {
        HttpPost request=new HttpPost(url);
        return  request;
    }

    public static HttpResponse getHttpResponse(HttpPost request) throws IOException {
        HttpParams httpParams=new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);//设置请求超时10秒
        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);//设置等待数据超时20秒
//        HttpConnectionParams.setSocketBufferSize(httpParams,8192);//设置缓冲区大小

        HttpClient httpClient=new DefaultHttpClient(httpParams);//设置连接属性
        HttpResponse response=httpClient.execute(request);

        return response;
    }

    public static String queryStringForPost(String url)
    {//FIXME
        HttpPost request=HttpUtil.getHttpPost(url);
        String result=null;
        try{
            HttpResponse response=HttpUtil.getHttpResponse(request);
            if(response.getStatusLine().getStatusCode()==200)
            {
                result= EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String queryStringForPost(HttpPost request,ArrayList<NameValuePair> param)
    {
//        HttpPost request=HttpUtil.getHttpPost(url);
        String result=null;
        HttpResponse response=null;
        try{
            System.out.println("客户端向服务器端请求数据:"+"  请求方法:"+param.get(0).getValue());
            response=HttpUtil.getHttpResponse(request);
            if(response.getStatusLine().getStatusCode()==200)
            {
                result= EntityUtils.toString(response.getEntity()/*,"UTF-8"*/);
                System.out.println("客户端向服务器端请求数据:"+"  请求方法:"+param.get(0).getValue()+" 已成功获取数据");
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response!=null)
        {
            System.out.println("客户端向服务器端请求数据:"+"  请求方法:"+param.get(0).getValue()+" 获取数据失败");
            System.out.println("ResponseStatus Code:"+response.getStatusLine().getStatusCode()+"  服务器错误");
        }
        else if(response==null)
        {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Looper.prepare();
//                    CMethod.popTextDialog(ApplicationData.getInstance().getAppCurrentContext()
//                            ,"服务器未开启"
//                            ,"服务器连接失败，联系qq674712964开启服务器！！");
//
//                    Toast.makeText(ApplicationData.getInstance().getAppCurrentContext(),
//                            "服务器连接失败，联系qq674712964开启服务器！！",Toast.LENGTH_LONG).show();
//                    Looper.loop();
//                }
//            }).start();


        }
        return null;
    }

    public static Bitmap getUrlImage(String url)
    {
        url=_baseResourceUrl+url;
        Bitmap img=null;
        System.out.println("客户端请求下载图片!! 请求地址:"+url);
        try {
            URL u=new URL(url);
            HttpURLConnection connection=(HttpURLConnection)u.openConnection();
            connection.setConnectTimeout(6 * 1000);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();
            InputStream is=connection.getInputStream();//获得图片数据流
            img= BitmapFactory.decodeStream(is);
            is.close();
//            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}


