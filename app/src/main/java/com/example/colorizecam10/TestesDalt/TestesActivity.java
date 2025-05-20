package com.example.colorizecam10.TestesDalt;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.colorize10.R;
import com.example.colorizecam10.Clinicas.ClinicasActivity;
import com.example.colorizecam10.MainActivity;
import com.google.android.material.navigation.NavigationView;

public class TestesActivity extends AppCompatActivity {
    private boolean isFlipped = false;
    private CardView cardView;
    private ImageView cardImage;
    private TextView answerText;
    private EditText userResponse;
    private String correctAnswer = "Visão normal vê o número 45." +
                                    "\nBoa parte dos daltônicos não " +
                                    "consegue ver esse número com clareza";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colorize10.R.layout.activity_testes);

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

            if (id == R.id.nav_testes) {
                Toast.makeText(TestesActivity.this,"", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_home) {
                Intent intent = new Intent(TestesActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_clinicas) {
                Intent intent = new Intent(TestesActivity.this, ClinicasActivity.class);
                startActivity(intent);
            }

            drawerLayout.closeDrawers();
            return true;
        });

        cardView = findViewById(R.id.cardView);
        cardImage = findViewById(R.id.cardImage);
        userResponse = findViewById(R.id.userResponse);
        answerText = findViewById(R.id.answerText);

        userResponse.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (!userResponse.getText().toString().trim().isEmpty()) {
                    flipCard();

                }
                userResponse.setText("");
                userResponse.requestFocus();
                return true;
            }
            return false;
                });

        userResponse.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        if (source.toString().contains("\n")) {
                            return"";
                        }
                        for (int i = start; i < end; i++) {
                            if (!Character.isDigit(source.charAt(i))) {
                                return "";
                            }
                        }
                        return null;
                        }
                    }
                });


        cardView.setOnClickListener(view -> flipCard());

    }

    private void flipCard() {

        String userAnswer = userResponse.getText().toString().trim();
        String respostaCompleta = correctAnswer +
                "\n\nSua resposta: " +
                (userAnswer.isEmpty() ? "Não respondido" : userAnswer);
        answerText.setText(respostaCompleta);


        cardImage.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            cardImage.setVisibility(View.INVISIBLE);
            answerText.setVisibility(View.VISIBLE);
            answerText.setAlpha(0f);
            answerText.animate().alpha(1f).setDuration(300).start();
        }).start();
    }
}





