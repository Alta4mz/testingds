package com.zovauto.model;

import com.zovauto.util.ComponentType;

/**
 * Аэродинамический комплект болида.
 * Подходит к любым компонентам, влияет на прижимную силу.
 */
public class AeroKit extends Component {
    private final double downforce;

    public AeroKit(String name, double basePerformance, double price, double downforce) {
        super(name, ComponentType.AERO_KIT, basePerformance, price);
        this.downforce = downforce;
    }

    public double getDownforce() {
        return downforce;
    }

    @Override
    public String toString() {
        return String.format("%s, Прижимная сила: %.2f",
                super.toString(), downforce);
    }
}
