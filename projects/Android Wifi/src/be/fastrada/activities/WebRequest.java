package be.fastrada.activities;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Thomas on 14/02/14.
 */
public class WebRequest extends Thread {
    public WebRequest() {
        Log.d("CONSTRUCTOR", "Hij is aangemaakt");
    }

    @Override
    public void run() {
        final HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse;
        StatusLine statusLine;
        Log.d("VOORLOOP", "Hij is aan de loop");
        for (int i = 0; i < 100; i++) {
            Log.d("INLOOP", "Hij is in de loop");
            try {
                httpResponse = httpClient.execute(new HttpGet("http://google.com"));
                statusLine = httpResponse.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(out);
                    out.close();

                    Log.d("GOOGLECALL", out.toString());
                } else {
                    httpResponse.getEntity().getContent().close();
                    Log.d("STATUSERROR", statusLine.getReasonPhrase());
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
