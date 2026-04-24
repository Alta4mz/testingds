package com.zovauto.service;

import com.zovauto.model.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Сервис проверки совместимости компонентов болида.
 * Реализует логику валидации сборки согласно ТЗ.
 */
public class CompatibilityChecker {

    private CompatibilityChecker() {
        // Утилитный класс
    }

    /**
     * Проверяет совместимость всех компонентов для сборки болида.
     * Возвращает список ошибок совместимости (пустой если всё ок).
     */
    public static List<String> checkCompatibility(Engine engine, Chassis chassis, Transmission transmission, 
                                                   Suspension suspension, AeroKit aeroKit, Tire tire) {
        List<String> errors = new ArrayList<>();

        // Проверка 1: вес двигателя <= maxWeight шасси
        if (engine != null && chassis != null) {
            if (engine.getWeight() > chassis.getMaxEngineWeight()) {
                errors.add(String.format("Двигатель '%s' (%.1f кг) слишком тяжёлый для шасси '%s' (макс. %.1f кг)",
                        engine.getName(), engine.getWeight(), chassis.getName(), chassis.getMaxEngineWeight()));
            }
        }

        // Проверка 2: тип трансмиссии совместим с типом двигателя
        if (engine != null && transmission != null) {
            if (!isTransmissionCompatibleWithEngine(transmission, engine)) {
                errors.add(String.format("Трансмиссия '%s' (%s) несовместима с двигателем '%s' (%s)",
                        transmission.getName(), transmission.getTransmissionType(),
                        engine.getName(), engine.getEngineType()));
            }
        }

        // Проверка 3: подвеска совместима с шасси
        if (suspension != null && chassis != null) {
            if (!suspension.isCompatibleWithChassis(chassis)) {
                errors.add(String.format("Подвеска '%s' несовместима с шасси '%s'",
                        suspension.getName(), chassis.getName()));
            }
        }

        // Аэропакет и шины подходят всегда - проверок нет

        return errors;
    }

    /**
     * Проверяет совместимость типа трансмиссии с типом двигателя.
     */
    private static boolean isTransmissionCompatibleWithEngine(Transmission transmission, Engine engine) {
        String transType = transmission.getTransmissionType().toLowerCase();
        String engineType = engine.getEngineType().toLowerCase();

        // Простая логика совместимости:
        // - Sequential подходит к V6, V8, V10
        // - CVT подходит к V4, V6
        // - Manual подходит ко всем
        
        if (transType.equals("manual")) {
            return true; // Ручная подходит ко всем
        }

        if (transType.equals("sequential")) {
            return engineType.contains("v6") || engineType.contains("v8") || engineType.contains("v10");
        }

        if (transType.equals("cvt")) {
            return engineType.contains("v4") || engineType.contains("v6");
        }

        return true; // По умолчанию считаем совместимым
    }

    /**
     * Проверяет полный набор компонентов на наличие всех необходимых.
     */
    public static boolean hasAllComponents(Engine engine, Chassis chassis, Transmission transmission,
                                           Suspension suspension, AeroKit aeroKit, Tire tire) {
        return engine != null && chassis != null && transmission != null
                && suspension != null && aeroKit != null && tire != null;
    }
}
