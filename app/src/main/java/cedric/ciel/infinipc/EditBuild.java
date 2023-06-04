package cedric.ciel.infinipc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cedric.ciel.infinipc.Parts.CPU;
import cedric.ciel.infinipc.Parts.Case;
import cedric.ciel.infinipc.Parts.CaseFan;
import cedric.ciel.infinipc.Parts.Cooler;
import cedric.ciel.infinipc.Parts.GPU;
import cedric.ciel.infinipc.Parts.Motherboard;
import cedric.ciel.infinipc.Parts.PSU;
import cedric.ciel.infinipc.Parts.RAM;
import cedric.ciel.infinipc.Parts.Storage;
import cedric.ciel.infinipc.Utils.DBHandler;
import cedric.ciel.infinipc.databinding.ActivityEditBuildBinding;

public class EditBuild extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;
    ActivityEditBuildBinding editBuildBinding;
    private String BuildName, sCPU, sCooler, sMobo, Memory, Storage, GPU, Case, PSU, CaseFan, BuildImgUrl;
    private int RAMCount, Watts;
    private double Price;
    Boolean hasCPU, hasCooler, hasMobo, hasRAM, hasStorage, hasGPU, hasPSU, hasCase, hasCaseFan = false;
    ArrayList<CPU> cpu = new ArrayList<>();
    ArrayList<Cooler> cooler = new ArrayList<>();
    ArrayList<Motherboard> mobo = new ArrayList<>();
    ArrayList<RAM> ram = new ArrayList<>();
    ArrayList<Storage> storage = new ArrayList<>();
    ArrayList<GPU> gpu = new ArrayList<>();
    ArrayList<PSU> psu = new ArrayList<>();
    ArrayList<Case> ccase = new ArrayList<>();
    ArrayList<CaseFan> casefan = new ArrayList<>();

    JSONObject jCpu, jcooler, jmobo, jram, jstorage, jGpu, jPsu, jCase, jCaseFan;
    private String ram_socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editBuildBinding = ActivityEditBuildBinding.inflate(getLayoutInflater());
        setContentView(editBuildBinding.getRoot());

        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sharedPreferences = getSharedPreferences(getPackageName() + "Picked_Parts", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String bName = sharedPreferences.getString("origBuild", null);

        if(sharedPreferences.getString("_bname", "").isEmpty()) editBuildBinding.etBuildName.setText(bName);
        else editBuildBinding.etBuildName.setText(sharedPreferences.getString("_bname", null));

        FloatingActionButton btn_saveBuild = findViewById(R.id.btn_saveBuild);

        dbHandler = new DBHandler(this);

        btn_saveBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildName = editBuildBinding.etBuildName.getText().toString();
                //CPU = editBuildBinding.tvCpuName.getText().toString();
                try {
                    jCpu = new JSONObject(sharedPreferences.getString("temp_CPU", ""));
                    dbHandler.updateCPU(bName, BuildName, jCpu.getString("title"), jCpu.getString("brand"), jCpu.getString("model"), jCpu.getString("speed"), jCpu.getString("socketType"), jCpu.getString("link"), jCpu.getString("img"), jCpu.getDouble("price"));

//                    if (hasCPU) {
//                        dbHandler.updateCPU(bName, BuildName, jCpu.getString("title"), jCpu.getString("brand"), jCpu.getString("model"), jCpu.getString("speed"), jCpu.getString("socketType"), jCpu.getString("link"), jCpu.getString("img"), jCpu.getDouble("price"));
//                    } else {
//                        dbHandler.addCPU(BuildName, jCpu.getString("title"), jCpu.getString("brand"), jCpu.getString("model"), jCpu.getString("speed"), jCpu.getString("socketType"), jCpu.getString("link"), jCpu.getString("img"), jCpu.getDouble("price"));
//                    }
                } catch (JSONException e) {
                    dbHandler.updateCPU(bName, BuildName);
                }
                sCPU = editBuildBinding.tvCpuName.getText().toString();;
                try {
                    jcooler = new JSONObject(sharedPreferences.getString("temp_Cooler", ""));
                    dbHandler.updateCooler(bName, BuildName, jcooler.getString("title"), jcooler.getString("brand"), jcooler.getString("model"), jcooler.getString("rpm"), jcooler.getString("noiseLevel"), jcooler.getString("link"), jcooler.getString("img"), jcooler.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updateCooler(bName, BuildName);
                }
                sCooler = editBuildBinding.tvCoolerName.getText().toString();
                try {
                    jmobo = new JSONObject(sharedPreferences.getString("temp_Mobo", ""));
                    dbHandler.updateMotherboard(bName, BuildName, jmobo.getString("title"), jmobo.getString("model"), jmobo.getString("formFactor"), jmobo.getString("memorySlots"), jmobo.getString("socketType"),
                            jmobo.getString("link"), jmobo.getString("img"), jmobo.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updateMotherboard(bName, BuildName);
                }
                sMobo = editBuildBinding.tvMoboName.getText().toString();
                try {
                    jram = new JSONObject(sharedPreferences.getString("temp_RAM", ""));
                    dbHandler.updateRAM(bName, BuildName, jram.getString("title"), jram.getString("model"), jram.getString("size"), jram.getString("quantity"), jram.getString("type"),
                            jram.getString("link"), jram.getString("img"), jram.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updateRAM(bName, BuildName);
                }
                Memory = editBuildBinding.tvMemoryName.getText().toString();
                String ramSize = editBuildBinding.tvMemorySize.getText().toString().replace("Size: ","");
                RAMCount = Integer.parseInt(ramSize.replace(" GB", ""));
                try {
                    jstorage = new JSONObject(sharedPreferences.getString("temp_Storage", ""));
                    dbHandler.updateStorage(bName, BuildName, jstorage.getString("title"), jstorage.getString("model"), jstorage.getString("cacheMemory"), jstorage.getString("storageInterface"), jstorage.getString("type"),
                            jstorage.getString("link"), jstorage.getString("img"), jstorage.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updateStorage(bName, BuildName);
                }
                Storage = editBuildBinding.tvStorageName.getText().toString();
                try {
                    jGpu = new JSONObject(sharedPreferences.getString("temp_GPU", ""));
                    dbHandler.updateGPU(bName, BuildName, jGpu.getString("title"), jGpu.getString("brand"), jGpu.getString("model"), jGpu.getString("storageInterface"), jGpu.getString("memory"),
                            jGpu.getString("link"), jGpu.getString("img"), jGpu.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updateGPU(bName, BuildName);
                }
                GPU = editBuildBinding.tvGpuName.getText().toString();

                try {
                    jPsu = new JSONObject(sharedPreferences.getString("temp_PSU", ""));
                    dbHandler.updatePSU(bName, BuildName, jPsu.getString("title"), jPsu.getString("brand"), jPsu.getString("model"), jPsu.getString("power"), jPsu.getString("efficiency"),
                            jPsu.getString("link"), jPsu.getString("img"), jPsu.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updatePSU(bName, BuildName);
                }
                PSU = editBuildBinding.tvPsuName.getText().toString();

                try {
                    jCase = new JSONObject(sharedPreferences.getString("temp_Case", ""));
                    dbHandler.updateCase(bName, BuildName, jCase.getString("title"), jCase.getString("brand"), jCase.getString("model"), jCase.getString("sidePanel"), jCase.getString("cabinetType"),
                            jCase.getString("link"), jCase.getString("img"), jCase.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updateCase(bName, BuildName);
                }
                Case = editBuildBinding.tvCaseName.getText().toString();

                try {
                    jCaseFan = new JSONObject(sharedPreferences.getString("temp_CaseFan", ""));
                    dbHandler.updateCaseFan(bName, BuildName, jCaseFan.getString("title"), jCaseFan.getString("model"), jCaseFan.getString("rpm"), jCaseFan.getString("noiseLevel"), jCaseFan.getString("airFlow"),
                            jCaseFan.getString("link"), jCaseFan.getString("img"), jCaseFan.getDouble("price"));
                } catch (JSONException e) {
                    dbHandler.updateCaseFan(bName, BuildName);
                }
                CaseFan = editBuildBinding.tvCasefanName.getText().toString();


                Price = getEstimated_Price(editBuildBinding.tvCpuPrice);
                Price += getEstimated_Price(editBuildBinding.tvCoolerPrice);
                Price += getEstimated_Price(editBuildBinding.tvMoboPrice);
                Price += getEstimated_Price(editBuildBinding.tvMemoryPrice);
                Price += getEstimated_Price(editBuildBinding.tvStoragePrice);
                Price += getEstimated_Price(editBuildBinding.tvGpuPrice);
                Price += getEstimated_Price(editBuildBinding.tvPsuPrice);
                Price += getEstimated_Price(editBuildBinding.tvCasePrice);
                Price += getEstimated_Price(editBuildBinding.tvCasefanPrice);

                if (Price <= 0) Watts = 0;
                else if(Price < 500) Watts = 300;
                else if (Price < 1000) Watts = 500;
                else if (Price < 1500) Watts = 750;
                else Watts = 900;
                BuildImgUrl = "https://";

                dbHandler.editBuild(bName, BuildName, sCPU, sCooler, sMobo, Memory, Storage, GPU, Case, PSU, CaseFan, RAMCount, Watts, Price, BuildImgUrl);
                dbHandler.close();
                Intent builds = new Intent(EditBuild.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(builds);
            }
        });
        getParts();
        partsSelector();
        try{
            if (!isRAMCompatible() || !isSocketCompatible()) {
                Snackbar.make(editBuildBinding.coordinatorLay, "One of the components have compatibility issue/s", Snackbar.LENGTH_INDEFINITE).setBackgroundTint(Color.rgb(255, 153, 0))
                        .setAction("See Info", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditBuild.this);
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
        editBuildBinding.cvCpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCPU = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseCPU.putExtra("type", "CPU");
                browseCPU.putExtra("from", "edit");
                startActivity(browseCPU);
            }
        });
        editBuildBinding.cvCpuCooler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseCoolers = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseCoolers.putExtra("type", "Coolers");
                browseCoolers.putExtra("from", "edit");
                startActivity(browseCoolers);
            }
        });
        editBuildBinding.cvMobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseMobos = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseMobos.putExtra("type", "Mobos");
                browseMobos.putExtra("from", "edit");
                startActivity(browseMobos);
            }
        });
        editBuildBinding.cvMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseMemory = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseMemory.putExtra("type", "RAMs");
                browseMemory.putExtra("from", "edit");
                startActivity(browseMemory);
            }
        });
        editBuildBinding.cvStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseMemory = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseMemory.putExtra("type", "Storages");
                browseMemory.putExtra("from", "edit");
                startActivity(browseMemory);
            }
        });
        editBuildBinding.cvGpu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseGpus = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseGpus.putExtra("type", "GPUs");
                browseGpus.putExtra("from", "edit");
                startActivity(browseGpus);
            }
        });
        editBuildBinding.cvPsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseGpus = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseGpus.putExtra("type", "PSUs");
                browseGpus.putExtra("from", "edit");
                startActivity(browseGpus);
            }
        });
        editBuildBinding.cvCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseGpus = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseGpus.putExtra("type", "Cases");
                browseGpus.putExtra("from", "edit");
                startActivity(browseGpus);
            }
        });
        editBuildBinding.cvCasefan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browseGpus = new Intent(EditBuild.this, BrowseParts.class);
                editor.putString("_bname", editBuildBinding.etBuildName.getText().toString());
                editor.apply();
                browseGpus.putExtra("type", "CaseFans");
                browseGpus.putExtra("from", "edit");
                startActivity(browseGpus);
            }
        });
    }

    private void getParts() {
        cpu = dbHandler.getCPU(cpu, editBuildBinding.etBuildName.getText().toString());
        cooler = dbHandler.getCooler(cooler, editBuildBinding.etBuildName.getText().toString());
        mobo = dbHandler.getMobo(mobo, editBuildBinding.etBuildName.getText().toString());
        ram = dbHandler.getRAM(ram, editBuildBinding.etBuildName.getText().toString());
        storage = dbHandler.getStorage(storage, editBuildBinding.etBuildName.getText().toString());
        gpu = dbHandler.getGPU(gpu, editBuildBinding.etBuildName.getText().toString());
        psu = dbHandler.getPSU(psu, editBuildBinding.etBuildName.getText().toString());
        ccase = dbHandler.getCase(ccase, editBuildBinding.etBuildName.getText().toString());
        casefan = dbHandler.getCaseFan(casefan, editBuildBinding.etBuildName.getText().toString());

        try {
            //CPU
            jCpu = new JSONObject(sharedPreferences.getString("temp_CPU", ""));
            Glide.with(EditBuild.this).load(jCpu.getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCpuImg);
            editBuildBinding.tvCpuName.setText(jCpu.getString("title"));
            editBuildBinding.tvCpuBrand.setText("Brand: " + jCpu.getString("brand"));
            editBuildBinding.tvCpuModel.setText("Model: " + jCpu.getString("model"));
            editBuildBinding.tvCpuSpeed.setText("Speed: " + jCpu.getString("speed"));
            editBuildBinding.tvCpuSocket.setText("Socket: " + jCpu.getString("socketType"));
            editBuildBinding.tvCpuPrice.setText("$" + jCpu.getDouble("price"));

            hasCPU = true;
        } catch (JSONException e) {
            //return;
            if (cpu.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(cpu.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCpuImg);
                editBuildBinding.tvCpuName.setText(cpu.get(0).getTitle());
                editBuildBinding.tvCpuBrand.setText("Brand: " + cpu.get(0).getBrand());
                editBuildBinding.tvCpuModel.setText("Model: " + cpu.get(0).getModel());
                editBuildBinding.tvCpuSpeed.setText("Speed: " + cpu.get(0).getSpeed());
                editBuildBinding.tvCpuSocket.setText("Socket: " + cpu.get(0).getSocket());
                editBuildBinding.tvCpuPrice.setText("$" + cpu.get(0).getPrice());
                hasCPU = true;
            } else {
                editBuildBinding.tvCpuName.setText("CPU");
                editBuildBinding.tvCpuBrand.setText("Brand: ");
                editBuildBinding.tvCpuModel.setText("Model: ");
                editBuildBinding.tvCpuSpeed.setText("Speed: ");
                editBuildBinding.tvCpuSocket.setText("Socket: ");
                editBuildBinding.tvCpuPrice.setText("$");
                hasCPU = false;
            }
        }
        try {
            //Cooler
            jcooler = new JSONObject(sharedPreferences.getString("temp_Cooler", ""));
            Glide.with(EditBuild.this)
                    .load(jcooler.getString("img"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(editBuildBinding.ivCoolerImg);
            editBuildBinding.tvCoolerName.setText(jcooler.getString("title"));
            editBuildBinding.tvCoolerBrand.setText("Brand: " + jcooler.getString("brand"));
            editBuildBinding.tvCoolerModel.setText("Model: " + jcooler.getString("model"));
            editBuildBinding.tvCoolerRpm.setText("RPM: " + jcooler.getString("rpm"));
            editBuildBinding.tvCoolerNoiseLvl.setText("Noise Level: " + jcooler.getString("noiseLevel"));
            editBuildBinding.tvCoolerPrice.setText("$" + jcooler.getDouble("price"));
            hasCooler = true;
        } catch (JSONException e) {
            if (cooler.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(cooler.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCoolerImg);
                editBuildBinding.tvCoolerName.setText(cooler.get(0).getTitle());
                editBuildBinding.tvCoolerBrand.setText("Brand: " + cooler.get(0).getBrand());
                editBuildBinding.tvCoolerModel.setText("Model: " + cooler.get(0).getModel());
                editBuildBinding.tvCoolerRpm.setText("RPM: " + cooler.get(0).getrpm());
                editBuildBinding.tvCoolerNoiseLvl.setText("Noise Level: " + cooler.get(0).getnoiseLvl());
                editBuildBinding.tvCoolerPrice.setText("$" + cooler.get(0).getPrice());
                hasCooler = true;
            } else {
                editBuildBinding.tvCoolerName.setText("CPU Cooler");
                editBuildBinding.tvCoolerBrand.setText("Brand: ");
                editBuildBinding.tvCoolerModel.setText("Model: ");
                editBuildBinding.tvCoolerRpm.setText("RPM: ");
                editBuildBinding.tvCoolerNoiseLvl.setText("Noise Level: ");
                editBuildBinding.tvCoolerPrice.setText("$");
                hasCooler = false;
            }
        }
        try {
            //Mobo
            jmobo = new JSONObject(sharedPreferences.getString("temp_Mobo", ""));
            Glide.with(EditBuild.this)
                    .load(jmobo.getString("img"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(editBuildBinding.ivMoboImg);
            editBuildBinding.tvMoboName.setText(jmobo.getString("title"));
            editBuildBinding.tvMoboModel.setText("Model: " + jmobo.getString("brand"));
            editBuildBinding.tvMoboForm.setText("Form: " + jmobo.getString("formFactor"));
            editBuildBinding.tvMoboRamslot.setText("Memory Slots: " + jmobo.getString("memorySlots"));
            editBuildBinding.tvMoboSocket.setText("Socket: " + jmobo.getString("socketType"));
            editBuildBinding.tvMoboPrice.setText("$" + jmobo.getDouble("price"));
            hasMobo = true;
        } catch (JSONException e) {
            if (mobo.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(mobo.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivMoboImg);
                editBuildBinding.tvMoboName.setText(mobo.get(0).getTitle());
                editBuildBinding.tvMoboModel.setText("Model: " + mobo.get(0).getModel());
                editBuildBinding.tvMoboForm.setText("Form: " + mobo.get(0).getForm());
                editBuildBinding.tvMoboRamslot.setText("Memory Slots: " + mobo.get(0).getRamslot());
                editBuildBinding.tvMoboSocket.setText("Socket: " + mobo.get(0).getSocket());
                editBuildBinding.tvMoboPrice.setText("$" + mobo.get(0).getPrice());

                hasMobo = true;
            } else {
                editBuildBinding.tvMoboName.setText("Motherboard");
                editBuildBinding.tvMoboModel.setText("Model: ");
                editBuildBinding.tvMoboForm.setText("Form: ");
                editBuildBinding.tvMoboRamslot.setText("Memory Slots: ");
                editBuildBinding.tvMoboSocket.setText("Socket: ");
                editBuildBinding.tvMoboPrice.setText("$");
                hasMobo = false;
            }
        }
        try {
            //Mobo
            jram = new JSONObject(sharedPreferences.getString("temp_RAM", ""));
            Glide.with(EditBuild.this)
                    .load(jram.getString("img"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(editBuildBinding.ivMemoryImg);
            editBuildBinding.tvMemoryName.setText(jram.getString("title"));
            editBuildBinding.tvMemoryModel.setText("Model: " + jram.getString("model"));
            editBuildBinding.tvMemorySize.setText("Size: " + jram.getString("size"));
            editBuildBinding.tvMemoryQty.setText("Quantity: " + jram.getString("quantity"));
            editBuildBinding.tvMemoryType.setText("Type: " + jram.getString("type"));
            editBuildBinding.tvMemoryPrice.setText("$" + jram.getDouble("price"));
            hasRAM = true;
        } catch (JSONException e) {
            if (ram.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(ram.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivMemoryImg);
                editBuildBinding.tvMemoryName.setText(ram.get(0).getTitle());
                editBuildBinding.tvMemoryModel.setText("Model: " + ram.get(0).getModel());
                editBuildBinding.tvMemorySize.setText("Size: " + ram.get(0).getSize());
                editBuildBinding.tvMemoryQty.setText("Quantity: " + ram.get(0).getQuantity());
                editBuildBinding.tvMemoryType.setText("Type: " + ram.get(0).getType());
                editBuildBinding.tvMemoryPrice.setText("$" + ram.get(0).getPrice());

                hasRAM = true;
            } else {
                editBuildBinding.tvMemoryName.setText("RAM / Memory");
                editBuildBinding.tvMemoryModel.setText("Model: ");
                editBuildBinding.tvMemorySize.setText("Size: ");
                editBuildBinding.tvMemoryQty.setText("Quantity: ");
                editBuildBinding.tvMemoryType.setText("Type: ");
                editBuildBinding.tvMemoryPrice.setText("$");
                hasRAM = false;
            }
        }
        try {
            //Storage
            jstorage = new JSONObject(sharedPreferences.getString("temp_Storage", ""));
            Glide.with(EditBuild.this)
                    .load(jstorage.getString("img"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(editBuildBinding.ivStorageImg);
            editBuildBinding.tvStorageName.setText(jstorage.getString("title"));
            editBuildBinding.tvStorageModel.setText("Model: " + jstorage.getString("brand"));
            editBuildBinding.tvStorageCachesize.setText("Cache Memory Size: " + jstorage.getString("cacheMemory"));
            editBuildBinding.tvStorageInterface.setText("Interface: " + jstorage.getString("storageInterface"));
            editBuildBinding.tvStorageType.setText("Type: " + jstorage.getString("type"));
            editBuildBinding.tvStoragePrice.setText("$" + jstorage.getDouble("price"));
            hasStorage = true;
        } catch (JSONException e) {
            if (storage.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(storage.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivStorageImg);
                editBuildBinding.tvStorageName.setText(storage.get(0).getTitle());
                editBuildBinding.tvStorageModel.setText("Model: " + storage.get(0).getModel());
                editBuildBinding.tvStorageCachesize.setText("Cache Memory Size: " + storage.get(0).getCacheSize());
                editBuildBinding.tvStorageInterface.setText("Interface: " + storage.get(0).getInterface());
                editBuildBinding.tvStorageType.setText("Type: " + storage.get(0).getType());
                editBuildBinding.tvStoragePrice.setText("$" + storage.get(0).getPrice());

                hasStorage = true;
            } else {
                editBuildBinding.tvStorageName.setText("Storage");
                editBuildBinding.tvStorageModel.setText("Model: ");
                editBuildBinding.tvStorageCachesize.setText("Cache Memory Size: ");
                editBuildBinding.tvStorageInterface.setText("Interface: ");
                editBuildBinding.tvStorageType.setText("Type: ");
                editBuildBinding.tvStoragePrice.setText("$");
                hasStorage = false;
            }
        }
        try {
            //GPU
            jGpu = new JSONObject(sharedPreferences.getString("temp_GPU", ""));
            Glide.with(EditBuild.this).load(jGpu.getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivGpuImg);
            editBuildBinding.tvGpuName.setText(jGpu.getString("title"));
            editBuildBinding.tvGpuModel.setText("Model: " + jGpu.getString("model"));
            editBuildBinding.tvGpuSpeed.setText("Speed: " + jGpu.getString("clockSpeed"));
            editBuildBinding.tvGpuInterface.setText("Interface: " + jGpu.getString("storageInterface"));
            editBuildBinding.tvGpuVram.setText("VRAM: " + jGpu.getString("memory"));
            editBuildBinding.tvGpuPrice.setText("$" + jGpu.getDouble("price"));
            hasCPU = true;
        } catch (JSONException e) {
            //return;
            if (gpu.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(gpu.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivGpuImg);
                editBuildBinding.tvGpuName.setText(gpu.get(0).getTitle());
                editBuildBinding.tvGpuModel.setText("Model: " + gpu.get(0).getModel());
                editBuildBinding.tvGpuSpeed.setText("Speed: " + gpu.get(0).getSpeed());
                editBuildBinding.tvGpuInterface.setText("Interface: " + gpu.get(0).getInterface());
                editBuildBinding.tvGpuVram.setText("VRAM: " + gpu.get(0).getVram());
                editBuildBinding.tvGpuPrice.setText("$" + gpu.get(0).getPrice());

                hasGPU = true;
            } else {
                editBuildBinding.tvGpuName.setText("Graphics Card: ");
                editBuildBinding.tvGpuModel.setText("Model: ");
                editBuildBinding.tvGpuSpeed.setText("clockSpeed: ");
                editBuildBinding.tvGpuInterface.setText("Interface: ");
                editBuildBinding.tvGpuVram.setText("VRAM: ");
                editBuildBinding.tvGpuPrice.setText("$");
                hasGPU = false;
            }
        }
        try {
            //PSU
            jPsu = new JSONObject(sharedPreferences.getString("temp_PSU", ""));
            Glide.with(EditBuild.this).load(jPsu.getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivPsuImg);
            editBuildBinding.tvPsuName.setText(jPsu.getString("title"));
            editBuildBinding.tvPsuBrand.setText("Brand: " + jPsu.getString("brand"));
            editBuildBinding.tvPsuModel.setText("Model: " + jPsu.getString("model"));
            editBuildBinding.tvPsuPower.setText("Power: " + jPsu.getString("power"));
            editBuildBinding.tvPsuEfficiency.setText("Efficiency: " + jPsu.getString("efficiency"));
            editBuildBinding.tvPsuPrice.setText("$" + jPsu.getDouble("price"));
            hasPSU = true;
        } catch (JSONException e) {
            //return;
            if (psu.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(psu.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivPsuImg);
                editBuildBinding.tvPsuName.setText(psu.get(0).getTitle());
                editBuildBinding.tvPsuBrand.setText("Brand: " + psu.get(0).getBrand());
                editBuildBinding.tvPsuModel.setText("Model: " + psu.get(0).getModel());
                editBuildBinding.tvPsuPower.setText("Power: " + psu.get(0).getPower());
                editBuildBinding.tvPsuEfficiency.setText("Efficiency: " + psu.get(0).getEfficiency());
                editBuildBinding.tvPsuPrice.setText("$" + psu.get(0).getPrice());

                hasPSU = true;
            } else {
                editBuildBinding.tvPsuName.setText("Power Supply");
                editBuildBinding.tvPsuBrand.setText("Brand: ");
                editBuildBinding.tvPsuModel.setText("Model: ");
                editBuildBinding.tvPsuPower.setText("Power: ");
                editBuildBinding.tvPsuEfficiency.setText("Efficiency: ");
                editBuildBinding.tvPsuPrice.setText("$");
                hasPSU = false;
            }
        }
        try {
            //Case
            jCase = new JSONObject(sharedPreferences.getString("temp_Case", ""));
            Glide.with(EditBuild.this).load(jCase.getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCaseImg);
            editBuildBinding.tvCaseName.setText(jCase.getString("title"));
            editBuildBinding.tvCaseBrand.setText("Brand: " + jCase.getString("brand"));
            editBuildBinding.tvCaseModel.setText("Model: " + jCase.getString("model"));
            editBuildBinding.tvCaseSidePanel.setText("Side Panel: " + jCase.getString("sidePanel"));
            editBuildBinding.tvCaseForm.setText("Cabinet Type: " + jCase.getString("cabinetType"));
            editBuildBinding.tvCasePrice.setText("$" + jCase.getDouble("price"));
            hasCPU = true;
        } catch (JSONException e) {
            //return;
            if (ccase.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(ccase.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCaseImg);
                    editBuildBinding.tvCaseName.setText(ccase.get(0).getTitle());
                    editBuildBinding.tvCaseBrand.setText("Brand: " + ccase.get(0).getBrand());
                    editBuildBinding.tvCaseModel.setText("Model: " + ccase.get(0).getModel());
                    editBuildBinding.tvCaseSidePanel.setText("Side Panel: " + ccase.get(0).getSidePanel());
                    editBuildBinding.tvCaseForm.setText("Cabinet Type: " + ccase.get(0).getCabinetType());
                    editBuildBinding.tvCasePrice.setText("$" + ccase.get(0).getPrice());

                    hasCase = true;
            } else {
                editBuildBinding.tvCaseName.setText("Case");
                editBuildBinding.tvCaseBrand.setText("Brand: ");
                editBuildBinding.tvCaseModel.setText("Model: ");
                editBuildBinding.tvCaseSidePanel.setText("Side Panel: ");
                editBuildBinding.tvCaseForm.setText("Cabinet Type: ");
                editBuildBinding.tvCasePrice.setText("$");
                hasCase = false;
            }
        }
        try {
            //CaseFan
            jCaseFan = new JSONObject(sharedPreferences.getString("temp_CaseFan", ""));
            Glide.with(EditBuild.this).load(jCaseFan.getString("img")).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCasefanImg);
            editBuildBinding.tvCasefanName.setText(jCaseFan.getString("title"));
            editBuildBinding.tvCasefanModel.setText("Model: " + jCaseFan.getString("brand") +" "+ jCaseFan.getString("model"));
            editBuildBinding.tvCasefanRpm.setText("RPM: " + jCaseFan.getString("rpm"));
            editBuildBinding.tvCasefanNoiseLvl.setText("Noise Level: " + jCaseFan.getString("noiseLevel"));
            editBuildBinding.tvCasefanAirflow.setText("Air Flow: " + jCaseFan.getString("airFlow"));
            editBuildBinding.tvCasefanPrice.setText("$" + jCaseFan.getDouble("price"));
            hasCaseFan = true;
        } catch (JSONException e) {
            //return;
            if (casefan.get(0).getTitle() != null) {
                Glide.with(EditBuild.this).load(casefan.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCasefanImg);
                editBuildBinding.tvCasefanName.setText(casefan.get(0).getTitle());
                editBuildBinding.tvCasefanModel.setText("Model: " + casefan.get(0).getModel());
                editBuildBinding.tvCasefanRpm.setText("RPM: " + casefan.get(0).getRpm());
                editBuildBinding.tvCasefanNoiseLvl.setText("Noise Level: " + casefan.get(0).getNoiseLvl());
                editBuildBinding.tvCasefanAirflow.setText("Air Flow: " + casefan.get(0).getAirFlow());
                editBuildBinding.tvCasefanPrice.setText("$" + casefan.get(0).getPrice());

                hasCaseFan = true;
            } else {
                editBuildBinding.tvCasefanName.setText("Case Fan");
                editBuildBinding.tvCasefanModel.setText("Model: ");
                editBuildBinding.tvCasefanRpm.setText("RPM: ");
                editBuildBinding.tvCasefanNoiseLvl.setText("Noise Level: ");
                editBuildBinding.tvCasefanAirflow.setText("Air Flow: ");
                editBuildBinding.tvCasefanPrice.setText("$");
                hasCaseFan = false;
            }
        }
    }

    private boolean isRAMCompatible(){
        if(editBuildBinding.tvMoboName.getText().toString().contains("DDR3")) ram_socket = "DDR3";
        else if(editBuildBinding.tvMoboName.getText().toString().contains("DDR4")) ram_socket = "DDR4";

        if(ram_socket.contains(editBuildBinding.tvMemoryType.getText().toString())) return true;

        return  false;
    }
    private boolean isSocketCompatible(){
        if(editBuildBinding.tvCpuSocket.getText().toString().equalsIgnoreCase(editBuildBinding.tvMoboSocket.getText().toString())){
            return true;
        }
        return false;
    }

    private double getEstimated_Price(TextView textView){
        double price = 0.00;
        try{
            price = Double.parseDouble(textView.getText().toString().replace("$", ""));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return price;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.del_build_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String bName = sharedPreferences.getString("origBuild", null);
        switch (id){
            case R.id.delete_build:
                dbHandler.deleteBuild(bName);
                Intent builds = new Intent(EditBuild.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(builds);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        editor.remove("temp_CPU");
//        editor.apply();
        dbHandler.close();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Quit editing?");
        builder.setMessage("You will lose your unsaved modification on this build");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(EditBuild.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
