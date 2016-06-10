package curisteando.com.semaforonutrimental.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import curisteando.com.barcodez.SimpleScannerFragmentActivity;
import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.TipoResultado;
import curisteando.com.semaforonutrimental.utilidades.Utils;


public class ResultadosNutricionActivity extends ActionBarActivity implements View.OnClickListener {

    TextView textAzucar, textSodio, textGrasa;
    ImageView btn_twitter,btn_shared;
    Button saberMas, otro, compartir,saber_mas_btn;
    int tipoAlimento;
    private AlertDialog customDialog= null;	//Creamos el dialogo generico
    String text_saber_mas ="";

    private IconRoundCornerProgressBar resultadoAzucar, resultadoGrasa, resultadoSodio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_nutricion);
        restoreActionBar();
        cargaControles();

    }

    private void cargaControles() {
        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if(extras.getInt(Constantes.PARAM_COMIDA_BEBIDA) == Constantes.PARAM_COMIDA){
            ((LinearLayout) findViewById(R.id.recomendaciones_comida)).setVisibility(View.VISIBLE);
        }else{
            ((LinearLayout) findViewById(R.id.recomendaciones_bebida)).setVisibility(View.VISIBLE);
            if(extras.getString(Constantes.PARAM_AZUCAR_RESULT).equals("0")){
                NotZugarDialog().show();
            }

        }

        Utils.formatoTextView(this, findViewById(R.id.azucares_txt), R.color.text_black);
        Utils.formatoTextView(this, findViewById(R.id.grasa_txt), R.color.text_black);
        Utils.formatoTextView(this, findViewById(R.id.sodio_txt), R.color.text_black);
        Utils.formatoTextView(this, findViewById(R.id.explicacion_text), R.color.text_white);

        ((TextView) findViewById(R.id.explicacion_text)).setText(extras.getString(Constantes.PARAM_TEXT));



        Utils.formatoTextView(this, findViewById(R.id.saber_mas_btn), R.color.text_white);
        Utils.formatoTextView(this, findViewById(R.id.compartir_txt), R.color.text_dark_gray);


        LinearLayout ll_buton__back_scanner=(LinearLayout)findViewById(R.id.ll_buton__back_scanner);
        ll_buton__back_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultadosNutricionActivity.this.finish();
            }
        });


        btn_twitter = (ImageView) findViewById(R.id.btn_twitter);
        btn_twitter.setOnClickListener(this);
        btn_shared = (ImageView) findViewById(R.id.btn_share);
        btn_shared.setOnClickListener(this);
        saber_mas_btn = (Button) findViewById(R.id.saber_mas_btn);
        saber_mas_btn.setOnClickListener(this);

        if (extras.getString(Constantes.PARAM_AZUCAR_RESULT).equals("0") || extras.getString(Constantes.PARAM_AZUCAR_RESULT).equals("No contiene")) {
            ((TextView)findViewById(R.id.no_azucares_txt)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.azucares_txt)).setText("Azúcares");

        } else{
            resultadoAzucar = (IconRoundCornerProgressBar) findViewById(R.id.progress_azucar);
            resultadoAzucar.setVisibility(View.VISIBLE);
            resultadoAzucar.setProgressColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_AZUCAR_RESULT) + "")));
            resultadoAzucar.setProgress(findProgress(extras.getString(Constantes.PARAM_AZUCAR_RESULT) + ""));
            resultadoAzucar.setHeaderColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_AZUCAR_RESULT) + "")));
            resultadoAzucar.setIconImageResource(findIndicador(extras.getString(Constantes.PARAM_AZUCAR_RESULT) + ""));
            resultadoAzucar.setMax(100);
        }

        if (extras.getString(Constantes.PARAM_GRASA_RESULT).equals("0")|| extras.getString(Constantes.PARAM_GRASA_RESULT).equals("No contiene")) {
            ((TextView)findViewById(R.id.no_grasa_txt)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.grasa_txt)).setText("Grasa saturada");

        } else {
            resultadoGrasa = (IconRoundCornerProgressBar) findViewById(R.id.progress_grasa);
            resultadoGrasa.setVisibility(View.VISIBLE);
            resultadoGrasa.setProgressColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_GRASA_RESULT) + "")));
            resultadoGrasa.setProgress(findProgress(extras.getString(Constantes.PARAM_GRASA_RESULT) + ""));
            resultadoGrasa.setHeaderColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_GRASA_RESULT) + "")));
            resultadoGrasa.setIconImageResource(findIndicador(extras.getString(Constantes.PARAM_GRASA_RESULT) + ""));
            resultadoGrasa.setMax(100);
        }

        if (extras.getString(Constantes.PARAM_SODIO_RESULT).equals("0") || extras.getString(Constantes.PARAM_SODIO_RESULT).equals("No contiene")) {
            ((TextView)findViewById(R.id.no_sodio_txt)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.sodio_txt)).setText("Sodio");

        } else {
            resultadoSodio = (IconRoundCornerProgressBar) findViewById(R.id.progress_sodio);
            resultadoSodio.setVisibility(View.VISIBLE);
            resultadoSodio.setProgressColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_SODIO_RESULT) + "")));
            resultadoSodio.setProgress(findProgress(extras.getString(Constantes.PARAM_SODIO_RESULT) + ""));
            resultadoSodio.setHeaderColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_SODIO_RESULT) + "")));
            resultadoSodio.setIconImageResource(findIndicador(extras.getString(Constantes.PARAM_SODIO_RESULT) + ""));
            resultadoSodio.setMax(100);
        }


    }

    private String findColor(String resultado){
        if (resultado.equals("Alto")){
            return "#CF2027";
        }else if(resultado.equals("Medio")){
            return "#F6CE0C";
        }else if(resultado.equals("Bajo")){
            return "#56BA48";
        }else{
            return "#56BA48";
        }
    }

    private float findProgress(String resultado){
        if (resultado.equals("Alto")){
            return 100;
        }else if(resultado.equals("Medio")){
            return 50;
        }else if(resultado.equals("Bajo")){
            return 25;
        }else{
            return 0;
        }


    }

    private int findIndicador(String resultado){
        if (resultado.equals("Alto")){
            return R.drawable.circulo_alto;
        }else if(resultado.equals("Medio")){
            return R.drawable.circulo_medio;
        }else if(resultado.equals("Bajo")){
            return R.drawable.circulo_bajo;
        }else{
            return R.drawable.circulo_bajo;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==otro){
           // Intent intent = new Intent(getContext(), MainActivity.class);
            //startActivity(intent);
        } else if(v==compartir){
            muestraDialogoCompartir();
        } else if(v==saber_mas_btn){
            if (text_saber_mas != ""){
                saberMas(text_saber_mas).show();
            }else{
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.url_web)));
                startActivity(i);
            }
        }else if(v==btn_twitter){
            startActivity(new Utils().sendTwitter(getBaseContext(), "Para saber si lo que comes o bebes es alto en azúcar, grasas o sodio, descarga #semaforoNutrimental http://www.elpoderdelconsumidor.org"));
        }else if(v==btn_shared){
            startActivity(new Utils().share_all(getBaseContext(), "Para saber si lo que comes o bebes es alto en azúcar, grasas o sodio, descarga #semaforoNutrimental http://www.elpoderdelconsumidor.org"));
        }
    }

    private Context getContext(){
        return ResultadosNutricionActivity.this;
    }

    public void muestraDialogoCompartir() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Que el mundo se entere");

        final EditText input = new EditText(getContext());
        input.setHeight(100);
        input.setWidth(340);
        input.setGravity(Gravity.LEFT);
        input.setHint("¿Que alimento es?");

        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alertDialog.show();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar = Utils.getFormatActionBar(this, actionBar);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.bbva_back_indicator);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    /**
     * Dialogo que muestra el acerca de
     *
     * @return Dialog (regresa el dialogo creado)
     **/
    public Dialog saberMas(String texto)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_mas, null);
        builder.setView(view);
        builder.setCancelable(true);

        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.dialogo_mas_txt), R.color.text_white);
        ((TextView) view.findViewById(R.id.dialogo_mas_txt)).setText(texto);


        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.dialogo_mas_url), R.color.text_white);
        ((TextView) view.findViewById(R.id.dialogo_mas_url)).setText(Html.fromHtml("<a href=" + getString(R.string.url_web) + ">"+getString(R.string.more_information)+"</a>"));
        ((TextView) view.findViewById(R.id.dialogo_mas_url)). setMovementMethod(LinkMovementMethod.getInstance());
        //escucha del boton aceptar
        ((ImageView) view.findViewById(R.id.dialogo_acercade_btnAceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog.dismiss();
            }
        });
        return (customDialog=builder.create());// return customDialog;//regresamos el di�logo
    }


    /**
     * Dialogo que muestra el acerca de
     *
     * @return Dialog (regresa el dialogo creado)
     **/
    public Dialog NotZugarDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_sin_azucar, null);
        builder.setView(view);
        builder.setCancelable(true);

        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.text_azucar_dialog_1), R.color.text_white);
        ((TextView) view.findViewById(R.id.text_azucar_dialog_1)).setText(getString(R.string.not_zugar1));

        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.text_azucar_dialog_2), R.color.text_white);
        ((TextView) view.findViewById(R.id.text_azucar_dialog_2)).setText(getString(R.string.not_zugar2));

        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.text_azucar_dialog_3), R.color.text_white);
        ((TextView) view.findViewById(R.id.text_azucar_dialog_3)).setText(getString(R.string.not_zugar3));



        //escucha del boton aceptar
        ((ImageView) view.findViewById(R.id.dialogo_acercade_btnAceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog.dismiss();
            }
        });
        return (customDialog=builder.create());// return customDialog;//regresamos el di�logo
    }



}
