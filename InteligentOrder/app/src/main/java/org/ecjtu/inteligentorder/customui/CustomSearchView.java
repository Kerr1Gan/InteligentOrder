package org.ecjtu.inteligentorder.customui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.MultiAutoCompleteTextView;

import org.ecjtu.inteligentorder.R;

/**
 * Created by KerriGan on 2016/1/13.
 */
public class CustomSearchView extends LinearLayout{
    private Context _context;
    private ViewGroup _rootView;
    private ViewGroup _dropDownView;
    public CustomSearchView(Context context) {
        this(context, null);
    }

    public CustomSearchView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context=context;
        init();
    }

    public void init()
    {
        _rootView= (ViewGroup) LayoutInflater.
                from(_context).inflate(R.layout.custom_search_view
                , this);
        _autoCompleteTextView= (AutoCompleteTextView) findViewById(R.id.auto_complete_search_view);
        _autoCompleteTextView.setThreshold(1);

        _autoCompleteTextView.setDropDownHeight(100);

//        _dropDownView= (ViewGroup)
//                _rootView.findViewById(_autoCompleteTextView.getDropDownAnchor());

        _autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String ss = s.toString();
//                _autoCompleteTextView.showDropDown();
                _popUpWindow.setWidth(200);
                _popUpWindow.setHeight(200);
                _popUpWindow.show();

            }
        });
        _autoCompleteTextView.setDropDownAnchor(R.layout.custom_order_item);
        int x=_autoCompleteTextView.getDropDownAnchor();
        _autoCompleteTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        _popUpWindow=new ListPopupWindow(_context);
    }


    private String _newSearchText=null;
    private String _oldSearchText=null;
    public AutoCompleteTextView _autoCompleteTextView;
    private ListPopupWindow _popUpWindow;
}
