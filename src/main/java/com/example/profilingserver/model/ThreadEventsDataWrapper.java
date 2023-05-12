package com.example.profilingserver.model;

import java.util.List;

public class ThreadEventsDataWrapper {
    String name;
    List<ThreadEventsData> data;
//    String pointPlacement;
    String shape;
    int pointWidth,pointPadding;

    float pointPlacement;
//    dataLabels dataLabels;

    class dataLabels{
        boolean enabled;
        String format;

        public dataLabels(boolean enabled, String format) {
            this.enabled = enabled;
            this.format = format;
        }

        @Override
        public String toString() {
            return "dataLabels{" +
                    "enabled=" + enabled +
                    ", format='" + format + '\'' +
                    '}';
        }
    }

    public ThreadEventsDataWrapper(String name, List<ThreadEventsData> data) {
        this.name = name;
        this.data = data;
        this.shape = "bar";
        this.pointWidth=20;
        this.pointPadding=5;
        this.pointPlacement = 0.7f;
//        this.dataLabels = new dataLabels(true,data.get(0).state1);
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
