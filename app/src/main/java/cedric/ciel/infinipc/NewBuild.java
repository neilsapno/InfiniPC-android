package cedric.ciel.infinipc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cedric.ciel.infinipc.Utils.DBHelper;

public class NewBuild extends AppCompatActivity {

    DBHelper db;
    private FloatingActionButton btn_saveBuild;
    private TextView tv_cpu_name, tv_cpu_cooler, tv_mobo_name;
    private EditText et_buildName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        tv_cpu_name = findViewById(R.id.tv_cpuName);
        tv_cpu_cooler = findViewById(R.id.tv_cooler_name);
        tv_mobo_name = findViewById(R.id.tv_mobo_name);
        et_buildName = findViewById(R.id.et_buildName);
        db=new DBHelper(this);

        btn_saveBuild = findViewById(R.id.btn_saveBuild);
        btn_saveBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bname_text=  et_buildName.getText().toString();
                String processor_text= tv_cpu_name.getText().toString();
                String cpu_cooler_text=tv_cpu_cooler.getText().toString();
                String mobo_text=tv_mobo_name.getText().toString();


                Boolean checkinsertData = db.insertBuildData(bname_text,processor_text,cpu_cooler_text,mobo_text);
                if (checkinsertData==true){
                    Toast.makeText(NewBuild.this, "New Data Inserted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NewBuild.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(NewBuild.this, "new Data Not Inserted!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}