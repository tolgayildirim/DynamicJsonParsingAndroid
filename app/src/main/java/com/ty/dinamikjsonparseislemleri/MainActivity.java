package com.ty.dinamikjsonparseislemleri;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ty.globalurl.GlobalURL;
import com.ty.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    WebService wb = new WebService();
    GlobalURL gu = new GlobalURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ana thread içerisinde network işlemleri izni
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if(isNetworkAvailable(getBaseContext())) {//internet var mı
            wb.webService(gu.url + "get_recent_posts/", "GET", null, MainActivity.class);
            if (wb.mesajlar != null) {
                try {
                    JSONObject MainData = new JSONObject(wb.mesajlar);//alınan json objesi
                    for (int i = 0; i < MainData.length(); i++) {// json objesinin boyutu kadar objeler arasında ilerleyeceğiz
                        /*
                        Iterator Java koleksiyonları(Java - Collections) yapısı içerisinde interface olarak kullanılabilmektedir
                        Iterator<E> iterator() : Collection yapısında objeleri get etmeye  , remove etmeye yada obje listesi üzerinde dolaşmaya yarar
                         */
                        Iterator<String> iter = MainData.keys();
                        /*
                        HashMap Java'da sıkça kullanılan bir veri saklama yoludur, Map Interface'i kullanan sınıflardır.
                         */
                        HashMap<String, String> CV = new HashMap<String, String>();//json objelerini Name ve Value olarak saklayabilmek için bir hashmap oluşturduk
                        while (iter.hasNext()) {//json objesi içerisinde dolaşım
                            String name = iter.next();// json objesinde name i alıyoruz
                            String value = MainData.getString(name);//json objesinde value alıyoruz
                            CV.put(name,value); //name, value
                            Log.i("Parsing " + "name " + name, " Value " + value);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.i("internet kontrol ","internet bağlantınız bulunmamaktadır");
            }
        }
    }
    //internet kontrolü
    // manifest dosyasında izin gerekli <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
