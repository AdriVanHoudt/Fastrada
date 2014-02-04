package com.example.Fastrada_Android;

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
 * -e class com.example.Fastrada_Android.MainTest \
 * com.example.Fastrada_Android.tests/android.test.InstrumentationTestRunner
 */
public class MainTest extends ActivityInstrumentationTestCase2<Main> {

    public MainTest() {
        super("com.example.Fastrada_Android", Main.class);
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
}
