package xyz.pagedemo.framework.http;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by xyz on 2017/5/10.
 */

public class JsonQuery {
    public JSONObject json;

    public JsonQuery(JSONObject json){
        this.json=json;
    }

    public JsonQuery(String json){
        if(json!=null && json.length()>0){
            try{
                this.json=new JSONObject(json);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public int getInt(String queryString,int defVal){
        int result=defVal;
        Object obj=get(queryString);
        if(obj!=null && obj instanceof Integer){
            result=(Integer)obj;
        }else if(obj!=null && obj instanceof String){
            try {
                result = Integer.parseInt((String) obj);
            }catch (NumberFormatException e){}
        }

        return result;
    }

    public int getInt(String queryString){
        return getInt(queryString,0);
    }

    public long getLong(String queryString, long defVal)
    {
        long result=defVal;
        Object obj=get(queryString);
        if(obj!=null){
            if(obj instanceof  Integer){
                result=(Integer)obj;
            }else if(obj instanceof Long){
                result=(Long)obj;
            }else if(obj!=null && obj instanceof String){
                try{
                    result=Long.parseLong((String)obj);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Long getLong(String queryString){
        return getLong(queryString,0);
    }

    public double getDouble(String queryString,double defVal){
        double result=defVal;
        Object obj=get(queryString);
        if(obj!=null){
            if(obj instanceof Double){
                result=(Double) obj;
            }else if(obj!=null && obj instanceof String){
                try{
                    result=Double.parseDouble((String)obj);
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public double getDouble(String queryString){
        return getDouble(queryString,0);
    }

    public float getFloat(String queryString, float defVal)
    {
        float result = defVal;
        Object obj = get(queryString);
        if(obj != null)
        {
            if(obj instanceof Float || obj instanceof Double)
            {
                result = (Float)obj;
            }
            else if(obj != null && obj instanceof String)
            {
                try {
                    result = Float.parseFloat((String)obj);
                } catch (NumberFormatException e) {
                }
            }
        }
        return result;
    }

    public float getFloat(String queryString)
    {
        return getFloat(queryString, 0);
    }


    public boolean getBoolean(String queryString)
    {
        boolean result = false;
        Object obj = get(queryString);
        if(obj != null)
        {
            if(obj instanceof Boolean)
            {
                result = (Boolean)obj;
            }
            else if(obj != null && obj instanceof Integer)
            {
                return ((Integer)obj) == 1;
            }
            else if(obj != null && obj instanceof String)
            {
                String str = (String)obj;
                if(str.length() > 0)
                {
                    return str.equals("1");
                }
            }
        }
        return result;
    }


    public String getString(String queryString)
    {
        String result = null;
        Object obj = get(queryString);
        if(obj != null)
        {
            result = obj.toString();
        }
        return result;
    }

    public JSONArray getJsonArray(String queryString){
        JSONArray result=null;
        Object obj=get(queryString);
        if(obj!=null && obj instanceof JSONArray){
            result=(JSONArray) obj;
        }

        return result;
    }

    public JsonQuery[] getJsonQueryArray(String querySring){
        JSONArray ja=getJsonArray(querySring);
        if(ja!=null){
            int length=ja.length();

            try {
                JsonQuery[] jqa = new JsonQuery[length];
                for (int i = 0; i < length; i++) {
                    jqa[i] = new JsonQuery(ja.getJSONObject(i));
                }
                return jqa;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public JsonQuery getJsonQuery(String queryString){
        JsonQuery result=null;
        Object obj=get(queryString);
        if(obj!=null){
            if(obj instanceof JSONObject){
                JSONObject js=(JSONObject)obj;
                result =new JsonQuery(js);
                return result;
            }
        }
        return null;
    }


    public Object get(String queryString){
        if(this.json==null)
            return null;

        String str=null;
        Object obj=null;
        JSONObject temp=json;

        try{
            int len = queryString.length();
            int pos1 = 0;
            int pos2 = queryString.indexOf('.');
            do
            {
                if(pos2 == -1)
                    pos2 = len;
                str = queryString.substring(pos1, pos2);
                obj = null;
                if(temp.has(str))
                {
                    obj = temp.get(str);
                    if(obj instanceof JSONObject)
                    {
                        temp = (JSONObject)obj;
                    }
                }else
                {
                    break;
                }
                if(pos2 >= len)
                    break;
                pos1 = pos2+1;
                pos2 = queryString.indexOf('.', pos1);
            }while(pos2 <= len);



        }catch (Exception e){
            e.printStackTrace();
        }

        return obj;
    }


}
