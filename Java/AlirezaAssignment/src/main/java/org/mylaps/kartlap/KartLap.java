package org.mylaps.kartlap;

import java.time.LocalTime;

public class KartLap {
    private Integer kart;
    private LocalTime passingTime;

    public KartLap(Integer kart, LocalTime passingTime) {
        this.kart = kart;
        this.passingTime = passingTime;
    }

    public Integer getKart() {
        return kart;
    }

    public void setKart(Integer kart) {
        this.kart = kart;
    }

    public LocalTime getPassingTime() {
        return passingTime;
    }

    public void setPassingTime(LocalTime passingTime) {
        this.passingTime = passingTime;
    }
}