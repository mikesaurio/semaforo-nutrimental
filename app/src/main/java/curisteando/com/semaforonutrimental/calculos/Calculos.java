package curisteando.com.semaforonutrimental.calculos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Map;

import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.activities.ResultadosNutricionActivity;
import curisteando.com.semaforonutrimental.entities.ParametrosCalculo;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.TipoAlimento;
import curisteando.com.semaforonutrimental.utilidades.TipoMedidas;
import curisteando.com.semaforonutrimental.utilidades.TipoResultado;


/**
 * <p>
 * Clase encargada de generar los calculos del semaforo en segundo plano.
 * </p>
 *
 * @author Capitan Durango.
 * @since 02/11/2014.
 */
public class Calculos extends AsyncTask<ParametrosCalculo, Void, Map<String, String>> {

    private ProgressDialog progress;
    private Context context;
    private final int basePorcion = 100;
    private String resultadoAzucar = "", resultadoGrasa = "", resultadoSodio = "";
    private int tipoProducto;
    private String salida_de_alimentos= "";
    private int l_sodio, l_grasa, l_azucar;
    private int a_sodio, a_grasa, a_azucar;

    public Calculos(Context context) {
        this.context = context;
    }

    @Override
    public void onPreExecute() {
        progress = new ProgressDialog(getContext());
        progress.setMessage(getContext().getString(R.string.calculando));
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected Map<String, String> doInBackground(ParametrosCalculo... params) {
        Log.d("APPNutricion", params[0].toString());

        ParametrosCalculo parametros = params[0];

        TipoAlimento tipoAlimento = parametros.getTipoAlimento();
        double tamanioPorcionVal = parametros.getTamanioPorcion();
        double azucaresVal = parametros.getAzucares();
        TipoMedidas azucarMedida = parametros.getAzucaresMedida();
        double grasasVal = parametros.getGrasas();
        TipoMedidas grasasMedida = parametros.getGrasasMedida();
        double sodioVal = parametros.getSodio();
        TipoMedidas sodioMedida = parametros.getSodioMedida();
        tipoProducto = parametros.getTipoProducto();


        double porcionPorcentaje = ((basePorcion * 100) / tamanioPorcionVal);
        double azucarPorcentaje = ((porcionPorcentaje * azucaresVal) / 100);
        double grasasPorcentaje = ((porcionPorcentaje * grasasVal) / 100);
        double sodioPorcentaje = ((porcionPorcentaje * sodioVal) / 100);

        Log.e("porcionPorcentaje : ", porcionPorcentaje+"");
        Log.e("azucarPorcentaje : ", azucarPorcentaje+"");
        Log.e("grasasPorcentaje : ", grasasPorcentaje+"");
        Log.e("sodioPorcentaje : ", sodioPorcentaje+"");

        if (tipoAlimento == TipoAlimento.BEBIDA && azucarMedida == TipoMedidas.GRAMOS && grasasMedida
                == TipoMedidas.GRAMOS && sodioMedida == TipoMedidas.MILIGRAMOS) {
            Log.e("******","en 1");
            if(grasasPorcentaje==0){
                resultadoGrasa = "0";
            }else if (grasasPorcentaje <= 0.75) {
                resultadoGrasa = Constantes.PARAM_BAJO;
                a_grasa = 0;
            } else if (grasasPorcentaje > 0.75 && grasasPorcentaje <= 2.5) {
                resultadoGrasa = Constantes.PARAM_MEDIO;
                a_grasa = 1;
            } else if (grasasPorcentaje > 2.5) {
                resultadoGrasa = Constantes.PARAM_ALTO;
                a_grasa = 2;
            }

            if(azucarPorcentaje==0){
                resultadoAzucar = "0";
            }else if (azucarPorcentaje <= 2.5) {
                resultadoAzucar = Constantes.PARAM_BAJO;
                a_azucar = 0;
            } else if (azucarPorcentaje > 2.5 && azucarPorcentaje <= 6.3) {
                resultadoAzucar = Constantes.PARAM_MEDIO;
                a_azucar = 1;
            } else if (azucarPorcentaje > 6.3) {
                resultadoAzucar = Constantes.PARAM_ALTO;
                a_azucar = 2;
            }

            if(sodioPorcentaje==0){
                resultadoSodio = "0";
            }else if (sodioPorcentaje <= 120) {
                resultadoSodio = Constantes.PARAM_BAJO;
                a_sodio = 0;
            } else if (sodioPorcentaje > 120 && sodioPorcentaje <= 600) {
                resultadoSodio = Constantes.PARAM_MEDIO;
                a_sodio = 1;
            } else if (sodioPorcentaje > 600) {
                resultadoSodio = Constantes.PARAM_ALTO;
                a_sodio = 2;
            }
        } else if (tipoAlimento == TipoAlimento.ALIMENTO && azucarMedida == TipoMedidas.GRAMOS
                && grasasMedida == TipoMedidas.GRAMOS
                && sodioMedida == TipoMedidas.MILIGRAMOS) {
            Log.e("******","en 2");

            if(grasasPorcentaje==0){
                resultadoGrasa = "0";
            }else if (grasasPorcentaje <= 1.5) {
                resultadoGrasa = Constantes.PARAM_BAJO;
                a_grasa = 0;
            } else if (grasasPorcentaje > 1.5 && grasasPorcentaje <= 5) {
                resultadoGrasa = Constantes.PARAM_MEDIO;
                a_grasa = 1;
            } else if (grasasPorcentaje > 5) {
                resultadoGrasa = Constantes.PARAM_ALTO;
                a_grasa = 2;
            }

            if(azucarPorcentaje==0){
                resultadoAzucar = "0";
            }else if (azucarPorcentaje <= 5) {
                resultadoAzucar = Constantes.PARAM_BAJO;
                a_azucar = 0;
            } else if (azucarPorcentaje > 5 && azucarPorcentaje <= 12.5) {
                resultadoAzucar = Constantes.PARAM_MEDIO;
                a_azucar = 1;
            } else if (azucarPorcentaje > 12.5) {
                resultadoAzucar = Constantes.PARAM_ALTO;
                a_azucar = 2;
            }

            if(sodioPorcentaje == 0){
                resultadoSodio = "0";
            }else if (sodioPorcentaje <= 120) {
                resultadoSodio = Constantes.PARAM_BAJO;
                a_sodio = 0;
            } else if (sodioPorcentaje > 120 && sodioPorcentaje <= 600) {
                resultadoSodio = Constantes.PARAM_MEDIO;
                a_sodio = 1;
            } else if (sodioPorcentaje > 600) {
                resultadoSodio = Constantes.PARAM_ALTO;
                a_sodio = 2;
            }

        }

        if (a_sodio == 2 || a_azucar == 2 || a_grasa == 2){
            salida_de_alimentos= "¡¡Evita consumir este producto!!";
        }else if((a_sodio == 1 && a_azucar == 1) || (a_sodio ==1 && a_grasa == 1) ||(a_grasa == 1 && a_azucar == 1)){
            salida_de_alimentos= "Evita el consumo regular de este producto.";
        }else if(a_sodio == 1 || a_azucar == 1 || a_grasa == 1){
            salida_de_alimentos = "Modera el consumo de este producto. Combínalo con alimentos frescos.";
        }else{
            salida_de_alimentos = "Combina este producto con alimentos frescos.";
        }

        Log.e("resultadoAzucar " , resultadoAzucar);
        Log.e("resultadoGrasa ", resultadoGrasa);
        Log.e("resultadoSodio ", resultadoSodio);



    return null;
}

    @Override
    protected void onPostExecute(Map<String, String> stringStringMap) {
        progress.dismiss();
        super.onPostExecute(stringStringMap);
        Intent intent = new Intent(getContext(), ResultadosNutricionActivity.class);
        intent.putExtra(Constantes.PARAM_COMIDA_BEBIDA, tipoProducto);
        intent.putExtra(Constantes.PARAM_AZUCAR_RESULT, resultadoAzucar);
        intent.putExtra(Constantes.PARAM_GRASA_RESULT, resultadoGrasa);
        intent.putExtra(Constantes.PARAM_SODIO_RESULT, resultadoSodio);
        intent.putExtra(Constantes.PARAM_AZUCAR_INT, "0");
        intent.putExtra(Constantes.PARAM_GRASA_INT, "0");
        intent.putExtra(Constantes.PARAM_SODIO_INT, "0");
        intent.putExtra(Constantes.PARAM_TEXT, salida_de_alimentos);
        intent.putExtra(Constantes.PARAM_SABER_MAS,"");

        getContext().startActivity(intent);
    }

    private Context getContext() {
        return context;
    }
}
