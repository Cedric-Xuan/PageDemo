package xyz.pagedemo.framework.http;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Created by xyz on 2017/5/10.
 */

public class HttpExecutor2 {

    private HttpClient httpClient=null;
    int timeout=30000;

    public  String connServerForResult(String url,String method,JSONObject paramsJson){
        if(url==null) return null;
        String getUrl=url;

        httpClient=new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,timeout);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,timeout);

        HttpUriRequest httpUriRequest=null;

        method=method.toUpperCase();
        if(method=="GET") {

            if(paramsJson!=null) {
                 getUrl= makeGetUrl(url, paramsJson);
            }

            HttpGet httpGet = null;

            httpGet = new HttpGet(getUrl);

            httpUriRequest = httpGet;

        }else {

            HttpEntity httpEntity=null;
            MultipartEntityBuilder entitys = MultipartEntityBuilder.create();
            entitys.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

//            if(params != null && params.size() > 0)
//            {
//                for(int i = 0; i < params.size(); i++)
//                {
//                    pair = params.get(i);
//                    entitys.addBinaryBody(pair.first, pair.second.getBytes());
//                }
//            }


            if(paramsJson!=null && paramsJson.length()>0){
                Iterator itKeys= paramsJson.keys();
                while (itKeys.hasNext()){
                    try {
                        String key = itKeys.next().toString();
                        String value = paramsJson.get(key).toString();

                        entitys.addBinaryBody(key,value.getBytes());

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }


            httpEntity=entitys.build();
            HttpEntityWrapper wrapper=new HttpEntityWrapper(httpEntity);

            HttpPost httpPost=new HttpPost(url);
            httpPost.setEntity(wrapper);
            httpUriRequest=httpPost;
        }


        try {
            HttpResponse httpResponse = httpClient.execute(httpUriRequest);
            HttpEntity responseEntify =httpResponse.getEntity();
            InputStream is = responseEntify.getContent();
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                String result=read(is);

                return result;
            }else{
                String result = read(is);
                System.out.println(""+result);
                return result;
            }

        }catch (Exception e){
            Log.d("httpResponse", "Exception"+e.getMessage());
            e.printStackTrace();
        }finally{
            if(httpClient!=null && httpClient.getConnectionManager()!=null){
                httpClient.getConnectionManager().shutdown();
            }
        }

        return null;
    }

    private  String read(InputStream in)throws IOException
    {
        if(in == null)
            return null;
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for(String line = r.readLine(); line != null; line = r.readLine())
            sb.append(line);

        in.close();
        return sb.toString();
    }



    /**
     * 产生get方式带参URL
     */
    private static String makeGetUrl(String url,JSONObject paramsJson){
        String getUrl=null;

        if(url!=null){
            getUrl=url;
            if(paramsJson!=null){
                boolean isFirst=true;
                Iterator itKeys=paramsJson.keys();
                while (itKeys.hasNext()) {
                    if (isFirst) {
                        getUrl += "?";
                        isFirst = false;
                    } else {
                        getUrl += "&";
                    }

                    try {
                        String key = itKeys.next().toString();
                        String value =paramsJson.get(key).toString();

                        getUrl += key + "=" + value;

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }

        return getUrl;
    }

}
