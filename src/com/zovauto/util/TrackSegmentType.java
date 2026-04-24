package com.zovauto.util;

/**
 * Перечисление типов сегментов трассы.
 * Используется для расчёта особенностей прохождения трассы пилотом.
 */
public enum TrackSegmentType {
    STRAIGHT("Прямая"),
    CORNER("Поворот"),
    UPHILL("Подъем"),
    DOWNHILL("Спуск");

    private final String displayName;

    TrackSegmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
