package com.example.profilingserver.service;

import com.example.profilingserver.model.*;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JFRExtractorService {

    public JFRExtractorService(){}


    public ResponseData extractDataFromJFR(String path) throws IOException {
        File file = new File(path);
        List<HeapSummaryData> heapSummaryDataList = new ArrayList<>();
        List<CpuLoadData> cpuLoadDataList = new ArrayList<>();
        List<GCPhasePauseData> gcPhasePauseDataList = new ArrayList<>();
        double young_max = 0,young_tot = 0,old_max = 0,old_tot = 0,all_max = 0,all_tot = 0,young_ct = 0,old_ct = 0,all_ct = 0,pause_max = 0,pause_tot = 0,pause_ct = 0;

        try (RecordingFile recordingFile = new RecordingFile(file.toPath())) {

            while (recordingFile.hasMoreEvents()) {
                RecordedEvent event = recordingFile.readEvent();

                if ("jdk.GCHeapSummary".equals(event.getEventType().getName()) ) {

                    String temp = event.getValue("heapSpace").toString();
                    String t1 =temp.substring(temp.indexOf("reservedSize")).split("=")[1];
                    String t2 = t1.substring(0,t1.length()-4).trim();
                    String[] t3 = t2.split(" ");
//                    reservedSize.add((t3[1].equals("GB")) ? Double.parseDouble(t3[0])*1024000000 : Double.parseDouble(t3[0])*1000000);
                    Object value = event.getValue("heapUsed");
//                    heapUsed.add((Long)value);
//                    startTime.add(event.getStartTime());
                    heapSummaryDataList.add(new HeapSummaryData((t3[1].equals("GB")) ? Double.parseDouble(t3[0])*1024000000 : Double.parseDouble(t3[0])*1000000,(Long)value,event.getStartTime().toEpochMilli()));

                }
//                else if (f == 0 && "jdk.GCConfiguration".equals(event.getEventType().getName()) ) {
//                    System.out.println(event+"\n");
//                    f=1;
//                }
//                else if ("jdk.GCHeapConfiguration".equals(event.getEventType().getName()) ) {
//                    System.out.println(event+"\n");
//                }
//                else if ("jdk.YoungGenerationConfiguration".equals(event.getEventType().getName()) ) {
//                    System.out.println(event+"\n");
//                }
//                else if ("jdk.GCSurvivorConfiguration".equals(event.getEventType().getName()) ) {
//                    System.out.println(event+"\n");
//                }
//                else if ("jdk.GCTLABConfiguration".equals(event.getEventType().getName()) ) {
//                    System.out.println(event+"\n");
//                }
                else if ("jdk.G1GarbageCollection".equals(event.getEventType().getName()) ) {

                    young_ct++;
                    young_tot+=event.getDuration().toMillis();
                    young_max = Math.max(event.getDuration().toMillis(),young_max);

                }
                else if ("jdk.GarbageCollection".equals(event.getEventType().getName()) ) {

                    all_ct++;
                    all_tot+=event.getDuration().toMillis();
                    all_max = Math.max(event.getDuration().toMillis(),all_max);

                }
                else if ("jdk.OldGarbageCollection".equals(event.getEventType().getName()) ) {

                    old_ct++;
                    old_tot+=event.getDuration().toMillis();
                    old_max = Math.max(event.getDuration().toMillis(),old_max);

                }
                else if ("jdk.GCPhasePause".equals(event.getEventType().getName()) ) {

//                    pauseDuration.add((double)event.getDuration().toMillis());
//                    pauseStartTime.add(event.getStartTime());
                    pause_ct++;
                    pause_tot+=event.getDuration().toMillis();
                    pause_max = Math.max(event.getDuration().toMillis(),pause_max);
                    gcPhasePauseDataList.add(new GCPhasePauseData(event.getStartTime().toEpochMilli(),event.getDuration().toMillis()));

                }
                else if ("jdk.CPULoad".equals(event.getEventType().getName()) ) {
//                    CpuStartTime.add(event.getStartTime());
//                    CpuJvmUser.add((Float) event.getValue("jvmUser")*100F);
//                    CpuJvmSystem.add((Float)event.getValue("jvmSystem")*100F);
//                    CpuMachineTotal.add((Float)event.getValue("machineTotal")*100F);
                    cpuLoadDataList.add(new CpuLoadData(event.getStartTime().toEpochMilli(),(Float) event.getValue("jvmUser")*100F,(Float)event.getValue("jvmSystem")*100F,(Float)event.getValue("machineTotal")*100F,(Float) event.getValue("jvmUser")*100F+(Float)event.getValue("jvmSystem")*100F));
                }
            }

        }
        return new ResponseData(heapSummaryDataList,cpuLoadDataList,new GCSummary(gcPhasePauseDataList,young_ct,young_tot,young_max,all_ct,all_tot,all_max,old_ct,old_tot,old_max,pause_ct,pause_tot,pause_max));
    }
}
