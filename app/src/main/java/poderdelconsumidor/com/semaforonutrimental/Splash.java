package poderdelconsumidor.com.semaforonutrimental;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import poderdelconsumidor.com.barcodez.SimpleScannerFragmentActivity;
import poderdelconsumidor.com.semaforonutrimental.fragments.CameraPermissionHelper;

/**
 * <p>
 * Clase encargada de mostrar el logo durante n tiempo.
 * </p>
 *
 * @author Capitan Durango
 * @author Mikesaurio
 * @since 02/11/2014.
 */
public class Splash extends AppCompatActivity implements
        CameraPermissionHelper.CameraPermissionCallback {

    /**
     * Handler encargado de manejar el tiempo que dura la primer pantalla.
     */
    private Handler handler = new Handler();

    /**
     * Tiempo predefinido para que dure la primer pantalla.
     */
    private int TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CameraPermissionHelper.attach(getSupportFragmentManager());
        } else {
            handler.postDelayed(runnable, TIMEOUT);
        }
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

    @Override
    public void onCameraPermissionResult(boolean successful) {
        if (successful) {
            handler.postDelayed(runnable, TIMEOUT);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.message_camera_permission), Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
