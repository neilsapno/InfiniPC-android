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

public class MainActivity extends AppCompatActivity implements BuildListAdapter.OnBuildClickListener{

    DBHandler dbHandler;
    SharedPreferences sharedPreferences, sp;
    SharedPreferences.Editor editor, spEdit;
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
        sp = getSharedPreferences(getPackageName() + "Picked_Parts", MODE_PRIVATE);
        spEdit = sp.edit();
        editor = sharedPreferences.edit();

        dbHandler = new DBHandler(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        buildData = dbHandler.getBuilds(buildData);
        mAdapter = new BuildListAdapter(this, buildData, this);
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
                        .addHeader("X-RapidAPI-Key", "9fd8b8c420mshdc1595d375a8251p15d615jsn89c88bc7f390") //497b5cfff5msh031bc0c787aa5eap17bebbjsn5657e0e05222
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

                //Memory
                Request memoryRequest = headerTemplate.newBuilder()
                        .url("https://computer-components-api.p.rapidapi.com/ram?limit=100&offset=0")
                        .get()
                        .build();
                client.newCall(memoryRequest).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.setMessage("Downloading RAM...");
                            }
                        });
                        String responseData = response.body().string();
                        editor.putString(getPackageName() + "rams", responseData);
                        editor.putString(getPackageName() + "storages", "[{\"id\":\"B003KKTASK\",\"title\":\"White Label 1TB Desktop Hard Drive\",\"link\":\"https://amazon.com/dp/B003KKTASK?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/410NIjWu30L._SL75_.jpg\",\"price\":26.99,\"brand\":\"White Label\",\"model\":\"8541578956\",\"storageInterface\":\"SATA 3.0Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B00461K1QW\",\"title\":\"WD Blue 250GB PC Desktop Hard Drive\",\"link\":\"https://amazon.com/dp/B00461K1QW?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/51BZ0IYRUwL._SL75_.jpg\",\"price\":24.24,\"brand\":\"Western Digital\",\"model\":\"WD2500AAKX\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"16 MB\"},{\"id\":\"B007X15MNO\",\"title\":\"Western Digital WD Bulk WD10EURX 1TB\",\"link\":\"https://amazon.com/dp/B007X15MNO?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/51%2BHtR8levL._SL75_.jpg\",\"price\":25.29,\"brand\":\"Western Digital\",\"model\":\"WD10EURX\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B0088PUEPK\",\"title\":\"WD Blue 1TB PC Hard Drive\",\"link\":\"https://amazon.com/dp/B0088PUEPK?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41M8rguEEvL._SL75_.jpg\",\"price\":39.89,\"brand\":\"Western Digital\",\"model\":\"WD10EZEX-60WN4A0\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B008BRE06E\",\"title\":\"Seagate Barracuda 2Tb 3.5 Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B008BRE06E?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41ITkN-Xg8L._SL75_.jpg\",\"price\":69.99,\"brand\":\"Seagate\",\"model\":\"ST2000DM001\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B008JJLXO6\",\"title\":\"WD Red 1TB NAS Hard Drive\",\"link\":\"https://amazon.com/dp/B008JJLXO6?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41bWLXFfitL._SL75_.jpg\",\"price\":44.83,\"brand\":\"Western Digital\",\"model\":\"WD10EFRX\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B00B99JU4S\",\"title\":\"Seagate SATA 6Gb/s 3.5-Inch 4TB Desktop HDD\",\"link\":\"https://amazon.com/dp/B00B99JU4S?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/511U6Za-nFL._SL75_.jpg\",\"price\":74.99,\"brand\":\"Seagate\",\"model\":\"ST4000DM000\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5900 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B00FJRS628\",\"title\":\"WD Black 2TB Performance Desktop Hard Disk Drive\",\"link\":\"https://amazon.com/dp/B00FJRS628?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41FOU8RDUWL._SL75_.jpg\",\"price\":772.83,\"brand\":\"Western Digital\",\"model\":\"WD2003FZEX\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B00OS8YP9M\",\"title\":\"WL 3TB Desktop Hard Drive\",\"link\":\"https://amazon.com/dp/B00OS8YP9M?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41l0wCJ3UzL._SL75_.jpg\",\"price\":35.99,\"brand\":\"White Label\",\"model\":\"LYSB00OS8YP9M-ELECTR\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B00QFXOL5G\",\"title\":\"WD Black 500GB Performance Mobile Hard Disk Drive\",\"link\":\"https://amazon.com/dp/B00QFXOL5G?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41qDdiwiqpL._SL75_.jpg\",\"price\":27.99,\"brand\":\"Western Digital\",\"model\":\"WD5000LPLX\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"32 MB\"},{\"id\":\"B00U7ZRWIQ\",\"title\":\"WL 1TB Internal Desktop 3.5\\\" Hard Drive\",\"link\":\"https://amazon.com/dp/B00U7ZRWIQ?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/511cR%2BRkhqL._SL75_.jpg\",\"price\":26.99,\"brand\":\"White Label\",\"model\":\"4328464215\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5900 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B00VS556S2\",\"title\":\"Seagate Pipeline 500GB Internal Desktop Hard Drive\",\"link\":\"https://amazon.com/dp/B00VS556S2?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41LjbLP%2BBYL._SL75_.jpg\",\"price\":29.99,\"brand\":\"Seagate\",\"model\":\"LYSB00VS556S2-ELECTRNCS\",\"storageInterface\":\"SATA 3.0Gb/s\",\"rpm\":\"5900 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"8 MB\"},{\"id\":\"B013HNYV42\",\"title\":\"WD Blue 3TB PC Hard Drive\",\"link\":\"https://amazon.com/dp/B013HNYV42?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41M8rguEEvL._SL75_.jpg\",\"price\":64.99,\"brand\":\"Western Digital\",\"model\":\"1412590\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B013HNYV8I\",\"title\":\"WD Blue 4TB PC Hard Drive\",\"link\":\"https://amazon.com/dp/B013HNYV8I?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41M8rguEEvL._SL75_.jpg\",\"price\":89.99,\"brand\":\"Western Digital\",\"model\":\"WD40EZRZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B013HNYV9W\",\"title\":\"WD Blue 500GB PC Hard Drive\",\"link\":\"https://amazon.com/dp/B013HNYV9W?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41%2BFeQy6k2L._SL75_.jpg\",\"price\":52.36,\"brand\":\"Western Digital\",\"model\":\"WD5000AZRZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B013HNYVCE\",\"title\":\"WD Blue 6TB PC Hard Drive\",\"link\":\"https://amazon.com/dp/B013HNYVCE?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41M8rguEEvL._SL75_.jpg\",\"price\":189.99,\"brand\":\"Western Digital\",\"model\":\"WD60EZRZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B013HNYVLA\",\"title\":\"WD Blue 1TB PC Hard Drive\",\"link\":\"https://amazon.com/dp/B013HNYVLA?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41M8rguEEvL._SL75_.jpg\",\"price\":48.99,\"brand\":\"Western Digital\",\"model\":\"WD10EZRZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B013JPKUHA\",\"title\":\"Toshiba 1TB Desktop\",\"link\":\"https://amazon.com/dp/B013JPKUHA?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41OWF6TlKML._SL75_.jpg\",\"price\":0,\"brand\":\"Toshiba\",\"model\":\"HDWD110XZSTA\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B013JPKUU2\",\"title\":\"Toshiba X300 4TB Performance Gaming Hard Drive\",\"link\":\"https://amazon.com/dp/B013JPKUU2?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41wKnn4hEHL._SL75_.jpg\",\"price\":149,\"brand\":\"Toshiba\",\"model\":\"HDWE140XZSTA\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B013JPLKQK\",\"title\":\"Toshiba X300 5TB Performance Gaming Hard Drive\",\"link\":\"https://amazon.com/dp/B013JPLKQK?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41RFzxEUCfL._SL75_.jpg\",\"price\":0,\"brand\":\"Toshiba\",\"model\":\"HDWE150XZSTA\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B013QFRS2S\",\"title\":\"WD Blue 2TB PC Hard Drive\",\"link\":\"https://amazon.com/dp/B013QFRS2S?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41%2BFeQy6k2L._SL75_.jpg\",\"price\":59.99,\"brand\":\"Western Digital\",\"model\":\"WD20EZRZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B01IA9GU0Q\",\"title\":\"Seagate IronWolf 10TB NAS Internal Hard Drive HDD\",\"link\":\"https://amazon.com/dp/B01IA9GU0Q?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41MVJsNQ7gL._SL75_.jpg\",\"price\":322.06,\"brand\":\"Seagate\",\"model\":\"ST10000VN0004\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"256 MB\"},{\"id\":\"B01IEKG3TY\",\"title\":\"Seagate BarraCuda 500GB Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B01IEKG3TY?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41akxaIElsL._SL75_.jpg\",\"price\":29,\"brand\":\"Seagate\",\"model\":\"ST500DM009\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"32 MB\"},{\"id\":\"B01IEKG4NE\",\"title\":\"Seagate BarraCuda 3TB Internal Hard Drive HDD\",\"link\":\"https://amazon.com/dp/B01IEKG4NE?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41t11GEiTOL._SL75_.jpg\",\"price\":170,\"brand\":\"Seagate\",\"model\":\"ST3000DM008\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B01LNJBA2I\",\"title\":\"Seagate BarraCuda 1TB Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B01LNJBA2I?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41pLbGTevQL._SL75_.jpg\",\"price\":45.39,\"brand\":\"Seagate\",\"model\":\"ST1000DM010\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B01LOOJ8QM\",\"title\":\"Seagate SkyHawk 2TB Surveillance Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B01LOOJ8QM?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/417wbwMIbAL._SL75_.jpg\",\"price\":71,\"brand\":\"Seagate\",\"model\":\"ST2000VX008\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5900 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B01LOOJ8R6\",\"title\":\"Seagate SkyHawk 1TB Surveillance HDD\",\"link\":\"https://amazon.com/dp/B01LOOJ8R6?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41vrPpwjo4L._SL75_.jpg\",\"price\":50,\"brand\":\"Seagate\",\"model\":\"ST1000VX005\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B01LOOJ8T4\",\"title\":\"Seagate IronWolf 2TB NAS Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B01LOOJ8T4?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41MVJsNQ7gL._SL75_.jpg\",\"price\":82.81,\"brand\":\"Seagate\",\"model\":\"ST2000VN004\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"256 MB\"},{\"id\":\"B01LWPEVPL\",\"title\":\"Seagate BarraCuda 3TB Internal Hard Drive HDD\",\"link\":\"https://amazon.com/dp/B01LWPEVPL?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/51dRe04tU1L._SL75_.jpg\",\"price\":192.89,\"brand\":\"Seagate\",\"model\":\"ST3000LM024\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B01LYNQXCP\",\"title\":\"Seagate BarraCuda 1TB Internal HDD\",\"link\":\"https://amazon.com/dp/B01LYNQXCP?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41Y3hI4PGqL._SL75_.jpg\",\"price\":47.51,\"brand\":\"Seagate\",\"model\":\"ST1000LM048\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B01LZMUNGR\",\"title\":\"Seagate BarraCuda Mobile Hard Drive 4TB\",\"link\":\"https://amazon.com/dp/B01LZMUNGR?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/51nBwfoJxyL._SL75_.jpg\",\"price\":118,\"brand\":\"Seagate\",\"model\":\"ST4000LM024\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B01M0AADIX\",\"title\":\"Seagate BarraCuda 5TB Internal HDD\",\"link\":\"https://amazon.com/dp/B01M0AADIX?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/515e9RIPMGL._SL75_.jpg\",\"price\":133,\"brand\":\"Seagate\",\"model\":\"ST5000LM000\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B01M20VBU7\",\"title\":\"Samsung 960 EVO 500GB Solid State Drive (MZ-V6E500BW) M.2 NVMe\",\"link\":\"https://amazon.com/dp/B01M20VBU7?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41s-VB9ibSL._SL75_.jpg\",\"price\":126.99,\"brand\":\"Samsung\",\"model\":\"MZ-V6E500BW\",\"storageInterface\":\"M.2-2280\",\"rpm\":\"SSD\",\"type\":\"SSD\",\"cacheMemory\":\"N/A\"},{\"id\":\"B01MQG7C5F\",\"title\":\"Marshal 320GB Internal Hard Disc Drive\",\"link\":\"https://amazon.com/dp/B01MQG7C5F?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41v9hG6hHBL._SL75_.jpg\",\"price\":15,\"brand\":\"Marshal\",\"model\":\"MAL2320SA-T54\",\"storageInterface\":\"SATA 3.0Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"8 MB\"},{\"id\":\"B01MSW4MNS\",\"title\":\"Seagate BarraCuda Pro 4TB Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B01MSW4MNS?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41Tvo6KHEQL._SL75_.jpg\",\"price\":228.68,\"brand\":\"Seagate\",\"model\":\"ST4000DM006\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B0713R3Y6F\",\"title\":\"Seagate 4TB BarraCuda Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B0713R3Y6F?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41fnP1CzI8L._SL75_.jpg\",\"price\":0,\"brand\":\"Seagate\",\"model\":\"ST4000DM004\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"256 MB\"},{\"id\":\"B071KVB4F8\",\"title\":\"WD Purple 4TB Surveillance Hard Drive\",\"link\":\"https://amazon.com/dp/B071KVB4F8?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41RCVCZWjML._SL75_.jpg\",\"price\":78,\"brand\":\"Western Digital\",\"model\":\"WD40PURZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B071RM2HS7\",\"title\":\"WD Purple 2TB Surveillance Hard Drive\",\"link\":\"https://amazon.com/dp/B071RM2HS7?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41RCVCZWjML._SL75_.jpg\",\"price\":65,\"brand\":\"Western Digital\",\"model\":\"WD20PURZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B071WLPRHN\",\"title\":\"Seagate Barracuda 4TB Internal Hard Drive HDD\",\"link\":\"https://amazon.com/dp/B071WLPRHN?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41Z-sFIFVOL._SL75_.jpg\",\"price\":71.88,\"brand\":\"Seagate\",\"model\":\"ST4000DMB04\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"256 MB\"},{\"id\":\"B072L175ZW\",\"title\":\"WD Purple 1TB Surveillance Hard Drive\",\"link\":\"https://amazon.com/dp/B072L175ZW?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41RCVCZWjML._SL75_.jpg\",\"price\":0,\"brand\":\"Western Digital\",\"model\":\"WD10PURZ\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B078TJ17QF\",\"title\":\"Seagate Barracuda Pro Performance Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B078TJ17QF?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/51EZCWwF21L._SL75_.jpg\",\"price\":55.74,\"brand\":\"Seagate\",\"model\":\"ST1000LM049\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B079BQS5WQ\",\"title\":\"WD Blue 2TB Mobile Hard Drive\",\"link\":\"https://amazon.com/dp/B079BQS5WQ?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41iFXh1UwhL._SL75_.jpg\",\"price\":64.99,\"brand\":\"Western Digital\",\"model\":\"WD20SPZX\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B07CYH8FLR\",\"title\":\"Western Digital RE4 WD2003FYYS 2TB\",\"link\":\"https://amazon.com/dp/B07CYH8FLR?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/51nIsNY6nFL._SL75_.jpg\",\"price\":70,\"brand\":\"Western Digital\",\"model\":\"WD2003FYYS-OEM\",\"storageInterface\":\"SATA 3.0Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B07D99KFPK\",\"title\":\"Seagate BarraCuda 1TB Internal Hard Drive HDD\",\"link\":\"https://amazon.com/dp/B07D99KFPK?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41pLbGTevQL._SL75_.jpg\",\"price\":44.99,\"brand\":\"Seagate\",\"model\":\"ST1000DMZ10/DM010\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B07D9C618D\",\"title\":\"Seagate Exos 4TB Internal Hard Drive Enterprise\",\"link\":\"https://amazon.com/dp/B07D9C618D?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41h7MsvYwAL._SL75_.jpg\",\"price\":114.99,\"brand\":\"Seagate\",\"model\":\"ST4000NMZ035\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"128 MB\"},{\"id\":\"B07FSZV81L\",\"title\":\"HP/Seagate Constellation ES 2TB Hard Drive OEM\",\"link\":\"https://amazon.com/dp/B07FSZV81L?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41CzMsnLe4L._SL75_.jpg\",\"price\":0,\"brand\":\"HP\",\"model\":\"MB2000EBZQC\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"7200 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B07H231394\",\"title\":\"Seagate Skyhawk 4TB Surveillance Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B07H231394?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41bNu0Y4vxL._SL75_.jpg\",\"price\":87.98,\"brand\":\"Seagate\",\"model\":\"ST4000VXZ07/VX007\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5900 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B07H2FPFW9\",\"title\":\"Seagate Skyhawk 1TB Surveillance Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B07H2FPFW9?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41vrPpwjo4L._SL75_.jpg\",\"price\":47.98,\"brand\":\"Seagate\",\"model\":\"ST1000VXZ05/VX005\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5900 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"},{\"id\":\"B07H2GY8ZS\",\"title\":\"Seagate BarraCuda 3TB Internal Hard Drive\",\"link\":\"https://amazon.com/dp/B07H2GY8ZS?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41t11GEiTOL._SL75_.jpg\",\"price\":79.99,\"brand\":\"Seagate\",\"model\":\"ST3000DMZ07/DM007\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5400 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"256 MB\"},{\"id\":\"B07H2GY8ZV\",\"title\":\"Seagate IronWolf 2TB NAS Internal HDD\",\"link\":\"https://amazon.com/dp/B07H2GY8ZV?tag=pcbuildcompat-20\",\"img\":\"https://images-na.ssl-images-amazon.com/images/I/41l%2Bu-aCM3L._SL75_.jpg\",\"price\":79.99,\"brand\":\"Seagate\",\"model\":\"ST2000VNZ04/VN004\",\"storageInterface\":\"SATA 6Gb/s\",\"rpm\":\"5900 RPM\",\"type\":\"HDD\",\"cacheMemory\":\"64 MB\"}]");
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
            }
        }).start();
    }

    @Override
    public void onBuildClick(int position, String buildname) {
        Intent editbuild = new Intent(this, EditBuild.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        spEdit.putString("origBuild", buildname);
        spEdit.apply();
        Toast.makeText(this, sp.getString("origBuild", ""), Toast.LENGTH_SHORT).show();
        startActivity(editbuild);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spEdit.remove("temp_CPU");
        spEdit.remove("temp_Cooler");
        spEdit.remove("temp_Mobo");
        spEdit.remove("temp_RAM");
        spEdit.remove("temp_Storage");
        spEdit.remove("origBuild");
        spEdit.remove("_bname");
        spEdit.apply();
    }
}