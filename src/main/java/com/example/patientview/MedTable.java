package com.example.patientview;

public class MedTable {
    String medName,qty,dosingTime;

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDosingTime() {
        return dosingTime;
    }

    public void setDosingTime(String dosingTime) {
        this.dosingTime = dosingTime;
    }

    public MedTable(String medName, String qty, String dosingTime) {
        this.medName = medName;
        this.qty = qty;
        this.dosingTime = dosingTime;
    }
}
