package xyz.pagedemo.Page;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import xyz.pagedemo.Bean.EnvironmentData;
import xyz.pagedemo.framework.BasePage;
import xyz.pagedemo.framework.PageBox;
import xyz.pagedemo.framework.PageLoader;
import xyz.pagedemo.framework.http.DecodeJson;
import xyz.pagedemo.framework.http.HttpExecutor2;

/**
 * Created by xyz on 2017/5/5.
 */

public class SecondPage extends BasePage {
    private Button back;
    private Button next;
    private TextView centerText;
    private HttpExecutor2 httpExecutor2 =new HttpExecutor2();
    private Handler handler=new Handler();
    private EnvironmentData data;


    public SecondPage(Context context, AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
        init(context);
    }

    public SecondPage(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    public SecondPage(Context context){
        super(context);
        init(context);
    }


    public void init(Context context) {
        initView(context);
        initListener();
        showInfo();
        data=new EnvironmentData();
    }

    public void initView(Context context){
        setBackgroundColor(0xffffffff);

        back=new Button(context);
        LayoutParams lp=new LayoutParams(WC,WC);
        back.setText("返回");
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(back,lp);

        next=new Button(context);
        lp=new LayoutParams(WC,WC);
        next.setText("下一页");
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(next,lp);


        centerText =new TextView(context);
        centerText.setText("Page2");
        centerText.setGravity(Gravity.CENTER);
        centerText.setTextColor(0xff009933);
        centerText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        lp=new LayoutParams(WC,WC);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(centerText,lp);


    }

    private void initListener(){
        back.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
    }

    private OnClickListener onClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==back){
                PageLoader.back(getContext());
            }
            if(v==next){
                PageLoader.load(getContext(), PageBox.PAGE_THIRD);
            }
        }
    };

    private void showInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                data= DecodeJson.getEnvironmentData();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(data!=null) {
                            centerText.append(
                                    "\n\n温度："+data.tem +"℃"+
                                            "\n湿度："+data.hum+"%\n"+
                                            (data.isPeo?"有人":"没人")+"\n"+
                                            (data.isSmoke?"有烟":"没烟")
                            );
                        }else {
                            Toast.makeText(getContext(), "网络错误，请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).start();

    }
}
