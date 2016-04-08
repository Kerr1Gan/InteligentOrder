package org.ecjtu.inteligentorder.DataStruct;

/**
 * Created by KerriGan on 2016/1/7.
 */
public class DataBase {

    protected String _id;

    public DataBase()
    {
        _id=null;
    }

    public DataBase(String id)
    {
        _id=id;
    }

    public void setID(String id)
    {
        _id=id;
    }

    public String getID()
    {
        return _id;
    }
}
