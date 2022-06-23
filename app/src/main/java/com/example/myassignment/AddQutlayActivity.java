package com.example.myassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myassignment.model.Material;
import com.example.myassignment.model.Owner;
import com.example.myassignment.model.Qutlay;

import java.util.ArrayList;

public class AddQutlayActivity extends AppCompatActivity {
    private Database db = null;
    private ArrayList<Owner> owners = new ArrayList<>();
    private ArrayList<Material> materials = new ArrayList<>();
    private int materialId = -1;
    private int ownerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_qutlay);

        EditText etPrice = findViewById(R.id.price);
        EditText etDate = findViewById(R.id.etDate);
        EditText etDescription = findViewById(R.id.etDescription);
        Spinner materialSpinner = findViewById(R.id.materialspinner);
        Spinner ownerSpinner = findViewById(R.id.ownerspinner);

        Button btnInsert = findViewById(R.id.insertBtn);

        db = Database.getInstace(getApplicationContext());
        try {
            owners = db.getOwners();
            materials = db.getMaterials();

            ArrayList<String> ownersNames = new ArrayList<>();
            ArrayList<String> materialsNames = new ArrayList<>();
            for (int i = 0; i < owners.size(); i++) {
                ownersNames.add(owners.get(i).getName());
            }
            for (int i = 0; i < owners.size(); i++) {
                materialsNames.add(materials.get(i).getName());
            }

            ArrayAdapter ownerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ownersNames);
            ArrayAdapter materialAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, materialsNames);

            materialSpinner.setAdapter(materialAdapter);
            ownerSpinner.setAdapter(ownerAdapter);

            materialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    materialId = materials.get(position).getId();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ownerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ownerId = owners.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPrice.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty() || etDate.getText().toString().isEmpty()) {
                    Toast.makeText(AddQutlayActivity.this, "All fields is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (materialId == -1 || ownerId == -1) {

                    Toast.makeText(AddQutlayActivity.this, "please select material and owner!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Qutlay qutlay = new Qutlay(materialId, ownerId, Integer.parseInt(etPrice.getText().toString()), etDate.getText().toString(), etDescription.getText().toString());
               /* if (btnInsert.getText() == "Update") {
                    material.setId(Integer.parseInt(intent.getStringExtra("id")));
                    db.updateMaterial(material);
                    AddMaterialActivity.super.onBackPressed();
                    return;
                }*/
                try {
                    db.insertQutlay(qutlay);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddQutlayActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;

                }
                AddQutlayActivity.super.onBackPressed();
            }
        });

    }
}