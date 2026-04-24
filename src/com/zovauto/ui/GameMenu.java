package com.zovauto.ui;

import com.zovauto.model.*;
import com.zovauto.service.*;
import com.zovauto.util.TrackSegmentType;
import com.zovauto.util.WeatherCondition;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Консольное меню игры.
 * Реализует главный цикл и обработку ввода пользователя.
 */
public class GameMenu {
    private final Scanner scanner;
    private Team playerTeam;
    // Обоснование коллекции: List для хранения результатов гонок игрока
    private final List<RaceResult> playerRaceHistory;
    // Обоснование коллекции: List для хранения команд ботов
    private final List<Team> botTeams;
    // Обоснование коллекции: List для хранения доступных компонентов на рынке
    private final List<Component> marketComponents;
    // Обоснование коллекции: List для хранения доступных пилотов
    private final List<Pilot> pilotMarket;
    // Обоснование коллекции: List для хранения доступных сотрудников
    private final List<Staff> staffMarket;

    public GameMenu() {
        this.scanner = new Scanner(System.in);
        this.playerRaceHistory = new ArrayList<>();
        this.botTeams = new ArrayList<>();
        this.marketComponents = new ArrayList<>();
        this.pilotMarket = new ArrayList<>();
        this.staffMarket = new ArrayList<>();
    }

