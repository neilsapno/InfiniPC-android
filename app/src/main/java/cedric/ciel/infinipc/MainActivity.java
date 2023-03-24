package cedric.ciel.infinipc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<BuildData> buildData;
    BuildListAdapter adapter;
    private FloatingActionButton btn_addBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_addBuild = findViewById(R.id.btn_addBuild);
        recyclerView=findViewById(R.id.recommendedBuilds);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);

        //SharedPref
        sharedPreferences = getSharedPreferences(getPackageName() +"Parts",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        dbHandler = new DBHandler(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        buildData = new ArrayList<>();
        buildData = dbHandler.getBuilds(buildData);
        mAdapter = new BuildListAdapter(this, buildData);
        recyclerView.setAdapter(mAdapter);

        if(sharedPreferences.getBoolean(getPackageName() +"isFirstRun?", true)) downloadParts();

        btn_addBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NewBuild.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
                Request request = new Request.Builder()
                        .url("https://computer-components-api.p.rapidapi.com/processor?limit=100&offset=0")
                        .get()
                        .addHeader("X-RapidAPI-Key", "30fb07d56dmshe61110abc62ea9dp1cd8a6jsn6af1119c8e56")
                        .addHeader("X-RapidAPI-Host", "computer-components-api.p.rapidapi.com")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String responseData = response.body().string();
                        try {
                            editor.putString(getPackageName() +"processors", responseData);
                            editor.putBoolean(getPackageName() +"isFirstRun?", false);
                            editor.apply();
                            JSONArray jsonArray = new JSONArray(sharedPreferences.getString(getPackageName()+"processors", ""));
                            for(int i = 0; i<jsonArray.length();i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                //cpu.setJSONResponse(json);
                                Log.d("JSON get", "json: " + json);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        progressDialog.dismiss();
                    }

                });
            }
        }).start();
    }
}