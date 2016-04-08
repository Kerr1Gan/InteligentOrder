package ReceiveRequest;

import java.io.File;  
import java.io.IOException;  
import java.io.PrintWriter;  
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Iterator;  
import java.util.List;  

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
 

import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileUploadException;  
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload;  

import com.sun.xml.xsom.impl.scd.Iterators.Map;

import DataBaseLibrary.DBManager;
  
public class FileUploadServlet extends HttpServlet {  
  
public FileUploadServlet() {  
   super();  
}  
  
public void destroy() {  
   super.destroy(); // Just puts "destroy" string in log  
   // Put your code here  
}  
  
public void doGet(HttpServletRequest request, HttpServletResponse response)  
    throws ServletException, IOException {  
   doPost(request, response);  
}  
  
private static final String METHOD="method";
//private UserBase _userData;
private final String _basePath="E:\\MyEclipseProgram\\InviteWebService\\WebRoot\\resource\\";


private HttpServletRequest _request=null;
private HttpServletResponse _response=null;

public void doPost(HttpServletRequest request, HttpServletResponse response)  
    throws ServletException, IOException {  

	_request=request;
	_response=response;
	request.setCharacterEncoding("UTF-8");
	
//	_userData=UserUtils.getInstance().getUserDataFromDataBase(
//			URLDecoder.decode(request.getHeader("userName"), "utf-8"));
	
	
	//用户请求记录
//	System.out.println(_userData.getUserName()+":请求上传图片!!");
	
	
   final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M  
   // 允许上传的文件格式的列表  
   final String[] allowedExt = new String[] { "jpg", "jpeg", "gif", "txt",  
     "doc", "docx", "mp3", "wma", "m4a","png" };  
   response.setContentType("text/html");  
   // 设置字符编码为UTF-8, 这样支持汉字显示  
   response.setCharacterEncoding("UTF-8");  
  
   // 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload  
   DiskFileItemFactory dfif = new DiskFileItemFactory();  
   dfif.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘  
   dfif.setRepository(new File(request.getSession().getServletContext().getRealPath("/")  
     + "ImagesUploadTemp"));// 设置存放临时文件的目录,web根目录下的ImagesUploadTemp目录  
  
   // 用以上工厂实例化上传组件  
   ServletFileUpload sfu = new ServletFileUpload(dfif);  
   // 设置最大上传尺寸  
   sfu.setSizeMax(MAX_SIZE);  
  
   PrintWriter out = response.getWriter();  
   // 从request得到 所有 上传域的列表  
   List fileList = null;  
   try {  
    fileList = sfu.parseRequest(request);  
   } catch (FileUploadException e) {// 处理文件尺寸过大异常  
    if (e instanceof SizeLimitExceededException) {  
     out.println("文件尺寸超过规定大小:" + MAX_SIZE + "字节<p />");  
     out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");  
     return;  
    }  
    e.printStackTrace();  
   }  
   // 没有文件上传  
   if (fileList == null || fileList.size() == 0) {  
    out.println("请选择上传文件<p />");  
    out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");  
    return;  
   }  
   // 得到所有上传的文件  
   Iterator fileItr = fileList.iterator();  
   // 循环处理所有文件  
   while (fileItr.hasNext()) {  
    FileItem fileItem = null;  
    String path = null;  
    long size = 0;  
    // 得到当前文件  
    fileItem = (FileItem) fileItr.next();  
    // 忽略简单form字段而不是上传域的文件域(<input type="text" />等)  
    if (fileItem == null || fileItem.isFormField()) {  
    	continue;  
    }  
    // 得到文件的完整路径  
    path = fileItem.getName();     
    // 得到文件的大小  
    size = fileItem.getSize();  
    if ("".equals(path) || size == 0) {  
     out.println("请选择上传文件<p />");  
     out.println("<a href=\"upload.html\" target=\"_top\">返回</a>");  
     return;  
    }  
  
    // 得到去除路径的文件名  
    String t_name = path.substring(path.lastIndexOf("\\") + 1);  
    // 得到文件的扩展名(无扩展名时将得到全名)  
    String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);  
    // 拒绝接受规定文件格式之外的文件类型  
    int allowFlag = 0;  
    int allowedExtCount = allowedExt.length;  
    for (; allowFlag < allowedExtCount; allowFlag++) {  
     if (allowedExt[allowFlag].equals(t_ext))  
      break;  
    }  
    if (allowFlag == allowedExtCount) {  
     out.println("请上传以下类型的文件<p />");  
     for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)  
      out.println("*." + allowedExt[allowFlag]  
        + "&nbsp;&nbsp;&nbsp;");  
     out.println("<p /><a href=\"upload.html\" target=\"_top\">返回</a>");  
     return;  
    }  
  
