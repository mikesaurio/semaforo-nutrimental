package curisteando.com.semaforonutrimental;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

import curisteando.com.barcodez.SimpleScannerFragmentActivity;
import curisteando.com.semaforonutrimental.utilidades.Utils;

/**
 * <p>
 *     Clase encargada de mostrar el logo durante n tiempo.
 * </p>
 * @author Capitan Durango
 * @author
 * @since 02/11/2014.
 */
public class Splash extends ActionBarActivity {

    /**
     * Handler encargado de manejar el tiempo que dura la primer pantalla.
     */
    private Handler handler = new Handler();

    /**
     * Tiempo predefinido para que dure la primer pantalla.
     */
    private int TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(runnable, TIMEOUT);
        restoreActionBar();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIMEOUT);
            redireccionActivityPrincipal();
        }
    };

    /**
     * Redirecciona a la siguiente actividad pasando N segundos.
     */
    private void redireccionActivityPrincipal() {
        handler.removeCallbacksAndMessages(null);
        Intent homepage = new Intent(getContext(), SimpleScannerFragmentActivity.class);
        startActivity(homepage);
        this.finish();
    }

    private Context getContext() {
        return Splash.this;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar = Utils.getFormatActionBar(this, actionBar);
    }
}
