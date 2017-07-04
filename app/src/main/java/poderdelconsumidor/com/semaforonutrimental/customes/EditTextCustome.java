package poderdelconsumidor.com.semaforonutrimental.customes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by mikesaurio on 30/06/17.
 */

public class EditTextCustome extends android.support.v7.widget.AppCompatEditText {

    public EditTextCustome(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public EditTextCustome(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public EditTextCustome(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("MyriadPro-Semibold.otf", context);
        setTypeface(customFont);
    }
}