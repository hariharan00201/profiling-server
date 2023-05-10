package com.example.profilingserver.service;

import com.example.profilingserver.model.*;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JFRExtractorService {

    public JFRExtractorService(){}

    void populateColorMap(Map<String,String> colorMap){
        colorMap.put("Blocked","purple");
        colorMap.put("Inflate","blue");
        colorMap.put("Wait","black");
        colorMap.put("Park","green");
        colorMap.put("Sleep","orange");
        colorMap.put("Read","red");
        colorMap.put("Write","yellow");

    }


    public ResponseData extractDataFromJFR(String path) throws IOException {
        File file = new File(path);
        List<HeapSummaryData> heapSummaryDataList = new ArrayList<>();
        List<CpuLoadData> cpuLoadDataList = new ArrayList<>();
        List<GCPhasePauseData> gcPhasePauseDataList = new ArrayList<>();
        List<ThreadEventsDataWrapper> threadEventsDataWrapperList = new ArrayList<>();
        Map<String,String> colorMap = new HashMap<>();
        Map<String,List<ThreadEventsData>> threadDataMap = new HashMap<>();
        populateColorMap(colorMap);
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
                else if ("jdk.JavaMonitorEnter".equals(event.getEventType().getName()) ) {
                    List<ThreadEventsData> temp = threadDataMap.getOrDefault(event.getThread().getOSName(),new ArrayList<>());
                    temp.add(new ThreadEventsData(event.getThread().getOSName(),event.getStartTime().toEpochMilli(),event.getEndTime().toEpochMilli(),"Blocked",colorMap.get("Blocked")));
                    threadDataMap.put(event.getThread().getOSName(),temp);
//                    threadEventsDataList.add();
                }
                else if ("jdk.JavaMonitorInflate".equals(event.getEventType().getName()) ) {
                    List<ThreadEventsData> temp = threadDataMap.getOrDefault(event.getThread().getOSName(),new ArrayList<>());
                    temp.add(new ThreadEventsData(event.getThread().getOSName(),event.getStartTime().toEpochMilli(),event.getEndTime().toEpochMilli(),"Inflate",colorMap.get("Inflate")));
                    threadDataMap.put(event.getThread().getOSName(),temp);
                }
                else if ("jdk.JavaMonitorWait".equals(event.getEventType().getName()) ) {
                    List<ThreadEventsData> temp = threadDataMap.getOrDefault(event.getThread().getOSName(),new ArrayList<>());
                    temp.add(new ThreadEventsData(event.getThread().getOSName(),event.getStartTime().toEpochMilli(),event.getEndTime().toEpochMilli(),"Wait",colorMap.get("Wait")));
                    threadDataMap.put(event.getThread().getOSName(),temp);
                }
                else if ("jdk.ThreadPark".equals(event.getEventType().getName()) ) {
                    List<ThreadEventsData> temp = threadDataMap.getOrDefault(event.getThread().getOSName(),new ArrayList<>());
                    temp.add(new ThreadEventsData(event.getThread().getOSName(),event.getStartTime().toEpochMilli(),event.getEndTime().toEpochMilli(),"Park",colorMap.get("Park")));
                    threadDataMap.put(event.getThread().getOSName(),temp);
                }
                else if ("jdk.ThreadSleep".equals(event.getEventType().getName()) ) {
                    List<ThreadEventsData> temp = threadDataMap.getOrDefault(event.getThread().getOSName(),new ArrayList<>());
                    temp.add(new ThreadEventsData(event.getThread().getOSName(),event.getStartTime().toEpochMilli(),event.getEndTime().toEpochMilli(),"Sleep",colorMap.get("Sleep")));
                    threadDataMap.put(event.getThread().getOSName(),temp);
                }
                else if ("jdk.SocketRead".equals(event.getEventType().getName()) ) {
                    List<ThreadEventsData> temp = threadDataMap.getOrDefault(event.getThread().getOSName(),new ArrayList<>());
                    temp.add(new ThreadEventsData(event.getThread().getOSName(),event.getStartTime().toEpochMilli(),event.getEndTime().toEpochMilli(),"Read",colorMap.get("Read")));
                    threadDataMap.put(event.getThread().getOSName(),temp);
                }
                else if ("jdk.SocketWrite".equals(event.getEventType().getName()) ) {
                    List<ThreadEventsData> temp = threadDataMap.getOrDefault(event.getThread().getOSName(),new ArrayList<>());
                    temp.add(new ThreadEventsData(event.getThread().getOSName(),event.getStartTime().toEpochMilli(),event.getEndTime().toEpochMilli(),"Write",colorMap.get("Write")));
                    threadDataMap.put(event.getThread().getOSName(),temp);
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

        for(String a: threadDataMap.keySet()){
            threadEventsDataWrapperList.add(new ThreadEventsDataWrapper(a,threadDataMap.get(a)));
        }

        return new ResponseData(heapSummaryDataList,cpuLoadDataList,new GCSummary(gcPhasePauseDataList,young_ct,young_tot,young_max,all_ct,all_tot,all_max,old_ct,old_tot,old_max,pause_ct,pause_tot,pause_max), threadEventsDataWrapperList);
    }
}
