package curisteando.com.semaforonutrimental.utilidades;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
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

    public Intent sendTwitter(Context ctx, String shareText)
    {
        Intent shareIntent;

        if(doesPackageExist(ctx, "com.twitter.android"))
        {
            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setClassName("com.twitter.android",
                    "com.twitter.android.PostActivity");
            shareIntent.setType("text/*");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
            return shareIntent;
        }
        else
        {
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + shareText;
            Uri uri = Uri.parse(tweetUrl);
            shareIntent = new Intent(Intent.ACTION_VIEW, uri);
            return shareIntent;
        }
    }



    public boolean doesPackageExist(Context ctx,String targetPackage) {

        PackageManager pm = ctx.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public Intent share_all(Context ctx, String shareText){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = shareText;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ctx.getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        return Intent.createChooser(sharingIntent, "Share via");
    }
}

enum FontLoader {
    DEFAULT ("Thonburi");

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
