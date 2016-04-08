package org.ecjtu.inteligentorder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.Http.HttpUtils.UserRequestProtocol;
import org.ecjtu.inteligentorder.AppInstance;
import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.ecjtu.inteligentorder.R;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent i=getIntent();
        Bundle b=i.getExtras();
        String id= (String) b.get("objID");
        OrderItemData d=null;
        for(OrderItemData data:AppInstance.getInstance().getAllOrderItemData())
        {
            if(id .equals(data.getID()) )
            {
                d=data;
            }
        }

        _buttonPay= (Button) findViewById(R.id.button_pay);
        _buttonExit= (Button) findViewById(R.id.button_exit);

        final OrderItemData finalD = d;
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.equals(_buttonPay)&& finalD !=null)
                {
                    new UserRequestProtocol().addItemToTablePay(
                            AppInstance.getInstance().getID(), finalD,
                            new UserRequestProtocol.ServerCallBack() {
                                @Override
                                public void getCallBackCode(int code, Object obj) {
                                    if(code==SUCCESED)
                                    {
                                        Toast.makeText(OrderActivity.this,
                                                "添加订单成功",Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                            }
                    );
                }

                OrderActivity.this.finish();
            }
        };

        _buttonPay.setOnClickListener(listener);
        _buttonExit.setOnClickListener(listener);



    }

    private Button _buttonPay;
    private Button _buttonExit;
}
