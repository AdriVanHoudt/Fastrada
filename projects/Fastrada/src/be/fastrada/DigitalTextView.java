package be.fastrada;

import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: M
 * Date: 5/02/14
 * Time: 8:58
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class DigitalTextView extends TextView {
    public DigitalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public DigitalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DigitalTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/DS-DIGI.otf");
        setTypeface(tf);
    }
}
