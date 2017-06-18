package curisteando.com.semaforonutrimental.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import curisteando.com.semaforonutrimental.R;
import curisteando.com.semaforonutrimental.beans.DatosBean;
import curisteando.com.semaforonutrimental.utilidades.Constantes;
import curisteando.com.semaforonutrimental.utilidades.Utils;


public class ResultadosNutricionActivity extends AppCompatActivity  {

    TextView tv_mensaje;
    LinearLayout recomendaciones_comida, recomendaciones_bebida,sellos,ll_mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_nutricion);

        tv_mensaje=(TextView)findViewById(R.id.tv_mensaje);
        recomendaciones_comida=(LinearLayout)findViewById(R.id.recomendaciones_comida);
        recomendaciones_bebida=(LinearLayout)findViewById(R.id.recomendaciones_bebidas);
        sellos=(LinearLayout)findViewById(R.id.sellos);
        ll_mensaje=(LinearLayout)findViewById(R.id.ll_mensaje);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels/6;
        sellos.getLayoutParams().height = height*4;
        sellos.requestLayout();

        ll_mensaje.getLayoutParams().height = height;
        ll_mensaje.requestLayout();

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
            tv_mensaje.setText(db.getMessage());
            if (db.getType_product().equals("food")){
                recomendaciones_comida.setVisibility(View.VISIBLE);
            }else{
                recomendaciones_bebida.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void make_sellos(DatosBean db){
        LinearLayout ll = null;
        if(db.getTotal_true().equals("0")){
            ll = (LinearLayout)findViewById(R.id.sello_one);
            set_sellos(0,ll,db.isResult_caloria(),db.isResult_sugar(),db.isResult_gs(), db.isResult_sodio());
        }else if(db.getTotal_true().equals("1")){
            ll = (LinearLayout)findViewById(R.id.sello_one);
            set_sellos(1,ll,db.isResult_caloria(),db.isResult_sugar(),db.isResult_gs(), db.isResult_sodio());
        }else  if(db.getTotal_true().equals("2")){
            ll = (LinearLayout)findViewById(R.id.sello_two);
            set_sellos(2,ll,db.isResult_caloria(),db.isResult_sugar(),db.isResult_gs(), db.isResult_sodio());
        }else  if(db.getTotal_true().equals("3")){
            ll = (LinearLayout)findViewById(R.id.sello_three);
            set_sellos(3,ll,db.isResult_caloria(),db.isResult_sugar(),db.isResult_gs(), db.isResult_sodio());
        }else if(db.getTotal_true().equals("4")){
            ll = (LinearLayout)findViewById(R.id.sello_four);
            set_sellos(4,ll,db.isResult_caloria(),db.isResult_sugar(),db.isResult_gs(), db.isResult_sodio());
        }
        ll.setVisibility(View.VISIBLE);
    }

    public void set_sellos(int num_sellos, LinearLayout ll, boolean calorias, boolean sugar, boolean gs, boolean sodio){
        if(num_sellos==0){
            change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)), R.drawable.ic_launch_ok);
        }
        if(num_sellos==1){
            if(calorias){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)), R.drawable.ic_launch_calorias);
            }else if(sugar){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)), R.drawable.ic_launch_azucar);
            }else if(gs){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)), R.drawable.ic_launch_grasas);
            }else if(sodio){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)), R.drawable.ic_launch_sodio);
            }
        }else if(num_sellos==2){
            if(calorias && sugar){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)), R.drawable.ic_launch_calorias, R.drawable.ic_launch_azucar);
            }else if(calorias && gs){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)), R.drawable.ic_launch_calorias, R.drawable.ic_launch_grasas);
            }else if(calorias && sodio){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)), R.drawable.ic_launch_calorias, R.drawable.ic_launch_sodio);
            }else if(sugar && gs){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)), R.drawable.ic_launch_azucar, R.drawable.ic_launch_grasas);
            }else if(sugar && sodio){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)), R.drawable.ic_launch_azucar, R.drawable.ic_launch_sodio);
            }else if(gs && sodio){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)), R.drawable.ic_launch_grasas, R.drawable.ic_launch_sodio);
            }
        }else if(num_sellos==3){
            if(calorias && sugar && gs){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)),((ImageView) ll.findViewById(R.id.iv_sello_three)), R.drawable.ic_launch_calorias, R.drawable.ic_launch_azucar, R.drawable.ic_launch_grasas);
            }else if(calorias && sugar && sodio){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)),((ImageView) ll.findViewById(R.id.iv_sello_three)), R.drawable.ic_launch_calorias, R.drawable.ic_launch_azucar, R.drawable.ic_launch_sodio);
            }else if(calorias && gs && sodio){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)),((ImageView) ll.findViewById(R.id.iv_sello_three)), R.drawable.ic_launch_calorias, R.drawable.ic_launch_grasas, R.drawable.ic_launch_sodio);
            }else if(sugar && gs && sodio){
                change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)),((ImageView) ll.findViewById(R.id.iv_sello_three)), R.drawable.ic_launch_grasas, R.drawable.ic_launch_azucar, R.drawable.ic_launch_sodio);
            }
        }else if(num_sellos==4){
            change_image(((ImageView) ll.findViewById(R.id.iv_sello_one)),((ImageView) ll.findViewById(R.id.iv_sello_two)),((ImageView) ll.findViewById(R.id.iv_sello_three)),((ImageView) ll.findViewById(R.id.iv_sello_four)), R.drawable.ic_launch_calorias, R.drawable.ic_launch_azucar, R.drawable.ic_launch_grasas, R.drawable.ic_launch_sodio);
        }
    }

    public void change_image(ImageView iv, int img){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(getResources().getDrawable(img, getApplicationContext().getTheme()));
        } else {
            iv.setImageDrawable(getResources().getDrawable(img));
        }
    }

    public void change_image(ImageView iv,ImageView iv2, int img, int img2){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(getResources().getDrawable(img, getApplicationContext().getTheme()));
            iv2.setImageDrawable(getResources().getDrawable(img2, getApplicationContext().getTheme()));
        } else {
            iv.setImageDrawable(getResources().getDrawable(img));
            iv2.setImageDrawable(getResources().getDrawable(img2));
        }
    }

    public void change_image(ImageView iv,ImageView iv2,ImageView iv3, int img, int img2,int img3){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(getResources().getDrawable(img, getApplicationContext().getTheme()));
            iv2.setImageDrawable(getResources().getDrawable(img2, getApplicationContext().getTheme()));
            iv3.setImageDrawable(getResources().getDrawable(img3, getApplicationContext().getTheme()));
        } else {
            iv.setImageDrawable(getResources().getDrawable(img));
            iv2.setImageDrawable(getResources().getDrawable(img2));
            iv3.setImageDrawable(getResources().getDrawable(img3));
        }
    }

    public void change_image(ImageView iv,ImageView iv2,ImageView iv3,ImageView iv4, int img, int img2,int img3,int img4){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv.setImageDrawable(getResources().getDrawable(img, getApplicationContext().getTheme()));
            iv2.setImageDrawable(getResources().getDrawable(img2, getApplicationContext().getTheme()));
            iv3.setImageDrawable(getResources().getDrawable(img3, getApplicationContext().getTheme()));
            iv4.setImageDrawable(getResources().getDrawable(img4, getApplicationContext().getTheme()));
        } else {
            iv.setImageDrawable(getResources().getDrawable(img));
            iv2.setImageDrawable(getResources().getDrawable(img2));
            iv3.setImageDrawable(getResources().getDrawable(img3));
            iv4.setImageDrawable(getResources().getDrawable(img4));
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar = Utils.getFormatActionBar(this, actionBar);
        actionBar.setHomeButtonEnabled(true);
    }

}
