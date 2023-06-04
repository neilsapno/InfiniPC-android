package cedric.ciel.infinipc;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

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

    private String BuildName, sCPU, Cooler, Mobo, Memory, Storage, GPU, Case, PSU, CaseFan, BuildImgUrl;
    private String ram_socket;
    private int RAMCount, Watts;
    private double Price;
    JSONObject jCpu, cooler, mobo, ram, storage, gpu, psu, ccase, caseFan;

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
                    try {
                        gpu = new JSONObject(sharedPreferences.getString("temp_GPU", ""));
                        dbHandler.addGPU(BuildName, gpu.getString("title"), gpu.getString("brand"), gpu.getString("model"), gpu.getString("storageInterface"), gpu.getString("memory"),
                                gpu.getString("link"), gpu.getString("img"), gpu.getDouble("price"));
                        GPU = gpu.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addGPU(BuildName);
                        GPU = "";
                    }

                    try {
                        psu = new JSONObject(sharedPreferences.getString("temp_PSU", ""));
                        dbHandler.addPSU(BuildName, psu.getString("title"), psu.getString("brand"), psu.getString("model"), psu.getString("power"), psu.getString("efficiency"),
                                psu.getString("link"), psu.getString("img"), psu.getDouble("price"));
                        PSU = psu.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addPSU(BuildName);
                        PSU= "";
                    }
                    try {
                        ccase = new JSONObject(sharedPreferences.getString("temp_Case", ""));
                            dbHandler.addCase(BuildName, ccase.getString("title"), ccase.getString("brand"), ccase.getString("model"), ccase.getString("sidePanel"), ccase.getString("cabinetType"),
                        ccase.getString("link"), ccase.getString("img"), ccase.getDouble("price"));
                            Case = ccase.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addCase(BuildName);
                        Case = "";
                    }
                    try {
                        caseFan = new JSONObject(sharedPreferences.getString("temp_CaseFan", ""));
                        dbHandler.addCaseFan(BuildName, caseFan.getString("title"), caseFan.getString("model"), caseFan.getString("rpm"), caseFan.getString("noiseLevel"), caseFan.getString("airFlow"),
                                caseFan.getString("link"), caseFan.getString("img"), caseFan.getDouble("price"));
                        CaseFan = caseFan.getString("title");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dbHandler.addCaseFan(BuildName);
                        CaseFan= "";
                    }


                    Price = getEstimated_Price(jCpu);
                    Price += getEstimated_Price(cooler);
                    Price += getEstimated_Price(mobo);
                    Price += getEstimated_Price(ram);
                    Price += getEstimated_Price(storage);
                    Price += getEstimated_Price(gpu);
                    Price += getEstimated_Price(psu);
                    Price += getEstimated_Price(ccase);
                    Price += getEstimated_Price(caseFan);



                        if (Price <= 0) Watts = 0;
                        else if(Price < 500) Watts = 300;
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

        try{
            if (!isRAMCompatible() || !isSocketCompatible()) {
                Snackbar.make(newBuildBinding.coordinatorLay, "One of the components have compatibility issue/s", Snackbar.LENGTH_INDEFINITE).setBackgroundTint(Color.rgb(255, 153, 0))
                        .setAction("See Info", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewBuild.this);
                                builder.setTitle("Compatibility Issue/s found:");
                                String message = "";
                                if (!isSocketCompatible())
                                    message += "The selected Motherboard and CPU socket type are not compatible.\n";
                                if (!isRAMCompatible())
                                    message += "The selected RAM is not compatible with Motherboard.";
                                builder.setMessage(message);
                                builder.setPositiveButton("Close", null);
                                builder.show();
                            }
                        })
                        .show();
            }
        } catch (Exception e) {

        }
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
        newBuildBinding.cvGpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseStorage = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseStorage.putExtra("type", "GPUs");
                browseStorage.putExtra("from", "new");
                startActivity(browseStorage);
            }
        });
        newBuildBinding.cvPsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseStorage = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseStorage.putExtra("type", "PSUs");
                browseStorage.putExtra("from", "new");
                startActivity(browseStorage);
            }
        });
        newBuildBinding.cvCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseStorage = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseStorage.putExtra("type", "Cases");
                browseStorage.putExtra("from", "new");
                startActivity(browseStorage);
            }
        });
        newBuildBinding.cvCasefan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseStorage = new Intent(NewBuild.this, BrowseParts.class);
                editor.putString("_bname", newBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseStorage.putExtra("type", "CaseFans");
                browseStorage.putExtra("from", "new");
                startActivity(browseStorage);
            }
        });
    }

    private void getParts() {
        try {
            jCpu = new JSONObject(sharedPreferences.getString("temp_CPU", ""));
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
            //Storage
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
        try {
            gpu = new JSONObject(sharedPreferences.getString("temp_GPU", ""));
            //GPU
            if (gpu.has("id")) {
                Glide.with(NewBuild.this)
                        .load(gpu.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivGpuImg);
                newBuildBinding.tvGpuName.setText(gpu.getString("title"));
                newBuildBinding.tvGpuModel.setText("Model: " + gpu.getString("brand"));
                newBuildBinding.tvGpuSpeed .setText("Cache Memory Size: " + gpu.getString("model"));
                newBuildBinding.tvGpuInterface.setText("Interface: " + gpu.getString("storageInterface"));
                newBuildBinding.tvGpuVram.setText("Type: " + gpu.getString("memory"));
                newBuildBinding.tvGpuPrice.setText("$" + gpu.getDouble("price"));
            }
        } catch (Exception e) {
        }
        try {
            psu = new JSONObject(sharedPreferences.getString("temp_PSU", ""));
            //PSU
            if (psu.has("id")) {
                Glide.with(NewBuild.this)
                        .load(psu.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivPsuImg);
                newBuildBinding.tvPsuName.setText(psu.getString("title"));
                newBuildBinding.tvPsuBrand.setText("Model: " + psu.getString("brand"));
                newBuildBinding.tvPsuModel .setText("Cache Memory Size: " + psu.getString("model"));
                newBuildBinding.tvPsuPower.setText("Interface: " + psu.getString("power"));
                newBuildBinding.tvPsuEfficiency.setText("Type: " + psu.getString("efficiency"));
                newBuildBinding.tvPsuPrice.setText("$" + psu.getDouble("price"));
            }
        } catch (Exception e) {
        }
        try {
            ccase = new JSONObject(sharedPreferences.getString("temp_Case", ""));
                //Case
                if (ccase.has("id")) {
                Glide.with(NewBuild.this)
                        .load(ccase.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(newBuildBinding.ivCaseImg);
                    newBuildBinding.tvCaseName.setText(ccase.getString("title"));
                    newBuildBinding.tvCaseBrand.setText("Brand: " + ccase.getString("brand"));
                    newBuildBinding.tvCaseModel .setText("Model: " + ccase.getString("model"));
                    newBuildBinding.tvCaseSidePanel.setText("Side Panel: " + ccase.getString("sidePanel"));
                    newBuildBinding.tvCaseForm.setText("Form Type: " + ccase.getString("cabinetType"));
                    newBuildBinding.tvCasePrice.setText("$" + ccase.getDouble("price"));
            }
        } catch (Exception e) {
        }
        try {
            caseFan = new JSONObject(sharedPreferences.getString("temp_CaseFan", ""));
            //Case Fan
            if (caseFan.has("id")) {
                Glide.with(NewBuild.this)
                        .load(caseFan.getString("img"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(newBuildBinding.ivCasefanImg);
                newBuildBinding.tvCasefanName.setText(caseFan.getString("title"));
                newBuildBinding.tvCasefanModel.setText("Model: " + caseFan.getString("brand") +" "+caseFan.getString("model"));
                newBuildBinding.tvCasefanRpm .setText("RPM: " + caseFan.getString("rpm"));
                newBuildBinding.tvCasefanNoiseLvl.setText("Noise Lvl: " + caseFan.getString("noiseLevel"));
                newBuildBinding.tvCasefanAirflow.setText("AirFlow: " + caseFan.getString("airFlow"));
                newBuildBinding.tvCasefanPrice.setText("$" + caseFan.getDouble("price"));
            }
        } catch (Exception e) {
        }


    }

    private boolean isRAMCompatible(){
        if(newBuildBinding.tvMoboName.getText().toString().contains("DDR3")) ram_socket = "DDR3";
        else if(newBuildBinding.tvMoboName.getText().toString().contains("DDR4")) ram_socket = "DDR4";

        if(ram_socket.contains(newBuildBinding.tvMemoryType.getText().toString())) return true;

        return false;
    }
    private boolean isSocketCompatible(){
        if(newBuildBinding.tvCpuSocket.getText().toString().equalsIgnoreCase(newBuildBinding.tvMoboSocket.getText().toString())){
            return true;
        }
        return false;
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