    long now = System.currentTimeMillis();  
    // 根据系统时间生成上传后保存的文件名  
    String prefix = String.valueOf(now);  
    // 保存的最终文件完整路径,保存在web根目录下的ImagesUploaded目录下  
    String u_name = request.getSession().getServletContext().getRealPath("/") + "ImagesUploadTemp/"  
      + prefix + "." + t_ext;  
    
    //self customize
//    String url=_basePath+_userData.getUserName()+"\\"; 存在bug导致不刷新eclipse就得不到文件
    String url=request.getSession().getServletContext().getRealPath("/")
    		+"Resource\\";
    String resPath=methodBranch(request);
    url+=resPath.substring(0,resPath.indexOf("\\"));
    File file=new File(url);
    if(!file.exists()&&!file.isDirectory())
    {
    	file.mkdir();
    }
//    url=url+prefix+"."+t_ext;
    url+=resPath.substring(resPath.indexOf("\\"));
    url=url+"."+t_ext;
    
    System.out.println("最终的路径"+url);
    
    //fix database data
//    String imgResource=_userData.getUserName()+"/"+prefix+"."+t_ext;
//    updateDataBaseData(imgResource);
    
    
    
	try {
				// 保存文件
		fileItem.write(new File(url));
		out.println("文件上传成功. 已保存为: " + prefix + "." + t_ext
				+ " &nbsp;&nbsp;文件大小: " + size + "字节<p />");
		out.println("<a href=\"upload.html\" target=\"_top\">继续上传</a>");
	} catch (Exception e) {
		e.printStackTrace();
	}
			// _isUserExsit=false;
			// _userData=null;
   }
   
   _request=null;
   _response=null;
  
}  

public void init() throws ServletException {  
   // Put your code here  
} 

public void updateDataBaseData(String url)
{
	Connection connection=new DBManager().getConnection();
	Statement stm=null;
	
	try {
		stm=connection.createStatement();
//		stm.execute("update user set img_resource='"+url+"' where name='"
//				+_userData.getUserName()+"'");
		
		stm.close();
		connection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

}

public String methodBranch(HttpServletRequest request)
{
	String methodName=null;
	try {
		methodName = URLDecoder.decode(request.getHeader(METHOD),"utf-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	String rootPath=null;
	
	if(methodName.equals("uploadHeadImage"))
	{
		try {
			String id=URLDecoder.decode(request.getHeader("id"), "utf-8");
			rootPath=new FileUploadMethods().UploadHeadImageByID(request);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	if(methodName.equals("uploadOrderItemImage"))
	{
		rootPath=new FileUploadMethods().uploadOrderItemImage(request);
	}
	
	

	
//	if(methodName.equals("getOrderItemImageByID"))
//	{
//		try {
//			String id=URLDecoder.decode(request.getHeader("userName"), "utf-8");
//			rootPath=new FileUploadMethods().
//					getOrderItemImageByID(request);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	if(methodName.equals("UploadHeadImageByID"))
//	{
//		try {
//			String id=URLDecoder.decode(request.getHeader("id"), "utf-8");
//			rootPath=new FileUploadMethods().UploadHeadImageByID(request);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	return rootPath;
}


}  
