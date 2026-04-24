package com.zovauto.model;

import com.zovauto.util.ComponentType;

/**
 * Компонент трансмиссии болида.
 * Имеет тип для проверки совместимости с двигателем.
 */
public class Transmission extends Component {
    private final String transmissionType;
    private final double efficiency;

    public Transmission(String name, double basePerformance, double price, String transmissionType, double efficiency) {
        super(name, ComponentType.TRANSMISSION, basePerformance, price);
        this.transmissionType = transmissionType;
        this.efficiency = efficiency;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public double getEfficiency() {
        return efficiency;
    }

    @Override
    public String toString() {
        return String.format("%s, Тип: %s, Эффективность: %.2f",
                super.toString(), transmissionType, efficiency);
    }
}
