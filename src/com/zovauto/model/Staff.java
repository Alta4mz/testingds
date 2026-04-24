package com.zovauto.model;

import com.zovauto.util.ValidationUtil;

/**
 * Абстрактный класс сотрудника команды.
 * Реализует наследование для различных типов персонала.
 */
public abstract class Staff {
    private final String name;
    private final String role;
    private final int qualificationLevel;
    private final double salary;

    protected Staff(String name, String role, int qualificationLevel, double salary) {
        ValidationUtil.validateNotEmpty(name, "Имя сотрудника");
        ValidationUtil.validateNotEmpty(role, "Роль");
        ValidationUtil.validatePositive(qualificationLevel, "Уровень квалификации");
        ValidationUtil.validateNonNegative(salary, "Зарплата");

        this.name = name;
        this.role = role;
        this.qualificationLevel = qualificationLevel;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getQualificationLevel() {
        return qualificationLevel;
    }

    public double getSalary() {
        return salary;
    }

    /**
     * Рассчитывает бонус к характеристикам болида от квалификации.
     * Переопределяется в наследниках.
     */
    public abstract double getPerformanceBonus();

    @Override
    public String toString() {
        return String.format("%s: %s | Квалификация: %d | Зарплата: %.0f",
                role, name, qualificationLevel, salary);
    }
}
