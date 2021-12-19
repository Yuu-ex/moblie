package com.example.myapplication.Data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShopDataLoader {
        public static String load(String urlString) {
            StringBuffer stringBuffer=new StringBuffer();
            try {
                URL url=new URL(urlString);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setUseCaches(false);
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    InputStream inputStream= httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                    String line="";
                    while( null!=(line=bufferedReader.readLine())){
                        stringBuffer.append(line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("test---",stringBuffer.toString());
            return stringBuffer.toString();
        }

}
