package com.zovauto.model;

import com.zovauto.util.TrackSegmentType;
import com.zovauto.util.WeatherCondition;
import java.util.List;
import java.util.ArrayList;

/**
 * Класс трассы с характеристиками.
 * Содержит сегменты, длину и погодные условия.
 */
public class Track {
    private final String name;
    private final double length; // в км
    // Обоснование коллекции: List используется для сохранения порядка сегментов трассы
    private final List<TrackSegmentType> segments;
    private final WeatherCondition weather;

    public Track(String name, double length, List<TrackSegmentType> segments, WeatherCondition weather) {
        this.name = name;
        this.length = length;
        this.segments = new ArrayList<>(segments);
        this.weather = weather;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public List<TrackSegmentType> getSegments() {
        return new ArrayList<>(segments);
    }

    public WeatherCondition getWeather() {
        return weather;
    }

    /**
     * Подсчитывает количество сегментов определённого типа.
     */
    public int countSegments(TrackSegmentType type) {
        int count = 0;
        for (TrackSegmentType segment : segments) {
            if (segment == type) {
                count++;
            }
        }
        return count;
    }

    /**
     * Рассчитывает множитель сложности трассы на основе сегментов.
     * Больше поворотов = выше сложность.
     */
    public double getTrackDifficultyMultiplier() {
        int corners = countSegments(TrackSegmentType.CORNER);
        int straights = countSegments(TrackSegmentType.STRAIGHT);
        
        // Базовый множитель 1.0
        // Каждый поворот добавляет 0.02, каждая прямая уменьшает на 0.01
        double multiplier = 1.0 + corners * 0.02 - straights * 0.01;
        return Math.max(0.8, multiplier); // Минимум 0.8
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Трасса: ").append(name).append("\n");
        sb.append("Длина: ").append(length).append(" км\n");
        sb.append("Погода: ").append(weather.getDisplayName()).append(" (коэфф: ").append(weather.getDifficultyMultiplier()).append(")\n");
        sb.append("Сегменты: ");
        for (TrackSegmentType segment : segments) {
            sb.append(segment.getDisplayName()).append(" ");
        }
        sb.append("\n");
        sb.append("Множитель сложности: ").append(String.format("%.2f", getTrackDifficultyMultiplier()));
        return sb.toString();
    }
}
