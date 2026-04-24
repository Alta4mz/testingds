package com.zovauto.service;

import com.zovauto.model.*;
import com.zovauto.util.ComponentType;
import com.zovauto.util.TrackSegmentType;
import com.zovauto.util.WeatherCondition;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Сервис генерации ботов-команд.
 * Создаёт команды с валидными болидами и персоналом в рамках бюджета.
 */
public class BotTeamGenerator {

    private static final String[] BOT_TEAM_NAMES = {
            "Red Dragon Racing", "Silver Arrow", "Blue Thunder",
            "Green Lightning", "Black Stallion", "White Wolf"
    };

    private BotTeamGenerator() {
        // Утилитный класс
    }

    /**
     * Создаёт случайную команду бота с указанным бюджетом.
     */
    public static Team createRandomTeam(double budget) {
        String name = BOT_TEAM_NAMES[(int) (Math.random() * BOT_TEAM_NAMES.length)];
        Team team = new Team(name, budget);
        team.setBot(true);

        // Генерируем бюджет на компоненты (примерно 80% от общего)
        double componentBudget = budget * 0.8;
        double remainingBudget = componentBudget;

        // Генерируем компоненты по порядку с проверкой совместимости
        Chassis chassis = generateRandomChassis(remainingBudget * 0.25);
        if (chassis != null) remainingBudget -= chassis.getPrice();

        Engine engine = generateRandomEngine(remainingBudget * 0.3, chassis);
        if (engine != null) remainingBudget -= engine.getPrice();

        Transmission transmission = generateRandomTransmission(remainingBudget * 0.2, engine);
        if (transmission != null) remainingBudget -= transmission.getPrice();

        Suspension suspension = generateRandomSuspension(remainingBudget * 0.15, chassis);
        if (suspension != null) remainingBudget -= suspension.getPrice();

        AeroKit aeroKit = generateRandomAeroKit(remainingBudget * 0.1);
        if (aeroKit != null) remainingBudget -= aeroKit.getPrice();

        Tire tire = generateRandomTire(remainingBudget * 0.1);
        if (tire != null) remainingBudget -= tire.getPrice();

        // Собираем болид если все компоненты созданы
        if (CompatibilityChecker.hasAllComponents(engine, chassis, transmission, suspension, aeroKit, tire)) {
            Car car = new Car(name + " Car #1");
            car.setEngine(engine);
            car.setChassis(chassis);
            car.setTransmission(transmission);
            car.setSuspension(suspension);
            car.setAeroKit(aeroKit);
            car.setTires(tire);
            team.addCar(car);
        }

        // Нанимаем пилота
        Pilot pilot = generateRandomPilot(budget * 0.15);
        if (pilot != null) {
            team.addPilot(pilot);
        }

        // Нанимаем инженера
        Engineer engineer = generateRandomEngineer(budget * 0.05);
        if (engineer != null) {
            team.addStaff(engineer);
        }

        return team;
    }

    private static Chassis generateRandomChassis(double budget) {
        String[] names = {"Carbon Mono", "Aluminum Frame", "Steel Core"};
        String name = names[(int) (Math.random() * names.length)];
        double maxWeight = 150 + Math.random() * 100;
        double baseWeight = 80 + Math.random() * 40;
        double performance = 50 + Math.random() * 50;
        double price = Math.min(budget, 50_000 + Math.random() * 100_000);
        return new Chassis(name, performance, price, maxWeight, baseWeight);
    }

    private static Engine generateRandomEngine(double budget, Chassis chassis) {
        String[] names = {"V8 Power", "V6 Turbo", "V10 Beast"};
        String[] types = {"V8", "V6", "V10"};
        int idx = (int) (Math.random() * names.length);
        String name = names[idx];
        String type = types[idx];
        double weight = 100 + Math.random() * 80;
        double maxSpeed = 300 + Math.random() * 100;
        double performance = 80 + Math.random() * 40;
        double price = Math.min(budget, 100_000 + Math.random() * 200_000);
        return new Engine(name, performance, price, maxSpeed, weight, type);
    }

    private static Transmission generateRandomTransmission(double budget, Engine engine) {
        String[] names = {"Seq Pro", "CVT Plus", "Manual Race"};
        String[] types = {"sequential", "cvt", "manual"};
        int idx = (int) (Math.random() * names.length);
        String name = names[idx];
        String type = types[idx];
        double efficiency = 0.85 + Math.random() * 0.1;
        double performance = 40 + Math.random() * 30;
        double price = Math.min(budget, 30_000 + Math.random() * 70_000);
        return new Transmission(name, performance, price, type, efficiency);
    }

