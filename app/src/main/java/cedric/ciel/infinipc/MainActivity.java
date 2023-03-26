package cedric.ciel.infinipc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import cedric.ciel.infinipc.Lists.BuildData;
import cedric.ciel.infinipc.Lists.BuildListAdapter;
import cedric.ciel.infinipc.Utils.DBHandler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    //ArrayList<String> bname, memory, watts, price;
    ArrayList<BuildData> buildData = new ArrayList<>();
    BuildListAdapter adapter;
    private FloatingActionButton btn_addBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_addBuild = findViewById(R.id.btn_addBuild);
        recyclerView = findViewById(R.id.recommendedBuilds);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);

        //SharedPref
        sharedPreferences = getSharedPreferences(getPackageName() + "Parts", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        dbHandler = new DBHandler(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        buildData = dbHandler.getBuilds(buildData);
        Toast.makeText(this, ""+buildData.get(2), Toast.LENGTH_SHORT).show();
        mAdapter = new BuildListAdapter(this, buildData);
        recyclerView.setAdapter(mAdapter);

        if (sharedPreferences.getBoolean(getPackageName() + "isFirstRun?", true)) downloadParts();

        btn_addBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewBuild.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void downloadParts() {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Updating parts list...");
        progressDialog.setTitle("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request headerTemplate = new Request.Builder()
                        .url("https://computer-components-api.p.rapidapi.com")
                        .addHeader("X-RapidAPI-Key", "30fb07d56dmshe61110abc62ea9dp1cd8a6jsn6af1119c8e56")
                        .addHeader("X-RapidAPI-Host", "computer-components-api.p.rapidapi.com")
                        .build();

                Request processorRequest = headerTemplate.newBuilder()
                        .url("https://computer-components-api.p.rapidapi.com/processor?limit=100&offset=0")
                        .get()
                        .build();
                //CPU
                client.newCall(processorRequest).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.setMessage("Downloading CPUs...");
                            }
                        });
                        String responseData = response.body().string();
                        editor.putString(getPackageName() + "processors", responseData);
                        editor.apply();
                        //                        progressDialog.dismiss();
                        call.cancel();
                        response.close();
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        call.cancel();
                        progressDialog.dismiss();
                    }

                });
                //Cooolers

                Request coolerRequest = headerTemplate.newBuilder()
                        .url("https://computer-components-api.p.rapidapi.com/cpu_fan?limit=100&offset=0")
                        .get()
                        .build();
                client.newCall(coolerRequest).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setMessage("Downloading CPU Coolers...");
                            }
                        });
                        String responseData = response.body().string();
                        editor.putString(getPackageName() + "coolers", responseData);
                        editor.apply();
//                        progressDialog.dismiss();
                        call.cancel();
                        response.close();
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        call.cancel();
                        progressDialog.dismiss();
                    }
                });
                //Motherboards

                Request moboRequest = headerTemplate.newBuilder()
                        .url("https://computer-components-api.p.rapidapi.com/motherboard?limit=100&offset=0")
                        .get()
                        .build();
                client.newCall(moboRequest).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setMessage("Downloading Motherboards...");
                            }
                        });
                        String responseData = response.body().string();
                        editor.putString(getPackageName() + "mobos", responseData);
                        editor.putBoolean(getPackageName() + "isFirstRun?", false);
                        editor.apply();
//                        progressDialog.dismiss();
                        call.cancel();
                        response.close();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        call.cancel();
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences(getPackageName() + "Picked_Parts", MODE_PRIVATE);
        sp.edit().remove("temp_CPU");

        sp.edit().apply();
    }
}