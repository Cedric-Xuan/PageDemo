package xyz.pagedemo.Page;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import xyz.pagedemo.framework.BasePage;
import xyz.pagedemo.framework.PageBox;
import xyz.pagedemo.framework.PageLoader;

/**
 * Created by xyz on 2017/5/3.
 */

public class HomePage extends BasePage{

    private TextView welcomeText;

    public HomePage(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
        init(context);
    }

    public HomePage(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    public HomePage(Context context){
        super(context);
        init(context);

    }

    private void init(Context context){
        initView(context);
        initListener();
    }

    private void initView(Context context){
        setBackgroundColor(0xffffffff);
        welcomeText =new TextView(context);
        welcomeText.setText("欢迎咯，难道能不欢迎吗\n点我看看吧");
        welcomeText.setGravity(Gravity.CENTER);
        welcomeText.setTextColor(0xffff3300);
        welcomeText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,27);
        LayoutParams lp=new LayoutParams(WC,WC);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(welcomeText,lp);


    }

    private void initListener(){
       welcomeText.setOnClickListener(onClickListener);
    }

    private OnClickListener onClickListener=new OnClickListener() {
        @Override
        public void onClick(View v) {
            PageLoader.load(getContext(), PageBox.PAGE_SECOND);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        Log.i("WP","onResume");
    }
}
