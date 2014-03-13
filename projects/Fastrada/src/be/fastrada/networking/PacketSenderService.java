package be.fastrada.networking;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PacketSenderService extends Service {
    private PacketGrouper packetGrouper;
    private RestSender restSender;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        this.packetGrouper = new PacketGrouper();
        this.restSender = new RestSender();
        this.packetGrouper.setSender(restSender);
        //TODO: get name from UI
        this.restSender.setRaceName("");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(packetGrouper).start();
        return START_STICKY;
    }
}
