package com.zovauto.util;

/**
 * Утилитный класс для валидации данных.
 * Содержит методы проверки "здравого смысла" согласно ТЗ.
 */
public class ValidationUtil {

    private ValidationUtil() {
        // Утилитный класс, не должен инстанцироваться
    }

    /**
     * Проверяет, что значение неотрицательное.
     */
    public static void validateNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " не может быть отрицательным");
        }
    }

    /**
     * Проверяет, что значение положительное.
     */
    public static void validatePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " должно быть положительным");
        }
    }

    /**
     * Проверяет, что значение в диапазоне [0, 1].
     */
    public static void validateRangeZeroToOne(double value, String fieldName) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException(fieldName + " должно быть в диапазоне [0, 1]");
        }
    }

    /**
     * Проверяет, что строка не пустая.
     */
    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
    }

    /**
     * Проверяет, что бюджет достаточен для покупки.
     */
    public static void validateSufficientBudget(double budget, double price, String itemName) {
        if (budget < price) {
            throw new IllegalArgumentException("Недостаточно средств для покупки: " + itemName);
        }
    }
}
