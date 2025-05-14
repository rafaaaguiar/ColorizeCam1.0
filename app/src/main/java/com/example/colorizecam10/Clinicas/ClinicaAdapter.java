package com.example.colorizecam10.Clinicas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colorize10.R;

import java.util.List;

public class ClinicaAdapter extends RecyclerView.Adapter<ClinicaAdapter.ClinicaViewHolder> {
    private List<Clinica> clinicas;
    private Context context;

    public ClinicaAdapter(List<Clinica> clinicas, Context context) {
        this.clinicas = clinicas;
        this.context = context;
    }

    @NonNull
    @Override
    public ClinicaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clinica_item, parent, false);
        return new ClinicaViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ClinicaViewHolder holder, int position) {
        Clinica clinica = clinicas.get(position);
        holder.clinicaNome.setText(clinica.getNome());
        holder.clinicaLink.setText(clinica.getLink());

        //Clicar no nome para abrir o link
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(clinica.getLink()));
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return clinicas.size();
    }

    public static class ClinicaViewHolder extends RecyclerView.ViewHolder {
        public TextView clinicaNome;
        public TextView clinicaLink;

        public ClinicaViewHolder(View itemView) {
        super(itemView);
        clinicaNome = itemView.findViewById(R.id.clinicaNome);
        clinicaLink = itemView.findViewById(R.id.clinicaLink);
        }
    }
}
