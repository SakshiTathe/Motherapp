package com.example.motherapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class custAdapter extends RecyclerView.Adapter<custAdapter.custviewhold>{
    private List<CustomModel> custlist;
    private Recyclecliklisten listen;
    private Context context;

    public custAdapter(Context context, List<CustomModel> custlist) {
        this.custlist = custlist;
        this.context = context;
    }

    @NonNull
    @Override
    public custviewhold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cust_first,parent,false);
        return new custviewhold(v);
    }

    @Override
    public void onBindViewHolder(@NonNull custviewhold holder, int position) {
        CustomModel currentuse=custlist.get(position);
        custviewhold.nameid.setText(String.valueOf(currentuse.getId()));
        custviewhold.names.setText(currentuse.getName());
        custviewhold.phoneview.setText(String.valueOf(currentuse.getPhone()));

        custviewhold.bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,bills.class);
                i.putExtra("custID", currentuse.getId());
                context.startActivity(i);
            }
        });
        custviewhold.measur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,Cust_measur.class);
                i.putExtra("custID", currentuse.getId());
                context.startActivity(i);
            }
        });
        //custviewhold.nametxt.setText(currentuse.getName());

    }

    @Override
    public int getItemCount() {
        return custlist.size();
    }
    public void updateList(List<CustomModel> newList) {
        this.custlist = newList;
        notifyDataSetChanged();
    }

    public interface Recyclecliklisten{
        void onclick(View v,int ID);
    }

    public static class custviewhold extends RecyclerView.ViewHolder {
         static TextView nameid;
         static TextView names;
         static TextView phoneview;
         static Button bill,measur;
        //TextView phonetxt;
        public custviewhold(@NonNull View itemView) {
            super(itemView);
            nameid=itemView.findViewById(R.id.nameid);
            names=itemView.findViewById(R.id.nameee);
            bill=itemView.findViewById(R.id.bill);
            measur=itemView.findViewById(R.id.measur);
            phoneview=itemView.findViewById(R.id.phonee);
        }
    }
}
