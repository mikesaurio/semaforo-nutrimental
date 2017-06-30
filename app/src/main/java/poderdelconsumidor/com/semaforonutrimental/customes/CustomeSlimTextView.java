package poderdelconsumidor.com.semaforonutrimental.customes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by mikesaurio on 29/06/17.
 */

public class CustomeSlimTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomeSlimTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomeSlimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomeSlimTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("MyriadPro-LightSemiExt.otf", context);
        setTypeface(customFont);
    }
}