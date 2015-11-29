package curisteando.com.semaforonutrimental.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.TipoResultado;
import curisteando.com.semaforonutrimental.utilidades.Utils;


public class ResultadosNutricionActivity extends ActionBarActivity implements View.OnClickListener {

    TextView textAzucar, textSodio, textGrasa;
    Button saberMas, otro, compartir;
    int tipoAlimento;


    private IconRoundCornerProgressBar resultadoAzucar, resultadoGrasa, resultadoSodio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_nutricion);
        restoreActionBar();
        cargaControles();
    }

    private void cargaControles(){

        Utils.formatoTextView(this, findViewById(R.id.azucares_txt),R.color.text_black, 24);
        Utils.formatoTextView(this, findViewById(R.id.grasa_txt),R.color.text_black, 24);
        Utils.formatoTextView(this, findViewById(R.id.sodio_txt),R.color.text_black, 24);

        Utils.formatoTextView(this, findViewById(R.id.saber_mas_btn),R.color.text_white, 18);
        Utils.formatoTextView(this, findViewById(R.id.compartir_txt),R.color.text_dark_gray, 18);

        /*textAzucar = (TextView) findViewById(R.id.textAzucar);
        textSodio = (TextView) findViewById(R.id.textSodio);
        textGrasa = (TextView) findViewById(R.id.textGrasa);

        saberMas = (Button) findViewById(R.id.saberMas);
        otro = (Button) findViewById(R.id.otro);
        compartir = (Button) findViewById(R.id.compartir);
        saberMas.setOnClickListener(this);
        otro.setOnClickListener(this);
        compartir.setOnClickListener(this);

        Intent intent = getIntent();
        textAzucar.setText(intent.getStringExtra(Constantes.PARAM_AZUCAR_RESULT));
        textSodio.setText(intent.getStringExtra(Constantes.PARAM_SODIO_RESULT));
        textGrasa.setText(intent.getStringExtra(Constantes.PARAM_GRASA_RESULT));*/
        //tipoAlimento = intent.getIntExtra(Constantes.PARAM_TIPO_ALIMENTO);

        Intent i = getIntent();
        Bundle extras = i.getExtras();

        Log.e("***************",extras.getString(Constantes.PARAM_AZUCAR_RESULT)+"");
        Log.e("***************",extras.getString(Constantes.PARAM_GRASA_RESULT)+"");
        Log.e("***************",extras.getString(Constantes.PARAM_SODIO_RESULT)+"");
       // Intent intent = getIntent();
       // TipoResultado resultadoAzucarCalc, resultadoGrasaCalc, resultadoSodioCalc;
        //resultadoAzucarCalc = (TipoResultado) intent.getSerializableExtra(Constantes.PARAM_AZUCAR_RESULT);
        //resultadoGrasaCalc = (TipoResultado) intent.getSerializableExtra(Constantes.PARAM_GRASA_RESULT);
        //resultadoSodioCalc = (TipoResultado) intent.getSerializableExtra(Constantes.PARAM_SODIO_RESULT);

        resultadoAzucar = (IconRoundCornerProgressBar) findViewById(R.id.progress_azucar);
        resultadoGrasa = (IconRoundCornerProgressBar) findViewById(R.id.progress_grasa);
        resultadoSodio = (IconRoundCornerProgressBar) findViewById(R.id.progress_sodio);

        resultadoAzucar.setProgressColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_AZUCAR_RESULT)+"")));
        resultadoGrasa.setProgressColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_GRASA_RESULT)+"")));
        resultadoSodio.setProgressColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_SODIO_RESULT) + "")));

        resultadoAzucar.setProgress(findProgress(extras.getString(Constantes.PARAM_AZUCAR_RESULT) + ""));
        resultadoGrasa.setProgress(findProgress(extras.getString(Constantes.PARAM_GRASA_RESULT)+""));
        resultadoSodio.setProgress(findProgress(extras.getString(Constantes.PARAM_SODIO_RESULT) + ""));

        resultadoAzucar.setHeaderColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_AZUCAR_RESULT) + "")));
        resultadoGrasa.setHeaderColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_GRASA_RESULT)+"")));
        resultadoSodio.setHeaderColor(Color.parseColor(findColor(extras.getString(Constantes.PARAM_SODIO_RESULT) + "")));

       /* resultadoAzucar.setIconImageResource(findIndicador(resultadoAzucarCalc));
        resultadoGrasa.setIconImageResource(findIndicador(resultadoGrasaCalc));
        resultadoSodio.setIconImageResource(findIndicador(resultadoSodioCalc));*/

        resultadoAzucar.setMax(100);
        resultadoGrasa.setMax(100);
        resultadoSodio.setMax(100);
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

    private int findIndicador(TipoResultado resultado){
        switch (resultado){
            case ALTO:{return R.drawable.circulo_alto; }
            case MEDIO:{return R.drawable.circulo_medio; }
            case BAJO:{return R.drawable.circulo_bajo; }
        }
        return R.drawable.logo_semaforo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_resultados_nutricion, menu);
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
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        } else if(v==compartir){

            muestraDialogoCompartir();



        } else if(v==saberMas){

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
}
