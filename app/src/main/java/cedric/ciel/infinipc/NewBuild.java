package cedric.ciel.infinipc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import cedric.ciel.infinipc.Utils.DBHandler;

public class NewBuild extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;
    private TextView tv_cpu_name,tv_cpu_brand, tv_cpu_model, tv_cpu_speed, tv_cpu_socket, tv_cpu_price, tv_cpu_cooler, tv_mobo_name;
    private EditText et_buildName;
    private CardView cv_cpu, cv_cooler, cv_mobo;
    private String BuildName, CPU, Cooler, Mobo, Memory, Storage, GPU, Case, PSU, CaseFan, BuildImgUrl;
    private int RAMCount, Watts;
    private double Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPreferences = getSharedPreferences(this.getPackageName() +"Picked_Parts", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tv_cpu_name = findViewById(R.id.tv_cpu_name);
        tv_cpu_brand = findViewById(R.id.tv_cpu_brand);
        tv_cpu_model = findViewById(R.id.tv_cpu_model);
        tv_cpu_speed = findViewById(R.id.tv_cpu_speed);
        tv_cpu_socket = findViewById(R.id.tv_cpu_socket);
        tv_cpu_cooler = findViewById(R.id.tv_cooler_name);
        tv_cpu_price = findViewById(R.id.tv_cpu_price);
        tv_mobo_name = findViewById(R.id.tv_mobo_name);
        et_buildName = findViewById(R.id.et_buildName);
        FloatingActionButton btn_saveBuild = findViewById(R.id.btn_saveBuild);
        cv_cpu = findViewById(R.id.cv_cpu);
        cv_cooler = findViewById(R.id.cv_cpu_cooler);
        cv_mobo = findViewById(R.id.cv_mobo);

        dbHandler = new DBHandler(this);

        btn_saveBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildName = et_buildName.getText().toString();
                CPU = tv_cpu_brand.getText().toString();
                Cooler = "COoler";
                Mobo = "MoBo";
                Memory = "Memory";
                Storage = "StorAge";
                GPU = "VCard";
                Case = "PC Case";
                PSU = "PSupply";
                CaseFan = "CaseFan";
                RAMCount = 8;
                Watts = 500;
                Price = 600.00;
                BuildImgUrl = "https://";

                dbHandler.addNewBuild(BuildName, CPU, Cooler, Mobo, Memory, Storage, GPU, Case, PSU, CaseFan, RAMCount, Watts, Price, BuildImgUrl);
                dbHandler.close();
                Intent builds = new Intent(NewBuild.this, MainActivity.class);
                startActivity(builds);
            }
        });
        getParts();
        partsSelector();
    }

    private void partsSelector() {
        cv_cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCPU = new Intent(NewBuild.this, BrowseParts.class);
                browseCPU.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(browseCPU);
            }
        });
    }

    private void getParts(){
        try {
            //CPU
            JSONObject cpu = new JSONObject(sharedPreferences.getString("temp_CPU", ""));
            tv_cpu_name.setText(cpu.getString("title"));
            tv_cpu_brand.setText("Brand: "+cpu.getString("brand"));
            tv_cpu_model.setText("Model: "+cpu.getString("model"));
            tv_cpu_speed.setText("Speed: "+cpu.getString("speed"));
            tv_cpu_socket.setText("Socket: "+cpu.getString("socketType"));
            tv_cpu_price.setText("$"+cpu.getDouble("price"));

            //Cooler
        }
        catch (Exception e){
            return;
        }
    }
}