    private static Suspension generateRandomSuspension(double budget, Chassis chassis) {
        String[] names = {"Sport Susp", "Race Setup", "Pro Handling"};
        String name = names[(int) (Math.random() * names.length)];
        double handlingBonus = 0.1 + Math.random() * 0.2;
        double performance = 30 + Math.random() * 30;
        double price = Math.min(budget, 20_000 + Math.random() * 50_000);
        // Совместима с любым шасси (пустой массив = универсальная)
        return new Suspension(name, performance, price, handlingBonus, new String[0]);
    }

    private static AeroKit generateRandomAeroKit(double budget) {
        String[] names = {"Aero Pack V1", "Downforce Pro", "Wind Master"};
        String name = names[(int) (Math.random() * names.length)];
        double downforce = 50 + Math.random() * 50;
        double performance = 20 + Math.random() * 30;
        double price = Math.min(budget, 15_000 + Math.random() * 40_000);
        return new AeroKit(name, performance, price, downforce);
    }

    private static Tire generateRandomTire(double budget) {
        String[] names = {"Soft Compound", "Medium Grip", "Hard Durability"};
        String[] compounds = {"soft", "medium", "hard"};
        int idx = (int) (Math.random() * names.length);
        String name = names[idx];
        String compound = compounds[idx];
        double dryGrip = 0.7 + Math.random() * 0.25;
        double wetGrip = 0.5 + Math.random() * 0.3;
        double performance = 25 + Math.random() * 35;
        double price = Math.min(budget, 5_000 + Math.random() * 20_000);
        return new Tire(name, performance, price, compound, wetGrip, dryGrip);
    }

    private static Pilot generateRandomPilot(double budget) {
        String[] names = {"Max Racer", "John Speed", "Alex Corner", "Sam Straight"};
        String name = names[(int) (Math.random() * names.length)];
        double accuracy = 0.5 + Math.random() * 0.45;
        double aggression = 0.5 + Math.random() * 0.45;
        double experience = 0.4 + Math.random() * 0.5;
        double price = Math.min(budget, 50_000 + Math.random() * 150_000);
        return new Pilot(name, accuracy, aggression, experience, price);
    }

    private static Engineer generateRandomEngineer(double budget) {
        String[] names = {"Tech Mike", "Engine Bob", "Data Sue"};
        String[] specs = {"Двигатель", "Шасси", "Телеметрия"};
        String name = names[(int) (Math.random() * names.length)];
        String spec = specs[(int) (Math.random() * specs.length)];
        int qualification = 3 + (int) (Math.random() * 7);
        double salary = Math.min(budget, 10_000 + Math.random() * 30_000);
        return new Engineer(name, qualification, salary, spec);
    }

    /**
     * Генерирует список трасс для выбора.
     */
    public static List<Track> generateAvailableTracks() {
        List<Track> tracks = new ArrayList<>();

        TrackSegmentType[] segments1 = {
                TrackSegmentType.STRAIGHT, TrackSegmentType.CORNER, TrackSegmentType.CORNER,
                TrackSegmentType.STRAIGHT, TrackSegmentType.UPHILL, TrackSegmentType.DOWNHILL,
                TrackSegmentType.CORNER, TrackSegmentType.STRAIGHT
        };
        tracks.add(new Track("Monza Circuit", 5.8, Arrays.asList(segments1), WeatherCondition.DRY));

        TrackSegmentType[] segments2 = {
                TrackSegmentType.CORNER, TrackSegmentType.CORNER, TrackSegmentType.STRAIGHT,
                TrackSegmentType.CORNER, TrackSegmentType.UPHILL, TrackSegmentType.CORNER,
                TrackSegmentType.DOWNHILL, TrackSegmentType.CORNER
        };
        tracks.add(new Track("Monaco Street", 3.3, Arrays.asList(segments2), WeatherCondition.CLOUDY));

        TrackSegmentType[] segments3 = {
                TrackSegmentType.STRAIGHT, TrackSegmentType.STRAIGHT, TrackSegmentType.CORNER,
                TrackSegmentType.UPHILL, TrackSegmentType.UPHILL, TrackSegmentType.DOWNHILL,
                TrackSegmentType.STRAIGHT, TrackSegmentType.CORNER
        };
        tracks.add(new Track("Spa-Francorchamps", 7.0, Arrays.asList(segments3), WeatherCondition.RAIN));

        // Обновляем погоду на случайную для каждой трассы
        for (Track track : tracks) {
            WeatherCondition weather = RaceCalculator.generateRandomWeather();
            // Создаём новую трассу с обновлённой погодой (т.к. поле final)
            tracks.set(tracks.indexOf(track), 
                    new Track(track.getName(), track.getLength(), track.getSegments(), weather));
        }

        return tracks;
    }
}
