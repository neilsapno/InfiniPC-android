package cedric.ciel.infinipc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import cedric.ciel.infinipc.Utils.DBHandler;
import cedric.ciel.infinipc.databinding.ActivityNewBuildBinding;

public class NewBuild extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;
    ActivityNewBuildBinding newBuildBinding;
    //private TextView tv_cpu_name,tv_cpu_brand, tv_cpu_model, tv_cpu_speed, tv_cpu_socket, tv_cpu_price, tv_cooler_name, tv_cooler_brand, tv_cooler_model, tv_cooler_rpm, tv_cooler_noiselvl,
    //       tv_mobo_name, tv_mobo_model, tv_mobo_form, tv_mobo_ramslot, tv_mobo_socket, tv;
    private ImageView iv_cpuImg;
    private EditText et_buildName;
    //private CardView cv_cpu, cv_cooler, cv_mobo, cv_memory, cv_storage, cv_gpu, cv_case, cv_psu, cv_casefan;
    private String BuildName, sCPU, Cooler, Mobo, Memory, Storage, GPU, Case, PSU, CaseFan, BuildImgUrl;
    private int RAMCount, Watts;
    private double Price;
    JSONObject jCpu, cooler, mobo, ram, storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newBuildBinding = ActivityNewBuildBinding.inflate(getLayoutInflater());
        setContentView(newBuildBinding.getRoot());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPreferences = getSharedPreferences(this.getPackageName() + "Picked_Parts", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (!sharedPreferences.getString("_bname", "").isEmpty())
            newBuildBinding.etBuildName.setText(sharedPreferences.getString("_bname", ""));


        //initialize();
        FloatingActionButton btn_saveBuild = findViewById(R.id.btn_saveBuild);

        dbHandler = new DBHandler(this);

        btn_saveBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildName = newBuildBinding.etBuildName.getText().toString();
                if (!BuildName.isBlank()) {
                    try {
                        jCpu = new JSONObject(sharedPreferences.getString("temp_CPU", ""));
                        dbHandler.addCPU(BuildName, jCpu.getString("title"), jCpu.getString("brand"), jCpu.getString("model"), jCpu.getString("speed"), jCpu.getString("socketType"),
                                jCpu.getString("link"), jCpu.getString("img"), jCpu.getDouble("price"));
                        sCPU = jCpu.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addCPU(BuildName);
                        sCPU = "";
                    }
                    try {
                        cooler = new JSONObject(sharedPreferences.getString("temp_Cooler", ""));
                        dbHandler.addCooler(BuildName, cooler.getString("title"), cooler.getString("brand"), cooler.getString("model"), cooler.getString("rpm"), cooler.getString("noiseLevel"),
                                cooler.getString("link"), cooler.getString("img"), cooler.getDouble("price"));
                        Cooler = cooler.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addCooler(BuildName);
                        Cooler = "";
                    }
                    try {
                        mobo = new JSONObject(sharedPreferences.getString("temp_Mobo", ""));
                        dbHandler.addMotherboard(BuildName, mobo.getString("title"), mobo.getString("model"), mobo.getString("formFactor"), mobo.getString("memorySlots"), mobo.getString("socketType"),
                                mobo.getString("link"), mobo.getString("img"), mobo.getDouble("price"));
                        Mobo = mobo.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addMotherboard(BuildName);
                        Mobo = "";
                    }
                    try {
                        ram = new JSONObject(sharedPreferences.getString("temp_RAM", ""));
                        dbHandler.addRAM(BuildName, ram.getString("title"), ram.getString("model"), ram.getString("size"), ram.getString("quantity"), ram.getString("type"),
                                ram.getString("link"), ram.getString("img"), ram.getDouble("price"));
                        Memory = ram.getString("title");
                        RAMCount = Integer.parseInt(ram.getString("size").replaceAll("[\\D]", ""));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addRAM(BuildName);
                        Memory = "";
                        RAMCount = 0;
                    }
                    try {
                        storage = new JSONObject(sharedPreferences.getString("temp_Storage", ""));
                        dbHandler.addStorage(BuildName, storage.getString("title"), storage.getString("model"), storage.getString("cacheMemory"), storage.getString("storageInterface"), storage.getString("type"),
                                storage.getString("link"), storage.getString("img"), storage.getDouble("price"));
                        Storage = storage.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addStorage(BuildName);
                        Storage = "";
                    }
                    GPU = "VCard";
                    Case = "PC Case";
                    PSU = "PSupply";
                    CaseFan = "CaseFan";
                    Price = getEstimated_Price(jCpu);
                    Price += getEstimated_Price(cooler);
                    Price += getEstimated_Price(mobo);
                    Price += getEstimated_Price(ram);
                    Price += getEstimated_Price(storage);
                    if(Price < 500) Watts = 300;
                    else if (Price < 1000) Watts = 500;
                    else if (Price < 1500) Watts = 750;
                    else Watts = 900;
                    BuildImgUrl = "https://";

                    dbHandler.addNewBuild(BuildName, sCPU, Cooler, Mobo, Memory, Storage, GPU, Case, PSU, CaseFan, RAMCount, Watts, Price, BuildImgUrl);
                    dbHandler.close();
                    Intent builds = new Intent(NewBuild.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(builds);
                }
                else{
                    Snackbar.make(newBuildBinding.coordinatorLay, "Please fill up the Build Name", Snackbar.LENGTH_SHORT).setBackgroundTint(Color.RED).show();
                }
            }
        });
        getParts();
        partsSelector();
    }

    private void partsSelector() {
        newBuildBinding.cvCpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCPU = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseCPU.putExtra("type", "CPU");
                browseCPU.putExtra("from", "new");
                startActivity(browseCPU);
            }
        });
        newBuildBinding.cvCpuCooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCoolers = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseCoolers.putExtra("type", "Coolers");
                browseCoolers.putExtra("from", "new");
                startActivity(browseCoolers);
            }
        });
        newBuildBinding.cvMobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseMobos = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseMobos.putExtra("type", "Mobos");
                browseMobos.putExtra("from", "new");
                startActivity(browseMobos);
            }
        });
        newBuildBinding.cvMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseMemory = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseMemory.putExtra("type", "RAMs");
                browseMemory.putExtra("from", "new");
                startActivity(browseMemory);
            }
        });
        newBuildBinding.cvStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseStorage = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseStorage.putExtra("type", "Storages");
                browseStorage.putExtra("from", "new");
                startActivity(browseStorage);
            }
        });
    }

    private void getParts() {
        try {
            jCpu = new JSONObject(sharedPreferences.getString("temp_CPU", ""));
//            JSONObject memory = new JSONObject(sharedPreferences.getString("temp_Memory", ""));
//            JSONObject storage = new JSONObject(sharedPreferences.getString("temp_Storage", ""));
//            JSONObject gpu = new JSONObject(sharedPreferences.getString("temp_GPU", ""));
//            JSONObject pccase = new JSONObject(sharedPreferences.getString("temp_Case", ""));
//            JSONObject psu = new JSONObject(sharedPreferences.getString("temp_PSU", ""));
//            JSONObject casefan = new JSONObject(sharedPreferences.getString("temp_CaseFan", ""));

            //CPU
            if (jCpu.has("id")) {
                Glide.with(NewBuild.this)
                        .load(jCpu.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivCpuImg);
                newBuildBinding.tvCpuName.setText(jCpu.getString("title"));
                newBuildBinding.tvCpuBrand.setText("Brand: " + jCpu.getString("brand"));
                newBuildBinding.tvCpuModel.setText("Model: " + jCpu.getString("model"));
                newBuildBinding.tvCpuSpeed.setText("Speed: " + jCpu.getString("speed"));
                newBuildBinding.tvCpuSocket.setText("Socket: " + jCpu.getString("socketType"));
                newBuildBinding.tvCpuPrice.setText("$" + jCpu.getDouble("price"));
            }
        } catch (Exception e) {
        }
        try {
            cooler = new JSONObject(sharedPreferences.getString("temp_Cooler", ""));
            //Cooler
            if (cooler.has("id")) {
                Glide.with(NewBuild.this)
                        .load(cooler.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivCoolerImg);
                newBuildBinding.tvCoolerName.setText(cooler.getString("title"));
                newBuildBinding.tvCoolerBrand.setText("Brand: " + cooler.getString("brand"));
                newBuildBinding.tvCoolerModel.setText("Model: " + cooler.getString("model"));
                newBuildBinding.tvCoolerRpm.setText("RPM: " + cooler.getString("rpm"));
                newBuildBinding.tvCoolerNoiseLvl.setText("Noise Level: " + cooler.getString("noiseLevel"));
                newBuildBinding.tvCoolerPrice.setText("$" + cooler.getDouble("price"));
            }
        } catch (Exception e) {
        }
        try {
            mobo = new JSONObject(sharedPreferences.getString("temp_Mobo", ""));
            //Motherboard
            if (mobo.has("id")) {
                Glide.with(NewBuild.this)
                        .load(mobo.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivMoboImg);
                newBuildBinding.tvMoboName.setText(mobo.getString("title"));
                newBuildBinding.tvMoboModel.setText("Model: " + mobo.getString("brand"));
                newBuildBinding.tvMoboForm.setText("Form: " + mobo.getString("formFactor"));
                newBuildBinding.tvMoboRamslot.setText("Memory Slots: " + mobo.getString("memorySlots"));
                newBuildBinding.tvMoboSocket.setText("Socket: " + mobo.getString("socketType"));
                newBuildBinding.tvMoboPrice.setText("$" + mobo.getDouble("price"));
            }
        } catch (Exception e) {
        }
        try {
            ram = new JSONObject(sharedPreferences.getString("temp_RAM", ""));
            //Motherboard
            if (ram.has("id")) {
                Glide.with(NewBuild.this)
                        .load(ram.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivMemoryImg);
                newBuildBinding.tvMemoryName.setText(ram.getString("title"));
                newBuildBinding.tvMemoryModel.setText("Model: " + ram.getString("model"));
                newBuildBinding.tvMemorySize.setText("Size: " + ram.getString("size"));
                newBuildBinding.tvMemoryQty.setText("Quantity: " + ram.getString("quantity"));
                newBuildBinding.tvMemoryType.setText("Type: " + ram.getString("type"));
                newBuildBinding.tvMemoryPrice.setText("$" + ram.getDouble("price"));
            }
        } catch (Exception e) {
        }
        try {
            storage = new JSONObject(sharedPreferences.getString("temp_Storage", ""));
            //Motherboard
            if (storage.has("id")) {
                Glide.with(NewBuild.this)
                        .load(storage.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivStorageImg);
                newBuildBinding.tvStorageName.setText(storage.getString("title"));
                newBuildBinding.tvStorageModel.setText("Model: " + storage.getString("brand"));
                newBuildBinding.tvStorageCachesize.setText("Cache Memory Size: " + storage.getString("cacheMemory"));
                newBuildBinding.tvStorageInterface.setText("Interface: " + storage.getString("storageInterface"));
                newBuildBinding.tvStorageType.setText("Type: " + storage.getString("type"));
                newBuildBinding.tvStoragePrice.setText("$" + storage.getDouble("price"));
            }
        } catch (Exception e) {
        }
    }

    private double getEstimated_Price(JSONObject json){
        double price = 0.00;
        try{
            price = json.getDouble("price");
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return price;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHandler.close();
//        editor.remove("temp_CPU");
//        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewBuild.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}