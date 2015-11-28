package curisteando.com.barcodez;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.utilidades.Utils;

public class SimpleScannerFragmentActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        try {
            setContentView(R.layout.activity_simple_scanner_fragment);
            Utils.formatoTextView(this, findViewById(R.id.instrucciones), R.color.text_black, 18);
        }catch(Exception e){
            e.printStackTrace();
        }
        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar = Utils.getFormatActionBar(this, actionBar);
    }
}