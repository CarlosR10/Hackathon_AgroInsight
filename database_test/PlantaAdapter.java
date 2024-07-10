package com.example.database_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder> {

    private final Context context;
    private final List<Planta> plantas;

    public PlantaAdapter(Context context, List<Planta> plantas) {
        this.context = context;
        this.plantas = plantas;
    }

    @NonNull
    @Override
    public PlantaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_planta, parent, false);
        return new PlantaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantaViewHolder holder, int position) {
        Planta planta = plantas.get(position);
        if (planta.getImagen() != null && planta.getImagen().length > 0) {
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(planta.getImagen(), 0, planta.getImagen().length));
        } else {
            holder.imageView.setImageResource(R.drawable.ic_arrow);  // Imagen de recurso predeterminado si no hay imagen
        }
        holder.textViewNombre.setText(planta.getNombreComun());
        holder.textViewFecha.setText(planta.getFechaGuardado());
    }

    @Override
    public int getItemCount() {
        return plantas.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPlantas(List<Planta> nuevasPlantas) {
        plantas.clear();
        plantas.addAll(nuevasPlantas);
        notifyDataSetChanged();
    }

    public void updatePlantas(List<Planta> nuevasPlantas) {
        setPlantas(nuevasPlantas);
    }

    public static class PlantaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewNombre;
        TextView textViewFecha;

        public PlantaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
        }
    }
}
