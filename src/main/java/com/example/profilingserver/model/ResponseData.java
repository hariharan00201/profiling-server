package com.example.profilingserver.model;

import java.util.List;
import java.util.Map;

public class ResponseData {

    List<HeapSummaryData> heapSummaryDataList;
    List<CpuLoadData> cpuLoadDataList;
    GCSummary gcSummary;
    List<ThreadEventsDataWrapper> threadEventsDataWrapperList;

    public ResponseData(List<HeapSummaryData> heapSummaryDataList, List<CpuLoadData> cpuLoadDataList, GCSummary gcSummary, List<ThreadEventsDataWrapper> threadEventsDataList) {
        this.heapSummaryDataList = heapSummaryDataList;
        this.cpuLoadDataList = cpuLoadDataList;
        this.gcSummary = gcSummary;
        this.threadEventsDataWrapperList = threadEventsDataList;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "heapSummaryDataList=" + heapSummaryDataList +
                ", cpuLoadDataList=" + cpuLoadDataList +
                ", gcSummary=" + gcSummary +
                ", threadEventsList=" + threadEventsDataWrapperList +
                '}';
    }
}
