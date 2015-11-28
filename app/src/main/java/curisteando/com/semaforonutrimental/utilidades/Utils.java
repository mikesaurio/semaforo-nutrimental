package curisteando.com.semaforonutrimental.utilidades;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import curisteando.com.semaforonutrimental.R;

/**
 * Created by enrique on 16/04/15.
 */
public class Utils {

    /**
     * Da formato al action bar de acuerdo a la seccion en la que se encuentra.
     *
     * @param context
     * @param actionBar
     * @return
     */
    public static ActionBar getFormatActionBar(Context context, ActionBar actionBar) {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

        actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.bbva_back_indicator);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setCustomView(R.layout.layout_bar);
        return actionBar;
    }

    /**
     * Da el formato por default para los textviews.
     * @param context
     * @param textView
     * @param size
     */
    public static void formatoTextView(Context context, View textView, int color, int size){
        Typeface typeFace=FontLoader.getTypeFace(context, FontLoader.DEFAULT);
        if(typeFace!=null)
            ((TextView)textView).setTypeface(typeFace);

        ((TextView)textView).setTextColor(context.getResources().getColor(color));
        ((TextView)textView).setTextSize(size);
    }
}

enum FontLoader {
    DEFAULT ("zurichltcnbtlight");

    private String name;
    private Typeface typeFace;

    private FontLoader(final String name){
        this.name = name;
        typeFace=null;
    }

    public static Typeface getTypeFace(Context context,FontLoader loader){
        try {
            FontLoader item = loader;
            if(item.typeFace==null){
                item.typeFace=Typeface.createFromAsset(context.getAssets(), "fonts/"+item.name+".ttf");
            }
            return item.typeFace;
        } catch (Exception e) {
            return null;
        }
    }
}
