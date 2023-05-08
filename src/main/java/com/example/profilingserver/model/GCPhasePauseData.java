package com.example.profilingserver.model;

public class GCPhasePauseData {

    Long startTime;
    double duration;

    public GCPhasePauseData(Long startTime, double duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "GCPhasePauseData{" +
                "startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
