package xyz.pagedemo.framework.http;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import xyz.pagedemo.Bean.EnvironmentData;

/**
 * Created by xyz on 2017/5/10.
 */

public  class DecodeJson {
    private final static String URL_BASE="http://120.27.113.129/GPRS/";

    /**
     * 获取数据
     * method:GET
     */
    private static final String URL_DATA=URL_BASE+"data";

    /**
     * 上传数据
     * method:GET
     */
    private static final String URL_UPDATE=URL_BASE+"update";


    /*********************************************************************************************/
    /**
     * 获取数据
     */
    public static EnvironmentData getEnvironmentData(){
        EnvironmentData data=null;
        String result=getJson(URL_DATA,"GET",null);

        if (result != null && !TextUtils.isEmpty(result)) {

            try {
                JsonQuery jq=new JsonQuery(result);
                if(jq.getInt("code") == 0){
                    if(!TextUtils.isEmpty(jq.getString("data"))){
                        data=new EnvironmentData();
                        JsonQuery dq=new JsonQuery(jq.getString("data"));
                        data.tem=dq.getInt("tem");
                        data.hum=dq.getInt("hum");
                        data.isSmoke=dq.getBoolean("smoke");
                        data.isPeo=dq.getBoolean("peo");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return data;
    }


    /**
     * 上传数据
     */
    public static String updateEnvironmentData(JSONObject paramsJson){
        String result=getJson(URL_UPDATE,"GET",paramsJson);
        Log.e("update",result);
        return result;
    }


    /*********************************************************************************************/
    /**
     * 获取JSON
     */
    private static String getJson(String url,String method,JSONObject paramsJson){
        if(url==null) return null;
        return new HttpExecutor2().connServerForResult(url,method,paramsJson);
    }


}
