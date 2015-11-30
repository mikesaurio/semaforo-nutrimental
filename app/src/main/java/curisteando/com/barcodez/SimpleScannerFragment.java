package curisteando.com.barcodez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import curisteando.com.semaforonutrimental.activities.CapturaDatosActivity;
import curisteando.com.semaforonutrimental.activities.ResultadosNutricionActivity;
import curisteando.com.semaforonutrimental.beans.datos_bean;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class SimpleScannerFragment extends Fragment implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String TAG = "SemaforoNutrimental";

    private boolean mFlash;
    private boolean mAutoFocus;
    private ArrayList<datos_bean> datosArray;

    SurfaceView mSurfaceView;

    private List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>(){{add(BarcodeFormat.EAN8);add(BarcodeFormat.EAN13);add(BarcodeFormat.UPCE);}};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mScannerView = new ZBarScannerView(getActivity());
        mSurfaceView = new SurfaceView(getActivity());
        datosArray = new ArrayList<datos_bean>();

        if(savedInstanceState != null) {
            mFlash = savedInstanceState.getBoolean(FLASH_STATE, false);
            mAutoFocus = savedInstanceState.getBoolean(AUTO_FOCUS_STATE, true);
            //mSelectedIndices = savedInstanceState.getIntegerArrayList(SELECTED_FORMATS);
        } else {
            mFlash = false;
            mAutoFocus = true;
            //mSelectedIndices = null;
        }


        mScannerView.setResultHandler(this);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
        mScannerView.setFormats(formats);
        mScannerView.setupScanner();

        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        /*SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);*/

        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        buscar_valores(rawResult.getContents());


    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    public void buscar_valores(String codigo){
        fill_beans();
        boolean encontrado = false;
        int id = -1;
        for (int i =0 ; i<datosArray.size();i++){
           if( datosArray.get(i).getCodigo().equals(codigo+"")){
               encontrado = true;
               id = i;
           }
        }
        if (encontrado){
            Intent intent = new Intent(getActivity(), ResultadosNutricionActivity.class);
            intent.putExtra(Constantes.CONST_CODIGO_BARRAS, codigo);
            intent.putExtra(Constantes.PARAM_AZUCAR_RESULT, datosArray.get(id).getGrado_azucar());
            intent.putExtra(Constantes.PARAM_GRASA_RESULT, datosArray.get(id).getGrado_grasa());
            intent.putExtra(Constantes.PARAM_SODIO_RESULT, datosArray.get(id).getGrado_sodio());
            intent.putExtra(Constantes.PARAM_AZUCAR_INT, datosArray.get(id).getAzucar100());
            intent.putExtra(Constantes.PARAM_GRASA_INT, datosArray.get(id).getGrasas100());
            intent.putExtra(Constantes.PARAM_SODIO_INT, datosArray.get(id).getSodio100());
            startActivity(intent);
        }else{
            Intent intent = new Intent(getActivity(), CapturaDatosActivity.class);
            intent.putExtra(Constantes.CONST_CODIGO_BARRAS, codigo);
            startActivity(intent);
        }
    }

    public void fill_beans(){
        String[] josns = {"inputs/alimentos.json","inputs/liquidos.json"};
        for (int j=0 ;j<josns.length; j++) {
            String json = readJson(josns[j]);
            try {
                JSONArray jArray = new JSONArray(json);
                for (int i = 0; i < jArray.length(); i++) {
                    datos_bean datos = new datos_bean();
                    JSONObject json_data = jArray.getJSONObject(i);
                    datos.setId_producto(json_data.getString("No_Producto"));
                    datos.setFecha(json_data.getString("Fecha"));
                    datos.setCodigo(json_data.getString("Codigo"));
                    datos.setCategoria(json_data.getString("Categoría"));
                    datos.setProducto(json_data.getString("Producto"));
                    datos.setMarca(json_data.getString("Marca"));
                    datos.setEmpresa(json_data.getString("Empresa"));
                    datos.setNeto(json_data.getString("Cont_neto"));
                    datos.setPorcion(json_data.getString("Porción"));
                    datos.setAzucar(json_data.getString("Cont_azúcar"));
                    datos.setGrasas(json_data.getString("Cont_GS"));
                    datos.setSodio(json_data.getString("Cont_sodio"));
                    datos.setAzucar100(json_data.getString("Cont_azúcar_100"));
                    datos.setGrasas100(json_data.getString("Cont_GS_100"));
                    datos.setSodio100(json_data.getString("Cont_sodio_100"));
                    datos.setGrado_azucar(json_data.getString("Resultado Azúcar"));
                    datos.setGrasas(json_data.getString("Resultado Grasa Saturada"));
                    datos.setGrado_sodio(json_data.getString("Resultado Sodio"));
                    datos.setMensaje(json_data.getString("Mensaje de advertencia"));
                    datos.setAlternativa(json_data.getString("Alternativa"));
                    datosArray.add(datos);
                }
               // Toast.makeText(getActivity().getBaseContext(), datosArray.size() + " en "+j, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("FALLA AL CARGAR", "FALLA");
            }
        }
    }




    public String readJson(String stringJson){
            String json = "";
            try {
                InputStream is = getActivity().getAssets().open(stringJson);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                return "";
            }
            return json;
    }

}