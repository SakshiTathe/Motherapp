package com.example.motherapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Bill_Diag extends AppCompatDialogFragment {
    private EditText pddates,pdprices,pddescs;
    private ImageView pdphotos;
    private Button upload;
    private static final int PICKIMG=1;
    private Bill_Diag.DialogueListener listener;
    private int mYear, mMonth, mDay;
    private Bitmap selectedbitmap;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.addbill_diag,null);

        pddescs=view.findViewById(R.id.pddescribe);
        pdphotos=view.findViewById(R.id.pdphoto);
        pdprices=view.findViewById(R.id.pdprice);
        pddates=view.findViewById(R.id.pddate);
        upload=view.findViewById(R.id.pdupbtn);

        builder.setView(view)
                .setTitle("Add New Product")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Save", null);
        return builder.create();
    }
    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        pddates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog =new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        pddates.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,PICKIMG);
            }
        });
        if (dialog != null) {
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String descstr = pddescs.getText().toString().trim();
                String pricestr = pdprices.getText().toString().trim();
                String datestr = pddates.getText().toString().trim();
                int intpric = Integer.parseInt(pricestr);
                byte[] photoBytes = imageViewToByte(pdphotos);
                if (descstr.isEmpty() || pricestr.isEmpty() || datestr.isEmpty() || selectedbitmap == null) {
                    Toast.makeText(getContext(), "Please enter valid name and phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onAddClicked( descstr,photoBytes,intpric,datestr);
                dismiss();
            });
        }
    }

    public interface DialogueListener{
        void  onAddClicked(String descstr,byte[] photostr,int pricestr,String datestr);
    }

    @Override
    public  void  onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Bill_Diag.DialogueListener){
            listener = (Bill_Diag.DialogueListener)context;
        }else{
            throw new RuntimeException(context + "must implement DialogueListener");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKIMG && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedbitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
                pdphotos.setImageBitmap(selectedbitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] imageViewToByte(ImageView imageView) {
        if (imageView.getDrawable() == null) return new byte[0];
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
