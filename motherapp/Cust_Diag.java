package com.example.motherapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Cust_Diag extends AppCompatDialogFragment {
    private EditText edNAMe,edPHONE;
    DialogueListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.addcust_diag,null);

        edNAMe=view.findViewById(R.id.edname);
        edPHONE=view.findViewById(R.id.edphone);
        builder.setView(view)
                .setTitle("Add New Customer")
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
        if (dialog != null) {
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                String name = edNAMe.getText().toString().trim();
                String phoneStr = edPHONE.getText().toString().trim();

                if (name.isEmpty() || phoneStr.isEmpty() || !phoneStr.matches("\\d+")) {
                    Toast.makeText(getContext(), "Please enter valid name and phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int phone = Integer.parseInt(phoneStr);
                    listener.onSaveClicked(name, phone);
                    dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Phone must be numeric", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface DialogueListener{
        void  onSaveClicked(String name, int phone);
    }

    @Override
    public  void  onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  DialogueListener){
            listener = (DialogueListener)context;
        }else{
            throw new RuntimeException(context + "must implement DialogueListener");
        }
    }
}
