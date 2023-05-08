package com.example.profilingserver.model;

import java.util.List;

public class ResponseData {

    List<HeapSummaryData> heapSummaryDataList;
    List<CpuLoadData> cpuLoadDataList;
    GCSummary gcSummary;

    public ResponseData(List<HeapSummaryData> heapSummaryDataList, List<CpuLoadData> cpuLoadDataList, GCSummary gcSummary) {
        this.heapSummaryDataList = heapSummaryDataList;
        this.cpuLoadDataList = cpuLoadDataList;
        this.gcSummary = gcSummary;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "heapSummaryDataList=" + heapSummaryDataList +
                ", cpuLoadDataList=" + cpuLoadDataList +
                ", gcSummary=" + gcSummary +
                '}';
    }
}
