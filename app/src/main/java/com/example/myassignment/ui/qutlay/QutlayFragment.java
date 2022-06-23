package com.example.myassignment.ui.qutlay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myassignment.AddQutlayActivity;
import com.example.myassignment.Database;
import com.example.myassignment.adapters.OwnerAdapter;
import com.example.myassignment.adapters.QutlayAdapter;
import com.example.myassignment.databinding.FragmentQutlayBinding;
import com.example.myassignment.model.Owner;
import com.example.myassignment.model.Qutlay;

import java.util.ArrayList;

public class QutlayFragment extends Fragment {

    private FragmentQutlayBinding binding;
    private QutlayAdapter adapter = null;
    private ArrayList<Qutlay> qutlays = new ArrayList<>();
    private Database db = null;

    @Override
    public void onResume() {
        super.onResume();
        try {
            ArrayList<Qutlay> qutlays = db.getQutlays();
            adapter = new QutlayAdapter(qutlays, getContext(), db);
            binding.recycler.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQutlayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Database.getInstace(getContext());

        try {
            ArrayList<Qutlay> qutlays = db.getQutlays();
            Log.d("----QutlaySize:", String.valueOf(qutlays.size()));
            adapter = new QutlayAdapter(qutlays, getContext(), db);
            binding.recycler.setAdapter(adapter);

        }catch (Exception e) {
            e.printStackTrace();
        }

        binding.faAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddQutlayActivity.class));
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