package com.example.motherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Cust_measur extends AppCompatActivity {
    String[] parts={"Hand","Chest","neck","waist","height"};
    ListView measurelist;
    DBHelper helper;
    Integer custid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cust_measur);
        measurelist=(ListView) findViewById(R.id.list_view);

        helper = new DBHelper(this);
        Intent geti = getIntent();

        custid = geti.getIntExtra("custID", 0);

        ArrayList<Cust_Measure_model> measurelists=new ArrayList<>();
        measurelists = (ArrayList<Cust_Measure_model>) helper.getallMeasures(custid);
        /*for(int i=1;i<parts.length;i++){
            measurelists.add(new Cust_Measure_model(i,parts[i-1],0));
        }*/
        Cust_Meas_Adapter adapter=new Cust_Meas_Adapter(this,measurelists);
        measurelist.setAdapter(adapter);
    }

    public void onMeasuresave(int custID,int mid,float changevalue){
        String column = "";
        switch(mid){
            case 1: column = "hand"; break;
            case 2: column = "chest"; break;
            case 3: column = "neck"; break;
            case 4: column = "waist"; break;
            case 5: column = "height"; break;
        }
        helper.update_measure(custID, column, changevalue);
    }
}