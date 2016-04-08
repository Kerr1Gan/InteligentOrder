package ReceiveRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.components.Select;

import DataBaseLibrary.DBManager;

public class FileUploadMethods {
	
	public FileUploadMethods() {
		// TODO Auto-generated constructor stub
	}
	
	public String getOrderItemImageByID(HttpServletRequest request)
	{
		Connection connection=new DBManager().getConnection();
		String id=null;
		id=request.getParameter("id");
//		try {
//			id = URLDecoder.decode(request.getHeader("userName"), "utf-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		Statement stm=null;
		ResultSet rSet=null;
		String rootPath=null;
		try {
			stm=connection.createStatement();
			rSet=stm.executeQuery("select img_path from table_order_item where id='"
					+id+"'");
			
			while(rSet.next())
			{
				rootPath=rSet.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    String url=request.getSession().getServletContext().getRealPath("/")
		+"Resource\\"+"OrderItemImage"+"\\";
		
	    url+=rootPath;
	    
		return url;
	}
	
	public String UploadHeadImageByID(HttpServletRequest request)
	{
		String path=null;
		String id=null;
		try {
			id=URLDecoder.decode(request.getHeader("id"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(id!=null || !id.equals(""))
		{
			path=id+"\\"+"HeadImage";
		}
		
		
		return path;
	}
	
	public String uploadOrderItemImage(HttpServletRequest request)
	{
		String path=null;
		String id=null;
		String dataBasePath=null;
		try {
			id=URLDecoder.decode(request.getHeader("id"),"utf-8");
//			if(id!=null || !id.equals(""))
//			{
//				path=id+"\\";
//			}
			path="OrderItemImage\\";
			dataBasePath="OrderItemImage/";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection=new DBManager().getConnection();
		int time=(int) System.currentTimeMillis();
		path=path+time;
		dataBasePath+=time;
		dataBasePath+=".jpg";
		try {
			Statement stm=connection.createStatement();
			stm.execute("update table_order_item set img_path='"+dataBasePath+"'"
					+" where id='"+id+"'");
			connection.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return path;
	}

}
