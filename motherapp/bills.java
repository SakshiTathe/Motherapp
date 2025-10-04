package com.example.motherapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class bills extends AppCompatActivity implements Bill_Diag.DialogueListener {
    private FloatingActionButton floatbtnn;
    DBHelper helper;
    Bill_Diag bidiag;
    Integer custidd;
    List<BillModel> billlist;
    TextView fixedd;
    private int mbYear, mbMonth, mbDay;
    HorizontalScrollView horizontalHeader,horizontalData;
    ScrollView verticalHeader,verticalData;

    LinearLayout columnHeader,rowHeader,tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bills);

        floatbtnn = findViewById(R.id.floatbtn);
        helper = new DBHelper(this);
        Intent geti = getIntent();
        Log.d("CHECK", "bills activity started");


        custidd = geti.getIntExtra("custID", 0);

        columnHeader = findViewById(R.id.columnHeader);
        rowHeader = findViewById(R.id.rowHeader);
        tableLayout = findViewById(R.id.tableLayout);
        // Synchronize scrolls
        horizontalHeader = findViewById(R.id.horizontalScrollViewHeader);
        horizontalData = findViewById(R.id.horizontalScrollViewData);
        verticalHeader = findViewById(R.id.scrollViewHeader);
        verticalData = findViewById(R.id.scrollViewData);
        fixedd=findViewById(R.id.fixed);


        floatbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bidiag = new Bill_Diag();
                bidiag.show(getSupportFragmentManager(), "fullbills");
            }
        });

        billlist = helper.getallBill(custidd);
        String[] headers = {"id", "Describe", "Photo", "Price", "Date", "paid_Price", "ReceiveDate", "Edit"};
        int rows = billlist.size(); // for example
        int cols = headers.length;
        TextView noBillsText = findViewById(R.id.noBillsTextView);
        if (billlist.isEmpty()) {
            noBillsText.setVisibility(View.VISIBLE);
            fixedd.setVisibility(View.GONE);
            fixedd.setLayoutParams(new RelativeLayout.LayoutParams(180, 120));
            horizontalHeader.setVisibility(View.GONE);
            horizontalData.setVisibility(View.GONE);
            verticalData.setVisibility(View.GONE);
            verticalHeader.setVisibility(View.GONE);

        } else {
            noBillsText.setVisibility(View.GONE);
            fixedd.setVisibility(View.VISIBLE);
            //fixedd.setLayoutParams(new RelativeLayout.LayoutParams(180, 120));
            horizontalHeader.setVisibility(View.VISIBLE);
            horizontalData.setVisibility(View.VISIBLE);
            verticalData.setVisibility(View.VISIBLE);
            verticalHeader.setVisibility(View.VISIBLE);

            // Create Column Headers (A, B, C,...)
            for (int c = 0; c < cols; c++) {
                TextView tv = new TextView(this);
                if (c == 6) { // ReceiveDate column
                    tv.setLayoutParams(new LinearLayout.LayoutParams(300, 120)); // wider width
                } else {
                    tv.setLayoutParams(new LinearLayout.LayoutParams(240, 120));
                }

                //tv.setLayoutParams(new LinearLayout.LayoutParams(240, 120));
                tv.setText(headers[c]);
                tv.setTextSize(17);
                tv.setGravity(Gravity.CENTER);
                columnHeader.addView(tv);
            }

            // Create Table Rows
            for (int r = 0; r < rows; r++) {
                BillModel bill = billlist.get(r);
                // Row Header (1,2,3..)
                TextView rowText = new TextView(this);
                rowText.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
                rowText.setText(String.valueOf(r + 1));
                rowText.setGravity(Gravity.CENTER);
                rowHeader.addView(rowText);

                // Table Row (cells)
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);

                String[] data = {
                        String.valueOf(bill.id),
                        bill.description,
                        "PHOTO_PLACEHOLDER",
                        String.valueOf(bill.price),
                        bill.date,
                        String.valueOf(bill.recprice),
                        bill.redate,
                };
                ArrayList<EditText> editableFields = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    if (i == 2) { // image column
                        ImageView imageCell = new ImageView(this);
                        imageCell.setLayoutParams(new LinearLayout.LayoutParams(240, 240));
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bill.photo, 0, bill.photo.length);
                        imageCell.setImageBitmap(bitmap);
                        imageCell.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        row.addView(imageCell);
                    }
                    else {
                    EditText cell = new EditText(this);
                    if (i == 6) { // ReceiveDate column
                        cell.setLayoutParams(new LinearLayout.LayoutParams(300, 240)); // wider width
                    } else {
                        cell.setLayoutParams(new LinearLayout.LayoutParams(240, 240));
                    }
                    cell.setText(data[i]);
                    //cell.setTextColor(R.color.black);
                        cell.setTextColor(Color.BLACK);
                    cell.setEnabled(false);
                    cell.setBackgroundColor(Color.argb(10,200,0,0));
                    cell.setGravity(Gravity.CENTER);
                    cell.setBackgroundResource(R.drawable.cell_border);
                    row.addView(cell);
                    editableFields.add(cell);
                    }

                }
                Button editBtn = new Button(this);
                editBtn.setLayoutParams(new LinearLayout.LayoutParams(240, 240));
                editBtn.setText("Edit");
                editableFields.get(4).setLayoutParams(new LinearLayout.LayoutParams(260, 240));

                editBtn.setOnClickListener(v -> {
                    // Enable price and date only (indexes 3 and 4 of data[])
                    if(editBtn.getText().equals("Edit")){
                        editableFields.get(3).setEnabled(true); // price
                        editableFields.get(3).requestFocus();
                        editableFields.get(4).setEnabled(true); // date
                        editableFields.get(4).requestFocus();
                        editBtn.setText("Save");
                        editableFields.get(4).setOnClickListener(dateView -> {
                            final Calendar c = Calendar.getInstance();
                            int year = c.get(Calendar.YEAR);
                            int month = c.get(Calendar.MONTH);
                            int day = c.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    bills.this,
                                    (view, year1, month1, dayOfMonth) ->
                                            editableFields.get(4).setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                                    year, month, day);
                            datePickerDialog.show();
                        });
                    }
                    else{
                        try {

                            int updatedPrice = Integer.parseInt(editableFields.get(3).getText().toString());
                            String updatedDate = editableFields.get(4).getText().toString();

                            helper.update_pricedate(custidd, bill.id, updatedDate, updatedPrice);
                            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

                            // Disable fields again after saving
                            editableFields.get(3).setEnabled(false);
                            editableFields.get(4).setEnabled(false);
                            editBtn.setText("Edit");
                        } catch (Exception e) {
                            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                row.addView(editBtn);
                tableLayout.addView(row);
            }

            horizontalData.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                horizontalHeader.scrollTo(scrollX, 0);
            });
            verticalData.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                verticalHeader.scrollTo(0, scrollY);
            });
        }
    }

    @Override
    public void onAddClicked(String descstr, byte[] photostr, int pricestr, String datestr) {
        Toast.makeText(this, "Data fatched!", Toast.LENGTH_SHORT).show();
        helper.insertBill(descstr, photostr,pricestr,datestr,custidd);
        recreate();
        Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show();
    }

}