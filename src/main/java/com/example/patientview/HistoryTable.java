package com.example.patientview;

public class HistoryTable {
    String medName, lastTaken;

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getLastTaken() {
        return lastTaken;
    }

    public void setLastTaken(String lastTaken) {
        this.lastTaken = lastTaken;
    }

    public HistoryTable(String medName, String lastTaken) {
        this.medName = medName;
        this.lastTaken = lastTaken;
    }
}
