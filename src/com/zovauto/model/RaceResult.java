package com.zovauto.model;

/**
 * Класс результата гонки.
 * Хранит информацию о месте, времени и участнике.
 */
public class RaceResult implements Comparable<RaceResult> {
    private final String teamName;
    private final Car car;
    private final Pilot pilot;
    private final Track track;
    private final double lapTime;
    private int position;
    private final boolean hadIncident;
    private final String incidentComponent;

    public RaceResult(String teamName, Car car, Pilot pilot, Track track, double lapTime, int position, boolean hadIncident, String incidentComponent) {
        this.teamName = teamName;
        this.car = car;
        this.pilot = pilot;
        this.track = track;
        this.lapTime = lapTime;
        this.position = position;
        this.hadIncident = hadIncident;
        this.incidentComponent = incidentComponent;
    }

    public String getTeamName() {
        return teamName;
    }

    public Car getCar() {
        return car;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public Track getTrack() {
        return track;
    }

    public double getLapTime() {
        return lapTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isHadIncident() {
        return hadIncident;
    }

    public String getIncidentComponent() {
        return incidentComponent;
    }

    /**
     * Возвращает призовые деньги в зависимости от позиции.
     */
    public double getPrizeMoney() {
        if (position == 1) return 1_000_000;
        if (position == 2) return 500_000;
        if (position == 3) return 250_000;
        return 0;
    }

    @Override
    public int compareTo(RaceResult other) {
        return Double.compare(this.lapTime, other.lapTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Команда: ").append(teamName).append("\n");
        sb.append("Место: ").append(position).append("\n");
        sb.append("Время круга: ").append(String.format("%.3f", lapTime)).append(" сек\n");
        sb.append("Призовые: ").append((int) getPrizeMoney()).append("\n");
        if (hadIncident) {
            sb.append("СТАТУС: Произошёл инцидент! Сломан: ").append(incidentComponent).append("\n");
        }
        return sb.toString();
    }
}
