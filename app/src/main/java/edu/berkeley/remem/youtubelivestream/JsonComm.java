package edu.berkeley.remem.youtubelivestream;

import android.os.AsyncTask;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by root on 11/27/14.
 */


public class JsonComm extends AsyncTask<String, Void, Void> {
    private String url;
    private Map params;
    public SetUp.asyncComp completion;

    public JsonComm(String mUrl, Map mParams) {
        url = mUrl;
        params = mParams;
        String killAppend = "false";

    }

    @Override
    protected Void doInBackground(String... strings) {
        System.out.println("Inside of makeRequest");

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        Iterator iter = params.entrySet().iterator();

        JSONObject jsonPOST = new JSONObject();

        try {
            while (iter.hasNext()) {
                Map.Entry pairs = (Map.Entry) iter.next();
                String key = (String) pairs.getKey();
                String value = (String) pairs.getValue();
                jsonPOST.put(key, value);
            }

            StringEntity se = new StringEntity(jsonPOST.toString());
            httpPost.setEntity(se);
            //httpPost.setHeader("deviceType", "Android");
            httpPost.setHeader("Content-type", "application/json");

            ResponseHandler responseHandler = new BasicResponseHandler();
            String response = (String) httpClient.execute(httpPost, responseHandler);
            System.out.println("My initial response is " + response);

            if (response.equals("false") || response.equals("true")) {
                System.out.println("Result in onPostComplete is " + response);
                completion.onComplete(response);
            }
            //System.out.println("posted in try");
            //return response;
        } catch (Exception e) {
            System.out.println("Error is" + e);
        }
        return null;
    }

}