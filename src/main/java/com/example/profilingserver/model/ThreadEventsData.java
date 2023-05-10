package com.example.profilingserver.model;

public class ThreadEventsData {
    String name;
    double start;
    double end;
    String state;
    String color;

    public ThreadEventsData(String name, double start, double end, String state, String color) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.state = state;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ThreadEvents{" +
                "name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", state='" + state + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
