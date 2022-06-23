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

import com.example.myassignment.model.Owner;

public class AddOwnerActivity extends AppCompatActivity {
    private Database db = null;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);

        EditText etName = findViewById(R.id.name);
        EditText etDescription = findViewById(R.id.etDescription);


        Button btnInsert = findViewById(R.id.insertBtn);

        db = Database.getInstace(getApplicationContext());

        intent = getIntent();
        if (intent.getStringExtra("name") != null) {
            etName.setText(intent.getStringExtra("name"));
            etDescription.setText(intent.getStringExtra("desc"));
            btnInsert.setText("Update");
        }


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(AddOwnerActivity.this, "All fields is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Owner owner = new Owner(etName.getText().toString(), etDescription.getText().toString());
                if (btnInsert.getText() == "Update") {
                    owner.setId(Integer.parseInt(intent.getStringExtra("id")));
                    db.updateOwner(owner);
                    AddOwnerActivity.super.onBackPressed();
                    return;
                }
                db.insertOwner(owner);
                AddOwnerActivity.super.onBackPressed();
            }
        });
    }
}