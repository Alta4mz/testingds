package com.zovauto.util;

/**
 * Перечисление погодных условий на трассе.
 * Влияет на коэффициент сложности прохождения трассы.
 */
public enum WeatherCondition {
    DRY("Сухо", 1.0),
    CLOUDY("Облачно", 1.05),
    RAIN("Дождь", 1.25),
    HEAVY_RAIN("Сильный дождь", 1.4);

    private final String displayName;
    private final double difficultyMultiplier;

    WeatherCondition(String displayName, double difficultyMultiplier) {
        this.displayName = displayName;
        this.difficultyMultiplier = difficultyMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDifficultyMultiplier() {
        return difficultyMultiplier;
    }
}
