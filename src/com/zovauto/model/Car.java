package com.zovauto.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Класс болида, содержащий все компоненты.
 * Управляет сборкой и проверкой совместимости компонентов.
 */
public class Car {
    private final String name;
    private Engine engine;
    private Chassis chassis;
    private Transmission transmission;
    private Suspension suspension;
    private AeroKit aeroKit;
    private Tire tires;

    public Car(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Chassis getChassis() {
        return chassis;
    }

    public void setChassis(Chassis chassis) {
        this.chassis = chassis;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Suspension getSuspension() {
        return suspension;
    }

    public void setSuspension(Suspension suspension) {
        this.suspension = suspension;
    }

    public AeroKit getAeroKit() {
        return aeroKit;
    }

    public void setAeroKit(AeroKit aeroKit) {
        this.aeroKit = aeroKit;
    }

    public Tire getTires() {
        return tires;
    }

    public void setTires(Tire tires) {
        this.tires = tires;
    }

    /**
     * Проверяет, укомплектован ли болид всеми компонентами.
     */
    public boolean isComplete() {
        return engine != null && chassis != null && transmission != null
                && suspension != null && aeroKit != null && tires != null;
    }

    /**
     * Возвращает список отсутствующих компонентов.
     */
    public List<String> getMissingComponents() {
        List<String> missing = new ArrayList<>();
        if (engine == null) missing.add("Двигатель");
        if (chassis == null) missing.add("Шасси");
        if (transmission == null) missing.add("Трансмиссия");
        if (suspension == null) missing.add("Подвеска");
        if (aeroKit == null) missing.add("Аэродинамический комплект");
        if (tires == null) missing.add("Шины");
        return missing;
    }

    /**
     * Рассчитывает эффективную скорость болида на основе всех компонентов.
     * Формула: maxSpeed * efficiency * (1 + aeroBonus) * tireGrip
     */
    public double getEffectiveSpeed() {
        if (!isComplete()) {
            return 0.0;
        }

        double baseSpeed = engine.getEffectivePerformance();
        double transmissionEfficiency = transmission.getEffectivePerformance() / 100.0;
        double aeroBonus = aeroKit.getEffectivePerformance() / 1000.0;
        double tireGrip = tires.getEffectivePerformance() / 100.0;
        double suspensionFactor = suspension.getEffectivePerformance() / 100.0;

        return baseSpeed * transmissionEfficiency * (1.0 + aeroBonus) * tireGrip * suspensionFactor;
    }

    /**
     * Применяет износ ко всем компонентам после гонки.
     */
    public void applyWear(double wearIncrement) {
        if (engine != null) engine.addWear(wearIncrement);
        if (chassis != null) chassis.addWear(wearIncrement);
        if (transmission != null) transmission.addWear(wearIncrement);
        if (suspension != null) suspension.addWear(wearIncrement);
        if (aeroKit != null) aeroKit.addWear(wearIncrement);
        if (tires != null) tires.addWear(wearIncrement);
    }

    /**
     * Проверяет, есть ли компоненты с износом выше 50%.
     */
    public boolean hasHighWearComponents() {
        return (engine != null && engine.getWear() > 0.5)
                || (chassis != null && chassis.getWear() > 0.5)
                || (transmission != null && transmission.getWear() > 0.5)
                || (suspension != null && suspension.getWear() > 0.5)
                || (aeroKit != null && aeroKit.getWear() > 0.5)
                || (tires != null && tires.getWear() > 0.5);
    }

    /**
     * Возвращает список компонентов с высоким износом.
     */
    public List<Component> getHighWearComponents() {
        List<Component> highWear = new ArrayList<>();
        if (engine != null && engine.getWear() > 0.5) highWear.add(engine);
        if (chassis != null && chassis.getWear() > 0.5) highWear.add(chassis);
        if (transmission != null && transmission.getWear() > 0.5) highWear.add(transmission);
        if (suspension != null && suspension.getWear() > 0.5) highWear.add(suspension);
        if (aeroKit != null && aeroKit.getWear() > 0.5) highWear.add(aeroKit);
        if (tires != null && tires.getWear() > 0.5) highWear.add(tires);
        return highWear;
    }

    /**
     * Ломает случайный компонент при инциденте.
     */
    public Component breakRandomComponent() {
        List<Component> allComponents = new ArrayList<>();
        if (engine != null) allComponents.add(engine);
        if (chassis != null) allComponents.add(chassis);
        if (transmission != null) allComponents.add(transmission);
        if (suspension != null) allComponents.add(suspension);
        if (aeroKit != null) allComponents.add(aeroKit);
        if (tires != null) allComponents.add(tires);

        if (allComponents.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * allComponents.size());
        Component broken = allComponents.get(randomIndex);
        broken.setBroken(true);
        return broken;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Болид: ").append(name).append("\n");
        sb.append(isComplete() ? "[Укомплектован]\n" : "[НЕ укомплектован]\n");
        if (engine != null) sb.append("  Двигатель: ").append(engine).append("\n");
        if (chassis != null) sb.append("  Шасси: ").append(chassis).append("\n");
        if (transmission != null) sb.append("  Трансмиссия: ").append(transmission).append("\n");
        if (suspension != null) sb.append("  Подвеска: ").append(suspension).append("\n");
        if (aeroKit != null) sb.append("  Аэропакет: ").append(aeroKit).append("\n");
        if (tires != null) sb.append("  Шины: ").append(tires).append("\n");
        return sb.toString();
    }
}
