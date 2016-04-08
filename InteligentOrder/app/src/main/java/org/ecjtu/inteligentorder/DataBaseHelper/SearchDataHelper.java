package org.ecjtu.inteligentorder.DataBaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.DataStruct.OrderItemData;

import java.util.ArrayList;

/**
 * Created by KerriGan on 2016/1/12.
 */
public class SearchDataHelper extends SQLiteOpenHelper{
    private String _dataBaseName;
    public SearchDataHelper(Context context) {
        super(context, "search_database", null, 1);
        _dataBaseName="search_database";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table table_search(_id INTEGER primary key autoincrement,"
                +"item_id varchar(25),restaurant varchar(25),name varchar(25)"
                +",style varchar(25))";

        db.execSQL(sql);

//        sql="insert into table_search(restaurant) values('交大')";
//        db.execSQL(sql);
//        sql="insert into table_search(restaurant) values('财大')";
//        db.execSQL(sql);

        ArrayList<OrderItemData> list=AppInstance.getInstance().
                getAllOrderItemData();
        String s=null;
        for(int i=0;i<list.size();i++)
        {
            OrderItemData data=list.get(i);
            s="insert into table_search(item_id,restaurant,name,style)"
                    +" values('"+data.getID()+"','"+data.getRestaurant()+"','"
                    +data.getItemName()+"','"+data.getStyle()+"')";
            db.execSQL(s);
        }
//        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getDataBaseName()
    {
        return _dataBaseName;
    }
}
