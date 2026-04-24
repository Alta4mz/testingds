package com.zovauto.model;

import com.zovauto.util.ComponentType;

/**
 * Компонент двигателя болида.
 * Влияет на максимальную скорость и имеет вес для проверки совместимости с шасси.
 */
public class Engine extends Component {
    private final double maxSpeed;
    private final double weight;
    private final String engineType;

    public Engine(String name, double basePerformance, double price, double maxSpeed, double weight, String engineType) {
        super(name, ComponentType.ENGINE, basePerformance, price);
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.engineType = engineType;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getWeight() {
        return weight;
    }

    public String getEngineType() {
        return engineType;
    }

    @Override
    public String toString() {
        return String.format("%s, Макс.скорость: %.0f, Вес: %.1f кг, Тип: %s",
                super.toString(), maxSpeed, weight, engineType);
    }
}
