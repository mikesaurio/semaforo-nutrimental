package curisteando.com.barcodez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.activities.CapturaDatosActivity;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.Utils;

public class SimpleScannerFragmentActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        try {
            setContentView(R.layout.activity_simple_scanner_fragment);
            Utils.formatoTextView(this, findViewById(R.id.instrucciones), R.color.text_black);
            LinearLayout ll_buton_scanner = (LinearLayout) findViewById(R.id.ll_buton_scanner);
            ll_buton_scanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SimpleScannerFragmentActivity.this, CapturaDatosActivity.class);
                    intent.putExtra(Constantes.CONST_CODIGO_BARRAS, 0);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar = Utils.getFormatActionBar(this, actionBar);
    }
}