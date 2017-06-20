package poderdelconsumidor.com.semaforonutrimental.customes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by mikesaurio on 20/06/17.
 */

public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(),  "fonts/"+fontname);
            } catch (Exception e) {
                Log.e("********", "Fala");
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }

}
