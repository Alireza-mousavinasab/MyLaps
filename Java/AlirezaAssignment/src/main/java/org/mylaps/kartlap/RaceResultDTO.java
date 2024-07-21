package org.mylaps.kartlap;

import java.time.Duration;
import java.time.LocalTime;

public class RaceResultDTO {
    private Integer kart;
    private Duration lapDuration;
    private Integer lapNumber;
    private LocalTime lapStartTime;
    private LocalTime lapEndTime;

    public RaceResultDTO(Integer kart, Duration lapDuration, Integer lapNumber, LocalTime lapStartTime, LocalTime lapEndTime) {
        this.kart = kart;
        this.lapDuration = lapDuration;
        this.lapNumber = lapNumber;
        this.lapStartTime = lapStartTime;
        this.lapEndTime = lapEndTime;
    }

    public Integer getKart() {
        return kart;
    }

    public void setKart(Integer kart) {
        this.kart = kart;
    }

    public String getLapDuration() {
        long seconds = lapDuration.getSeconds();
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;

        if (hours > 0) {
            return String.format("%d hours %d minutes %d seconds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%d minutes %d seconds", minutes, seconds);
        } else {
            return String.format("%d seconds", seconds);
        }
    }

    public void setLapDuration(Duration lapDuration) {
        this.lapDuration = lapDuration;
    }

    public Integer getLapNumber() {
        return lapNumber;
    }

    public void setLapNumber(Integer lapNumber) {
        this.lapNumber = lapNumber;
    }

    public LocalTime getLapStartTime() {
        return lapStartTime;
    }

    public void setLapStartTime(LocalTime lapStartTime) {
        this.lapStartTime = lapStartTime;
    }

    public LocalTime getLapEndTime() {
        return lapEndTime;
    }

    public void setLapEndTime(LocalTime lapEndTime) {
        this.lapEndTime = lapEndTime;
    }

    @Override
    public String toString() {
        return "RaceResultDTO{" +
                "kart=" + kart +
                ", lapDuration=" + lapDuration +
                ", lapNumber=" + lapNumber +
                ", lapStartTime=" + lapStartTime +
                ", lapEndTime=" + lapEndTime +
                '}';
    }
}
