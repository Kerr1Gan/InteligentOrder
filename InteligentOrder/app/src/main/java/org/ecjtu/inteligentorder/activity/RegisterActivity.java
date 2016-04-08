package org.ecjtu.inteligentorder.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.Http.HttpUtils.UserRequestProtocol;
import org.ecjtu.inteligentorder.R;

/**
 * Created by KerriGan on 2016/1/4.
 */
public class RegisterActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _editTextID= (EditText) findViewById(R.id.edit_text_id);
        _edieTextCode= (EditText) findViewById(R.id.edit_text_code);

        _button= (Button) findViewById(R.id.button_login);

        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserRequestProtocol().registerUser(_editTextID.getText()
                                .toString(), _edieTextCode.getText().toString(),
                        new UserRequestProtocol.ServerCallBack() {
                            @Override
                            public void getCallBackCode(int code, Object obj) {
                                if(code== UserRequestProtocol.
                                        ServerCallBack.SUCCESED)
                                {
                                    Toast.makeText(RegisterActivity.this,
                                            "注册成功",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this,
                                            "账号已存在，注册失败",Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
            }
        });

    }

    private EditText _editTextID;
    private EditText _edieTextCode;
    private Button _button;
}
