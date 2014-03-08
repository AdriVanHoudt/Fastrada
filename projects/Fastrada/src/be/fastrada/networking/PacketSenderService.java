package be.fastrada.networking;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author: Shana Steenssens
 * @version: 1.0 6/03/14 16:59
 */
public class PacketSenderService extends Service {
    private PacketSender packetSender;
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        this.packetSender = new PacketSender();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        packetSender.start();

        return START_STICKY;
    }
}
