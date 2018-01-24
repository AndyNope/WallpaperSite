/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_appli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author 5im14anbui
 */
public class JSON {

    private JSONObject jsonobject;
    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Object> maps = new ArrayList<>();

    
    public void getMap(String keyword) {
        // Create a trust manager that does not validate certificate chains like the default 

        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    //No need to implement.
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    //No need to implement.
                }
            }
        };
//         / Install the all
//        -trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println(e);
        }
        HttpURLConnection c = null;
        try {
            String url = "https://pixabay.com/api/?key=7653222-c1879dade83030fdd0c463af3&q="+keyword+"&image_type=photo&pretty=true";
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(1000);
            c.setReadTimeout(1000);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    String ls = System.getProperty("line.separator");
                    while ((line = r.readLine()) != null) {
                        sb.append(line);
                        sb.append(ls);
                    }
//                    JSONArray results = new JSONObject(sb.toString()).getJSONObject("");
//                    JSONArray address_components;
//                    for (Object array : results) {
//                        JSONObject jsonObject = (JSONObject) array;
//                        System.out.println(jsonObject.get("hits"));
//                    }
                    JSONObject ob = new JSONObject(sb.toString());
                    JSONArray results = new JSONObject(sb.toString()).getJSONArray("hits");
                    for (Object array : results) {
                        JSONObject jsonObject = (JSONObject) array;
                        System.out.println("Link:"+jsonObject.get("webformatURL"));
                        System.out.println("Likes:"+jsonObject.get("likes"));
                        System.out.println("Height:"+jsonObject.get("imageHeight"));
                        System.out.println("Width:"+jsonObject.get("imageWidth"));
                        
                    }
                    r.close();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    } public void geoLocating() {
        JSONArray map = null;
        try {
            HttpURLConnection connection = new HttpURLConnection(new URL("https://maps.googleapis.com/maps/api/geocode/json?address=8400Winterthur")) {

                @Override
                public void disconnect() {

                }

                @Override
                public boolean usingProxy() {
                    return false;
                }

                @Override
                public void connect() throws IOException {

                }
            };

        } catch (MalformedURLException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSON json = new JSON();
        json.getMap("Monster");
        json.getMap("Kind");
    }

}
