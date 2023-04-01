package cedric.ciel.infinipc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cedric.ciel.infinipc.Lists.StoreAdapter;
import cedric.ciel.infinipc.Lists.StoreParts;

public class BrowseParts extends AppCompatActivity implements StoreAdapter.OnPartClickListener {

    CoordinatorLayout store_coordinator;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor;
    String typePart, fromActivity;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    //ArrayList<String> bname, memory, watts, price;
    ArrayList<StoreParts> storeParts;
    StoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_parts);

        store_coordinator = findViewById(R.id.store_coordinator);
        recyclerView=findViewById(R.id.availableParts);
        sharedPreferences = getSharedPreferences(this.getPackageName() +"Parts", MODE_PRIVATE);
        sharedPreferences1 = getSharedPreferences(this.getPackageName() +"Picked_Parts", MODE_PRIVATE);

        editor = sharedPreferences1.edit();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storeParts = new ArrayList<>();

        Intent type = getIntent();
        typePart = type.getStringExtra("type");
        fromActivity = type.getStringExtra("from");
        if(typePart.equals("CPU")) {
            //StoreParts(String img_url, String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, String link, double tv_partsPrice)
            try {
                JSONArray jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() + "processors", ""));
//                Toast.makeText(this, "" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    storeParts.add(new StoreParts(json.getString("img"), json.getString("title"), json.getString("brand"), json.getString("model"), json.getString("speed"), json.getString("socketType"), json.getString("id"), json.getString("link"), json.getDouble("price")));
                    //Log.d("JSON get", "json: " + json);
                    Snackbar.make(store_coordinator, "Processors Loaded", Snackbar.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
            }
        }
        else if(typePart.equals("Coolers")) {
            //StoreParts(String img_url, String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, String link, double tv_partsPrice)
            try {
                JSONArray jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() + "coolers", ""));
//                Toast.makeText(this, "" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    storeParts.add(new StoreParts(json.getString("img"), json.getString("title"), json.getString("brand"), json.getString("model"), json.getString("rpm"), json.getString("noiseLevel"), json.getString("id"), json.getString("link"), json.getDouble("price")));
                    //Log.d("JSON get", "json: " + json);
                    Snackbar.make(store_coordinator, "Coolers Loaded", Snackbar.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
            }
        }
        else if(typePart.equals("Mobos")) {
            //StoreParts(String img_url, String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, String link, double tv_partsPrice)
            try {
                JSONArray jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() + "mobos", ""));
//                Toast.makeText(this, "" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    storeParts.add(new StoreParts(json.getString("img"), json.getString("title"), json.getString("brand")+" "+json.getString("model"), json.getString("formFactor"), json.getString("memorySlots"), json.getString("socketType"), json.getString("id"), json.getString("link"), json.getDouble("price")));
                    //Log.d("JSON get", "json: " + json);
                    Snackbar.make(store_coordinator, "Motherboards Loaded", Snackbar.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
            }
        }
        else if(typePart.equals("RAMs")) {
            //StoreParts(String img_url, String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, String link, double tv_partsPrice)
            try {
                JSONArray jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() + "rams", ""));
//                Toast.makeText(this, "" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    storeParts.add(new StoreParts(json.getString("img"), json.getString("title"), json.getString("brand")+" "+json.getString("model"), json.getString("size"), json.getString("quantity"), json.getString("type"), json.getString("id"), json.getString("link"), json.getDouble("price")));
                    //Log.d("JSON get", "json: " + json);
                    Snackbar.make(store_coordinator, "RAMs Loaded", Snackbar.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
            }
        }
        else if(typePart.equals("Storages")) {
            //StoreParts(String img_url, String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, String link, double tv_partsPrice)
            try {
                JSONArray jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() + "storages", ""));
//                Toast.makeText(this, "" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    storeParts.add(new StoreParts(json.getString("img"), json.getString("title"), json.getString("brand")+" "+json.getString("model"), json.getString("cacheMemory"), json.getString("storageInterface"), json.getString("type"), json.getString("id"), json.getString("link"), json.getDouble("price")));
                    //Log.d("JSON get", "json: " + json);
                    Snackbar.make(store_coordinator, "RAMs Loaded", Snackbar.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
            }
        }


        mAdapter = new StoreAdapter(this, storeParts, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPartClick(int position, String partID) {
        if(typePart.equalsIgnoreCase("CPU")) {
//            Toast.makeText(BrowseParts.this, partID, Toast.LENGTH_SHORT).show();
            editor.putString("temp_CPU", getPartsInfo(partID));
        }
        else if(typePart.equalsIgnoreCase("Coolers")){
//            Toast.makeText(BrowseParts.this, partID, Toast.LENGTH_SHORT).show();
            editor.putString("temp_Cooler", getPartsInfo(partID));
        }
        else if(typePart.equalsIgnoreCase("Mobos")){
//            Toast.makeText(BrowseParts.this, partID, Toast.LENGTH_SHORT).show();
            editor.putString("temp_Mobo", getPartsInfo(partID));
        }
        else if(typePart.equalsIgnoreCase("RAMs")){
//            Toast.makeText(BrowseParts.this, partID, Toast.LENGTH_SHORT).show();
            editor.putString("temp_RAM", getPartsInfo(partID));
        }
        else if(typePart.equalsIgnoreCase("Storages")){
//            Toast.makeText(BrowseParts.this, partID, Toast.LENGTH_SHORT).show();
            editor.putString("temp_Storage", getPartsInfo(partID));
        }
        editor.apply();

        if(fromActivity.equals("new")) {
            Intent newbuild = new Intent(BrowseParts.this, NewBuild.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(newbuild);
        }
        else{
            Intent editbuild = new Intent(BrowseParts.this, EditBuild.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(editbuild);
        }
    }

    private String getPartsInfo(String partID){
        try {
            JSONArray jsonArray = new JSONArray();
            if(typePart.equalsIgnoreCase("cpu")) jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() +"processors", ""));
            else if(typePart.equalsIgnoreCase("coolers")) jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() +"coolers", ""));
            else if(typePart.equalsIgnoreCase("mobos")) jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() +"mobos", ""));
            else if(typePart.equalsIgnoreCase("rams")) jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() +"rams", ""));
            else if(typePart.equalsIgnoreCase("storages")) jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() +"storages", ""));


            for(int i = 0; i<jsonArray.length();i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if(json.getString("id").equals(partID)){

                    return json.toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
        }
        return "";
    }
}