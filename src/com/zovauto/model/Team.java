package com.zovauto.model;

import java.util.List;
import java.util.ArrayList;

/**
 * Класс команды (игрока или бота).
 * Содержит бюджет, болиды, пилотов и персонал.
 */
public class Team {
    private final String name;
    private double budget;
    // Обоснование коллекции: List для хранения нескольких болидов команды
    private final List<Car> cars;
    // Обоснование коллекции: List для хранения нескольких пилотов
    private final List<Pilot> pilots;
    // Обоснование коллекции: List для хранения нескольких сотрудников
    private final List<Staff> staff;
    private boolean isBot;

    public Team(String name, double budget) {
        this.name = name;
        this.budget = budget;
        this.cars = new ArrayList<>();
        this.pilots = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.isBot = false;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<Car> getCars() {
        return new ArrayList<>(cars);
    }

    public List<Pilot> getPilots() {
        return new ArrayList<>(pilots);
    }

    public List<Staff> getStaff() {
        return new ArrayList<>(staff);
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    /**
     * Добавляет деньги к бюджету.
     */
    public void addBudget(double amount) {
        this.budget += amount;
    }

    /**
     * Снимает деньги с бюджета с проверкой.
     */
    public boolean spendBudget(double amount) {
        if (budget >= amount) {
            this.budget -= amount;
            return true;
        }
        return false;
    }

    /**
     * Добавляет болид в команду.
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Добавляет пилота в команду.
     */
    public void addPilot(Pilot pilot) {
        pilots.add(pilot);
    }

    /**
     * Добавляет сотрудника в команду.
     */
    public void addStaff(Staff staffMember) {
        staff.add(staffMember);
    }

    /**
     * Проверяет, есть ли у команды хотя бы один инженер.
     */
    public boolean hasEngineer() {
        for (Staff s : staff) {
            if (s instanceof Engineer) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает суммарный бонус от всех инженеров.
     */
    public double getTotalEngineerBonus() {
        double totalBonus = 0.0;
        for (Staff s : staff) {
            if (s instanceof Engineer) {
                totalBonus += s.getPerformanceBonus();
            }
        }
        return totalBonus;
    }

    /**
     * Удаляет сломанный компонент из всех болидов.
     */
    public void removeBrokenComponents() {
        for (Car car : cars) {
            if (car.getEngine() != null && car.getEngine().isBroken()) {
                car.setEngine(null);
            }
            if (car.getChassis() != null && car.getChassis().isBroken()) {
                car.setChassis(null);
            }
            if (car.getTransmission() != null && car.getTransmission().isBroken()) {
                car.setTransmission(null);
            }
            if (car.getSuspension() != null && car.getSuspension().isBroken()) {
                car.setSuspension(null);
            }
            if (car.getAeroKit() != null && car.getAeroKit().isBroken()) {
                car.setAeroKit(null);
            }
            if (car.getTires() != null && car.getTires().isBroken()) {
                car.setTires(null);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Команда: ").append(name).append("\n");
        sb.append("Бюджет: ").append((int) budget).append("\n");
        sb.append("Тип: ").append(isBot ? "Бот" : "Игрок").append("\n");
        sb.append("Болиды: ").append(cars.size()).append("\n");
        sb.append("Пилоты: ").append(pilots.size()).append("\n");
        sb.append("Персонал: ").append(staff.size()).append("\n");
        return sb.toString();
    }
}
