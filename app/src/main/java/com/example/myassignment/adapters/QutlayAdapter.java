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
import com.example.myassignment.Database;
import com.example.myassignment.R;
import com.example.myassignment.model.Material;
import com.example.myassignment.model.Owner;
import com.example.myassignment.model.Qutlay;

import java.util.ArrayList;


public class QutlayAdapter extends RecyclerView.Adapter<QutlayAdapter.ViewHolder> {

    private ArrayList<Qutlay> qutlays;
    private Context context;
    private Database db;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ownerName;
        public TextView materialName;
        public TextView price;
        public TextView date;
        public TextView desc;
        public Button update;
        public Button delete;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            ownerName = (TextView) view.findViewById(R.id.ownerName);
            materialName = (TextView) view.findViewById(R.id.materialName);
            desc = (TextView) view.findViewById(R.id.tvDesc);
            date = (TextView) view.findViewById(R.id.date);
            price = (TextView) view.findViewById(R.id.tvPrice);
            update = (Button) view.findViewById(R.id.update);
            delete = (Button) view.findViewById(R.id.delete);
        }

    }

    public QutlayAdapter(ArrayList<Qutlay> qutlays, Context context, Database db) {
        this.qutlays = qutlays;
        this.context = context;
        this.db = db;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.qutlay_card, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        int materialId = qutlays.get(position).getMaterial_id();
        int ownerId = qutlays.get(position).getOwner_id();

        String materialName = "";
        String ownerName = "";
        ArrayList<Material> materials = db.getMaterials();
        ArrayList<Owner> owners = db.getOwners();
        for (int i=0; i<owners.size(); i++) {
            if (owners.get(i).getId() == ownerId) {
                ownerName = owners.get(i).getName();
            }
        }
        for (int i=0; i<materials.size(); i++) {
            if (materials.get(i).getId() == materialId) {
                materialName = materials.get(i).getName();
            }
        }
        viewHolder.ownerName.setText(ownerName);
        viewHolder.materialName.setText(materialName);
        viewHolder.desc.setText(qutlays.get(position).getDescription());
        //viewHolder.price.setText(qutlays.get(position).getPrice());
        viewHolder.date.setText(qutlays.get(position).getDate());



       /* viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddMaterialActivity.class);
                intent.putExtra("name", materials.get(position).getName());
                intent.putExtra("desc", materials.get(position).getDescription());
                intent.putExtra("service", materials.get(position).getService().toString());
                intent.putExtra("id", materials.get(position).getId().toString());
                context.startActivity(intent);
            }
        });*/

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                AlertDialog dialog = null;
                alertDialogBuilder.setTitle("Delete");
                alertDialogBuilder.setMessage("Are you sure?");
                alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteQutlay(qutlays.get(position));
                        qutlays.remove(position);
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
        return qutlays.size();
    }
}

