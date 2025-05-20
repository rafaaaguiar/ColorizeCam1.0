package com.example.colorizecam10.Clinicas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colorize10.R;
import com.example.colorizecam10.MainActivity;
import com.example.colorizecam10.TestesDalt.TestesActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ClinicasActivity extends AppCompatActivity {
    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicas);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_clinicas) {
                Toast.makeText(ClinicasActivity.this,"", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_home) {
                Intent intent = new Intent(ClinicasActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_testes) {
                Intent intent = new Intent(ClinicasActivity.this, TestesActivity.class);
                startActivity(intent);
            }

            drawerLayout.closeDrawers(); // Fechar o menu corretamente após qualquer clique
            return true;
        });


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