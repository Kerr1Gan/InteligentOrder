package org.ecjtu.inteligentorder.DataStruct;

/**
 * Created by KerriGan on 2016/1/7.
 */
public class OrderItemData extends DataBase{

    private String _itemName;
    private String _itemDetail;
    private String _price;
    private String _style;
    private String _imgPath;
    private String _restaurant;
    public OrderItemData()
    {
        _itemName=null;
        _itemDetail=null;
        _price=null;
        _style=null;
        _imgPath=null;
        _restaurant=null;
    }

    public OrderItemData(String id)
    {
        super(id);
    }

    public void setItemName(String name)
    {
        _itemName=name;
    }

    public String getItemName()
    {
        return _itemName;
    }

    public void setItemDetail(String detail)
    {
        _itemDetail=detail;
    }
    public String getItemDetail()
    {
        return _itemDetail;
    }

    public void setPrice(String price)
    {
        _price=price;
    }

    public String getPrice()
    {
        return _price;
    }

    public void setStyle(String style)
    {
        _style=style;
    }

    public String getStyle()
    {
        return _style;
    }

    public void setImagePath(String path)
    {
        _imgPath=path;
    }

    public String getImagePath()
    {
        return _imgPath;
    }

    public void setRestaurant(String name)
    {
        _restaurant=name;
    }

    public String getRestaurant()
    {
        return _restaurant;
    }
}
