package poderdelconsumidor.com.semaforonutrimental.customes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mikesaurio on 20/06/17.
 */

public class CustomeTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomeTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Thonburi.ttf", context);
        setTypeface(customFont);
    }
}