package cedric.ciel.infinipc;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PartsList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_list);

        Intent receivedIntent = getIntent();
        String type = receivedIntent.getStringExtra("type");

        if(type.equalsIgnoreCase("cpu")){
            loadCPU();
        }
    }

    private void loadCPU() {

    }
}