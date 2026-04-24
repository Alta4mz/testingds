package com.zovauto.model;

import com.zovauto.util.ComponentType;

/**
 * Компонент шасси болида.
 * Имеет максимальную массу двигателя для проверки совместимости.
 */
public class Chassis extends Component {
    private final double maxEngineWeight;
    private final double baseWeight;

    public Chassis(String name, double basePerformance, double price, double maxEngineWeight, double baseWeight) {
        super(name, ComponentType.CHASSIS, basePerformance, price);
        this.maxEngineWeight = maxEngineWeight;
        this.baseWeight = baseWeight;
    }

    public double getMaxEngineWeight() {
        return maxEngineWeight;
    }

    public double getBaseWeight() {
        return baseWeight;
    }

    @Override
    public String toString() {
        return String.format("%s, Макс.вес двигателя: %.1f кг, Собств.вес: %.1f кг",
                super.toString(), maxEngineWeight, baseWeight);
    }
}
