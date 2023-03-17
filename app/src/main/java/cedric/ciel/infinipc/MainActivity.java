package cedric.ciel.infinipc;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import cedric.ciel.infinipc.Lists.BuildListAdapter;
import cedric.ciel.infinipc.Utils.DBHelper;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> bname,processor,cpu_cooler,motherboard;
    DBHelper db;
    BuildListAdapter adapter;
    private FloatingActionButton btn_addBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_addBuild = findViewById(R.id.btn_addBuild);
        db = new DBHelper( this);
        bname=new ArrayList<>();
        processor=new ArrayList<>();
        cpu_cooler=new ArrayList<>();
        motherboard=new ArrayList<>();
        recyclerView=findViewById(R.id.recommendedBuilds);
        adapter = new BuildListAdapter(this,bname,processor,cpu_cooler,motherboard);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displayData();

        btn_addBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NewBuild.class));
            }
        });
    }

    private void displayData() {
        Cursor cursor=db.getData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No Data Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                bname.add(cursor.getString(0));
                processor.add(cursor.getString(1));
                cpu_cooler.add(cursor.getString(2));
                motherboard.add(cursor.getString(3));

            }
        }
    }

}