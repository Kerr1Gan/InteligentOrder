package org.ecjtu.inteligentorder.activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.Http.HttpUtils.UserRequestProtocol;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.R;
import org.inteligentorder.animation.LeftToRightAnimation;

public class LoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();
    }

    private Button _registerButton;
    private Button _loginButton;
    private EditText _editTextID;
    private EditText _editTextCode;

    protected void initialize()
    {
        _registerButton= (Button) findViewById(R.id.button_register);
        _loginButton= (Button) findViewById(R.id.button_login);

        _editTextID= (EditText) findViewById(R.id.edit_text_id);
        _editTextCode= (EditText) findViewById(R.id.edit_text_code);


        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=_editTextID.getText().toString();
                String code=_editTextCode.getText().toString();
                isUserInServer(id,code);
//                LoginActivity.this.startActivity(new Intent(LoginActivity.this,
//                        MainActivity.class));
//                LoginActivity.this.finish();
            }
        });

        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this,
                        RegisterActivity.class));
            }
        });
//        First base=new First();
//        base.setDuration(1000);
//        base.setTargetView(_loginButton);
//        base.runAnimation();

        LeftToRightAnimation anim=new LeftToRightAnimation();
        anim.setDuration(500);
        findViewById(R.id.edit_text_id).setVisibility(View.INVISIBLE);
        anim.setTargetView(findViewById(R.id.edit_text_id));
        anim.runAnimation(1000);

        LeftToRightAnimation anim1=new LeftToRightAnimation();
        anim1.setDuration(1000);
        findViewById(R.id.edit_text_code).setVisibility(View.INVISIBLE);
        anim1.setTargetView(findViewById(R.id.edit_text_code));
        anim1.runAnimation(1000);

    }

    public void isUserInServer(String id,String code)
    {
        UserRequestProtocol protocol=new UserRequestProtocol();
        final String fid=id;
        protocol.isUserInServer(id, code,
                new UserRequestProtocol.ServerCallBack() {
            @Override
            public void getCallBackCode(int code,Object obj) {
                if(code== UserRequestProtocol.ServerCallBack.SUCCESED)
                {
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this,
                            MainActivity.class));
                    LoginActivity.this.finish();
                    AppInstance.getInstance().setID(fid);
                }
                else if(code==
                        UserRequestProtocol.ServerCallBack.NOT_FOUND_IN_SERVER)
                {
                    Toast.makeText(LoginActivity.this,"账号或密码错误"
                            ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
