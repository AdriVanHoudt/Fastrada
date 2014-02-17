package be.fastrada.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.SocketException;

public class MyActivity extends Activity {
    private TextView serverStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.serverStatus = (TextView) findViewById(R.id.tv_server_status);

        try {
            final Server server = new Server(this);
            server.execute();
            serverStatus.setText("Running");
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
