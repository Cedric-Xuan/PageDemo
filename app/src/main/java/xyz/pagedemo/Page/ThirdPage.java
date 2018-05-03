package xyz.pagedemo.Page;

import android.content.Context;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import xyz.pagedemo.framework.BasePage;
import xyz.pagedemo.framework.PageBox;
import xyz.pagedemo.framework.PageLoader;
import xyz.pagedemo.framework.http.DecodeJson;

/**
 * Created by xyz on 2017/5/5.
 */

public class ThirdPage extends BasePage{
    private Button back;
    private Button next;
    private TextView centerText;
    private EditText temText;
    private EditText humText;
    private Button updateBtn;

    private Handler handler=new Handler();

    public ThirdPage(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
        init(context);
    }

    public ThirdPage(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    public ThirdPage(Context context){
        super(context);
        init(context);
    }


    public void init(Context context) {
        initView(context);
        initListener();
    }

    public void initView(Context context){
        setBackgroundColor(0xffffffff);

        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(MP,MP);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(linearLayout,llp);

        RelativeLayout titleLayout=new RelativeLayout(context);
        llp=new LinearLayout.LayoutParams(MP,WC);
        linearLayout.addView(titleLayout,llp);

        back=new Button(context);
        RelativeLayout.LayoutParams rlp=new RelativeLayout.LayoutParams(WC,WC);
        back.setText("返回");
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleLayout.addView(back,rlp);

        next=new Button(context);
        rlp=new LayoutParams(WC,WC);
        next.setText("下一页");
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP|RelativeLayout.ALIGN_PARENT_RIGHT);
        titleLayout.addView(next,rlp);

//        LinearLayout mainLayout=new LinearLayout(context);
//        llp=new LinearLayout.LayoutParams(MP,WC);
//        llp.weight=1;
//        addView(mainLayout,llp);

        centerText =new TextView(context);
        centerText.setText("Page3\n修改数据并上传");
        centerText.setGravity(Gravity.CENTER);
        centerText.setTextColor(0xff000066);
        centerText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        llp=new LinearLayout.LayoutParams(WC,WC);
        llp.gravity=Gravity.CENTER_HORIZONTAL;
        linearLayout.addView(centerText,llp);


        temText =new EditText(context);
        temText.setTextColor(0xff000066);
        temText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        temText.setInputType(InputType.TYPE_CLASS_NUMBER);
        temText.setText("28");
        llp=new LinearLayout.LayoutParams(240,WC);
        llp.gravity=Gravity.CENTER_HORIZONTAL;
        linearLayout.addView(temText,llp);

        humText =new EditText(context);
        humText.setTextColor(0xff000066);
        humText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
        humText.setInputType(InputType.TYPE_CLASS_NUMBER);
        humText.setText("57");
        llp=new LinearLayout.LayoutParams(240,WC);
        llp.gravity=Gravity.CENTER_HORIZONTAL;
        linearLayout.addView(humText,llp);

        updateBtn=new Button(context);
        updateBtn.setText("上传");
        llp=new LinearLayout.LayoutParams(WC,WC);
        llp.gravity=Gravity.CENTER_HORIZONTAL;
        linearLayout.addView(updateBtn,llp);



    }

    private void initListener(){
        back.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
        centerText.setOnClickListener(onClickListener);
        updateBtn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v==back){
                PageLoader.back(getContext());
            }

            if(v==next){
                PageLoader.load(getContext(), PageBox.PAGE_THIRD);
            }

            if(v==centerText){
                Log.e("num",PageLoader.getPageNum()+"");
            }

            if(v==updateBtn){
                if(temText.getText()!=null && humText.getText()!=null){
                    upDateData();
                }else {
                    Toast.makeText(getContext(), "温度和湿度不可为空哦~", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private void upDateData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject js = new JSONObject();
                try {
                    js.put("tem", Integer.parseInt(temText.getText().toString()));
                    js.put("hum", Integer.parseInt(humText.getText().toString()));
                    js.put("smoke", 1);
                    js.put("peo", 1);
                }catch (Exception e){
                    e.printStackTrace();
                }

                final String result=DecodeJson.updateEnvironmentData(js);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();

    }

}
