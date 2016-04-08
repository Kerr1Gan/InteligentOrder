package org.ecjtu.inteligentorder.activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.AsyncTask.AsyncImageTask;
import org.AsyncTask.Task;
import org.Http.HttpUtils.UploadImageProtocol;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.DataBaseHelper.SearchDataHelper;
import org.ecjtu.inteligentorder.R;
import org.ecjtu.inteligentorder.customfragment.PersonCenterFragment;
import org.ecjtu.inteligentorder.customfragment.MainMenuFragment;
import org.ecjtu.inteligentorder.customfragment.SettingFragment;
import org.ecjtu.inteligentorder.customui.BottomMenu;
import org.ecjtu.inteligentorder.customui.CustomActionBar;

import java.io.File;

public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar=getSupportActionBar();
        CustomActionBar bar=new CustomActionBar(this);
        actionBar.setCustomView(bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        AppInstance.getInstance().setMainActivityActionBar(bar);



        BottomMenu bottomMenu= (BottomMenu) this.findViewById(R.id.menu_bottom);
        bottomMenu.setOnItemClickLitener(new BottomMenu.onItemClickListener() {
            @Override
            public void itemClick(View v, int pos) {
                if(pos==0)
                {
                    MainMenuFragment fragment = new MainMenuFragment();
                    FragmentManager manager = MainActivity.
                            this.getSupportFragmentManager();
                    FragmentTransaction transaction =
                            manager.beginTransaction();

                    transaction.replace(R.id.main_content, fragment);
                    transaction.commit();
                }
                if (pos == 1) {
                    ViewGroup group= (ViewGroup) MainActivity.this.findViewById(R.id
                    .main_content);
                    group.removeAllViews();
                    PersonCenterFragment fragment = new PersonCenterFragment();
                    _personCenterFragment=fragment;
                    FragmentManager manager = MainActivity.
                            this.getSupportFragmentManager();
                    FragmentTransaction transaction =
                            manager.beginTransaction();

                    transaction.replace(R.id.main_content, fragment);
                    transaction.commit();

                }
                if(pos==2)
                {
                    ViewGroup group= (ViewGroup) MainActivity.this.findViewById(R.id
                            .main_content);
                    group.removeAllViews();
                    SettingFragment fragment=new SettingFragment();
                    _settingFragment=fragment;
                    MainActivity.this.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_content,fragment).commit();
                }
            }
        });

        AppInstance.getInstance().clearBuffer();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private Fragment _personCenterFragment;
    private Fragment _settingFragment;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _personCenterFragment.onActivityResult(requestCode,resultCode,
                data);
    }

}
