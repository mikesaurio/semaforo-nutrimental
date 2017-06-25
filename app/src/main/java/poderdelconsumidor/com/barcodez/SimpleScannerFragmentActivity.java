package poderdelconsumidor.com.barcodez;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import poderdelconsumidor.com.semaforonutrimental.R;
import poderdelconsumidor.com.semaforonutrimental.activities.CapturaDatosActivity;
import poderdelconsumidor.com.semaforonutrimental.utilidades.Constantes;
import poderdelconsumidor.com.semaforonutrimental.utilidades.Utils;

public class SimpleScannerFragmentActivity extends AppCompatActivity {
    private AlertDialog customDialog= null;
    ShareDialog shareDialog;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        shareDialog = new ShareDialog(this);

        try {
            setContentView(R.layout.activity_simple_scanner_fragment);
            Utils.formatoTextView(this, findViewById(R.id.instrucciones), R.color.text_black);
            LinearLayout ll_buton_scanner = (LinearLayout) findViewById(R.id.ll_buton_scanner);
            ll_buton_scanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SimpleScannerFragmentActivity.this, CapturaDatosActivity.class);
                    intent.putExtra(Constantes.CONST_CODIGO_BARRAS, "");
                    intent.putExtra(Constantes.CONST_IS_FOUND, false);
                    startActivity(intent);
                    SimpleScannerFragmentActivity.this.finish();
                }
            });

            ImageView iv_scaner_help=(ImageView)findViewById(R.id.iv_scaner_help);
            iv_scaner_help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    (customDialog = mostrarHelp()).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        restoreActionBar();
    }

    public void restoreActionBar() {
        Utils.getFormatActionBar(getSupportActionBar());
    }

    /**
     * Dialogo que muestra el acerca de
     *
     * @return Dialog (regresa el dialogo creado)
     **/
    public AlertDialog mostrarHelp()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_help, null);
        builder.setView(view);
        builder.setCancelable(true);

        view.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_twitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Utils().sendTwitter(getBaseContext(), "Para saber si lo que comes es saludable, descarga #EscanerNutrimental http://www.elpoderdelconsumidor.org");
            }
        });
        view.findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Utils().share_all(getBaseContext(), "Para saber si lo que comes o bebes es alto en azúcar, grasas o sodio, descarga #EscanerNutrimental http://www.elpoderdelconsumidor.org"));
            }
        });
        view.findViewById(R.id.btn_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(getString(R.string.url_web)))
                        .setQuote("Para saber si lo que comes es saludable, descarga #EscanerNutrimental http://www.elpoderdelconsumidor.org")
                        .build();

                shareDialog.show(content);
            }
        });

        view.findViewById(R.id.saber_mas_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.url_web)));
                startActivity(i);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        return dialog;
    }
}