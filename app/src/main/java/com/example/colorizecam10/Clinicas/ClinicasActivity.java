package com.example.colorizecam10.Clinicas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colorize10.R;

import java.util.ArrayList;
import java.util.List;

public class ClinicasActivity extends AppCompatActivity {
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicas);

        RecyclerView recyclerView = findViewById(R.id.clinicaRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Clinica> clinicas = new ArrayList<>();
        clinicas.add(new Clinica("CEMA Hospital", "https://www.cemahospital.com.br/blog/daltonismo"));
        clinicas.add(new Clinica("Clinica Lividi", "https://www.clinicalividi.com.br"));
        clinicas.add(new Clinica("Clinica Vision One", "https://www.visionone.com.br"));

        ClinicaAdapter adapter = new ClinicaAdapter(clinicas, this);
        recyclerView.setAdapter(adapter);


    }
}