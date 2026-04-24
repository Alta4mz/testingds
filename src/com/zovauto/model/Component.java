package com.zovauto.model;

import com.zovauto.util.ComponentType;
import com.zovauto.util.ValidationUtil;

/**
 * Абстрактный класс компонента болида.
 * Содержит базовые характеристики и состояние износа.
 * Реализует инкапсуляцию через private поля и геттеры/сеттеры.
 */
public abstract class Component {
    private final String name;
    private final ComponentType type;
    private final double basePerformance;
    private final double price;
    private double wear;
    private boolean isBroken;

    protected Component(String name, ComponentType type, double basePerformance, double price) {
        ValidationUtil.validateNotEmpty(name, "Название компонента");
        ValidationUtil.validatePositive(basePerformance, "Базовая производительность");
        ValidationUtil.validateNonNegative(price, "Цена");

        this.name = name;
        this.type = type;
        this.basePerformance = basePerformance;
        this.price = price;
        this.wear = 0.0;
        this.isBroken = false;
    }

    /**
     * Возвращает название компонента.
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает тип компонента.
     */
    public ComponentType getType() {
        return type;
    }

    /**
     * Возвращает базовую производительность.
     */
    public double getBasePerformance() {
        return basePerformance;
    }

    /**
     * Возвращает цену компонента.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Возвращает текущий износ (0.0 - 1.0).
     */
    public double getWear() {
        return wear;
    }

    /**
     * Устанавливает износ с валидацией.
     */
    public void setWear(double wear) {
        ValidationUtil.validateRangeZeroToOne(wear, "Износ");
        this.wear = wear;
    }

    /**
     * Проверяет, сломан ли компонент.
     */
    public boolean isBroken() {
        return isBroken;
    }

    /**
     * Устанавливает состояние поломки.
     */
    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    /**
     * Увеличивает износ на указанное значение.
     */
    public void addWear(double wearIncrement) {
        ValidationUtil.validateNonNegative(wearIncrement, "Прибавка износа");
        if (!isBroken) {
            this.wear = Math.min(1.0, this.wear + wearIncrement);
        }
    }

    /**
     * Ремонтирует компонент, снижая износ.
     */
    public void repair() {
        if (!isBroken) {
            this.wear = Math.max(0.0, this.wear - 0.3);
        }
    }

    /**
     * Полностью восстанавливает компонент.
     */
    public void fullyRepair() {
        if (!isBroken) {
            this.wear = 0.0;
        }
    }

    /**
     * Возвращает эффективную производительность с учётом износа.
     */
    public double getEffectivePerformance() {
        if (isBroken) {
            return 0.0;
        }
        return basePerformance * (1.0 - wear * 0.5);
    }

    @Override
    public String toString() {
        String status = isBroken ? "[СЛОМАН] " : "";
        String wearPercent = String.format("%.1f%%", wear * 100);
        return String.format("%s%s (%s): %.2f, Износ: %s, Цена: %.0f",
                status, name, type.getDisplayName(), basePerformance, wearPercent, price);
    }
}
