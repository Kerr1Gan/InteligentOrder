package ReceiveRequest;

import java.awt.Robot;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;

import com.mysql.fabric.Response;
import com.sun.xml.bind.v2.model.core.ID;

import DataBaseLibrary.DBManager;
import DataStruct.OrderItemData;

public class NameForMethod {
	public static final String METHOD = "method";
	public static final String CODE = "code";

	public static PrintWriter _writer;
	public static HttpServletResponse _response;

	public static synchronized String doMethodByName(
			HttpServletRequest request, HttpServletResponse res) {
		String methodName = request.getParameter(METHOD);
		try {
			_writer = res.getWriter();
			_response = res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (methodName.equals("isUserInServer")) {
			isUserInServer(request.getParameter("id"),
					request.getParameter("code"));
		}
		if (methodName.equals("registerUser")) {
			registerUser(request.getParameter("id"),
					request.getParameter("code"));
		}
		if (methodName.equals("addItemDataToServer")) {
			String str = request.getParameter("jsonOrderItemData");
			OrderItemData data = new OrderItemData();
			try {
				JSONObject root = new JSONObject(str);
				data.setID(root.getString("id"));
				data.setItemDetail(root.getString("detail"));
				data.setItemName(root.getString("name"));
				data.setPrice(root.getString("price"));
				data.setStyle(root.getString("style"));
				data.setImagePath(root.getString("img_path"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			addItemDataToServer(data);
		}
		if (methodName.equals("getAllItemData")) {
			getAllItemData(request.getParameter("style"));
		}
		if (methodName.equals("getOrderItemImageByID")) {
			String result = new FileUploadMethods()
					.getOrderItemImageByID(request);
			_writer.write(result);
		}
		if (methodName.equals("getAllPayedItem")) {
			getAllPayedItem(request.getParameter("id"));
		}
		if (methodName.equals("getAllCollectItem")) {
			getAllCollectItem(request.getParameter("id"));
		}
		if (methodName.equals("getAllCartItem")) {
			getAllCartItem(request.getParameter("id"));
		}
		
		if(methodName.equals("addItemToTablePay"))
		{
			int code=addItemToTablePay(request.getParameter("id"), request);
		}
		
		if(methodName.equals("addItemToTableCart"))
		{
			int code=addItemToTableCart(request.getParameter("id"), request);
		}
		
		if(methodName.equals("addItemToTableCollect"))
		{
			int code=addItemToTableCollect(request.getParameter("id"), request);
		}
			

		_writer = null;
		_response = null;

		return null;
	}

	public static int isUserInServer(String id, String code) {
		Connection connection = new DBManager().getConnection();
		int result = NOT_SUCCESED;

		JSONObject rootJson = new JSONObject();

		try {
			Statement stm = connection.createStatement();

			ResultSet rSet = stm.executeQuery("select * from table_user "
					+ "where id='" + id + "'");

			if (!rSet.next())
				result = NOT_FOUND_IN_SERVER;
			else {
				rSet.previous();
			}
			while (rSet.next()) {
				if (rSet.getString(2).equals(code)) {
					result = SUCCESED;
				} else {
					result = NOT_FOUND_IN_SERVER;
				}

			}

			try {
				rootJson.put(CODE, result);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			connection.close();
			rSet.close();
			stm.close();
			_writer.write(rootJson.toString());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static int registerUser(String id, String code) {
		Connection connection = new DBManager().getConnection();
		Statement stm = null;
		int result = NOT_SUCCESED;
		try {
			stm = connection.createStatement();
			stm.execute("insert into table_user(id,code)" + " values('" + id
					+ "','" + code + "')");

			result = SUCCESED;
			connection.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = NOT_SUCCESED;
		}

		JSONObject root = new JSONObject();
		try {
			root.put(CODE, result);
			_writer.write(root.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static int addItemDataToServer(OrderItemData data) {
		Connection connection = new DBManager().getConnection();
		Statement stm = null;
		int code = NOT_SUCCESED;
		try {
			stm = connection.createStatement();
			stm.execute("insert into table_order_item(id,detail,price,style,name,img_path) "
					+ "values('"
					+ data.getID()
					+ "','"
					+ data.getItemDetail()
					+ "','"
					+ data.getPrice()
					+ "','"
					+ data.getStyle()
					+ "','"
					+ data.getItemName() + "','"
					+""+"')");
			code = SUCCESED;
			connection.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code = NOT_SUCCESED;
		}

		JSONObject root = new JSONObject();
		try {
			root.put(CODE, code);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_writer.write(root.toString());

		return 0;
	}

	public static int getAllItemData(String style) {
		Connection connection = new DBManager().getConnection();
		Statement stm = null;
		ResultSet rSet = null;
		JSONObject root = new JSONObject();
		JSONArray array = new JSONArray();
		int code = NOT_SUCCESED;
	
		if (style == null || style.equals("")) {
			try {
				stm = connection.createStatement();
				rSet = stm.executeQuery("select * from table_order_item");
				while (rSet.next()) {
					JSONObject json = new JSONObject();
					try {
						json.put("id", rSet.getString(1));
						json.put("detail", rSet.getString(2));
						json.put("price", rSet.getString(3));
						json.put("style", rSet.getString(4));
						json.put("name", rSet.getString(5));
						json.put("img_path", rSet.getString(6));
						json.put("restaurant", rSet.getString(7));
						array.put(json);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				code = SUCCESED;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				code = NOT_FOUND_IN_SERVER;
			}
			try {
				root.put("jsonArray", array);
				root.put(CODE, code);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			_writer.write(root.toString());
		}

		return code;
	}

	public static int getAllPayedItem(String id) {
		Connection connection=new DBManager().getConnection();
		String result=null;
		int code=NOT_SUCCESED;
		try {
			Statement stm=connection.createStatement();
			ResultSet rSet=stm.
					executeQuery("select table_pay from table_user where id='"
					+id+"'");
			while(rSet.next())
			{
				result=rSet.getString(1);
			}
			if(result==null || result.equals("null"))
			{
				Statement stm1=connection.createStatement();
				stm1.execute("update table_user set table_pay='table_pay_item_"+
				id+"' where id='"+id+"'");
				Statement stm2=connection.createStatement();
				stm2.execute("create table table_pay_item_"+id+
						"(id varchar(30) primary key,detail varchar(30),price varchar(30),"
						+"style varchar(30) ,name varchar(30),img_path varchar(50)"
						+",restaurant varchar(30))");
				code=SUCCESED;
				stm1.close();
				stm2.close();
			}
			stm.close();
			rSet.close();
//			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code=NOT_SUCCESED;
		}
		
		
		JSONObject jsonRoot=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		try {
			Statement statement=connection.createStatement();
			ResultSet rSet=statement.executeQuery("select * from table_pay_item_"+id);
			while(rSet.next())
			{
				JSONObject obj=new JSONObject();
				
				obj.put("id", rSet.getString(1));
				obj.put("detail", rSet.getString(2));
				obj.put("price", rSet.getString(3));
				obj.put("style", rSet.getString(4));
				obj.put("name", rSet.getString(5));
				obj.put("img_path", rSet.getString(6));
				obj.put("restaurant", rSet.getString(7));
				jsonArray.put(obj);
			}
			jsonRoot.put("jsonArray", jsonArray);
			code=SUCCESED;
			jsonRoot.put("code", code);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_writer.write(jsonRoot.toString());
		
		return code;
	}

	public static int getAllCollectItem(String id) {
		Connection connection=new DBManager().getConnection();
		String result=null;
		int code=NOT_SUCCESED;
		try {
			Statement stm=connection.createStatement();
			ResultSet rSet=stm.
					executeQuery("select table_collect from table_user where id='"
					+id+"'");
			while(rSet.next())
			{
				result=rSet.getString(1);
			}
			if(result==null || result.equals("null"))
			{
				Statement stm1=connection.createStatement();
				stm1.execute("update table_user set table_collect='table_collect_item_"+
				id+"' where id='"+id+"'");
				Statement stm2=connection.createStatement();
				stm2.execute("create table table_collect_item_"+id+
						"(id varchar(30)primary key,detail varchar(30),price varchar(30),"
						+"style varchar(30) ,name varchar(30),img_path varchar(50)"
						+",restaurant varchar(30))");
				code=SUCCESED;
				stm1.close();
				stm2.close();
			}
			stm.close();
			rSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code=NOT_SUCCESED;
		}
		
		JSONObject jsonRoot=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		try {
			Statement statement=connection.createStatement();
			ResultSet rSet=statement.executeQuery("select * from table_collect_item_"+id);
			while(rSet.next())
			{
				JSONObject obj=new JSONObject();
				
				obj.put("id", rSet.getString(1));
				obj.put("detail", rSet.getString(2));
				obj.put("price", rSet.getString(3));
				obj.put("style", rSet.getString(4));
				obj.put("name", rSet.getString(5));
				obj.put("img_path", rSet.getString(6));
				obj.put("restaurant", rSet.getString(7));
				jsonArray.put(obj);
			}
			jsonRoot.put("jsonArray", jsonArray);
			code=SUCCESED;
			jsonRoot.put("code", code);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_writer.write(jsonRoot.toString());
		
		
		return code;

	}

	public static int getAllCartItem(String id) {
		Connection connection=new DBManager().getConnection();
		String result=null;
		int code=NOT_SUCCESED;
		try {
			Statement stm=connection.createStatement();
			ResultSet rSet=stm.
					executeQuery("select table_cart from table_user where id='"
					+id+"'");
			while(rSet.next())
			{
				result=rSet.getString(1);
			}
			if(result==null || result.equals("null"))
			{
				Statement stm1=connection.createStatement();
				stm1.execute("update table_user set table_cart='table_cart_item_"+
				id+"' where id='"+id+"'");
				Statement stm2=connection.createStatement();
				stm2.execute("create table table_cart_item_"+id+
						"(id varchar(30)primary key,detail varchar(30),price varchar(30),"
						+"style varchar(30) ,name varchar(30),img_path varchar(50)"
						+",restaurant varchar(30))");
				code=SUCCESED;
				stm1.close();
				stm2.close();
			}
			stm.close();
			rSet.close();
//			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code=NOT_SUCCESED;
		}
		
		JSONObject jsonRoot=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		try {
			Statement statement=connection.createStatement();
			ResultSet rSet=statement.executeQuery("select * from table_cart_item_"+id);
			while(rSet.next())
			{
				JSONObject obj=new JSONObject();
				
				obj.put("id", rSet.getString(1));
				obj.put("detail", rSet.getString(2));
				obj.put("price", rSet.getString(3));
				obj.put("style", rSet.getString(4));
				obj.put("name", rSet.getString(5));
				obj.put("img_path", rSet.getString(6));
				obj.put("restaurant", rSet.getString(7));
				jsonArray.put(obj);
			}
			jsonRoot.put("jsonArray", jsonArray);
			code=SUCCESED;
			jsonRoot.put("code", code);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_writer.write(jsonRoot.toString());
		
		return code;

	}
	
	public static int addItemToTablePay(String id,HttpServletRequest request)
    {
		checkTable(id, 0);
		String jsonString=request.getParameter("jsonOrderItemData");
		OrderItemData data=new OrderItemData();
		int code=NOT_SUCCESED;
		try {
			JSONObject root=new JSONObject(jsonString);
			data.setID(root.getString("id"));
			data.setItemDetail(root.getString("detail"));
			data.setItemName(root.getString("name"));
			data.setPrice(root.getString("price"));
			data.setStyle(root.getString("style"));
			data.setImagePath(root.getString("img_path"));
			data.setRestaurant(root.getString("restaurant"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection=new DBManager().getConnection();
		Statement stm=null;
		
		try {
			stm=connection.createStatement();
			stm.execute("insert into table_pay_item_"+id+"(id,detail,price,style"
					+",name,img_path,restaurant)"+" values('"
					+data.getID()+"','"+data.getItemDetail()+"','"
					+data.getPrice()+"','"+data.getStyle()+"','"
					+data.getItemName()+"','"+data.getImagePath()
					+"','"+data.getRestaurant()
					+"')");
			code=SUCCESED;
			stm.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code=NOT_SUCCESED;
		}
		
		JSONObject object=new JSONObject();
		try {
			object.put("code", code);
			_writer.write(object.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        return code;
    }
	
	public static int addItemToTableCollect(String id,HttpServletRequest request)
    {
		checkTable(id, 1);

		String jsonString=request.getParameter("jsonOrderItemData");
		OrderItemData data=new OrderItemData();
		int code=NOT_SUCCESED;
		try {
			JSONObject root=new JSONObject(jsonString);
			data.setID(root.getString("id"));
			data.setItemDetail(root.getString("detail"));
			data.setItemName(root.getString("name"));
			data.setPrice(root.getString("price"));
			data.setStyle(root.getString("style"));
			data.setImagePath(root.getString("img_path"));
			data.setRestaurant(root.getString("restaurant"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection=new DBManager().getConnection();
		Statement stm=null;
		
		try {
			stm=connection.createStatement();
			stm.execute("insert into table_collect_item_"+id+"(id,detail,price,style"
					+",name,img_path,restaurant)"+" values('"
					+data.getID()+"','"+data.getItemDetail()+"','"
					+data.getPrice()+"','"+data.getStyle()+"','"
					+data.getItemName()+"','"+data.getImagePath()
					+"','"+data.getRestaurant()
					+"')");
			code=SUCCESED;
			stm.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code=NOT_SUCCESED;
		}
		
		JSONObject object=new JSONObject();
		try {
			object.put("code", code);
			_writer.write(object.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return code;
    }
	
	
	public static int addItemToTableCart(String id,HttpServletRequest request)
    {
		checkTable(id, 2);
		String jsonString=request.getParameter("jsonOrderItemData");
		OrderItemData data=new OrderItemData();
		int code=NOT_SUCCESED;
		try {
			JSONObject root=new JSONObject(jsonString);
			data.setID(root.getString("id"));
			data.setItemDetail(root.getString("detail"));
			data.setItemName(root.getString("name"));
			data.setPrice(root.getString("price"));
			data.setStyle(root.getString("style"));
			data.setImagePath(root.getString("img_path"));
			data.setRestaurant(root.getString("restaurant"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection=new DBManager().getConnection();
		Statement stm=null;
		
		try {
			stm=connection.createStatement();
			stm.execute("insert into table_cart_item_"+id+"(id,detail,price,style"
					+",name,img_path,restaurant)"+" values('"
					+data.getID()+"','"+data.getItemDetail()+"','"
					+data.getPrice()+"','"+data.getStyle()+"','"
					+data.getItemName()+"','"+data.getImagePath()
					+"','"+data.getRestaurant()
					+"')");
			code=SUCCESED;
			connection.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			code=NOT_SUCCESED;
		}
		
		JSONObject object=new JSONObject();
		try {
			object.put("code", code);
			_writer.write(object.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
        return code;
    }
	
	/**
	 * index=0 table pay,
	 * index=1 table collect,
	 * index=2 table cart,
	 * @param id
	 * @param index
	 */
	public static void checkTable(String id, int index) {
		Connection connection = new DBManager().getConnection();

		Statement stm1 = null;
		Statement stm2 = null;
		String table = null;
		String valueTable=null;
		switch (index) {
		case 0:
			table = "table_pay_item_" + id;
			valueTable="table_pay";
			break;
		case 1:
			table = "table_collect_item_" + id;
			valueTable="table_collect";
			break;
		case 2:
			table = "table_cart_item_" + id;
			valueTable="table_cart";
			break;
		}
		try {
			stm1 = connection.createStatement();
			stm2 = connection.createStatement();

			ResultSet rSet = stm1.executeQuery("select * from " + table);

			stm1.close();
			stm2.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				stm2 = connection.createStatement();
				stm2.execute("create table "+table
						+ "(id varchar(30) primary key,detail varchar(30),price varchar(30),"
						+ "style varchar(30) ,name varchar(30),img_path varchar(50)"
						+",restaurant varchar(30)"
						+ ")");
		
				Statement stm3=connection.createStatement();
				stm3.execute("update table_user set "+valueTable+" ='"+table+"' "
					+"where id='"+id+"'");
				stm2.close();
				connection.close();
				stm1.close();
				stm3.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

	}
	
	public static int getOrderItemsByRestaurant(HttpServletRequest request)
	{
		int code=NOT_SUCCESED;
		
		String id=request.getParameter("id");
		String restaurant=request.getParameter("restaurant");
		
		Connection connection=new DBManager().getConnection();
		JSONObject jsonRoot=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		try {
			Statement stm=connection.createStatement();
			ResultSet rSet=stm.
					executeQuery("select * from table_order_item where restaurant like '"
			+restaurant.charAt(0)+"%'");
			while(rSet.next())
			{
				JSONObject object=new JSONObject();
				object.put("id", rSet.getString(1));
				object.put("detail", rSet.getString(2));
				object.put("price", rSet.getString(3));
				object.put("style", rSet.getString(4));
				object.put("name", rSet.getString(5));
				object.put("img_path", rSet.getString(6));
				object.put("restaurant", rSet.getString(7));
				jsonArray.put(object);
			}
			jsonRoot.put("jsonArray", jsonArray);
			code=SUCCESED;
			
			jsonRoot.put("code",code);
			
			stm.close();
			rSet.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_writer.write(jsonRoot.toString());
		
		
		return code;
	}

	static final int NOT_SUCCESED = 0;
	static final int NOT_FOUND_IN_SERVER = 1;
	static final int SUCCESED = 2;

}
