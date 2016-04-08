package org.ecjtu.inteligentorder.customfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.activity.AddItemActivity;

/**
 * Created by KerriGan on 2016/1/8.
 */
public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        init();
        return _rootView;
    }

    private ViewGroup _rootView;

    private Button _buttonAddItem;
    public void init()
    {
        _buttonAddItem= (Button) _rootView.findViewById(R.id.button_add_item);
        _buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment.this.getActivity().startActivity(
                        new Intent(SettingFragment.this.getActivity(),
                                AddItemActivity.class)
                );
            }
        });
    }

}
