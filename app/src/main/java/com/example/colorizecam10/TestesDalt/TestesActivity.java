package com.example.colorizecam10.TestesDalt;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
    private CardView cardView;
    private ImageView cardImage;
    private TextView answerText;
    private EditText userResponse;
    private Button btnProximoTeste;

    private int[] imagensTestes = {
            R.drawable.img_test1,
            R.drawable.img_test8,
            R.drawable.img_test3
    };

    private String[] correctAnswers = {
            "Visão normal vê o número 45.\n\nBoa parte dos daltônicos não consegue ver esse número com clareza",
            "Visão normal vê o número 8.\n\nDaltonismo de verde-vermelho vê o número 3.\n\nDaltonismo total não vê nada.",
            "Visão normal vê o número 3.\n\nDaltonismo de verde-vermelho vê o número 5.\n\nDaltonismo total não vê nada."
    };

    private int testAtual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testes);

        // Configuração da Toolbar e Navigation Drawer
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
                Toast.makeText(TestesActivity.this, "", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(TestesActivity.this, MainActivity.class));
            } else if (id == R.id.nav_clinicas) {
                startActivity(new Intent(TestesActivity.this, ClinicasActivity.class));
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // inicialização das views
        cardView = findViewById(R.id.cardView);
        cardImage = findViewById(R.id.cardImage);
        userResponse = findViewById(R.id.userResponse);
        answerText = findViewById(R.id.answerText);
        btnProximoTeste = findViewById(R.id.btnProximoTeste);

        // configuração do EditText
        userResponse.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userResponse.getWindowToken(), 0);

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
                            return "";
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

        // Configuração dos cliques
        cardView.setOnClickListener(view -> flipCard());
        btnProximoTeste.setOnClickListener(view -> avancarTeste());

        // Carrega o primeiro teste
        carregarTesteAtual();
    }

    private void carregarTesteAtual() {
        // Atualiza a imagem e resposta correta
        cardImage.setImageResource(imagensTestes[testAtual]);

        // Reseta o estado
        cardImage.setVisibility(View.VISIBLE);
        answerText.setVisibility(View.INVISIBLE);
        cardImage.setAlpha(1f);
        userResponse.setText("");
        userResponse.requestFocus();
    }

    private void avancarTeste() {
        testAtual++;

        if (testAtual >= imagensTestes.length) {
            Toast.makeText(this, "Você completou todos os testes!", Toast.LENGTH_SHORT).show();
            testAtual = 0;
            btnProximoTeste.setVisibility(View.VISIBLE);
        }

        if (testAtual == imagensTestes.length - 1) {
            btnProximoTeste.setVisibility(View.GONE);
        }

        carregarTesteAtual();
    }

    private void flipCard() {
        String userAnswer = userResponse.getText().toString().trim();
        if (userAnswer.isEmpty()) {
            userResponse.setError("Digite uma resposta");
            return;
        }

        String respostaCompleta = correctAnswers[testAtual] +
                "\n\nSua resposta: " +
                (userAnswer.isEmpty() ? "Não respondido" : userAnswer);
        answerText.setText(respostaCompleta);

        // Animação de fade
        cardImage.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            cardImage.setVisibility(View.INVISIBLE);
            answerText.setVisibility(View.VISIBLE);
            answerText.setAlpha(0f);
            answerText.animate().alpha(1f).setDuration(300).start();
        }).start();
    }
}