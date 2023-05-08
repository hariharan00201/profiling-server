package com.example.profilingserver.model;

public class HeapSummaryData {
    Double reservedSize;
    Long heapUsed;
    Long startTime;

    public HeapSummaryData(Double reservedSize, Long heapUsed, Long startTime) {
        this.reservedSize = reservedSize;
        this.heapUsed = heapUsed;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "HeapSummaryData{" +
                "reservedSize=" + reservedSize +
                ", heapUsed=" + heapUsed +
                ", startTime=" + startTime +
                '}';
    }
}
