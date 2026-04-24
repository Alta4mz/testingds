package com.zovauto.model;

import com.zovauto.util.ValidationUtil;

/**
 * Класс пилота с характеристиками.
 * Влияет на время прохождения трассы через свои статы.
 */
public class Pilot {
    private final String name;
    private final double accuracy;
    private final double aggression;
    private final double experience;
    private final double price;

    public Pilot(String name, double accuracy, double aggression, double experience, double price) {
        ValidationUtil.validateNotEmpty(name, "Имя пилота");
        ValidationUtil.validateRangeZeroToOne(accuracy, "Аккуратность");
        ValidationUtil.validateRangeZeroToOne(aggression, "Агрессивность");
        ValidationUtil.validateRangeZeroToOne(experience, "Опыт");
        ValidationUtil.validateNonNegative(price, "Цена пилота");

        this.name = name;
        this.accuracy = accuracy;
        this.aggression = aggression;
        this.experience = experience;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getAggression() {
        return aggression;
    }

    public double getExperience() {
        return experience;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Рассчитывает коэффициент влияния пилота на время круга.
     * Высокая аккуратность уменьшает время на поворотах.
     * Высокая агрессия уменьшает время на прямых, но увеличивает риск.
     */
    public double getPilotTimeModifier() {
        // Формула: 1 + aggression * 0.05 - accuracy * 0.03
        return 1.0 + aggression * 0.05 - accuracy * 0.03;
    }

    @Override
    public String toString() {
        return String.format("Пилот: %s | Аккуратность: %.2f, Агрессия: %.2f, Опыт: %.2f | Цена: %.0f",
                name, accuracy, aggression, experience, price);
    }
}
