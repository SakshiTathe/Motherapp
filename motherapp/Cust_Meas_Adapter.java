package com.example.motherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Cust_Meas_Adapter extends ArrayAdapter<Cust_Measure_model> {
    Context context;
    List<Cust_Measure_model> measure;

    public Cust_Meas_Adapter(Context context, List<Cust_Measure_model> measures) {
        super(context,0,measures);
        this.context=context;
        this.measure=measures;
    }
    static class ViewHolder {
        TextView tmesID, tmeasname;
        EditText tinch;
        Button edits, savest;
    }
    @Override
    public int getCount() {
        return measure.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
        convertView= LayoutInflater.from(context).inflate(R.layout.listmeasure,parent,false);
        holder=new ViewHolder();
        holder.tmesID=convertView.findViewById(R.id.mesID);
        holder.tmeasname=convertView.findViewById(R.id.measname);
        holder.tinch=convertView.findViewById(R.id.inch);

        holder.edits=convertView.findViewById(R.id.edit);
        //holder.savest=convertView.findViewById(R.id.saves);
        convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cust_Measure_model current=measure.get(position);

        holder.tmesID.setText(String.valueOf(current.id));
        holder.tmeasname.setText(current.parts);
        holder.tinch.setText(String.valueOf(current.meaures));
        holder.tinch.setEnabled(false);

        holder.edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentLabel = holder.edits.getText().toString();

                if (currentLabel.equalsIgnoreCase("Edit")) {
                    // Enable EditText for editing
                    holder.tinch.setEnabled(true);
                    holder.tinch.requestFocus();
                    holder.edits.setText("Save");
                } else {
                    try {
                        float newValue = Float.parseFloat(holder.tinch.getText().toString());
                        current.meaures=newValue;
                        String partName = current.parts.toLowerCase();
                        if (context instanceof Cust_measur) {
                            ((Cust_measur) context).onMeasuresave(((Cust_measur) context).custid, current.id, newValue);
                            Toast.makeText(context, "Saved: " + newValue, Toast.LENGTH_SHORT).show();
                            holder.tinch.setEnabled(false);
                            holder.edits.setText("Edit");
                        }
                    }catch (NumberFormatException e){
                        Toast.makeText(context, "Invalid number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /*holder.savest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float newValue = Float.parseFloat(holder.tinch.getText().toString());
                    current.meaures=newValue;
                    String partName = current.parts.toLowerCase();
                    if (context instanceof Cust_measur) {
                        ((Cust_measur) context).onMeasuresave(((Cust_measur) context).custid, current.id, newValue);
                        Toast.makeText(context, "Saved: " + newValue, Toast.LENGTH_SHORT).show();
                        holder.tinch.setEnabled(false);
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(context, "Invalid number", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        return convertView;
    }
}
