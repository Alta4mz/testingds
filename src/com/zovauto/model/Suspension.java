package com.zovauto.model;

import com.zovauto.util.ComponentType;

/**
 * Компонент подвески болида.
 * Влияет на управляемость и имеет список совместимых моделей шасси.
 */
public class Suspension extends Component {
    private final double handlingBonus;
    private final String[] compatibleChassisModels;

    public Suspension(String name, double basePerformance, double price, double handlingBonus, String[] compatibleChassisModels) {
        super(name, ComponentType.SUSPENSION, basePerformance, price);
        this.handlingBonus = handlingBonus;
        this.compatibleChassisModels = compatibleChassisModels;
    }

    public double getHandlingBonus() {
        return handlingBonus;
    }

    public String[] getCompatibleChassisModels() {
        return compatibleChassisModels;
    }

    /**
     * Проверяет совместимость с шасси по названию модели.
     */
    public boolean isCompatibleWithChassis(Chassis chassis) {
        if (compatibleChassisModels == null || compatibleChassisModels.length == 0) {
            return true; // Если список пустой - совместима со всеми
        }
        for (String model : compatibleChassisModels) {
            if (chassis.getName().contains(model)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s, Бонус управляемости: %.2f",
                super.toString(), handlingBonus);
    }
}
