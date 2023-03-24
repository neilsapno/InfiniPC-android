package cedric.ciel.infinipc;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cedric.ciel.infinipc.Utils.DBHandler;

public class NewBuild extends AppCompatActivity {

    DBHandler dbHandler;
    private TextView tv_cpu_name, tv_cpu_cooler, tv_mobo_name;
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

        tv_cpu_name = findViewById(R.id.tv_partsTitle);
        tv_cpu_cooler = findViewById(R.id.tv_cooler_name);
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
                CPU = "CPU";
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

        partsSelector();
    }

    private void partsSelector() {
        cv_cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCPU = new Intent(NewBuild.this, BrowseParts.class);
                startActivity(browseCPU);
            }
        });
    }
}