package com.example.demo.local.modal;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="VitalItem")
@IdClass(VitalItem.VitalItemId.class)
public class VitalItem {
    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public int getVisitID() {
        return visitID;
    }

    public void setVisitID(int visitID) {
        this.visitID = visitID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getSystolicBloodPressure() {
        return systolicBloodPressure;
    }

    public void setSystolicBloodPressure(double systolicBloodPressure) {
        this.systolicBloodPressure = systolicBloodPressure;
    }

    public double getDiastolicBloodPressure() {
        return diastolicBloodPressure;
    }

    public void setDiastolicBloodPressure(double diastolicBloodPressure) {
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    public double getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(double creatinine) {
        this.creatinine = creatinine;
    }

    public double getCreatinineClearance() {
        return creatinineClearance;
    }

    public void setCreatinineClearance(double creatinineClearance) {
        this.creatinineClearance = creatinineClearance;
    }

    public double getRedBloodCellVolumeRatio() {
        return redBloodCellVolumeRatio;
    }

    public void setRedBloodCellVolumeRatio(double redBloodCellVolumeRatio) {
        this.redBloodCellVolumeRatio = redBloodCellVolumeRatio;
    }

    public int getElevatedHeartMarkers() {
        return elevatedHeartMarkers;
    }

    public void setElevatedHeartMarkers(int elevatedHeartMarkers) {
        this.elevatedHeartMarkers = elevatedHeartMarkers;
    }

    public int getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(int diabetes) {
        this.diabetes = diabetes;
    }

    public int getHistoryOfPreviousVascularSystemDiseases() {
        return historyOfPreviousVascularSystemDiseases;
    }

    public void setHistoryOfPreviousVascularSystemDiseases(int historyOfPreviousVascularSystemDiseases) {
        this.historyOfPreviousVascularSystemDiseases = historyOfPreviousVascularSystemDiseases;
    }

    public int getCardiacArrest() {
        return cardiacArrest;
    }

    public void setCardiacArrest(int cardiacArrest) {
        this.cardiacArrest = cardiacArrest;
    }

    public int getsTSegmentOffset() {
        return sTSegmentOffset;
    }

    public void setsTSegmentOffset(int sTSegmentOffset) {
        this.sTSegmentOffset = sTSegmentOffset;
    }

    public int getCongestiveHeartFailureSign() {
        return congestiveHeartFailureSign;
    }

    public void setCongestiveHeartFailureSign(int congestiveHeartFailureSign) {
        this.congestiveHeartFailureSign = congestiveHeartFailureSign;
    }

    public int getKillip() {
        return killip;
    }

    public void setKillip(int killip) {
        this.killip = killip;
    }

    @Id
    @Column(name = "patientID")
    private String patientID;
    @Id
    @Column(name = "visitID")
    private int visitID;
    @Id
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "height")
    private double height= -1;
    @Column(name = "weight")
    private double weight= -1;
    @Column(name = "heartRate")
    private double heartRate= -1;
    @Column(name = "temperature")
    private double temperature= -1;
    @Column(name = "systolicBloodPressure")
    private double systolicBloodPressure= -1;
    @Column(name = "diastolicBloodPressure")
    private double diastolicBloodPressure= -1;
    @Column(name = "creatinine")
    private double creatinine= -1;
    @Column(name = "creatinineClearance")
    private double creatinineClearance= -1;
    @Column(name = "redBloodCellVolumeRatio")
    private double redBloodCellVolumeRatio= -1;
    @Column(name = "elevatedHeartMarkers")
    private int elevatedHeartMarkers= -1;
    @Column(name = "diabetes")
    private int diabetes= -1;
    @Column(name = "historyOfPreviousVascularSystemDiseases")
    private int historyOfPreviousVascularSystemDiseases= -1;
    @Column(name = "cardiacArrestDiabetes")
    private int cardiacArrest= -1;
    @Column(name = "sTSegmentOffset")
    private int sTSegmentOffset= -1;
    @Column(name = "congestiveHeartFailureSign")
    private int congestiveHeartFailureSign= -1;
    @Column(name = "killip")
    private int killip= -1;

    public static class VitalItemId implements Serializable
    {

        private String patientID;

        private int visitID;
        private LocalDateTime dateTime;
        public VitalItemId() {

        }
        public VitalItemId(String patientID, int visitID,LocalDateTime dateTime) {
            this.patientID = patientID;
            this.visitID = visitID;
            this.dateTime = dateTime;
        }
        @Override
        public int hashCode() {
            final int prime = 37;
            int result = 17;
            result =prime*result + patientID.hashCode();
            result =prime*result + visitID;
            result =prime*result + dateTime.hashCode();
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            else
                return false;
        }




    }


}
