package com.zovauto.service;

import com.zovauto.model.*;
import com.zovauto.util.WeatherCondition;

/**
 * Сервис расчёта времени круга и симуляции гонки.
 * Учитывает все 5 факторов из ТЗ: болид, персонал, инженеры, пилот, трасса.
 */
public class RaceCalculator {

    private RaceCalculator() {
        // Утилитный класс
    }

    /**
     * Рассчитывает время прохождения круга.
     * Формула учитывает:
     * 1) Характеристики Болида (effectiveSpeed)
     * 2) Действия персонала (бонусы от staff)
     * 3) Квалификацию инженеров (qualification bonus)
     * 4) Особенности Пилота (accuracy/aggression modifier)
     * 5) Особенности трассы (сегменты, погода)
     */
    public static double calculateLapTime(Car car, Pilot pilot, Team team, Track track) {
        // Базовое время = длина трассы / эффективная скорость болида
        double baseTime = track.getLength() / car.getEffectiveSpeed() * 1000;

        // Фактор погоды (сухо=1.0, дождь=1.25 и т.д.)
        double weatherFactor = track.getWeather().getDifficultyMultiplier();

        // Фактор пилота (aggression/accuracy modifier)
        double pilotFactor = pilot.getPilotTimeModifier();

        // Бонус от инженеров (вычитаем из времени)
        double engineerBonus = team.getTotalEngineerBonus();

        // Множитель сложности трассы
        double trackDifficulty = track.getTrackDifficultyMultiplier();

        // Итоговое время:
        // baseTime * weatherFactor * pilotFactor * trackDifficulty - engineerBonus
        double finalTime = baseTime * weatherFactor * pilotFactor * trackDifficulty - engineerBonus;

        // Добавляем немного случайности (±2%)
        double randomVariation = 0.98 + Math.random() * 0.04;
        finalTime *= randomVariation;

        return Math.max(10.0, finalTime); // Минимум 10 секунд
    }

    /**
     * Генерирует случайные погодные условия для трассы.
     */
    public static WeatherCondition generateRandomWeather() {
        double rand = Math.random();
        if (rand < 0.5) {
            return WeatherCondition.DRY;
        } else if (rand < 0.7) {
            return WeatherCondition.CLOUDY;
        } else if (rand < 0.9) {
            return WeatherCondition.RAIN;
        } else {
            return WeatherCondition.HEAVY_RAIN;
        }
    }

    /**
     * Определяет, произошёл ли инцидент (30% шанс если игнорировать износ).
     */
    public static boolean checkIncidentOccurrence(boolean ignoredWarning) {
        if (!ignoredWarning) {
            return false; // Если не игнорировали - инцидента нет
        }
        return Math.random() < 0.3; // 30% шанс
    }

    /**
     * Применяет износ ко всем компонентам после гонки.
     */
    public static void applyPostRaceWear(Car car) {
        double wearIncrement = 0.05 + Math.random() * 0.10; // 5-15%
        car.applyWear(wearIncrement);
    }
}
