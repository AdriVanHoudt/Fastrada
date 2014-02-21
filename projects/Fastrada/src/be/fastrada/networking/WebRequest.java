package be.fastrada.networking;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Class to test the simultaneous connection via wifi and 3G
 */
public class WebRequest extends Thread {
    private int amount;

    public WebRequest(int amount) {
        this.amount = amount;
    }

    @Override
    public void run() {
        final HttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse;
        StatusLine statusLine;

        for (int i = 0; i < amount; i++) {
            try {
                httpResponse = httpClient.execute(new HttpGet("http://google.com"));
                statusLine = httpResponse.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();

                    httpResponse.getEntity().writeTo(out);
                    out.close();
                } else {
                    httpResponse.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
