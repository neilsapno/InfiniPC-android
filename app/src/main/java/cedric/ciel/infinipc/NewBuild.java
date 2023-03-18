package cedric.ciel.infinipc;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cedric.ciel.infinipc.Parts.CPU;
import cedric.ciel.infinipc.Utils.DBHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewBuild extends AppCompatActivity {

    DBHelper db;
    CPU cpu = new CPU();
    private TextView tv_cpu_name, tv_cpu_cooler, tv_mobo_name;
    private EditText et_buildName;
    private CardView cv_cpu, cv_cooler, cv_mobo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        tv_cpu_name = findViewById(R.id.tv_cpuName);
        tv_cpu_cooler = findViewById(R.id.tv_cooler_name);
        tv_mobo_name = findViewById(R.id.tv_mobo_name);
        et_buildName = findViewById(R.id.et_buildName);
        FloatingActionButton btn_saveBuild = findViewById(R.id.btn_saveBuild);
        cv_cpu = findViewById(R.id.cv_cpu);
        cv_cooler = findViewById(R.id.cv_cpu_cooler);
        cv_mobo = findViewById(R.id.cv_mobo);

        partsSelector();

        db = new DBHelper(this);
        btn_saveBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bname_text = et_buildName.getText().toString();
                String processor_text = tv_cpu_name.getText().toString();
                String cpu_cooler_text = tv_cpu_cooler.getText().toString();
                String mobo_text = tv_mobo_name.getText().toString();


                Boolean checkinsertData = db.insertBuildData(bname_text, processor_text, cpu_cooler_text, mobo_text);
                if (checkinsertData) {
                    Toast.makeText(NewBuild.this, "New Data Inserted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewBuild.this, MainActivity.class));
                } else {
                    Toast.makeText(NewBuild.this, "new Data Not Inserted!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void partsSelector() {
        cv_cpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://computer-components-api.p.rapidapi.com/processor?limit=5&offset=0")
                            .get()
                            .addHeader("X-RapidAPI-Key", "30fb07d56dmshe61110abc62ea9dp1cd8a6jsn6af1119c8e56")
                            .addHeader("X-RapidAPI-Host", "computer-components-api.p.rapidapi.com")
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            //String responseData = response.body();
                            try {
                                JSONObject json = new JSONObject(String.valueOf(response.body()));
                                cpu.setJSONResponse(json);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                    });

                    Intent toCpu = new Intent(NewBuild.this, PartsList.class)
                            .putExtra("type", "CPU");
                    startActivity(toCpu);
            }
        });
    }
}