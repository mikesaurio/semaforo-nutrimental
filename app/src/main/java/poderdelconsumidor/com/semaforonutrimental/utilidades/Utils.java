package poderdelconsumidor.com.semaforonutrimental.utilidades;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import poderdelconsumidor.com.semaforonutrimental.R;

/**
 * Created by enrique on 16/04/15.
 *
 */
public class Utils {

    /**
     * Da formato al action bar de acuerdo a la seccion en la que se encuentra.
     *
     * @param actionBar actionBar
     * @return actionbar
     */
    public static ActionBar getFormatActionBar( ActionBar actionBar) {

        actionBar.setCustomView(R.layout.layout_bar);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        Toolbar toolbar=(Toolbar)actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.getContentInsetEnd();
        toolbar.setPadding(0, 0, 0, 0);


        return actionBar;
    }

    /**
     * Da el formato por default para los textviews.
     * @param context contexto de la app
     * @param textView  textView a cambiar
     */
    public static void formatoTextView(Context context, View textView, int color){
        Typeface typeFace=FontLoader.getTypeFace(context, FontLoader.DEFAULT);
        if(typeFace!=null)
            ((TextView)textView).setTypeface(typeFace);

        ((TextView)textView).setTextColor(ContextCompat.getColor(context,color));
    }

    public void sendTwitter(Context ctx, String shareText) {

        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        tweetIntent.setType("text/plain");

        PackageManager packManager = ctx.getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            ctx.startActivity(tweetIntent);
        } else {
            Toast.makeText(ctx, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }


    }



    private boolean doesPackageExist(Context ctx, String targetPackage) {

        PackageManager pm = ctx.getPackageManager();
        try {
             pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public Intent share_all(Context ctx, String shareText){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ctx.getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        return Intent.createChooser(sharingIntent, "Share via");
    }
}

enum FontLoader {
    DEFAULT ("Thonburi");

    private String name;
    private Typeface typeFace;

    FontLoader(final String name){
        this.name = name;
        typeFace=null;
    }

    public static Typeface getTypeFace(Context context,FontLoader loader){
        try {
            if(loader.typeFace==null){
                loader.typeFace=Typeface.createFromAsset(context.getAssets(), "fonts/"+loader.name+".ttf");
            }
            return loader.typeFace;
        } catch (Exception e) {
            return null;
        }
    }



}
