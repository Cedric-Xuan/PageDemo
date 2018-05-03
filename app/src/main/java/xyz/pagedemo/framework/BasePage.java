package xyz.pagedemo.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import xyz.pagedemo.framework.view.IPage;

/**
 * Created by xyz on 2017/5/2.
 */

public class BasePage extends RelativeLayout implements IPage {

    public static int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    public static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    public BasePage(Context context, AttributeSet attributeSet,int defStyle){
        super(context,attributeSet,defStyle);
        init(context);
    }

    public BasePage(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    public BasePage(Context context){
        super(context);
        init(context);
    }

    private void init(Context context){
        LayoutParams lp=new LayoutParams(MP,MP);
        setLayoutParams(lp);
        initListener(context);
    }

    private void initListener(final Context context){
        setOnTouchListener(onTouchListener);
    }

    private OnTouchListener onTouchListener=new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };


    @Override
    public void onClose() {

    }

    @Override
    public boolean onBack() {
        return false;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

}
