package xyz.pagedemo.framework;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import xyz.pagedemo.framework.view.IView;

/**
 * Created by xyz on 2017/5/3.
 */

public class PageLoader {
    private static IView iView;
    private static List<BasePage> pageList;
    private static Class cls;
    public static void init(IView iv){
        iView=iv;
        PageBox.bind();
        pageList=new ArrayList<BasePage>();
    }

    public static void load(Context context,int pageId){
        BasePage page=null;
        Class<?> PageClass=PageBox.getPageClass(pageId);

        try{
            page = instantiateModule(context, PageClass);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(iView!=null && page!=null){
            iView.addPage(context, page);
            pageList.add(page);
        }

        /****/
        cls=page.getClass();

        /****/
        if(pageList.size()>5){
            removePage(context,1);
        }

    }

    public static void back(Context context){
        removePage(context,pageList.size()-1);
    }

    public static void removePage(Context context,int position){

        if(position>0 && getPageNum()>0) {
            BasePage lastPage=null;


            if(position==getPageNum()-1){
                lastPage=getPage(position-1);
            }else {
                lastPage=getPage(getPageNum()-1);
            }


            if (iView != null && lastPage!=null) {
                iView.removePage(context, position, lastPage);
                pageList.remove(position);
            }
        }


    }

    private static BasePage instantiateModule(Context context,Class<?> cls) throws Exception{
        Constructor<?> c=cls.getConstructor(Context.class);
        if(c!=null){
            return (BasePage)c.newInstance(context);
        }

        return null;
    }



    public static BasePage getPage(int position){
        return pageList.get(position);
    }

    public static BasePage getTopPage(){
        return pageList.get(getPageNum()-1);
    }

    public static int getPageNum(){
        return pageList.size();
    }



    //调用页面内方法
    public static void callMethod(String method,Object... params) {
        Object obj = getTopPage();
        if (obj != null) {

            if (cls != null) {
                Method[] methods = cls.getDeclaredMethods();

                if(methods !=null) {

                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].getName().equals(method)) {

                            Class<?>[] types = methods[i].getParameterTypes();
                            int num1 = 0;
                            int num2 = 0;

                            if (params != null) {
                                num1 = params.length;
                            }

                            if (types != null) {
                                num2 = types.length;
                            }

                            if (num1 == num2) {

                                try {
//                                    Method m = cls.getDeclaredMethod(method);
                                    methods[i].invoke(obj, params);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                            }


                        }

                    }


                }

            }
        }
    }




}
