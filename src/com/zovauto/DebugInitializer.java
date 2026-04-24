package com.zovauto;

import com.zovauto.model.*;
import com.zovauto.service.BotTeamGenerator;
import com.zovauto.ui.GameMenu;

import java.util.List;
import java.util.ArrayList;

/**
 * Класс инициализации debug-режима.
 * Создаёт готовую к игре конфигурацию для быстрого старта.
 */
public class DebugInitializer {

    private DebugInitializer() {
        // Утилитный класс
    }

    /**
     * Инициализирует игру в debug-режиме:
     * - Бюджет игрока: 10_000_000
     * - Готовый болид со всеми компонентами
     * - Пилот с хорошими характеристиками
     * - Инженер высокой квалификации
     * - 3 команды ботов
     */
    public static GameMenu createDebugGame() {
        System.out.println("=== DEBUG MODE ===");
        System.out.println("Инициализация готовой конфигурации...");

        // Создаём команду игрока
        Team playerTeam = new Team("Player Racing Team", 10_000_000);

        // Создаём готовый болид
        Car playerCar = new Car("ZovAuto Racer v1.0");
        
        // Двигатель
        Engine engine = new Engine("ZovAuto V8 Power", 100.0, 200_000, 400.0, 150.0, "V8");
        playerCar.setEngine(engine);
        
        // Шасси
        Chassis chassis = new Chassis("ZovAuto Carbon Mono", 80.0, 150_000, 200.0, 100.0);
        playerCar.setChassis(chassis);
        
        // Трансмиссия
        Transmission transmission = new Transmission("ZovAuto Sequential Pro", 70.0, 100_000, "sequential", 0.92);
        playerCar.setTransmission(transmission);
        
        // Подвеска
        Suspension suspension = new Suspension("ZovAuto Sport Susp", 60.0, 80_000, 0.25, new String[0]);
        playerCar.setSuspension(suspension);
        
        // Аэропакет
        AeroKit aeroKit = new AeroKit("ZovAuto Downforce Max", 50.0, 60_000, 80.0);
        playerCar.setAeroKit(aeroKit);
        
        // Шины
        Tire tires = new Tire("ZovAuto Ultra Soft", 55.0, 25_000, "ultra-soft", 0.75, 0.95);
        playerCar.setTires(tires);
        
        playerTeam.addCar(playerCar);

        // Пилот
        Pilot pilot = new Pilot("Max Verstappen Jr", 0.85, 0.75, 0.9, 500_000);
        playerTeam.addPilot(pilot);

        // Инженер
        Engineer engineer = new Engineer("Tech Genius Bob", 10, 100_000, "Двигатель");
        playerTeam.addStaff(engineer);
        
        // Механик
        Mechanic mechanic = new Mechanic("Fast Joe", 8, 50_000, "Пит-стоп");
        playerTeam.addStaff(mechanic);

        // Создаём ботов
        List<Team> botTeams = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Team botTeam = BotTeamGenerator.createRandomTeam(5_000_000);
            botTeams.add(botTeam);
        }

        // Создаём меню
        GameMenu gameMenu = new GameMenu();
        gameMenu.setPlayerTeam(playerTeam);
        gameMenu.setBotTeams(botTeams);

        System.out.println("Готово!");
        System.out.println("Команда: " + playerTeam.getName());
        System.out.println("Бюджет: " + (int) playerTeam.getBudget());
        System.out.println("Болид: " + playerCar.getName() + " (укомплектован: " + playerCar.isComplete() + ")");
        System.out.println("Пилот: " + pilot.getName());
        System.out.println("Инженеров: " + (playerTeam.hasEngineer() ? "Да" : "Нет"));
        System.out.println("Ботов: " + botTeams.size());
        System.out.println("\nМожно сразу начать гонку (пункт 1)!");

        return gameMenu;
    }

    /**
     * Основной метод для запуска debug-режима.
     */
    public static void main(String[] args) {
        GameMenu game = createDebugGame();
        game.start();
    }
}
