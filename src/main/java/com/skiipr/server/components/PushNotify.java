package com.skiipr.server.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class PushNotify {
    
    private static final String url = "https://go.urbanairship.com/";
    private static final String appKey = "81lVQCF3SoeQ7EyhZjUxgQ";
    private static final String masterSecret = "rn203frYQ2CXRIpauQM94Q";
    
    public static void notifyOrderReady(String apid) throws MalformedURLException, IOException{
        //String message = "{\"device_tokens\" : [\"" + apid +"\"]}";
        String message = "{\"android\" : {\"alert\":\"\"}, \"apids\": [\"" +apid + "\"]}";
        System.out.println(message);
        String path = url + "api/push/";
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(path);
        String authString = appKey + ":" + masterSecret;
        String encoded = Base64.encodeBase64URLSafeString(authString.getBytes());
        String auth = "Basic " + encoded;
        request.setHeader("Authorization", auth);
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(message));
        
        client.execute(request);
        client.getConnectionManager().shutdown();
    }
    
}
