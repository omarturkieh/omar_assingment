package com.example.myassignment.ui.material;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myassignment.AddMaterialActivity;
import com.example.myassignment.Database;
import com.example.myassignment.adapters.MaterialAdapter;
import com.example.myassignment.adapters.OwnerAdapter;
import com.example.myassignment.databinding.FragmentMaterialBinding;
import com.example.myassignment.model.Material;
import com.example.myassignment.model.Owner;

import java.util.ArrayList;

public class MaterialFragment extends Fragment {

    private FragmentMaterialBinding binding;
    private MaterialAdapter adapter = null;
    private ArrayList<Material> materials = new ArrayList<>();
    private Database db = null;

    @Override
    public void onResume() {
        super.onResume();
        try {
            ArrayList<Material> materials = db.getMaterials();
            adapter = new MaterialAdapter(materials, getContext(), db);
            binding.recycler.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMaterialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Database.getInstace(getContext());

        try {
            ArrayList<Material> materials = db.getMaterials();
            adapter = new MaterialAdapter(materials, getContext(), db);
            binding.recycler.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }

        binding.faAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddMaterialActivity.class));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}