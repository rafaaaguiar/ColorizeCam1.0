package com.example.colorizecam10;

import java.util.HashMap;
import java.util.Map;

public class ColorDictionary {
    private static final Map<String, int[]> colorMap = new HashMap<>();

    static {
        //colorMap.put("Vermelho", new int[][]{{200, 255}, {0, 50}, {0, 50}});
        colorMap.put("Vermelho", new int[]{255, 0, 0});
        colorMap.put("Azul", new int[]{0, 0, 255});
        colorMap.put("Verde", new int[]{0, 255, 0});
        colorMap.put("Amarelo", new int[]{255, 255, 0});
        //colorMap.put("Ciano", new int[]{0, 255, 255});
        //colorMap.put("Magenta", new int[]{255, 0, 255});
        colorMap.put("Preto", new int[]{0, 0, 0});
        colorMap.put("Branco", new int[]{255, 255, 255});
        //colorMap.put("Cinza", new int[]{128, 128, 128});
        colorMap.put("Laranja", new int[]{246, 120, 40});
        colorMap.put("Roxo", new int[]{128, 0, 128});
        colorMap.put("Marrom", new int[]{150, 75, 0});
        //colorMap.put("Rosa", new int[]{255, 192, 203});
        //colorMap.put("Turquesa", new int[]{64, 224, 208});
        //colorMap.put("Oliva", new int[]{128, 128, 0});
        //colorMap.put("Bege", new int[]{255, 228, 196});
        //colorMap.put("Violeta", new int[]{238, 130, 238});
        //colorMap.put("Ametista", new int[]{255, 222, 173});
        //colorMap.put("Prata", new int[]{192, 192, 192});
        //colorMap.put("Fúcsia", new int[]{255, 0, 255});
        //colorMap.put("Índigo", new int[]{75, 0, 130});
        //colorMap.put("Ameixa", new int[]{178, 34, 34});
        //colorMap.put("Camomila", new int[]{255, 192, 203});

    }

    public static String getClosestColor(int r, int g, int b) {
        String closestColor = "Desconhecido";
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<String, int[]> entry : colorMap.entrySet()) {
            int[] colorValues = entry.getValue();
            double distance = Math.sqrt(
                    Math.pow(r - colorValues[0], 2) +
                    Math.pow(g - colorValues[1], 2) +
                    Math.pow(b - colorValues[2], 2));

            if (distance < minDistance) {
                minDistance = distance;
                closestColor = entry.getKey();
            }
        }
        return closestColor;
        }
    }
