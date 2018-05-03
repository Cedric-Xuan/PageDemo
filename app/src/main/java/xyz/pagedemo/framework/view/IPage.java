package xyz.pagedemo.framework.view;

/**
 * Created by xyz on 2017/5/2.
 */

public interface IPage {
    public boolean onBack();
    public void onStop();
    public void onPause();
    public void onStart();
    public void onResume();
    public void onClose();
    public void onRestart();
}
