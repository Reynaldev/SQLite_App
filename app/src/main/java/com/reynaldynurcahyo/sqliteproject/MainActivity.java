package com.reynaldynurcahyo.sqliteproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseManager dbManager;
    EditText edtName, edtPrice;
    Button btnAdd;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this);
        tableLayout = (TableLayout) findViewById(R.id.table_data);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtPrice = (EditText) findViewById(R.id.edt_price);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        updateTable();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            setRowData();
        }
    }

    protected void setRowData() {
        try {
            dbManager.addRow(
                    edtName.getText().toString(),
                    edtPrice.getText().toString()
            );

            Toast.makeText(getBaseContext(),
                    edtName.getText().toString() + "Berhasil Disimpan",
                    Toast.LENGTH_SHORT).show();
            updateTable();
            setEmptyField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(),
                    "Gagal menyimpan data : " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void setEmptyField() {
        edtName.setText(null);
        edtPrice.setText(null);
    }

    protected void updateTable() {
        while (tableLayout.getChildCount() > 1) {
            tableLayout.removeViewAt(1);
        }

        ArrayList<ArrayList<Object>> data = dbManager.getAllRow();

        for (int position = 0; position < data.size(); position++) {
            TableRow tableRow = new TableRow(this);
            ArrayList<Object> row = data.get(position);

            TextView txtId = new TextView(this);
            txtId.setText(row.get(0).toString());
            tableRow.addView(txtId);

            TextView txtName = new TextView(this);
            txtName.setText(row.get(1).toString());
            tableRow.addView(txtName);

            TextView txtPrice = new TextView(this);
            txtPrice.setText(row.get(2).toString());
            tableRow.addView(txtPrice);

            tableLayout.addView(tableRow);
        }
    }
}