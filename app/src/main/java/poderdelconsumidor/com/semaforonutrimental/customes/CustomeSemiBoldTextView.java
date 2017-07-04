package poderdelconsumidor.com.semaforonutrimental.customes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by mikesaurio on 30/06/17.
 */

public class CustomeSemiBoldTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomeSemiBoldTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomeSemiBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomeSemiBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("MyriadPro-Semibold.otf", context);
        setTypeface(customFont);
    }
}