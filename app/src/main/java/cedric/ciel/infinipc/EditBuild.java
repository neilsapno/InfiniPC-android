package cedric.ciel.infinipc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cedric.ciel.infinipc.Parts.CPU;
import cedric.ciel.infinipc.Parts.Cooler;
import cedric.ciel.infinipc.Parts.Motherboard;
import cedric.ciel.infinipc.Parts.RAM;
import cedric.ciel.infinipc.Parts.Storage;
import cedric.ciel.infinipc.Utils.DBHandler;
import cedric.ciel.infinipc.databinding.ActivityEditBuildBinding;

public class EditBuild extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DBHandler dbHandler;
    ActivityEditBuildBinding editBuildBinding;
    private ImageView iv_cpuImg;
    private EditText et_buildName;
    private String BuildName, sCPU, sCooler, sMobo, Memory, Storage, GPU, Case, PSU, CaseFan, BuildImgUrl;
    private int RAMCount, Watts;
    private double Price;
    Boolean hasCPU, hasCooler, hasMobo, hasRAM, hasStorage = false;
    ArrayList<CPU> cpu = new ArrayList<>();
    ArrayList<Cooler> cooler = new ArrayList<>();
    ArrayList<Motherboard> mobo = new ArrayList<>();
    ArrayList<RAM> ram = new ArrayList<>();
    ArrayList<Storage> storage = new ArrayList<>();

    JSONObject jCpu, jcooler, jmobo, jram, jstorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editBuildBinding = ActivityEditBuildBinding.inflate(getLayoutInflater());
        setContentView(editBuildBinding.getRoot());

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
                GPU = "VCard";
                Case = "PC Case";
                PSU = "PSupply";
                CaseFan = "CaseFan";
                Price = getEstimated_Price(editBuildBinding.tvCpuPrice);
                Price += getEstimated_Price(editBuildBinding.tvCoolerPrice);
                Price += getEstimated_Price(editBuildBinding.tvMoboPrice);
                Price += getEstimated_Price(editBuildBinding.tvMemoryPrice);
                Price += getEstimated_Price(editBuildBinding.tvStoragePrice);
                if(Price < 500) Watts = 300;
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
    }

    private void getParts() {
        cpu = dbHandler.getCPU(cpu, editBuildBinding.etBuildName.getText().toString());
        cooler = dbHandler.getCooler(cooler, editBuildBinding.etBuildName.getText().toString());
        mobo = dbHandler.getMobo(mobo, editBuildBinding.etBuildName.getText().toString());
        ram = dbHandler.getRAM(ram, editBuildBinding.etBuildName.getText().toString());
        storage = dbHandler.getStorage(storage, editBuildBinding.etBuildName.getText().toString());

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
            if (!cpu.isEmpty()) {
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
            if (!cooler.isEmpty()) {
                Glide.with(EditBuild.this).load(cooler.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivCoolerImg);
                editBuildBinding.tvCoolerName.setText(cooler.get(0).getTitle());
                editBuildBinding.tvCoolerBrand.setText("Brand: " + cooler.get(0).getBrand());
                editBuildBinding.tvCoolerModel.setText("Model: " + cooler.get(0).getModel());
                editBuildBinding.tvCoolerRpm.setText("RPM: " + cooler.get(0).getrpm());
                editBuildBinding.tvCoolerNoiseLvl.setText("Noise Level: " + cooler.get(0).getnoiseLvl());
                editBuildBinding.tvCoolerPrice.setText("$" + cooler.get(0).getPrice());
                hasCooler = true;
            } else {
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
            if (!mobo.isEmpty()) {
                Glide.with(EditBuild.this).load(mobo.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivMoboImg);
                editBuildBinding.tvMoboName.setText(mobo.get(0).getTitle());
                editBuildBinding.tvMoboModel.setText("Model: " + mobo.get(0).getModel());
                editBuildBinding.tvMoboForm.setText("Form: " + mobo.get(0).getForm());
                editBuildBinding.tvMoboRamslot.setText("Memory Slots: " + mobo.get(0).getRamslot());
                editBuildBinding.tvMoboSocket.setText("Socket: " + mobo.get(0).getSocket());
                editBuildBinding.tvMoboPrice.setText("$" + mobo.get(0).getPrice());
                
                hasMobo = true;
            } else {
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
            if (!ram.isEmpty()) {
                Glide.with(EditBuild.this).load(ram.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivMemoryImg);
                editBuildBinding.tvMemoryName.setText(ram.get(0).getTitle());
                editBuildBinding.tvMemoryModel.setText("Model: " + ram.get(0).getModel());
                editBuildBinding.tvMemorySize.setText("Size: " + ram.get(0).getSize());
                editBuildBinding.tvMemoryQty.setText("Quantity: " + ram.get(0).getQuantity());
                editBuildBinding.tvMemoryType.setText("Type: " + ram.get(0).getType());
                editBuildBinding.tvMemoryPrice.setText("$" + ram.get(0).getPrice());
                
                hasRAM = true;
            } else {
                hasRAM = false;
            }
        }
        try {
            //Mobo
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
            if (!storage.isEmpty()) {
                Glide.with(EditBuild.this).load(storage.get(0).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(editBuildBinding.ivStorageImg);
                editBuildBinding.tvStorageName.setText(storage.get(0).getTitle());
                editBuildBinding.tvStorageModel.setText("Model: " + storage.get(0).getModel());
                editBuildBinding.tvStorageCachesize.setText("Cache Memory Size: " + storage.get(0).getCacheSize());
                editBuildBinding.tvStorageInterface.setText("Interface: " + storage.get(0).getInterface());
                editBuildBinding.tvStorageType.setText("Type: " + storage.get(0).getType());
                editBuildBinding.tvStoragePrice.setText("$" + storage.get(0).getPrice());
                
                hasStorage = true;
            } else {
                hasStorage = false;
            }
        }
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