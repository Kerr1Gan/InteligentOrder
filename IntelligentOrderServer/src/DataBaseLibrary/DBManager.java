package DataBaseLibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {
	String _userName="root";
	String _passWord="";
	Connection _connection;
	Statement stmt=null;
	String _url="jdbc:mysql://localhost:3306/intelligent_order_app_server";
	ArrayList<String> _list=new ArrayList<String>();
	String _sql=null;
	
	public DBManager()
	{
//		_sql="select * from user";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
		try {
			_connection=DriverManager.getConnection(_url,_userName,_passWord);
//			stmt=_connection.createStatement();
//			ResultSet rSet=stmt.executeQuery(_sql);
//			while(rSet.next())
//			{
//				String name=new String(rSet.getString(1));
//				_list.add(name);
//			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		return _connection;
	}
	
	public ArrayList<String> getList()
	{
		return _list;
	}

}
