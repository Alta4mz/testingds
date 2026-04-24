package com.zovauto.model;

/**
 * Класс инженера, наследуется от Staff.
 * Влияет на бонусы к характеристикам компонентов болида.
 */
public class Engineer extends Staff {
    private final String specialization;

    public Engineer(String name, int qualificationLevel, double salary, String specialization) {
        super(name, "Инженер", qualificationLevel, salary);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public double getPerformanceBonus() {
        // Инженер добавляет бонус 0.5 сек за уровень квалификации
        return getQualificationLevel() * 0.5;
    }

    @Override
    public String toString() {
        return String.format("%s | Специализация: %s", super.toString(), specialization);
    }
}
