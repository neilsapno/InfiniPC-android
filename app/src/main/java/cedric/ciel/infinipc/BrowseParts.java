package cedric.ciel.infinipc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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

        //StoreParts(String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, double tv_partsPrice)
        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() +"processors", ""));
            Toast.makeText(this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
            for(int i = 0; i<jsonArray.length();i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                storeParts.add(new StoreParts(json.getString("img"), json.getString("title"), json.getString("brand"), json.getString("model"), json.getString("speed"), json.getString("socketType"), json.getString("id"), json.getString("link"), json.getDouble("price")));
                //Log.d("JSON get", "json: " + json);
                Snackbar.make(store_coordinator, "Processors Loaded", Snackbar.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
        }
        mAdapter = new StoreAdapter(this, storeParts, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPartClick(int position, String partID) {
        Toast.makeText(BrowseParts.this,partID,Toast.LENGTH_SHORT).show();
        editor.putString("temp_CPU", getPartsInfo(partID));
        editor.apply();
        Intent newbuild = new Intent(BrowseParts.this, NewBuild.class);
        startActivity(newbuild);
        finish();
    }

    private String getPartsInfo(String partID){
        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString(this.getPackageName() +"processors", ""));
            Toast.makeText(this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
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