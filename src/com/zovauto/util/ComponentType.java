package com.zovauto.util;

/**
 * Перечисление типов компонентов болида.
 * Используется для категоризации и проверки совместимости.
 */
public enum ComponentType {
    ENGINE("Двигатель"),
    CHASSIS("Шасси"),
    TRANSMISSION("Трансмиссия"),
    SUSPENSION("Подвеска"),
    AERO_KIT("Аэродинамический комплект"),
    TIRE("Шины");

    private final String displayName;

    ComponentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
