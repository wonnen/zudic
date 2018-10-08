package yun.zudic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ZudictP{
    public String request(String _url, String sentences){
        URL url;
        HttpURLConnection conn;
        String jsonData;
        BufferedReader br = null;
        StringBuffer sb;
        String returnText = "";
        String targetUrl = _url + sentences;

        try{
            url = new URL(targetUrl);

            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/x-www-form-urlencoded");
            conn.setRequestMethod("GET");
            conn.connect();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            sb = new StringBuffer();

            while((jsonData = br.readLine()) != null){
                sb.append(jsonData);
            }

            returnText = sb.toString();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(br != null) br.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return returnText;
    }
}
