package com.example.profilingserver.model;

public class CpuLoadData {
    Long startTime;
    Float CpuJvmUserPercentage;
    Float CpuJvmSystemPercentage;
    Float CpuMachineTotalPercentage;
    Float TotalJVMPercentage;

    public CpuLoadData(Long startTime, Float cpuJvmUserPercentage, Float cpuJvmSystemPercentage, Float cpuMachineTotalPercentage, Float totalJVMPercentage) {
        this.startTime = startTime;
        CpuJvmUserPercentage = cpuJvmUserPercentage;
        CpuJvmSystemPercentage = cpuJvmSystemPercentage;
        CpuMachineTotalPercentage = cpuMachineTotalPercentage;
        TotalJVMPercentage = totalJVMPercentage;
    }

    @Override
    public String toString() {
        return "CpuLoadData{" +
                "startTime=" + startTime +
                ", CpuJvmUserPercentage=" + CpuJvmUserPercentage +
                ", CpuJvmSystemPercentage=" + CpuJvmSystemPercentage +
                ", CpuMachineTotalPercentage=" + CpuMachineTotalPercentage +
                ", TotalJVMPercentage=" + TotalJVMPercentage +
                '}';
    }
}
