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

import java.util.ArrayList;
import java.util.List;

import curisteando.com.semaforonutrimental.activities.CapturaDatosActivity;
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

    SurfaceView mSurfaceView;

    private List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>(){{add(BarcodeFormat.EAN8);add(BarcodeFormat.EAN13);add(BarcodeFormat.UPCE);}};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZBarScannerView(getActivity());
        mSurfaceView = new SurfaceView(getActivity());

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
        Toast.makeText(getActivity(), "Contents = " + rawResult.getContents() +", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), CapturaDatosActivity.class);
        intent.putExtra(Constantes.CONST_CODIGO_BARRAS, rawResult.getContents());
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

}