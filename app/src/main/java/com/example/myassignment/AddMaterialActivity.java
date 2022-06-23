package com.example.myassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myassignment.model.Material;
import com.example.myassignment.model.Owner;

public class AddMaterialActivity extends AppCompatActivity {
    private Database db = null;

    Intent intent = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        EditText etName = findViewById(R.id.name);
        EditText etDescription = findViewById(R.id.etDescription);
        Checkable isService = findViewById(R.id.isService);


        Button btnInsert = findViewById(R.id.insertBtn);


        db = Database.getInstace(getApplicationContext());

        intent = getIntent();
        if (intent.getStringExtra("name") != null) {
            etName.setText(intent.getStringExtra("name"));
            etDescription.setText(intent.getStringExtra("desc"));
            isService.setChecked(Integer.parseInt(intent.getStringExtra("service")) == 1);
            btnInsert.setText("Update");
        }


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(AddMaterialActivity.this, "All fields is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int service = 0;
                if(isService.isChecked()) service = 1;
                Material material = new Material(etName.getText().toString(), etDescription.getText().toString(), service);
                if (btnInsert.getText() == "Update") {
                    material.setId(Integer.parseInt(intent.getStringExtra("id")));
                    db.updateMaterial(material);
                    AddMaterialActivity.super.onBackPressed();
                    return;
                }
                db.insertMaterial(material);
                AddMaterialActivity.super.onBackPressed();
            }
        });
    }
}