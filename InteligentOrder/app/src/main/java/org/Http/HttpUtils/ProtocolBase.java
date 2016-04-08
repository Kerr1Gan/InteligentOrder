package org.Http.HttpUtils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by KerriGan on 2015/7/23.
 */
public class ProtocolBase {
//    protected final String _url="http://192.168.191.1:8080/InviteWebService/servlet/MainServlet";
    protected HttpPost _postRequest=null;
    protected ArrayList<NameValuePair> _postParameters=null;
    protected String _responseResult;

    protected final String METHOD="method";

    public ProtocolBase()
    {
        _postRequest=new HttpPost(HttpUtil._requestUrl);
        _postParameters=new ArrayList<>();
    }

    public void addPostParameters(String name,String value)
    {
        _postParameters.add(new BasicNameValuePair(name,value));
    }

    /**
     * return server sends data
     * */
    public String request()
    {
        try {
            _postRequest.setEntity(new UrlEncodedFormEntity(_postParameters, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        _responseResult=HttpUtil.queryStringForPost(_postRequest,_postParameters);
        return _responseResult;
    }

    public void resetParameter()
    {
        _postParameters.clear();
    }

}
