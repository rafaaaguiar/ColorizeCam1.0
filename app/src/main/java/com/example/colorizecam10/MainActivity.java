package com.example.colorizecam10;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.example.colorize10.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private PreviewView previewView;
    private ExecutorService cameraExecutor;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização de componentes
        previewView = findViewById(R.id.previewView);
        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        Button captureButton = findViewById(R.id.captureColorButton);

        // Inicialização da câmera
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCamera(cameraProvider);
            } catch (Exception e) {
                Log.e("CameraXApp", "Erro ao iniciar a câmera", e);
            }
        }, ContextCompat.getMainExecutor(this));

        // Configuração do botão para capturar a cor
        captureButton.setOnClickListener(v -> detectColor());
    }

    private void startCamera(ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(this, cameraSelector, preview);
    }

    private void detectColor() {
        Bitmap bitmap = previewView.getBitmap();
        if (bitmap != null) {
            int pixelColor = getPixelColor(bitmap);

            // Extrair componentes RGB
            int r = (pixelColor >> 16) & 0xFF;
            int g = (pixelColor >> 8) & 0xFF;
            int b = pixelColor & 0xFF;

            String colorName = ColorDictionary.getClosestColor(r, g, b);
            // Exibir a cor detectada no TextView
            updateColorNameText(colorName);
            Log.d("ColorDetected", "Cor detectada: R=" + r + " G=" + g + " B=" + b + "  " + colorName);
        } else {
            Log.e("CameraXApp", "Bitmap está nulo.");
        }
    }

    private int getPixelColor(Bitmap bitmap) {
        int x = bitmap.getWidth() / 2;
        int y = bitmap.getHeight() / 2;
        return bitmap.getPixel(x, y);
    }

    private void updateColorNameText(String colorName) {
        runOnUiThread(() -> {
            TextView colorNameText = findViewById(R.id.colorNameText);
            colorNameText.setText(colorName);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}

