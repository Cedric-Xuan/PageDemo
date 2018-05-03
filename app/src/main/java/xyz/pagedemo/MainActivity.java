package xyz.pagedemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import xyz.pagedemo.Page.HomePage;
import xyz.pagedemo.framework.BasePage;
import xyz.pagedemo.framework.PageBox;
import xyz.pagedemo.framework.PageLoader;
import xyz.pagedemo.framework.view.IPage;
import xyz.pagedemo.framework.view.IView;

public class MainActivity extends Activity implements IView {

    private FrameLayout frameLayout;
    private IPage iPage;
    private long exitTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        frameLayout=(FrameLayout) findViewById(R.id.mainFramePage);

        PageLoader.init(this);

        PageLoader.load(getBaseContext(), PageBox.PAGE_WELCOME);

    }



    @Override
    public void addPage(Context context, BasePage page) {
        frameLayout.addView(page);
        iPage=page;

        if(iPage!=null){
            iPage.onStart();
            iPage.onResume();
        }

    }

    @Override
    public void removePage(Context context,int position,BasePage lastPage) {
        frameLayout.removeViewAt(position);

        if(iPage!=null){
            iPage.onStop();
            iPage.onClose();
        }

//        iPage=PageLoader.getPage(position-1);
        iPage=lastPage;
    }



    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Main","onStop");

        if(iPage!=null){
            iPage.onStop();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Main","onStart");

        if(iPage!=null){
            iPage.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Main","onResume");

        if(iPage!=null){
            iPage.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Main","onPause");

        if(iPage!=null){
            iPage.onPause();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Main","onRestart");

        if(iPage!=null){
            iPage.onRestart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Main","onDestroy");

        if(iPage!=null){
            iPage.onClose();
        }
    }


    //下方返回按键
    @Override
    public void onBackPressed() {
        //在主页时 检测是否按两次退出应用
        if(PageLoader.getTopPage().getClass() == HomePage.class){
            if(System.currentTimeMillis()-exitTime<2000){
                this.finish();
                System.exit(0);
            }else {
                exitTime=System.currentTimeMillis();
                Toast.makeText(getApplicationContext(),"再按一次返回键退出应用",Toast.LENGTH_SHORT).show();
            }
        }else {//不在主页时 执行页面返回操作
            PageLoader.back(getBaseContext());
        }

        return;

    }
}
