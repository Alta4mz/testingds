package com.zovauto.model;

/**
 * Класс механика, наследуется от Staff.
 * Влияет на скорость ремонта и снижение износа.
 */
public class Mechanic extends Staff {
    private final String specialization;

    public Mechanic(String name, int qualificationLevel, double salary, String specialization) {
        super(name, "Механик", qualificationLevel, salary);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    @Override
    public double getPerformanceBonus() {
        // Механик добавляет небольшой бонус к надёжности
        return getQualificationLevel() * 0.2;
    }

    /**
     * Рассчитывает эффективность ремонта.
     */
    public double getRepairEfficiency() {
        return 0.1 + getQualificationLevel() * 0.05;
    }

    @Override
    public String toString() {
        return String.format("%s | Специализация: %s", super.toString(), specialization);
    }
}
