package com.example.motherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Cust_Diag.DialogueListener{
    TextView name;
    Cust_Diag diag;
    FloatingActionButton floatbtn;
    DBHelper helper;
    custAdapter custadp;
    RecyclerView recview;
    List<CustomModel> custlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        helper=new DBHelper(this);
        recview=findViewById(R.id.recycleview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        custlist =helper.getallcust();

        custadp=new custAdapter(this,custlist);
        recview.setAdapter(custadp);

        floatbtn=findViewById(R.id.floatbtn);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diag=new Cust_Diag();
                diag.show(getSupportFragmentManager(),"sakshis");
            }
        });

    }

    @Override
    public void onSaveClicked(String name, int phone) {
        Toast.makeText(this, "Data fatched!", Toast.LENGTH_SHORT).show();
        helper.insertCust(name,phone);
        List<CustomModel> updatedlist=helper.getallcust();
        custadp.updateList(updatedlist);
        Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show();
    }
}