package com.example.myassignment.ui.owner;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myassignment.AddOwnerActivity;
import com.example.myassignment.Database;
import com.example.myassignment.adapters.OwnerAdapter;
import com.example.myassignment.databinding.FragmentOwnerBinding;
import com.example.myassignment.model.Owner;

import java.util.ArrayList;

public class OwnerFragment extends Fragment {

    private FragmentOwnerBinding binding;
    private OwnerAdapter adapter = null;
    private ArrayList<Owner> owners = new ArrayList<>();
    private Database db = null;

    @Override
    public void onResume() {
        super.onResume();
        try {
            ArrayList<Owner> owners = db.getOwners();
            adapter = new OwnerAdapter(owners, getContext(), db);
            binding.recycler.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentOwnerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Database.getInstace(getContext());

        try {
            ArrayList<Owner> owners = db.getOwners();
            adapter = new OwnerAdapter(owners, getContext(), db);
            binding.recycler.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }


        binding.faAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddOwnerActivity.class));
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