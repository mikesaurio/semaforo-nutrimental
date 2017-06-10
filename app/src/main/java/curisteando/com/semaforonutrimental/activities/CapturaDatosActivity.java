package curisteando.com.semaforonutrimental.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.MessagePattern;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.calculos.Calculos;
import curisteando.com.semaforonutrimental.entities.ParametrosCalculo;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.TipoAlimento;
import curisteando.com.semaforonutrimental.utilidades.TipoMedidas;
import curisteando.com.semaforonutrimental.utilidades.Utils;

/**
 * <p>
 *     Pantalla principal en la cual se solicitan los datos de entrada.
 * </p>
 * @author Capitan Durango
 * @since 02/11/2014.
 */
public class CapturaDatosActivity extends ActionBarActivity implements View.OnClickListener {

    /**
     * Lista de tipos de alimentos a mostrar en el control.
     */
    private List<String> listaTiposAlimentos;


    /**
     * Control UI para calcular el semaforo.
     */
    private Button continuar,limpiar;

    private EditText entradaTamanioPorcion;
    private EditText entradaAzucares;
    private EditText entradaGrasa;
    private EditText entradaSodio;
    private EditText nombreProducto;
    private  int tipoProducto;

    private ImageView bebidaImg;
    private ImageView alimentoImg;
    private LinearLayout ll_comidas,ll_bebidas;

    private TipoAlimento alimento = TipoAlimento.NINGUNO;
    private AlertDialog customDialog= null;	//Creamos el dialogo generico

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
    }

    /**
     * Inicializa las variables con su valor default.
     */
    private void inicializaVariables() {

        listaTiposAlimentos = new ArrayList<String>();
        listaTiposAlimentos.add(getString(R.string.alimento));
        listaTiposAlimentos.add(getString(R.string.bebida));

        tipoProducto = Constantes.PARAM_COMIDA;
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
     * @param alimento
     *          Tipo de alimento que se asigna desde la entrada.
     */
    private void asignaDefaults(TipoAlimento alimento){
        this.alimento = alimento;
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
        tipoProducto = Constantes.PARAM_COMIDA;

    }

    private void ayudaBebida(){
        tipoProducto = Constantes.PARAM_BEBIDA;
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
     * Regresa el contexto actual de la aplicacion para utilizarlo.
     *
     * @return Contexto de la aplicación.
     */
    private Context getContext() {
        return CapturaDatosActivity.this;
    }

    @Override
    public void onClick(View v) {
        if( v == continuar ){
            if(entradaAzucares.getText().toString().equals("")||entradaGrasa.getText().toString().equals("")||
                    entradaSodio.getText().toString().equals("")||entradaTamanioPorcion.getText().toString().equals("")){
                Toast.makeText(getBaseContext(), getString(R.string.toast_validation),Toast.LENGTH_LONG).show();
            }else{

                ParametrosCalculo params = new ParametrosCalculo();
                params.setTipoAlimento(alimento);

                params.setAzucares(Double.parseDouble(entradaAzucares.getText().toString()));
                params.setGrasas(Double.parseDouble(entradaGrasa.getText().toString()));
                params.setSodio(Double.parseDouble(entradaSodio.getText().toString()));
                params.setTamanioPorcion(Double.parseDouble(entradaTamanioPorcion.getText().toString()));

                /*params.setTamanioPorcionMedida(TipoMedidas.values()[tamanioPorcionSpinner.getSelectedItemPosition()]);
                params.setAzucaresMedida(TipoMedidas.values()[azucaresSpinner.getSelectedItemPosition()]);
                params.setGrasasMedida(TipoMedidas.values()[grasaSpinner.getSelectedItemPosition()]);
                params.setSodioMedida(TipoMedidas.values()[sodioSpinner.getSelectedItemPosition()]);
                params.setTipoProducto(tipoProducto);*/

                new Calculos(getContext(),CapturaDatosActivity.this).execute(params);
            }
        }else if(v == alimentoImg){
            alimentoImg.setImageResource(R.drawable.ic_comida_azul);
            ll_comidas.setBackgroundColor(getResources().getColor(R.color.blue_button));
            bebidaImg.setImageResource(R.drawable.ic_bebida_gris);
            ll_bebidas.setBackgroundColor(getResources().getColor(R.color.gray_button));
            asignaDefaults(TipoAlimento.ALIMENTO);
        }else if(v == bebidaImg){
            bebidaImg.setImageResource(R.drawable.ic_bebida_azul);
            ll_bebidas.setBackgroundColor(getResources().getColor(R.color.blue_button));
            alimentoImg.setImageResource(R.drawable.ic_comida_gris);
            ll_comidas.setBackgroundColor(getResources().getColor(R.color.gray_button));
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

        ((ImageView) view.findViewById(R.id.dialog_close)).setOnClickListener(new View.OnClickListener() {
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


}
