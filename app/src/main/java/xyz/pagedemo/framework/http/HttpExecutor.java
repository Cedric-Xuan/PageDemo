package xyz.pagedemo.framework;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by xyz on 2017/5/9.
 */

public class HttpExecutor {

    public String connServerForResult(String url){
        HttpGet httpGet=new HttpGet(url);
        String ResultString=null;

        try{
            HttpClient httpClient=new DefaultHttpClient();
            HttpResponse httpResponse=httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                ResultString= EntityUtils.toString(httpResponse.getEntity());
            }

        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResultString;
    }

}
