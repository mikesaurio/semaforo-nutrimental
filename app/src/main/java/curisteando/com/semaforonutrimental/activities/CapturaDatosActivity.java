package curisteando.com.semaforonutrimental.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
     * Lista de las unidades a mostrar en el control.
     */
    private List<String> listaUnidades;
    /**
     * Lista de tipos de alimentos a mostrar en el control.
     */
    private List<String> listaTiposAlimentos;

    /**
     * Control UI para el tipo de Alimento.
     */
    private Spinner tipoAlimentoSpinner;

    /**
     * Control UI para el tamaño de porcion.
     */
    private Spinner tamanioPorcionSpinner;
    /**
     * Control UI para el azucares.
     */
    private Spinner azucaresSpinner;
    /**
     * Control UI para el grasa.
     */
    private Spinner grasaSpinner;
    /**
     * Control UI para sodio.
     */
    private Spinner sodioSpinner;

    /**
     * Control UI para calcular el semaforo.
     */
    private Button continuar;



    private EditText entradaTamanioPorcion;
    private EditText entradaAzucares;
    private EditText entradaGrasa;
    private EditText entradaSodio;
    private EditText nombreProducto;


    private ImageView bebidaImg;
    private ImageView alimentoImg;

    private TipoAlimento alimento = TipoAlimento.NINGUNO;
    private AlertDialog customDialog= null;	//Creamos el dialogo generico

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        restoreActionBar();
        inicializaVariables();
        inicializaControles();
        mostrarExplica().show();
    }

    /**
     * Inicializa las variables con su valor default.
     */
    private void inicializaVariables() {
        listaUnidades = new ArrayList<String>();
        listaUnidades.add(getString(R.string.gramos));
        listaUnidades.add(getString(R.string.kilocalorias));
        listaUnidades.add(getString(R.string.miligramos));
        listaUnidades.add(getString(R.string.mililitros));

        listaTiposAlimentos = new ArrayList<String>();
        listaTiposAlimentos.add(getString(R.string.alimento));
        listaTiposAlimentos.add(getString(R.string.bebida));
    }

    /**
     * Inicializa los controles con los valores default.
     */
    private void inicializaControles() {
        bebidaImg = (ImageView) findViewById(R.id.bebida_img);
        alimentoImg = (ImageView) findViewById(R.id.alimento_img);

        bebidaImg.setOnClickListener(this);
        alimentoImg.setOnClickListener(this);

        tamanioPorcionSpinner = (Spinner) findViewById(R.id.tamanioPorcionSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listaUnidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tamanioPorcionSpinner.setAdapter(adapter);

        azucaresSpinner = (Spinner) findViewById(R.id.azucaresSpinner);
        ArrayAdapter<String> adapterAzucares = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listaUnidades);
        adapterAzucares.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        azucaresSpinner.setAdapter(adapterAzucares);

        grasaSpinner = (Spinner) findViewById(R.id.grasaSpinner);
        ArrayAdapter<String> adapterGrasas = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listaUnidades);
        adapterGrasas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grasaSpinner.setAdapter(adapterGrasas);

        sodioSpinner = (Spinner) findViewById(R.id.sodioSpinner);
        ArrayAdapter<String> adapterSodio = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listaUnidades);
        adapterSodio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sodioSpinner.setAdapter(adapterSodio);

        entradaTamanioPorcion = (EditText) findViewById(R.id.edit_tamanio_porcion);
        entradaAzucares = (EditText) findViewById(R.id.entradaAzucares);
        entradaGrasa = (EditText) findViewById(R.id.entradaGrasa);
        entradaSodio = (EditText) findViewById(R.id.entradaSodio);
        nombreProducto = (EditText) findViewById(R.id.edit_nombre_producto);

        continuar = (Button) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);

        Utils.formatoTextView(this, findViewById(R.id.selecciona_producto_txt),R.color.text_black, 18);
        Utils.formatoTextView(this, findViewById(R.id.bebida_txt),R.color.text_black, 18);
        Utils.formatoTextView(this, findViewById(R.id.alimento_txt),R.color.text_black, 18);
        Utils.formatoTextView(this, findViewById(R.id.nombre_producto_txt),R.color.text_black, 18);
        Utils.formatoTextView(this, findViewById(R.id.tamanio_porcion_txt),R.color.text_black, 18);
        Utils.formatoTextView(this, findViewById(R.id.azucares_txt),R.color.text_black, 18);
        Utils.formatoTextView(this, findViewById(R.id.grasa_txt),R.color.text_black, 18);
        Utils.formatoTextView(this, findViewById(R.id.sodio_txt),R.color.text_black, 18);

        Utils.formatoTextView(this, findViewById(R.id.limpiar),R.color.text_dark_gray, 18);
        Utils.formatoTextView(this, findViewById(R.id.continuar),R.color.text_white, 18);

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
                tamanioPorcionSpinner.setSelection(0);//Selecciona la posicion 0 de la lista.
                azucaresSpinner.setSelection(0);
                grasaSpinner.setSelection(0);
                sodioSpinner.setSelection(2);
                ayudaAlimento();
                break;
            }
            case BEBIDA:{
                tamanioPorcionSpinner.setSelection(0);//Selecciona la posicion 0 de la lista.
                azucaresSpinner.setSelection(0);
                grasaSpinner.setSelection(0);
                sodioSpinner.setSelection(2);
                ayudaBebida();
                break;
            }
        }
    }

    private void ayudaAlimento(){
        nombreProducto.setHint(getString(R.string.ejemplo_producto_alimento));
    }

    private void ayudaBebida(){
        nombreProducto.setHint(getString(R.string.ejemplo_producto_bebida));
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
                Toast.makeText(getBaseContext(), "Debes llenar todos los campos",Toast.LENGTH_LONG).show();
            }else {

                ParametrosCalculo params = new ParametrosCalculo();
                params.setTipoAlimento(alimento);

                params.setAzucares(Double.parseDouble(entradaAzucares.getText().toString()));
                params.setGrasas(Double.parseDouble(entradaGrasa.getText().toString()));
                params.setSodio(Double.parseDouble(entradaSodio.getText().toString()));
                params.setTamanioPorcion(Double.parseDouble(entradaTamanioPorcion.getText().toString()));

                params.setTamanioPorcionMedida(TipoMedidas.values()[tamanioPorcionSpinner.getSelectedItemPosition()]);
                params.setAzucaresMedida(TipoMedidas.values()[azucaresSpinner.getSelectedItemPosition()]);
                params.setGrasasMedida(TipoMedidas.values()[grasaSpinner.getSelectedItemPosition()]);
                params.setSodioMedida(TipoMedidas.values()[sodioSpinner.getSelectedItemPosition()]);

                new Calculos(getContext()).execute(params);
            }
        }else if(v == alimentoImg){
            alimentoImg.setImageResource(R.drawable.alimento_azul);
            bebidaImg.setImageResource(R.drawable.bebida_gris);
            asignaDefaults(TipoAlimento.ALIMENTO);
        }else if(v == bebidaImg){
            bebidaImg.setImageResource(R.drawable.bebida_azul);
            alimentoImg.setImageResource(R.drawable.alimento_gris);
            asignaDefaults(TipoAlimento.BEBIDA);
        }
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
    public Dialog mostrarExplica()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialogo_explica, null);
        builder.setView(view);
        builder.setCancelable(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //metrics.widthPixels

        ((LinearLayout) view.findViewById(R.id.img_help_1)).setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.heightPixels / 2, 0.4f));
        ((LinearLayout) view.findViewById(R.id.img_help_2)).setLayoutParams(new LinearLayout.LayoutParams(metrics.widthPixels / 3, metrics.heightPixels / 2, 0.4f));

        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.dialog_lista), R.color.text_white, 10);
        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.dialog_nutrimental), R.color.text_white, 10);


        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.dialogo_acercade_tv_correo), R.color.text_white, 13);
        Utils.formatoTextView(getBaseContext(), view.findViewById(R.id.dialogo_acercade_nota), R.color.text_white, 13);

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
