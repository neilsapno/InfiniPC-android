package cedric.ciel.infinipc;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import cedric.ciel.infinipc.Parts.CPU;

public class PartsList extends AppCompatActivity {

    CPU cpu = new CPU();
    String imgUrl, AmazonLink;
    ArrayList t_specs0, t_specs1, t_specs2, t_specs3, t_specs4;

    double t_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_list);

        Intent receivedIntent = getIntent();
        String type = receivedIntent.getStringExtra("type");
        t_specs0=new ArrayList<>();
        t_specs1=new ArrayList<>();
        t_specs2=new ArrayList<>();
        t_specs3=new ArrayList<>();
        t_specs4=new ArrayList<>();

        if(type.equalsIgnoreCase("cpu")){
            loadCPU();
        }
    }

    private void loadCPU() {
        imgUrl = cpu.getImgUrl();
        t_specs0.add(cpu.getTitle());
        t_specs1.add(cpu.getBrand());
        t_specs2.add(cpu.getModel());
        t_specs3.add(cpu.getSpeed());
        t_specs4.add(cpu.getSocket());
        AmazonLink = cpu.getAmazonLink();
        t_price = cpu.getPrice();
    }
}