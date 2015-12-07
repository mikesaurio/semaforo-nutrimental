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

        //prueba 1
        /*double tamanioPorcionVal = 100;
        double azucaresVal = 6.1;
        TipoMedidas azucarMedida = TipoMedidas.GRAMOS;
        double grasasVal = 22.1;
        TipoMedidas grasasMedida = TipoMedidas.GRAMOS;
        double sodioVal = 2;
        TipoMedidas sodioMedida = TipoMedidas.MILIGRAMOS;*/

//	//Coca light

//	double tamanioPorcionVal = 600;

//	double azucaresVal = 0;

//	TipoMedidas azucarMedida = TipoMedidas.GRAMOS;

//	double grasasVal = 0;

//	TipoMedidas grasasMedida = TipoMedidas.GRAMOS;

//	double sodioVal = 119;

//	TipoMedidas sodioMedida = TipoMedidas.MILIGRAMOS;

//Coca normal

//	double tamanioPorcionVal = 200;

//	double azucaresVal = 21;

//	TipoMedidas azucarMedida = TipoMedidas.GRAMOS;

//	double grasasVal = 84;

//	TipoMedidas grasasMedida = TipoMedidas.GRAMOS;

//	double sodioVal = 0;

//	TipoMedidas sodioMedida = TipoMedidas.MILIGRAMOS;

        double porcionPorcentaje = ((basePorcion * 100) / tamanioPorcionVal);
        double azucarPorcentaje = ((porcionPorcentaje * azucaresVal) / 100);
        double grasasPorcentaje = ((porcionPorcentaje * grasasVal) / 100);
        double sodioPorcentaje = ((porcionPorcentaje * sodioVal) / 100);

        System.out.println("porcionPorcentaje : " + porcionPorcentaje);
        System.out.println("azucarPorcentaje : " + azucarPorcentaje);
        System.out.println("grasasPorcentaje : " + grasasPorcentaje);
        System.out.println("sodioPorcentaje : " + sodioPorcentaje);

        if (tipoAlimento == TipoAlimento.BEBIDA && azucarMedida == TipoMedidas.GRAMOS
                && grasasMedida == TipoMedidas.GRAMOS
                && sodioMedida == TipoMedidas.MILIGRAMOS) {
            //Bebida liquidos 1
            if (grasasPorcentaje <= 0.75) {
                resultadoGrasa = Constantes.PARAM_BAJO;
            } else if (grasasPorcentaje > 0.75 && grasasPorcentaje <= 2.5) {
                resultadoGrasa = Constantes.PARAM_MEDIO;
            } else if (grasasPorcentaje > 2.5) {
                resultadoGrasa = Constantes.PARAM_ALTO;
            }
            if (azucarPorcentaje <= 2.5) {
                resultadoAzucar = Constantes.PARAM_BAJO;
            } else if (azucarPorcentaje > 2.5 && azucarPorcentaje <= 6.3) {
                resultadoAzucar = Constantes.PARAM_MEDIO;
            } else if (azucarPorcentaje > 6.3) {
                resultadoAzucar = Constantes.PARAM_ALTO;
            }

            if (sodioPorcentaje <= 120) {
                resultadoSodio = Constantes.PARAM_BAJO;
            } else if (sodioPorcentaje > 120 && sodioPorcentaje <= 600) {
                resultadoSodio = Constantes.PARAM_MEDIO;
            } else if (sodioPorcentaje > 600) {
                resultadoSodio = Constantes.PARAM_ALTO;
            }
        } else if (tipoAlimento == TipoAlimento.BEBIDA && azucarMedida == TipoMedidas.KILOCALORIAS
                && grasasMedida == TipoMedidas.KILOCALORIAS
                && sodioMedida == TipoMedidas.MILIGRAMOS) {
//Bebida liquidos 2

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
        intent.putExtra(Constantes.PARAM_AZUCAR_RESULT, resultadoAzucar);
        intent.putExtra(Constantes.PARAM_GRASA_RESULT, resultadoGrasa);
        intent.putExtra(Constantes.PARAM_SODIO_RESULT, resultadoSodio);
        intent.putExtra(Constantes.PARAM_AZUCAR_INT, "0");
        intent.putExtra(Constantes.PARAM_GRASA_INT, "0");
        intent.putExtra(Constantes.PARAM_SODIO_INT, "0");
        intent.putExtra(Constantes.PARAM_TEXT, "");
        intent.putExtra(Constantes.PARAM_SABER_MAS,"");

        getContext().startActivity(intent);
    }

    private Context getContext() {
        return context;
    }
}
