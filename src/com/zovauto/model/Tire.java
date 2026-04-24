package com.zovauto.model;

import com.zovauto.util.ComponentType;

/**
 * Компонент шин болида.
 * Подходит к любым компонентам, имеет тип состава для разных погодных условий.
 */
public class Tire extends Component {
    private final String compoundType;
    private final double wetGrip;
    private final double dryGrip;

    public Tire(String name, double basePerformance, double price, String compoundType, double wetGrip, double dryGrip) {
        super(name, ComponentType.TIRE, basePerformance, price);
        this.compoundType = compoundType;
        this.wetGrip = wetGrip;
        this.dryGrip = dryGrip;
    }

    public String getCompoundType() {
        return compoundType;
    }

    public double getWetGrip() {
        return wetGrip;
    }

    public double getDryGrip() {
        return dryGrip;
    }

    @Override
    public String toString() {
        return String.format("%s, Состав: %s, Сцепление (сухо/мокро): %.2f/%.2f",
                super.toString(), compoundType, dryGrip, wetGrip);
    }
}
