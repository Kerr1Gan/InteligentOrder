package org.Http.HttpUtils;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by KerriGan on 2015/8/22.
 */
public class UploadUtil {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000;
    private static String CHARSET = "utf-8";
    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
    public static final String METHOD="method";

    protected static final String _imgRequestUrl="http://192.168.191.1:8080/IntelligentOrder/servlet/FileUploadServlet";

//    protected static final String _imgRequestUrl="http://xiangqian.wicp.net/InviteWebService/servlet/FileUploadServlet";

    public static boolean uploadFile(File file, String requestUrl,String id,String
                                      methodName,String[][] values)
    {
        System.out.println("请求上传图片:"+file.getAbsolutePath());
        String BOUNDARY= UUID.randomUUID().toString();
        String PREFIX="--",LINE_END="\r\n";
        String CONTENT_TYPE="multipart/form-data";

        try {
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();

            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            conn.setRequestProperty("id", URLEncoder.encode(id,"utf-8"));
            conn.setRequestProperty(METHOD,URLEncoder.encode(methodName,"utf-8"));
            if(values!=null)
            {
                for(int i=0;i<values.length;i++)
                {
                    conn.setRequestProperty(values[i][0],values[i][1]);
                }
            }



            if(file!=null)
            {
                OutputStream outputStream=conn.getOutputStream();
                DataOutputStream dos=new DataOutputStream(outputStream);
                StringBuffer sb=new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);

                sb.append("Content-Disposition: form-data;name=\"img\"; filename=\""+file.getName()+
                "\""+LINE_END);
                sb.append("Content-Type: application/octet-stream;charset="+CHARSET+LINE_END);
                sb.append(LINE_END);

                dos.write(sb.toString().getBytes());
                InputStream is=new FileInputStream(file);
                byte[] bytes=new byte[1024];
                int len=0;
                while ((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes,0,len);
                }

                is.close();
                dos.write(LINE_END.getBytes());
                byte[] endData=(PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(endData);
                dos.flush();

                int res=conn.getResponseCode();
                if(res==200)
                {
                    System.out.println("上传图片成功:"+file.getAbsolutePath());
                    return SUCCESS==1?true:false;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FAILURE==0?false:true;
    }

}
