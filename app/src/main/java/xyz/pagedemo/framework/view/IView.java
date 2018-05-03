package xyz.pagedemo.framework.view;

import android.content.Context;

import xyz.pagedemo.framework.BasePage;


/**
 * Created by xyz on 2017/5/2.
 */

public interface IView {

    public void addPage(Context context, BasePage page);

    public void removePage(Context context,int position,BasePage page);



}
