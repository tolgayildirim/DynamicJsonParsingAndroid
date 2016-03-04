package com.ty.webservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class WebService extends AsyncTask<Void, Void, Void> {
	String index;
	private static final int TIMEOUT_MILLISEC = 0;
	public String mesajlar;

	BufferedReader in = null;
	String data = null;

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	public void webService(String url, String HttpMethod, ArrayList<NameValuePair> alinanDegerler,
			Class kulActivity) {

		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);

			
			if (HttpMethod == "GET") {

				HttpClient httpclient = new DefaultHttpClient();

				HttpGet request = new HttpGet();
				URI website = new URI(url);
				request.setURI(website);
				request.setHeader("where", "mobile");
				request.setHeader("Authorization", "");
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();

					String result = RestClient.convertStreamToString(instream);
					Log.i("Read from server", result);
					// Toast.makeText(this, result,
					// Toast.LENGTH_LONG).show();
					mesajlar = result;
				}

			} else {
				HttpPost request = new HttpPost(url);
				request.setEntity(new UrlEncodedFormEntity(alinanDegerler));

				request.setHeader("where", "mobile");
				request.setHeader("Authorization","");
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();

					String result = RestClient.convertStreamToString(instream);
					Log.i("Read from server", result);
					// Toast.makeText(this, result,
					// Toast.LENGTH_LONG).show();
					mesajlar = result;
				}
			}
			// If the response does not enclose an entity, there is no nee
		} catch (Throwable t) {
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
			mesajlar = "Request Failed: " + t.toString();

		}

	}

}
