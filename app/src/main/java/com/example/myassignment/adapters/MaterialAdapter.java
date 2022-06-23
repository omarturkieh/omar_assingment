package com.example.myassignment.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myassignment.AddMaterialActivity;
import com.example.myassignment.AddOwnerActivity;
import com.example.myassignment.Database;
import com.example.myassignment.R;
import com.example.myassignment.model.Material;
import com.example.myassignment.model.Owner;

import java.util.ArrayList;


public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {

    private ArrayList<Material> materials;
    private Context context;
    private Database db;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView desc;
        public TextView service;
        public Button update;
        public Button delete;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = (TextView) view.findViewById(R.id.tvName);
            desc = (TextView) view.findViewById(R.id.tvDesc);
            service = (TextView) view.findViewById(R.id.tvService);
            update = (Button) view.findViewById(R.id.update);
            delete = (Button) view.findViewById(R.id.delete);
        }

    }

    public MaterialAdapter(ArrayList<Material> materials, Context context, Database db) {
        this.materials = materials;
        this.context = context;
        this.db = db;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.material_card, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.setText(materials.get(position).getName());
        viewHolder.desc.setText(materials.get(position).getDescription());
        if (materials.get(position).getService() == 1) {
            viewHolder.service.setText("service");
        } else {
            viewHolder.service.setText("not service");
            viewHolder.service.setTextColor(Color.RED);
        }


        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddMaterialActivity.class);
                intent.putExtra("name", materials.get(position).getName());
                intent.putExtra("desc", materials.get(position).getDescription());
                intent.putExtra("service", materials.get(position).getService().toString());
                intent.putExtra("id", materials.get(position).getId().toString());
                context.startActivity(intent);
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* db.deleteMaterial(materials.get(position).getId());
                materials.remove(position);
                notifyDataSetChanged();*/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                AlertDialog dialog = null;
                alertDialogBuilder.setTitle("Delete");
                alertDialogBuilder.setMessage("Are you sure?");
                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteMaterial(materials.get(position).getId());
                        materials.remove(position);
                        notifyDataSetChanged();
                    }
                });
                alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = alertDialogBuilder.create();
                dialog.show();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return materials.size();
    }
}

