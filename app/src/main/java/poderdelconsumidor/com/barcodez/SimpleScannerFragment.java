package poderdelconsumidor.com.barcodez;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import poderdelconsumidor.com.semaforonutrimental.activities.CapturaDatosActivity;
import poderdelconsumidor.com.semaforonutrimental.activities.ResultadosNutricionActivity;
import poderdelconsumidor.com.semaforonutrimental.utilidades.Constantes;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class SimpleScannerFragment extends Fragment implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";

    private SweetAlertDialog pDialog;
    private getDataTask mAuthTask = null;


    private List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>() {{
        add(BarcodeFormat.EAN8);
        add(BarcodeFormat.EAN13);
        add(BarcodeFormat.UPCE);
    }};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZBarScannerView(getActivity());
        boolean mAutoFocus;
        boolean mFlash;
        if (savedInstanceState != null) {
            mFlash = savedInstanceState.getBoolean(FLASH_STATE, false);
            mAutoFocus = savedInstanceState.getBoolean(AUTO_FOCUS_STATE, true);
        } else {
            mFlash = false;
            mAutoFocus = true;
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
        mAuthTask = new getDataTask(codigo);
        mAuthTask.execute((Void) null);
    }

    private class getDataTask extends AsyncTask<Void, Void, Boolean> {

        private String response_,code;

        getDataTask(String code) {
            this.code= code;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoad();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
                if(code == null || code.equals("") || code.equals("0")){
                    return false;
                }
                try {
                  URL obj = new URL("https://consumidor.herokuapp.com/api/feeds/"+code);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    int responseCode = con.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) { //success
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        this.response_ = response.toString();
                        return true;
                    } else {
                        Log.d("***********", "POST request did not work.");
                    }
                } catch (IOException e) {
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
                Intent intent = new Intent(getActivity(), ResultadosNutricionActivity.class);
                intent.putExtra(Constantes.CONS_RESPONSE,response_);
                startActivity(intent);

            }else{
                Intent intent = new Intent(getActivity(), CapturaDatosActivity.class);
                intent.putExtra(Constantes.CONST_CODIGO_BARRAS, code);
                intent.putExtra(Constantes.CONST_IS_FOUND, true);
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
        pDialog.setTitleText("Espere un momento...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

}