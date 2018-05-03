package xyz.pagedemo.framework;

import android.util.SparseArray;

import xyz.pagedemo.Page.SecondPage;
import xyz.pagedemo.Page.ThirdPage;
import xyz.pagedemo.Page.HomePage;

/**
 * Created by xyz on 2017/5/4.
 */

public class PageBox {
    private static SparseArray<Class<?>> mClass;

    public static final int PAGE_WELCOME=170001;//欢迎页
    public static final int PAGE_SECOND=170002;//第二页
    public static final int PAGE_THIRD=170003;//第三页


    public static void bind(){
        mClass=new SparseArray<Class<?>>();

        mClass.put(PAGE_WELCOME, HomePage.class);
        mClass.put(PAGE_SECOND, SecondPage.class);
        mClass.put(PAGE_THIRD, ThirdPage.class);
    }


    public static Class<?> getPageClass(int pageId){
        if(mClass.size()>0){
            return mClass.get(pageId);
        }
     return  null;
    }
}
