package org.ecjtu.inteligentorder;

import android.app.backup.BackupAgent;
import android.os.Environment;

import org.Http.HttpUtils.UploadImageProtocol;
import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.ecjtu.inteligentorder.customui.CustomActionBar;
import org.ecjtu.inteligentorder.customui.ModifyImageDialog;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by KerriGan on 2016/1/7.
 */
public class AppInstance {

    private static AppInstance _instance;

    public ArrayList<OrderItemData> _orderItemList=null;
    private ArrayList<OrderItemData> _payedItemList=null;
    private ArrayList<OrderItemData> _itemInCart=null;
    private ArrayList<OrderItemData> _itemInCollect=null;

    private String _id;

    private AppInstance()
    {
    }

    public static AppInstance getInstance()
    {
        if(_instance==null)
            _instance=new AppInstance();

        return _instance;
    }

    public void setPayedItemList(ArrayList<OrderItemData> list)
    {
        _payedItemList=list;
    }

    public ArrayList<OrderItemData> getAllOrderItemData()
    {
        return _orderItemList;
    }

    public void setItemInCart(ArrayList<OrderItemData> list)
    {
        _itemInCart=list;
    }

    public void setItemInCollect(ArrayList<OrderItemData> list)
    {
        _itemInCollect= list;
    }

    public ArrayList<OrderItemData> getPayedItemList()
    {
        return _payedItemList;
    }

    public ArrayList<OrderItemData> getItemInCart()
    {
        return _itemInCart;
    }

    public ArrayList<OrderItemData> getItemInCollect()
    {
        return _itemInCollect;
    }

    private CustomActionBar _mainActivityActionBar;
    public void setMainActivityActionBar(CustomActionBar bar)
    {
        _mainActivityActionBar=bar;
    }
    public CustomActionBar getMainActivityActionBar()
    {
        return _mainActivityActionBar;
    }


    public void setID(String id)
    {
        _id=id;
    }

    public String getID()
    {
        return _id;
    }

    public void clearBuffer()
    {
        String rootPath= Environment.getExternalStorageDirectory().toString()
                +"/IntelligentOrder/";
        File f=new File(rootPath);
        if(!f.exists())
        {
            ModifyImageDialog.cacheFileByUri();
            f=new File(rootPath);
        }
        File list[]=f.listFiles();
        if(list==null)
            return;
        for(int i=0;i<list.length;i++)
        {
            if(list[i].exists())
                list[i].delete();
        }
    }
}
