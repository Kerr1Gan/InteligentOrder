package org.ecjtu.inteligentorder.customui;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Looper;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ecjtu.inteligentorder.DataStruct.OrderItemData;
import org.ecjtu.inteligentorder.R;

import java.lang.annotation.Documented;

/**
 * Created by KerriGan on 2016/1/5.
 */
public class CustomOrderItem extends LinearLayout{

    private Context _context;
    private ViewGroup _rootView;
    private boolean _canTouch=true;

    public CustomOrderItem(Context context) {
        this(context,null);
    }

    public CustomOrderItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomOrderItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context=context;
        _canTouch=true;
        initialize();
    }

    private TextView _textViewId;
    private TextView _textViewName;
    private TextView _textViewPrice;
    private TextView _textViewClass;
    private TextView _textViewRestaurant;
    private ImageView _imageView;

    public synchronized void initialize()
    {
        _rootView= (ViewGroup) LayoutInflater.from(_context).
                inflate(R.layout.custom_order_item, this);

        _rootView= (ViewGroup) _rootView.findViewById(R.id.main_content);

        _rightHideContent= (ViewGroup) _rootView.
                findViewById(R.id.right_hide_content);

        _canTouch=true;


        _imageView= (ImageView) findViewById(R.id.image_button);

        _textViewId= (TextView) _rootView.findViewById(R.id.text_view_id);
        _textViewName= (TextView) _rootView.findViewById(R.id.text_view_name);
        _textViewPrice= (TextView) _rootView.findViewById(R.id.text_view_price);
        _textViewClass= (TextView) _rootView.findViewById(R.id.text_view_class);
        _textViewRestaurant= (TextView) _rootView.findViewById(R.id.text_view_restaurant);


        _buttonAddCart= (Button) _rootView.findViewById(R.id.button_add_cart);
        _buttonCollect= (Button) _rootView.findViewById(R.id.button_collect);
        _buttonOrder= (Button) _rootView.findViewById(R.id.button_order);

        _buttonOrder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag","clicke button order");
                if(_itemClickListener!=null)
                {
                    _itemClickListener.onClick(0);
                }
            }
        });
        _buttonCollect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag", "clicke button collect");
                if(_itemClickListener!=null)
                {
                    _itemClickListener.onClick(2);
                    Toast.makeText(_context,"已收藏",Toast.LENGTH_SHORT)
                    .show();
                }

            }
        });
        _buttonAddCart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("tag","clicke button addcart");
                if(_itemClickListener!=null)
                {
                    _itemClickListener.onClick(1);
                    Toast.makeText(_context,"加入购物车",Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public ImageView getImageView()
    {
        return _imageView;
    }

    private OrderItemData _orderItemData=null;
    public void setOrderItem(OrderItemData data)
    {
        _orderItemData=data;
        _textViewId.setText(
                _textViewId.getText().toString()+data.getID());
        _textViewName.setText(
                _textViewName.getText().toString() + data.getItemName());
        _textViewPrice.setText(_textViewPrice.getText().toString()
                            +data.getPrice());
        _textViewClass.setText(_textViewClass.getText().toString()
        +data.getStyle());
        _textViewRestaurant.setText(_textViewRestaurant.getText().toString()
        +data.getRestaurant());
    }

    public OrderItemData getOrderItem()
    {
        return _orderItemData;
    }


    protected void onFinishInflate() {
        super.onFinishInflate();
//        initialize();
    }


    private Point _lastTouch=null;
    private ViewGroup _rightHideContent=null;
    private int _maxToLeftDistance;
    private int _rightHideContentBeginPosX;
    private Button _buttonOrder;
    private Button _buttonAddCart;
    private Button _buttonCollect;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int size=ev.getHistorySize();
        if(ev.getAction()==MotionEvent.ACTION_MOVE)
        {
            if(_lastTouch==null)
            {
                _lastTouch=new Point();
                _lastTouch.x= (int) ev.getX();
                _lastTouch.y= (int) ev.getY();
                return true;
            }
            int offSetX= (int) (ev.getX()-_lastTouch.x);
            int offSetY= (int) (ev.getY()-_lastTouch.y);

//            Log.i("msg","offSetX:"+offSetX);
//            Log.i("msg","offSetY:"+offSetY);

            if(Math.abs(offSetX)>Math.abs(offSetY)&&offSetX<0
                    &&Math.abs(_rootView.getX())<_maxToLeftDistance
                    &&_canTouch)
            {
                MoveTo(offSetX);
                if(_rootView.getX()<=-4)
                {
                    autoHideToLeft();
                }

            }
            else if(Math.abs(offSetX)>Math.abs(offSetY)&&offSetX>0
                    &&_rootView.getX()<0&&_canTouch)
            {
                MoveTo(offSetX);
                if(_rootView.getX()>=-_maxToLeftDistance+4)
                {
                    autoResetToRight();
                }
            }
        }


        if(ev.getAction()==MotionEvent.ACTION_UP)
        {
            _lastTouch=null;
            Rect rcOrder=new Rect();
            _buttonOrder.getGlobalVisibleRect(rcOrder);
            Rect rcCart=new Rect();
            _buttonAddCart.getGlobalVisibleRect(rcCart);
            Rect rcCollect=new Rect();
            _buttonCollect.getGlobalVisibleRect(rcCollect);
//            Log.i("tag", "RawX:" + ev.getRawX() + " RawY:" + ev.getRawY());
//            Log.i("tag","Rect:"+rc1.toString());
            int touchX= (int) ev.getRawX();
            int touchY= (int) ev.getRawY();
            if(rcOrder.contains(touchX,touchY))
            {
                _buttonOrder.dispatchTouchEvent(ev);
//                _buttonOrder.onTouchEvent(ev);
                _buttonOrder.callOnClick();
            }
            else if(rcCart.contains(touchX,touchY))
            {
                _buttonAddCart.dispatchTouchEvent(ev);
                _buttonAddCart.callOnClick();
            }
            else if(rcCollect.contains(touchX,touchY))
            {
                _buttonCollect.dispatchTouchEvent(ev);
                _buttonCollect.callOnClick();
            }
        }
        return true;
    }

    private boolean _mutexBetweenAThreadWidthPost=false;
    public void autoHideToLeft()
    {
        _canTouch=false;
        final int animTime=500;

        new Thread(new Runnable() {
            @Override
            public void run() {
                final int[] timeCount = {0};
                while (true)
                {
                    synchronized (CustomOrderItem.this)
                    {
                        CustomOrderItem.this._rootView.post(new Runnable() {
                                @Override
                                public void run() {

                                    synchronized (CustomOrderItem.this)
                                    {
                                        MoveTo(-20);
                                        if(CustomOrderItem.this.
                                                _mutexBetweenAThreadWidthPost)
                                        {
                                            _mutexBetweenAThreadWidthPost=false;
                                            CustomOrderItem.this.notify();
                                        }
                                    }

                                }
                            });

                        if(CustomOrderItem.this._mutexBetweenAThreadWidthPost
                                ==false)
                        {
                            try {
                                CustomOrderItem.this._mutexBetweenAThreadWidthPost
                                        =true;
                                CustomOrderItem.this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                            try {
                                timeCount[0] +=10;
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if(/*timeCount[0] >=animTime ||*/
                                    CustomOrderItem.this._rootView.getX()
                                            <=-_maxToLeftDistance)
                            {
                                timeCount[0]=0;
                                _canTouch=true;
                                break;
                            }
                    }
                }
            }
        }).start();
    }

    public void autoResetToRight()
    {
        _canTouch=false;
        final int animTime=500;

        new Thread(new Runnable() {
            @Override
            public void run() {
                final int[] timeCount = {0};
                while (true)
                {
                    synchronized (CustomOrderItem.this)
                    {
                        CustomOrderItem.this._rootView.post(new Runnable() {
                            @Override
                            public void run() {

                                synchronized (CustomOrderItem.this)
                                {
                                    MoveTo(20);
                                    if(CustomOrderItem.this.
                                            _mutexBetweenAThreadWidthPost)
                                    {
                                        _mutexBetweenAThreadWidthPost=false;
                                        CustomOrderItem.this.notify();
                                    }
                                }

                            }
                        });

                        if(CustomOrderItem.this._mutexBetweenAThreadWidthPost
                                ==false)
                        {
                            try {
                                CustomOrderItem.this._mutexBetweenAThreadWidthPost
                                        =true;
                                CustomOrderItem.this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        try {
                            timeCount[0] +=10;
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if(/*timeCount[0] >=animTime ||*/
                                CustomOrderItem.this._rootView.getX()
                                        >=0)
                        {
                            timeCount[0]=0;
                            _canTouch=true;
                            break;
                        }
                    }
                }
            }
        }).start();
    }

    public void MoveTo(int offSetX)
    {
        if(offSetX<0)
        {
            if (offSetX > -20 || offSetX < -40)
                offSetX = -20;
            if (_rootView.getX() + offSetX <= -_maxToLeftDistance) {
                offSetX = -(int) (_maxToLeftDistance -
                        Math.abs(_rootView.getX()));
            }

            _rootView.setX(_rootView.getX() + offSetX);

            _rightHideContent.setX(_rightHideContent.getX() + offSetX);

            LayoutParams margin = (LayoutParams) _rootView.getLayoutParams();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    _rootView.getWidth() - offSetX,
                    _rootView.getHeight()
            );
            params.bottomMargin = margin.bottomMargin;
            _rootView.setLayoutParams(params);

            _lastTouch = null;
        }
        else if(offSetX>0)
        {
            if(offSetX<20 || offSetX>40)
                offSetX=20;
            if(_rootView.getX()+offSetX>=0)
            {
                offSetX= (int) (0-_rootView.getX());
            }
            _rootView.setX(_rootView.getX() + offSetX);

            _rightHideContent.setX(_rightHideContent.getX()+offSetX);

            LayoutParams margin= (LayoutParams) _rootView.getLayoutParams();
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    _rootView.getWidth()-offSetX,
                    _rootView.getHeight()
            );
            params.bottomMargin=margin.bottomMargin;
            _rootView.setLayoutParams(params);

            _lastTouch=null;
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);


        if (_rightHideContent != null && (_rightHideContent.getWidth() == 0 ||
                _maxToLeftDistance == 0)) {
            _maxToLeftDistance = _rightHideContent.getWidth();
            _rightHideContentBeginPosX = (int) _rightHideContent.getX();
        }

    }

    public void setOnItemClickListener(onItemClickListener litener)
    {
        _itemClickListener=litener;
    }

    private onItemClickListener _itemClickListener;


    public interface onItemClickListener
    {
        /**
        * index=0 is order,
        * index=1 is add to cart,
        * index=2 is collect
        * */
        void onClick(int index);
    }

}
