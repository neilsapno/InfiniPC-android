package cedric.ciel.infinipc;

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

public class BrowseParts extends AppCompatActivity {

    CoordinatorLayout store_coordinator;
    SharedPreferences sharedPreferences;
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
        sharedPreferences = getSharedPreferences("Parts", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        storeParts = new ArrayList<>();

        //StoreParts(String tv_partsTitle, String tv_partsInfo1, String tv_partsInfo2, String tv_partsInfo3, String tv_partsInfo4, String partsID, double tv_partsPrice)
        try {
            JSONArray jsonArray = new JSONArray(sharedPreferences.getString("processors", ""));
            for(int i = 0; i<jsonArray.length();i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                storeParts.add(new StoreParts(json.getString("img"), json.getString("title"), json.getString("brand"), json.getString("model"), json.getString("speed"), json.getString("socketType"), json.getString("id"), json.getString("link"), json.getDouble("price")));
               // Log.d("JSON get", "json: " + json);
                Snackbar.make(store_coordinator, "Processors Loaded", Snackbar.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Snackbar.make(store_coordinator, "Something went wrong while getting parts list", Snackbar.LENGTH_LONG).show();
        }
        mAdapter = new StoreAdapter(this, storeParts);
        recyclerView.setAdapter(mAdapter);
    }
}