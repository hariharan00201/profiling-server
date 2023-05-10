package com.example.profilingserver.model;

import java.util.List;

public class ThreadEventsDataWrapper {
    String name;
    List<ThreadEventsData> data;
//    String pointPlacement;
    float y;

    public ThreadEventsDataWrapper(String name, List<ThreadEventsData> data) {
        this.name = name;
        this.data = data;
        this.y = 1f;
//        this.pointPlacement = pointPlacement;
    }

    @Override
    public String toString() {
        return "ThreadEventsDataWrapper{" +
                "name='" + name + '\'' +
                ", data=" + data +
//                ", pointPlacement=" + pointPlacement +
                '}';
    }
}
