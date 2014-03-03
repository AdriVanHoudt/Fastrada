package unit.communication;

import be.fastrada.networking.WebRequest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bavo on 27/02/14.
 */
public class WebRequestTest
{
    private final WebRequest webRequest;
    private final WebRequest webRequest2;
    private final byte[] packet;


    public WebRequestTest() {
        webRequest = new WebRequest(50);
        webRequest2 = new WebRequest(25);
        packet = new byte[10];

        for (int i = 0; i < 10; ++i)
        {
            byte b = Byte.parseByte("" + i, 2);

            packet[i] = b;
        }
    }
    //groeperen per aantal
    //na x milliseconden flushen



}
