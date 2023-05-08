package com.example.profilingserver.model;

import java.util.List;

public class GCSummary {
    List<GCPhasePauseData> gcPhasePauseDataList;
    double young_ct;
    double young_tot;
    double young_max;

    double all_ct;
    double all_tot;
    double all_max;

    double old_ct;
    double old_tot;
    double old_max;

    double pause_ct;
    double pause_tot;
    double pause_max;

    public GCSummary(List<GCPhasePauseData> gcPhasePauseDataList, double young_ct, double young_tot, double young_max, double all_ct, double all_tot, double all_max, double old_ct, double old_tot, double old_max, double pause_ct, double pause_tot, double pause_max) {
        this.gcPhasePauseDataList = gcPhasePauseDataList;
        this.young_ct = young_ct;
        this.young_tot = young_tot;
        this.young_max = young_max;
        this.all_ct = all_ct;
        this.all_tot = all_tot;
        this.all_max = all_max;
        this.old_ct = old_ct;
        this.old_tot = old_tot;
        this.old_max = old_max;
        this.pause_ct = pause_ct;
        this.pause_tot = pause_tot;
        this.pause_max = pause_max;
    }
}
