package org.ecjtu.inteligentorder.customui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ecjtu.inteligentorder.R;

import java.security.ProtectionDomain;

/**
 * Created by KerriGan on 2016/1/9.
 */
public class CustomDialogBase {
    private Context _context;
    private View _rootView;
    public CustomDialogBase(Context context)
    {
        _context=context;
        init();
    }

    protected AlertDialog _dialog;
    protected AlertDialog.Builder _dialogBuilder;

    protected void init()
    {
        _dialogBuilder=new AlertDialog.Builder(_context);
    }

    public void setTitle(String title)
    {
        _dialogBuilder.setTitle(title);
    }

    public void setIcon(Drawable drawable)
    {
        if(_dialog==null)
            _dialogBuilder.setIcon(drawable);
        else
            _dialog.setIcon(drawable);
    }

    public void setView(View view)
    {
        _dialogBuilder.setView(view);
    }

    public void beforeShow(View root)
    {
        _rootView=root;
    }
    public AlertDialog show()
    {
        _dialog=_dialogBuilder.create();
        _dialog.show();
        return _dialog;
    }

    public void disMiss()
    {
        if(_dialog!=null)
        {
            _dialog.dismiss();
//            _dialog.hide();
            _dialog = null;
        }
    }

    public Context getContext()
    {
        return _context;
    }

}
