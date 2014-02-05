package be.fastrada;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ProgressBar;
import junit.framework.Assert;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class be.fastrada.MainTest \
 * be.fastrada.tests/android.test.InstrumentationTestRunner
 */
public class MainTest extends ActivityInstrumentationTestCase2<Main> {

    public MainTest() {
        super("be.fastrada", Main.class);
    }

    public void testProgressBar() throws Exception {
        Main activity = getActivity();
        ProgressBar bar = (ProgressBar) activity.findViewById(R.id.rpmIndicator);

        // Set RPM max
        bar.setMax(5000);

        // Assert value correct
        Assert.assertEquals("RPM max should be 5000", 5000, bar.getMax());

        // Vary values of rpm indicator
        bar.setProgress(200);
        Assert.assertEquals("RPM max should be 200", 200, bar.getProgress());
        bar.setProgress(1200);
        bar.setProgress(700);
        bar.setProgress(900);
        Assert.assertEquals("RPM max should be 900", 900, bar.getProgress());
        bar.setProgress(800);

    }

    public void testMeters() throws Exception {
        Main activity = getActivity();
        HoloCircularProgressBar speedMeter = (HoloCircularProgressBar) activity.findViewById(R.id.speedIndicator);
        HoloCircularProgressBar themormeter = (HoloCircularProgressBar) activity.findViewById(R.id.thermometer);

        Assert.assertEquals("Progress expected = 0.8", 0.8f, speedMeter.getProgress());
        Assert.assertEquals("Progress expected = 0.8", 0.8f, themormeter.getProgress());


        // Cannot call setProgress -  Only the original thread that created a view hierarchy can touch its views - Custom view shortcoming
    /*    themormeter.setProgress(0.3f);
        speedMeter.setProgress(0.3f);

        Assert.assertEquals("Progress expected = 0.3", 0.3f, speedMeter.getProgress());
        Assert.assertEquals("Progress expected = 0.3", 0.3f, themormeter.getProgress());    */

    }
}
