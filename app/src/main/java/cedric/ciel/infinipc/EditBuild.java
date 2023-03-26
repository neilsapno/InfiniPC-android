package cedric.ciel.infinipc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import cedric.ciel.infinipc.Utils.DBHandler;
import cedric.ciel.infinipc.databinding.ActivityEditBuildBinding;

public class EditBuild extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;
    ActivityEditBuildBinding newBuildBinding;
    private ImageView iv_cpuImg;
    private EditText et_buildName;
    private String BuildName, CPU, Cooler, Mobo, Memory, Storage, GPU, Case, PSU, CaseFan, BuildImgUrl;
    private int RAMCount, Watts;
    private double Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newBuildBinding = ActivityEditBuildBinding.inflate(getLayoutInflater());
        setContentView(newBuildBinding.getRoot());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPreferences = getSharedPreferences(this.getPackageName() +"Picked_Parts", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        Intent update = getIntent();

        FloatingActionButton btn_saveBuild = findViewById(R.id.btn_saveBuild);

        dbHandler = new DBHandler(this);

        btn_saveBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildName = newBuildBinding.etBuildName.getText().toString();
                CPU = newBuildBinding.tvCpuName.getText().toString();
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

                dbHandler.addEditBuild(BuildName, CPU, Cooler, Mobo, Memory, Storage, GPU, Case, PSU, CaseFan, RAMCount, Watts, Price, BuildImgUrl);
                dbHandler.close();
                Intent builds = new Intent(EditBuild.this, MainActivity.class);
                startActivity(builds);
            }
        });
        getParts();
        partsSelector();
    }

    private void partsSelector() {
        newBuildBinding.cvCpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCPU = new Intent(EditBuild.this, BrowseParts.class);
                browseCPU.putExtra("type", "CPU");
                startActivity(browseCPU);
            }
        });
        newBuildBinding.cvCpuCooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCoolers = new Intent(EditBuild.this, BrowseParts.class);
                browseCoolers.putExtra("type", "Coolers");
                startActivity(browseCoolers);
            }
        });
        newBuildBinding.cvMobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCoolers = new Intent(EditBuild.this, BrowseParts.class);
                browseCoolers.putExtra("type", "Mobos");
                startActivity(browseCoolers);
            }
        });

    }

    private void getParts(){
        try {
            //CPU
            if(cpu.has("brand")) {
                Glide.with(EditBuild.this)
                        .load(cpu.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivCpuImg);
                newBuildBinding.tvCpuName.setText(cpu.getString("title"));
                newBuildBinding.tvCpuBrand.setText("Brand: " + cpu.getString("brand"));
                newBuildBinding.tvCpuModel.setText("Model: " + cpu.getString("model"));
                newBuildBinding.tvCpuSpeed.setText("Speed: " + cpu.getString("speed"));
                newBuildBinding.tvCpuSocket.setText("Socket: " + cpu.getString("socketType"));
                newBuildBinding.tvCpuPrice.setText("$" + cpu.getDouble("price"));
            }

            //Cooler
        }
        catch (Exception e){
            //return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.remove("temp_CPU");
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditBuild.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}