    /**
     * Запускает главное меню игры.
     */
    public void start() {
        System.out.println("=== STRATEGIC RACING TEAM SIMULATOR ===");
        System.out.println("Добро пожаловать, Директор Команды!");
        
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = readIntChoice(1, 11);
            exit = handleMenuChoice(choice);
        }
        System.out.println("Спасибо за игру! До свидания.");
    }

    private void displayMainMenu() {
        System.out.println("\n========== ГЛАВНОЕ МЕНЮ ==========");
        System.out.println("Бюджет команды: " + (int) playerTeam.getBudget());
        System.out.println("1) Начать гонку");
        System.out.println("2) Купить комплектующие");
        System.out.println("3) Собрать болид");
        System.out.println("4) Нанять команду");
        System.out.println("5) Нанять пилота");
        System.out.println("6) Просмотреть болиды");
        System.out.println("7) Просмотреть пилотов");
        System.out.println("8) Просмотреть статистику гонок");
        System.out.println("9) Просмотреть другие команды");
        System.out.println("10) Просмотреть другие результаты");
        System.out.println("11) Выход");
        System.out.print("Выберите пункт: ");
    }

    private boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                startRaceMenu();
                break;
            case 2:
                buyComponentsMenu();
                break;
            case 3:
                assembleCarMenu();
                break;
            case 4:
                hireStaffMenu();
                break;
            case 5:
                hirePilotMenu();
                break;
            case 6:
                viewCars();
                break;
            case 7:
                viewPilots();
                break;
            case 8:
                viewRaceStatistics();
                break;
            case 9:
                viewOtherTeams();
                break;
            case 10:
                viewOtherResults();
                break;
            case 11:
                return true;
            default:
                System.out.println("Неверный выбор!");
        }
        return false;
    }

    private int readIntChoice(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.print("Введите число от " + min + " до " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    private void startRaceMenu() {
        System.out.println("\n=== НАЧАТЬ ГОНКУ ===");

        // Проверка готовности
        if (playerTeam.getCars().isEmpty()) {
            System.out.println("У вас нет болидов! Сначала соберите болид (пункт 3).");
            return;
        }

        Car selectedCar = selectCarForRace();
        if (selectedCar == null) return;

        if (!selectedCar.isComplete()) {
            System.out.println("Болид не укомплектован! Missing: " + selectedCar.getMissingComponents());
            return;
        }

        if (playerTeam.getPilots().isEmpty()) {
            System.out.println("У вас нет пилотов! Наймите пилота (пункт 5).");
            return;
        }

        Pilot selectedPilot = selectPilotForRace();
        if (selectedPilot == null) return;

        if (!playerTeam.hasEngineer()) {
            System.out.println("У вас нет инженеров! Наймите хотя бы одного (пункт 4).");
            return;
        }

        // Выбор трассы
        List<Track> tracks = BotTeamGenerator.generateAvailableTracks();
        System.out.println("\nДоступные трассы:");
        for (int i = 0; i < tracks.size(); i++) {
            Track t = tracks.get(i);
            System.out.println((i + 1) + ") " + t.getName() + " - " + t.getLength() + " км, Погода: " + t.getWeather().getDisplayName());
        }
        System.out.print("Выберите трассу: ");
        int trackChoice = readIntChoice(1, tracks.size());
        Track selectedTrack = tracks.get(trackChoice - 1);

        System.out.println("\n" + selectedTrack);

        // Подтверждение
        System.out.print("Запустить гонку? (1=Да, 2=Нет): ");
        int confirm = readIntChoice(1, 2);
        if (confirm != 1) return;

        // Проверка износа
        boolean ignoredWarning = false;
        if (selectedCar.hasHighWearComponents()) {
            System.out.println("\nВНИМАНИЕ! Компоненты с износом > 50%:");
            for (Component c : selectedCar.getHighWearComponents()) {
                System.out.println("  - " + c);
            }
            System.out.print("Продолжить? (1=Да/Игнорировать, 2=Нет/Ремонт): ");
            int wearChoice = readIntChoice(1, 2);
            if (wearChoice == 1) {
                ignoredWarning = true;
            } else {
                repairComponents(selectedCar);
                return;
            }
        }

        // Симуляция гонки
        simulateRace(selectedCar, selectedPilot, selectedTrack, ignoredWarning);
    }

    private Car selectCarForRace() {
        List<Car> cars = playerTeam.getCars();
        if (cars.size() == 1) {
            return cars.get(0);
        }

        System.out.println("\nВыберите болид:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println((i + 1) + ") " + cars.get(i).getName());
        }
        System.out.print("Выбор: ");
        int choice = readIntChoice(1, cars.size());
        return cars.get(choice - 1);
    }

    private Pilot selectPilotForRace() {
        List<Pilot> pilots = playerTeam.getPilots();
        if (pilots.size() == 1) {
            return pilots.get(0);
        }

        System.out.println("\nВыберите пилота:");
        for (int i = 0; i < pilots.size(); i++) {
            System.out.println((i + 1) + ") " + pilots.get(i).getName());
        }
        System.out.print("Выбор: ");
        int choice = readIntChoice(1, pilots.size());
        return pilots.get(choice - 1);
    }

    private void simulateRace(Car car, Pilot pilot, Track track, boolean ignoredWarning) {
        System.out.println("\n=== СИМУЛЯЦИЯ ГОНКИ ===");
        
        // Подготовка результатов всех команд
        List<RaceResult> allResults = new ArrayList<>();

        // Результат игрока
        double playerTime = RaceCalculator.calculateLapTime(car, pilot, playerTeam, track);
        boolean hadIncident = false;
        String incidentComponent = null;

        // Проверка инцидента
        if (ignoredWarning && RaceCalculator.checkIncidentOccurrence(true)) {
            hadIncident = true;
            Component broken = car.breakRandomComponent();
            incidentComponent = broken != null ? broken.getName() : "Неизвестно";
            System.out.println("!!! ПРОИЗОШЁЛ ИНЦИДЕНТ! Сломан: " + incidentComponent + " !!!");
        }

        RaceResult playerResult = new RaceResult(
                playerTeam.getName(), car, pilot, track,
                playerTime, 0, hadIncident, incidentComponent
        );
        allResults.add(playerResult);

        // Результаты ботов
        for (Team botTeam : botTeams) {
            if (!botTeam.getCars().isEmpty() && !botTeam.getPilots().isEmpty()) {
                Car botCar = botTeam.getCars().get(0);
                Pilot botPilot = botTeam.getPilots().get(0);
                double botTime = RaceCalculator.calculateLapTime(botCar, botPilot, botTeam, track);
                
                boolean botHadIncident = false;
                String botIncidentComponent = null;
                if (botCar.hasHighWearComponents() && Math.random() < 0.3) {
                    botHadIncident = true;
                    Component broken = botCar.breakRandomComponent();
                    botIncidentComponent = broken != null ? broken.getName() : null;
                }

                RaceResult botResult = new RaceResult(
                        botTeam.getName(), botCar, botPilot, track,
                        botTime, 0, botHadIncident, botIncidentComponent
                );
                allResults.add(botResult);
            }
        }

        // Сортировка по времени
        allResults.sort(null);

        // Присвоение позиций
        for (int i = 0; i < allResults.size(); i++) {
            allResults.get(i).setPosition(i + 1);
        }

        // Применение износа
        RaceCalculator.applyPostRaceWear(car);
        for (Team botTeam : botTeams) {
            for (Car botCar : botTeam.getCars()) {
                RaceCalculator.applyPostRaceWear(botCar);
            }
        }

        // Нахождение результата игрока
        RaceResult finalPlayerResult = null;
        for (RaceResult r : allResults) {
            if (r.getTeamName().equals(playerTeam.getName())) {
                finalPlayerResult = r;
                break;
            }
        }

        // Вывод результатов
        System.out.println("\n=== РЕЗУЛЬТАТЫ ГОНКИ ===");
        for (RaceResult r : allResults) {
            String medal = "";
            if (r.getPosition() == 1) medal = "🥇 ";
            else if (r.getPosition() == 2) medal = "🥈 ";
            else if (r.getPosition() == 3) medal = "🥉 ";
            
            System.out.println(medal + r.getPosition() + ". " + r.getTeamName() + 
                    " - " + String.format("%.3f", r.getLapTime()) + " сек" +
                    (r.isHadIncident() ? " [ИНЦИДЕНТ]" : ""));
        }

        // Призовые
        double prizeMoney = finalPlayerResult.getPrizeMoney();
        if (prizeMoney > 0) {
            playerTeam.addBudget(prizeMoney);
            System.out.println("\nПризовые: +" + (int) prizeMoney);
        }

        // Сохранение в историю
        playerRaceHistory.add(finalPlayerResult);

        // Удаление сломанных компонентов
        playerTeam.removeBrokenComponents();
        for (Team botTeam : botTeams) {
            botTeam.removeBrokenComponents();
        }
    }

    private void repairComponents(Car car) {
        if (!playerTeam.hasEngineer()) {
            System.out.println("У вас нет инженеров для ремонта!");
            return;
        }

        System.out.println("\n=== РЕМОНТ КОМПОНЕНТОВ ===");
        double totalRepairCost = 0;

        for (Component c : car.getHighWearComponents()) {
            double repairCost = c.getPrice() * 0.1;
            System.out.println(c.getName() + " - Ремонт: " + (int) repairCost);
            totalRepairCost += repairCost;
        }

        System.out.println("Общая стоимость: " + (int) totalRepairCost);
        System.out.print("Выполнить ремонт? (1=Да, 2=Нет): ");
        int choice = readIntChoice(1, 2);

        if (choice == 1) {
            if (playerTeam.spendBudget(totalRepairCost)) {
                for (Component c : car.getHighWearComponents()) {
                    c.fullyRepair();
                }
                System.out.println("Ремонт выполнен!");
            } else {
                System.out.println("Недостаточно средств!");
            }
        }
    }

    private void buyComponentsMenu() {
        System.out.println("\n=== РЫНОК КОМПОНЕНТОВ ===");
        generateMarketComponents();

        while (true) {
            System.out.println("\nБюджет: " + (int) playerTeam.getBudget());
            System.out.println("1) Двигатели");
            System.out.println("2) Шасси");
            System.out.println("3) Трансмиссии");
            System.out.println("4) Подвески");
            System.out.println("5) Аэропакеты");
            System.out.println("6) Шины");
            System.out.println("7) Назад");
            System.out.print("Выбор: ");

            int choice = readIntChoice(1, 7);
            if (choice == 7) break;

            showComponentMarket(choice);
        }
    }

    private void generateMarketComponents() {
        marketComponents.clear();
        // Генерируем случайные компоненты для рынка
        for (int i = 0; i < 3; i++) {
            marketComponents.add(new Engine("Engine V8-" + i, 80 + Math.random() * 40, 
                    100_000 + Math.random() * 100_000, 350 + Math.random() * 50, 
                    120 + Math.random() * 50, "V8"));
            marketComponents.add(new Chassis("Chassis Carbon-" + i, 50 + Math.random() * 50,
                    50_000 + Math.random() * 100_000, 180 + Math.random() * 50, 
                    90 + Math.random() * 30));
            marketComponents.add(new Transmission("Trans Seq-" + i, 40 + Math.random() * 30,
                    30_000 + Math.random() * 70_000, "sequential", 0.85 + Math.random() * 0.1));
            marketComponents.add(new Suspension("Susp Sport-" + i, 30 + Math.random() * 30,
                    20_000 + Math.random() * 50_000, 0.1 + Math.random() * 0.2, new String[0]));
            marketComponents.add(new AeroKit("Aero Pro-" + i, 20 + Math.random() * 30,
                    15_000 + Math.random() * 40_000, 50 + Math.random() * 50));
            marketComponents.add(new Tire("Tire Soft-" + i, 25 + Math.random() * 35,
                    5_000 + Math.random() * 20_000, "soft", 0.6 + Math.random() * 0.3, 
                    0.8 + Math.random() * 0.15));
        }
    }

    private void showComponentMarket(int category) {
        List<Component> filtered = new ArrayList<>();
        String categoryName = "";

        for (Component c : marketComponents) {
            boolean match = false;
            switch (category) {
                case 1: match = c.getType() == com.zovauto.util.ComponentType.ENGINE; categoryName = "Двигатели"; break;
                case 2: match = c.getType() == com.zovauto.util.ComponentType.CHASSIS; categoryName = "Шасси"; break;
                case 3: match = c.getType() == com.zovauto.util.ComponentType.TRANSMISSION; categoryName = "Трансмиссии"; break;
                case 4: match = c.getType() == com.zovauto.util.ComponentType.SUSPENSION; categoryName = "Подвески"; break;
                case 5: match = c.getType() == com.zovauto.util.ComponentType.AERO_KIT; categoryName = "Аэропакеты"; break;
                case 6: match = c.getType() == com.zovauto.util.ComponentType.TIRE; categoryName = "Шины"; break;
            }
            if (match) filtered.add(c);
        }

        System.out.println("\n=== " + categoryName + " ===");
        for (int i = 0; i < filtered.size(); i++) {
            Component c = filtered.get(i);
            System.out.println((i + 1) + ") " + c + " | Цена: " + (int) c.getPrice());
        }

        System.out.print("Купить (0=Отмена): ");
        int choice = readIntChoice(0, filtered.size());
        if (choice == 0) return;

        Component selected = filtered.get(choice - 1);
        if (playerTeam.spendBudget(selected.getPrice())) {
            System.out.println("Куплено: " + selected.getName());
            // Добавляем в инвентарь (упрощённо - создаём временный болид для хранения)
            storePurchasedComponent(selected);
        } else {
            System.out.println("Недостаточно средств!");
        }
    }

    // Обоснование коллекции: Map был бы лучше, но используем List+Car как временное хранилище
    private final List<Car> componentInventory = new ArrayList<>();

    private void storePurchasedComponent(Component component) {
        Car storage = new Car("Component Storage");
        switch (component.getType()) {
            case ENGINE: storage.setEngine((Engine) component); break;
            case CHASSIS: storage.setChassis((Chassis) component); break;
            case TRANSMISSION: storage.setTransmission((Transmission) component); break;
            case SUSPENSION: storage.setSuspension((Suspension) component); break;
            case AERO_KIT: storage.setAeroKit((AeroKit) component); break;
            case TIRE: storage.setTires((Tire) component); break;
        }
        componentInventory.add(storage);
    }

    private void assembleCarMenu() {
        System.out.println("\n=== СБОРКА БОЛИДА ===");
        
        if (componentInventory.isEmpty()) {
            System.out.println("У вас нет компонентов! Купите их на рынке (пункт 2).");
            return;
        }

        System.out.print("Название болида: ");
        String carName = scanner.nextLine().trim();
        if (carName.isEmpty()) carName = "Car #" + (playerTeam.getCars().size() + 1);

        Car newCar = new Car(carName);

        // Предлагаем компоненты из инвентаря
        System.out.println("\nДоступные компоненты:");
        Engine engine = selectComponentFromInventory(Engine.class);
        Chassis chassis = selectComponentFromInventory(Chassis.class);
        Transmission transmission = selectComponentFromInventory(Transmission.class);
        Suspension suspension = selectComponentFromInventory(Suspension.class);
        AeroKit aeroKit = selectComponentFromInventory(AeroKit.class);
        Tire tire = selectComponentFromInventory(Tire.class);

        // Проверка совместимости
        List<String> errors = CompatibilityChecker.checkCompatibility(
                engine, chassis, transmission, suspension, aeroKit, tire
        );

        if (!errors.isEmpty()) {
            System.out.println("\nОшибки совместимости:");
            for (String error : errors) {
                System.out.println("  - " + error);
            }
            System.out.print("Продолжить сборку? (1=Да, 2=Нет): ");
            int choice = readIntChoice(1, 2);
            if (choice == 2) return;
        }

        // Установка компонентов
        newCar.setEngine(engine);
        newCar.setChassis(chassis);
        newCar.setTransmission(transmission);
        newCar.setSuspension(suspension);
        newCar.setAeroKit(aeroKit);
        newCar.setTires(tire);

        playerTeam.addCar(newCar);
        System.out.println("\nБолид '" + carName + "' собран и добавлен в гараж!");
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> T selectComponentFromInventory(Class<T> type) {
        List<T> components = new ArrayList<>();
        for (Car storage : componentInventory) {
            T comp = null;
            if (type == Engine.class) comp = (T) storage.getEngine();
            else if (type == Chassis.class) comp = (T) storage.getChassis();
            else if (type == Transmission.class) comp = (T) storage.getTransmission();
            else if (type == Suspension.class) comp = (T) storage.getSuspension();
            else if (type == AeroKit.class) comp = (T) storage.getAeroKit();
            else if (type == Tire.class) comp = (T) storage.getTires();

            if (comp != null) components.add(comp);
        }

        if (components.isEmpty()) {
            System.out.println("Нет доступных компонентов типа " + type.getSimpleName());
            return null;
        }

        for (int i = 0; i < components.size(); i++) {
            System.out.println((i + 1) + ") " + components.get(i));
        }
        System.out.print("Выберите (0=Пропустить): ");
        int choice = readIntChoice(0, components.size());
        return choice == 0 ? null : components.get(choice - 1);
    }

    private void hireStaffMenu() {
        System.out.println("\n=== НАНЯТЬ КОМАНДУ ===");
        generateStaffMarket();

        System.out.println("Доступные сотрудники:");
        for (int i = 0; i < staffMarket.size(); i++) {
            Staff s = staffMarket.get(i);
            System.out.println((i + 1) + ") " + s);
        }

        System.out.print("Выберите (0=Отмена): ");
        int choice = readIntChoice(0, staffMarket.size());
        if (choice == 0) return;

        Staff selected = staffMarket.get(choice - 1);
        if (playerTeam.spendBudget(selected.getSalary())) {
            playerTeam.addStaff(selected);
            System.out.println("Нанят: " + selected.getName());
        } else {
            System.out.println("Недостаточно средств!");
        }
    }

    private void generateStaffMarket() {
        staffMarket.clear();
        String[] names = {"Tech Mike", "Engine Bob", "Data Sue", "Mechanic Joe"};
        String[] specs = {"Двигатель", "Шасси", "Телеметрия", "Пит-стоп"};
        for (int i = 0; i < 4; i++) {
            staffMarket.add(new Engineer(names[i], 3 + (int)(Math.random() * 7),
                    10_000 + Math.random() * 30_000, specs[i]));
            staffMarket.add(new Mechanic(names[i] + " Jr", 2 + (int)(Math.random() * 5),
                    5_000 + Math.random() * 15_000, specs[i]));
        }
    }

    private void hirePilotMenu() {
        System.out.println("\n=== НАНЯТЬ ПИЛОТА ===");
        generatePilotMarket();

        System.out.println("Доступные пилоты:");
        for (int i = 0; i < pilotMarket.size(); i++) {
            Pilot p = pilotMarket.get(i);
            System.out.println((i + 1) + ") " + p);
        }

        System.out.print("Выберите (0=Отмена): ");
        int choice = readIntChoice(0, pilotMarket.size());
        if (choice == 0) return;

        Pilot selected = pilotMarket.get(choice - 1);
        if (playerTeam.spendBudget(selected.getPrice())) {
            playerTeam.addPilot(selected);
            System.out.println("Нанят: " + selected.getName());
        } else {
            System.out.println("Недостаточно средств!");
        }
    }

    private void generatePilotMarket() {
        pilotMarket.clear();
        String[] names = {"Max Racer", "John Speed", "Alex Corner", "Sam Straight", "Dan Drift"};
        for (String name : names) {
            pilotMarket.add(new Pilot(name, 
                    0.5 + Math.random() * 0.45,
                    0.5 + Math.random() * 0.45,
                    0.4 + Math.random() * 0.5,
                    50_000 + Math.random() * 150_000));
        }
    }

    private void viewCars() {
        System.out.println("\n=== ВАШИ БОЛИДЫ ===");
        if (playerTeam.getCars().isEmpty()) {
            System.out.println("У вас пока нет болидов.");
            return;
        }
        for (Car car : playerTeam.getCars()) {
            System.out.println(car);
        }
    }

    private void viewPilots() {
        System.out.println("\n=== ВАШИ ПИЛОТЫ ===");
        if (playerTeam.getPilots().isEmpty()) {
            System.out.println("У вас пока нет пилотов.");
            return;
        }
        for (Pilot pilot : playerTeam.getPilots()) {
            System.out.println(pilot);
        }
    }

    private void viewRaceStatistics() {
        System.out.println("\n=== СТАТИСТИКА ГОНОК ===");
        if (playerRaceHistory.isEmpty()) {
            System.out.println("Вы ещё не участвовали в гонках.");
            return;
        }

        int totalRaces = playerRaceHistory.size();
        int wins = 0, podiums = 0;
        double totalPrize = 0;

        for (RaceResult r : playerRaceHistory) {
            if (r.getPosition() == 1) wins++;
            if (r.getPosition() <= 3) podiums++;
            totalPrize += r.getPrizeMoney();
        }

        System.out.println("Всего гонок: " + totalRaces);
        System.out.println("Побед: " + wins);
        System.out.println("Подиумов: " + podiums);
        System.out.println("Всего призовых: " + (int) totalPrize);

        System.out.println("\nПоследние результаты:");
        int showCount = Math.min(5, playerRaceHistory.size());
        for (int i = playerRaceHistory.size() - showCount; i < playerRaceHistory.size(); i++) {
            RaceResult r = playerRaceHistory.get(i);
            System.out.println("  " + r.getPosition() + ". " + r.getTrack().getName() + 
                    " - " + String.format("%.3f", r.getLapTime()) + " сек");
        }
    }

    private void viewOtherTeams() {
        System.out.println("\n=== ДРУГИЕ КОМАНДЫ ===");
        if (botTeams.isEmpty()) {
            System.out.println("Нет других команд.");
            return;
        }
        for (Team team : botTeams) {
            System.out.println(team);
        }
    }

    private void viewOtherResults() {
        System.out.println("\n=== РЕЗУЛЬТАТЫ ДРУГИХ КОМАНД ===");
        System.out.println("(Отображаются после гонок)");
        // В полной версии можно хранить историю результатов ботов
    }

    public void setPlayerTeam(Team playerTeam) {
        this.playerTeam = playerTeam;
    }

    public void setBotTeams(List<Team> botTeams) {
        this.botTeams.clear();
        this.botTeams.addAll(botTeams);
    }

    public List<RaceResult> getPlayerRaceHistory() {
        return new ArrayList<>(playerRaceHistory);
    }
}
