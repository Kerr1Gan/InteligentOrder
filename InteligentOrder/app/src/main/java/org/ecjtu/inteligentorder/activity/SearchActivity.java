package org.ecjtu.inteligentorder.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ScrollView;
import android.widget.SearchView;

import org.AsyncTask.AsyncImageTask;
import org.AsyncTask.Task;
import org.Http.HttpUtils.UserRequestProtocol;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.DataBaseHelper.SearchDataHelper;
import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.customui.CustomOrderItem;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity {

    private ArrayList<OrderItemData> _allOrderItemData;
    private SearchView _searchView;
    private ViewGroup _scrollViewContent;
    private String _searchText;
    private ArrayList<OrderItemData> _dataInSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        _allOrderItemData= AppInstance.getInstance().getAllOrderItemData();
        _searchView= (SearchView) findViewById(R.id.search_view);
        _searchView.onActionViewExpanded();

        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        deleteDatabase(new SearchDataHelper(this).getDataBaseName());

//        new SearchDataHelper(SearchActivity.this);

        SQLiteDatabase db= new SearchDataHelper(SearchActivity.this).
                getWritableDatabase();
        Cursor c=db.rawQuery("select * from table_search;",null);

//        StringBuffer sb=new StringBuffer();
//        while(c.moveToNext())
//        {
//            sb.append(c.getString(3));
//        }
        _searchText=new String("");
        _dataInSearch=new ArrayList<>();

//        _searchView.setQueryRefinementEnabled(true);


        _searchView.setSuggestionsAdapter(new CursorAdapter(SearchActivity.this
                , c) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                CustomOrderItem item = null;
                for (OrderItemData data : AppInstance.getInstance().getAllOrderItemData()) {
                    if ((data.getRestaurant().contains(_searchText)
                            || data.getItemName().contains(_searchText)
                            || data.getStyle().contains(_searchText)
                            || data.getID().contains(_searchText))
                            && !isContain(data)) {
                        item = new CustomOrderItem(context);
                        item.setOrderItem(data);
                        AsyncImageTask task = new AsyncImageTask();
                        Task obj = new Task(item.getImageView());
                        obj.setResPath(data.getImagePath());
                        task.addTask(obj);
                        task.destroy();
                        _dataInSearch.add(data);

                        final CustomOrderItem finalItem = item;
                        item.setOnItemClickListener(new CustomOrderItem.onItemClickListener() {
                            @Override
                            public void onClick(int index) {
                                if(index==0)
                                {
                                    SearchActivity.this.startActivity(
                                            new Intent(SearchActivity.this
                                                    , OrderActivity.class));
                                }
                                else if(index==1)
                                {
                                    new UserRequestProtocol().addItemToTableCart(
                                            AppInstance.getInstance().getID(),
                                            finalItem.getOrderItem(),
                                            new UserRequestProtocol.ServerCallBack() {
                                                @Override
                                                public void getCallBackCode(int code, Object obj) {
                                                    System.out.println("添加数据到"
                                                            +"Cart成功!");
                                                }
                                            }
                                    );
                                }
                                else if(index==2)
                                {
                                    new UserRequestProtocol().addItemToTableCollect(
                                            AppInstance.getInstance().getID(),
                                            finalItem.getOrderItem(),
                                            new UserRequestProtocol.ServerCallBack() {
                                                @Override
                                                public void getCallBackCode(int code, Object obj) {
                                                    System.out.println("添加数据到"
                                                            +"Collect成功!");
                                                }
                                            }
                                    );
                                }
                            }
                        });
                        break;
                    }
                }
                if (item == null) {
                    View v = new View(context);
                    v.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                    return v;
                }
                return item;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

            }

        });
        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                int oldSize=_searchText.length();
                _searchText=newText;
                if(oldSize!=_searchText.length())
                    _dataInSearch.clear();

                return false;
            }

        });


        _scrollViewContent= (ViewGroup) findViewById(R.id.scroll_view_content);

        initReflect();

    }

    public boolean isContain(OrderItemData data)
    {
        for(OrderItemData d:_dataInSearch)
        {
            if(d.getID().equals(data.getID()))
                return true;
        }
        return false;
    }

    public void initReflect()
    {
        Class cls=_searchView.getClass();
        Field[] fields=cls.getDeclaredFields();

        for(int i=0;i<fields.length;i++)
        {
            System.out.println(fields[i].toString());
        }

        try {
            Field f=cls.getDeclaredField("mQueryTextView");
            f.setAccessible(true);

            Object obj=f.get(_searchView);

            Field ff=obj.getClass().getDeclaredField("mThreshold");
            ff.setAccessible(true);


            Method me=obj.getClass().getMethod("setThreshold",
                   int.class);
            me.invoke(obj,1);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
