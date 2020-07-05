package com.fw.utils;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yqf
 */
public class HttpHeaderIterator {

    public Map<String,String> getHeader(HttpURLConnection connection){
        int i = 1;
        Map<String,String> map = new HashMap<String, String>(16);

        while (true){
            String mine = connection.getHeaderFieldKey(i);
            if(mine==null){
                break;
            }

            map.put(mine,connection.getHeaderField(i));
            i++;
        }
        return map;
    }
}
