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
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import curisteando.com.barcodez.SimpleScannerFragmentActivity;
import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.beans.DatosBean;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.TipoResultado;
import curisteando.com.semaforonutrimental.utilidades.Utils;


public class ResultadosNutricionActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_nutricion);

        restoreActionBar();

        Intent i = getIntent();
        Bundle extras = i.getExtras();
       String  json_string = extras.getString(Constantes.CONS_RESPONSE);

        init(json_string);
    }

    public void init(String json_string){
        try {
            JSONObject jsonObjectFeed = new JSONObject(json_string);
            JSONObject jsonObject= new JSONObject(jsonObjectFeed.getString("feed"));
            DatosBean db = new DatosBean();
            db.setType_product(jsonObject.getString("type"));
            db.setCode(jsonObject.getString("code"));
            db.setCategory(jsonObject.getString("category"));
            db.setSubcategory(jsonObject.getString("subcategory"));
            db.setName(jsonObject.getString("name"));

            db.setMark(jsonObject.getString("mark"));
            db.setCompany(jsonObject.getString("company"));
            db.setNet_content(jsonObject.getString("net_content"));
            db.setPortion(jsonObject.getString("portion"));
            db.setEnergy(jsonObject.getString("energy"));

            db.setSugar_portion(jsonObject.getString("sugar_portion"));
            db.setGs_portion(jsonObject.getString("gs_portion"));
            db.setSodio_portion(jsonObject.getString("sodio_portion"));
            db.setSweetener(jsonObject.getString("sweetener"));
            db.setCaloria_100(jsonObject.getString("caloria_100"));

            db.setSugar_100(jsonObject.getString("sugar_100"));
            db.setGs_100(jsonObject.getString("gs_100"));
            db.setSodio_100(jsonObject.getString("sodio_100"));
            db.setResult_caloria(jsonObject.getBoolean("result_caloria"));
            db.setResult_sugar(jsonObject.getBoolean("result_sugar"));

            db.setResult_gs(jsonObject.getBoolean("result_gs"));
            db.setResult_sodio(jsonObject.getBoolean("result_sodio"));
            db.setTotal_true(jsonObject.getString("total_true"));
            db.setMessage(jsonObject.getString("message"));
            db.setRecommendation(jsonObject.getString("recommendation"));

            make_sellos(db);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void make_sellos(DatosBean db){

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar = Utils.getFormatActionBar(this, actionBar);
        actionBar.setHomeButtonEnabled(true);
    }

}
