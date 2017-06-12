package curisteando.com.semaforonutrimental.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import cn.pedant.SweetAlert.SweetAlertDialog;
import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.TipoAlimento;
import curisteando.com.semaforonutrimental.utilidades.Utils;


/**
 * Pantalla principal en la cual se solicitan los datos de entrada.
 * @author Mikesaurio
 * @since 02/11/2014.
 */
public class CapturaDatosActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Control UI para calcular el semaforo.
     */
    private Button continuar, limpiar;

    private EditText entradaTamanioPorcion,entradaCalorias,entradaAzucares,entradaGrasa,entradaSodio,nombreProducto;
    private ImageView bebidaImg,alimentoImg;
    private LinearLayout ll_comidas,ll_bebidas;
    private String code,type_feed="drink";
    private AlertDialog customDialog= null;
    private SweetAlertDialog pDialog;
    private setValuesTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        restoreActionBar();
        inicializaVariables();
        inicializaControles();

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras.getBoolean(Constantes.CONST_IS_FOUND)) {
            (customDialog = mostrarExplica()).show();
        }
        code = extras.getString(Constantes.CONST_CODIGO_BARRAS);
    }

    /**
     * Inicializa las variables con su valor default.
     */
    private void inicializaVariables() {
        type_feed = Constantes.PARAM_COMIDA;
    }

    /**
     * Inicializa los controles con los valores default.
     */
    private void inicializaControles() {
        bebidaImg = (ImageView) findViewById(R.id.bebida_img);
        alimentoImg = (ImageView) findViewById(R.id.alimento_img);
        ll_comidas=(LinearLayout)findViewById(R.id.ll_comidas);
        ll_bebidas=(LinearLayout)findViewById(R.id.ll_bebidas);

        bebidaImg.setOnClickListener(this);
        alimentoImg.setOnClickListener(this);


        entradaTamanioPorcion = (EditText) findViewById(R.id.entradaTamanioPorcion);
        entradaCalorias =(EditText)findViewById(R.id.entradaCalorias);
        entradaAzucares = (EditText) findViewById(R.id.entradaAzucares);
        entradaGrasa = (EditText) findViewById(R.id.entradaGrasa);
        entradaSodio = (EditText) findViewById(R.id.entradaSodio);

        nombreProducto=(EditText)findViewById(R.id.nombreProducto);

        continuar = (Button) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);
        limpiar = (Button) findViewById(R.id.limpiar);
        limpiar.setOnClickListener(this);


        Utils.formatoTextView(this, findViewById(R.id.selecciona_producto_txt),R.color.text_black);
        Utils.formatoTextView(this, findViewById(R.id.bebida_txt),R.color.text_white);

        Utils.formatoTextView(this, findViewById(R.id.limpiar), R.color.text_white);
        Utils.formatoTextView(this, findViewById(R.id.continuar),R.color.text_white);

        bebidaImg.performClick();

    }

    /**
     * Asigna valores default para los spinner de azucares, grasas, sodio y tamaño por porcion.
     * @param alimento alimento que se asigna desde la entrada.
     */
    private void asignaDefaults(TipoAlimento alimento){
        switch (alimento){
            case ALIMENTO:{
                ((TextView)findViewById(R.id.tx_entradaTamanioPorcion)).setText("mg");
                ((TextView)findViewById(R.id.tx_entradaAzucares)).setText("g");
                ((TextView)findViewById(R.id.tx_entradaGrasa)).setText("g");
                ((TextView)findViewById(R.id.tx_entradaSodio)).setText("g");
                ayudaAlimento();
                break;
            }
            case BEBIDA:{
                ((TextView)findViewById(R.id.tx_entradaTamanioPorcion)).setText("ml");
                ((TextView)findViewById(R.id.tx_entradaAzucares)).setText("g");
                ((TextView)findViewById(R.id.tx_entradaGrasa)).setText("g");
                ((TextView)findViewById(R.id.tx_entradaSodio)).setText("g");
                ayudaBebida();
                break;
            }
        }
    }

    private void ayudaAlimento(){
        type_feed = Constantes.PARAM_COMIDA;

    }

    private void ayudaBebida(){
        type_feed = Constantes.PARAM_BEBIDA;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
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

    /**
     * Regresa el contexto actual de la aplicacion para utilizarlo
     * @return Contexto de la aplicación.
     */
    private Context getContext() {
        return CapturaDatosActivity.this;
    }

    @Override
    public void onClick(View v) {
        if( v == continuar ){
            if(entradaAzucares.getText().toString().equals("")||entradaGrasa.getText().toString().equals("")||
                    entradaSodio.getText().toString().equals("")||entradaTamanioPorcion.getText().toString().equals("")||
                    entradaCalorias.getText().toString().equals("")){
                Toast.makeText(getBaseContext(), getString(R.string.toast_validation),Toast.LENGTH_LONG).show();
            }else{
                mAuthTask = new setValuesTask(code, type_feed, nombreProducto.getText().toString(),
                        entradaTamanioPorcion.getText().toString(),
                        entradaCalorias.getText().toString(),
                        entradaAzucares.getText().toString(),
                        entradaGrasa.getText().toString(),
                        entradaSodio.getText().toString());
                mAuthTask.execute((Void) null);
            }
        }else if(v == alimentoImg){
            alimentoImg.setImageResource(R.drawable.ic_comida_azul);
            ll_comidas.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue_button));
            bebidaImg.setImageResource(R.drawable.ic_bebida_gris);
            ll_bebidas.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.gray_button));
            asignaDefaults(TipoAlimento.ALIMENTO);
        }else if(v == bebidaImg){
            bebidaImg.setImageResource(R.drawable.ic_bebida_azul);
            ll_bebidas.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.blue_button));
            alimentoImg.setImageResource(R.drawable.ic_comida_gris);
            ll_comidas.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.gray_button));
            asignaDefaults(TipoAlimento.BEBIDA);
        }else if(v == limpiar){
            entradaTamanioPorcion.setText("");
            entradaAzucares.setText("");
            entradaGrasa.setText("");
            entradaSodio.setText("");
            nombreProducto.setText("");
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar = Utils.getFormatActionBar(this, actionBar);
        actionBar.setHomeButtonEnabled(true);
    }

    /**
     * Dialogo que muestra el acerca de
     *
     * @return Dialog (regresa el dialogo creado)
     **/
    public AlertDialog mostrarExplica()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialogo_explica, null);
        builder.setView(view);
        builder.setCancelable(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ((TextView) view.findViewById(R.id.dialogo_acercade_title)).setText(fromHtml(getString(R.string.text_explica_title)));
        ((TextView) view.findViewById(R.id.dialogo_acercade_nota1)).setText(fromHtml(getString(R.string.text_not_found1)));
        ((TextView) view.findViewById(R.id.dialogo_acercade_nota2)).setText(fromHtml(getString(R.string.text_not_found2)));
        ((TextView) view.findViewById(R.id.dialogo_acercade_nota3)).setText(fromHtml(getString(R.string.text_not_found3)));
        ((TextView) view.findViewById(R.id.dialogo_acercade_nota4)).setText(fromHtml(getString(R.string.text_not_found4)));

        view.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        return dialog;// return customDialog;//regresamos el di�logo
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }


    private class setValuesTask extends AsyncTask<Void, Void, Boolean> {

        private String response_,code,type_feed,name,portion,energy_portion,sugar_portion,gs_portion,sodio_portion;

        setValuesTask(String code,String type_feed,String name,String portion,String energy_portion,String sugar_portion,String gs_portion,String sodio_portion) {
            this.code= code;
            this.type_feed= type_feed;
            this.name= name;
            this.portion= portion;
            this.energy_portion= energy_portion;
            this.sugar_portion= sugar_portion;
            this.gs_portion= gs_portion;
            this.sodio_portion= sodio_portion;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoad();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SyncHttpClient client = new SyncHttpClient();
                client.addHeader("Authorization", "Bearer d91pRselbVRPdGjWKkrayGtUYnqV6E");
                client.addHeader("Content-Type", "application/x-www-form-urlencoded");
                RequestParams rp =  new RequestParams();
                rp.put("contribution[code]", code);
                rp.put("contribution[type_feed]", type_feed);
                rp.put("contribution[name]", name);
                rp.put("contribution[portion]", portion);
                rp.put("contribution[energy_portion]", energy_portion);
                rp.put("contribution[sugar_portion]", sugar_portion);
                rp.put("contribution[gs_portion]", gs_portion);
                rp.put("contribution[sodio_portion]", sodio_portion);
                client.post("https://consumidor.herokuapp.com/api/contributions/create", rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        response_=response.toString();
                    }


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        response_=response.toString();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        response_=null;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        response_=null;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        response_=null;
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                        response_=responseString;
                    }
                });

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if(pDialog.isShowing()){
                pDialog.dismissWithAnimation();
            }
            if (success && response_ != null) {
                Log.e("*************", response_+"");
                Intent intent = new Intent(CapturaDatosActivity.this, ResultadosNutricionActivity.class);
                intent.putExtra(Constantes.CONS_RESPONSE, response_);
                startActivity(intent);
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public void dialogLoad(){
        pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Espere un momento, obteniendo información");
        pDialog.setCancelable(false);
        pDialog.show();
    }


}
