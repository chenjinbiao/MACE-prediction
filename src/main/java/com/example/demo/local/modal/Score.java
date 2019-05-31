package com.example.demo.local.modal;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name="SCORE")
@IdClass(Score.ScoreId.class)
public class Score {
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

    public double getGrace() {
        return grace;
    }

    public void setGrace(double grace) {
        this.grace = grace;
    }

    public double getCrusade() {
        return crusade;
    }

    public void setCrusade(double crusade) {
        this.crusade = crusade;
    }

    public double getGraischemiaModelScorece() {
        return graischemiaModelScorece;
    }

    public void setGraischemiaModelScorece(double graischemiaModelScorece) {
        this.graischemiaModelScorece = graischemiaModelScorece;
    }

    public double getBleedModelScore() {
        return bleedModelScore;
    }

    public void setBleedModelScore(double bleedModelScore) {
        this.bleedModelScore = bleedModelScore;
    }

    public double getIschemiaDoctorScore() {
        return ischemiaDoctorScore;
    }

    public void setIschemiaDoctorScore(double ischemiaDoctorScore) {
        this.ischemiaDoctorScore = ischemiaDoctorScore;
    }

    public double getBleedDoctorScore() {
        return bleedDoctorScore;
    }

    public void setBleedDoctorScore(double bleedDoctorScore) {
        this.bleedDoctorScore = bleedDoctorScore;
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
    @Column(name = "GRACE")
    private double grace= -1;
    @Column(name = "CRUSADE")
    private double crusade= -1;
    @Column(name = "ischemiaModelScore")
    private double graischemiaModelScorece= -1;
    @Column(name = "bleedModelScore")
    private double bleedModelScore= -1;
    @Column(name = "ischemiaDoctorScore")
    private double ischemiaDoctorScore= -1;
    @Column(name = "bleedDoctorScore")
    private double bleedDoctorScore= -1;

    public static class ScoreId implements Serializable
    {

        private String patientID;

        private int visitID;
        private LocalDateTime dateTime;
        public ScoreId() {

        }
        public ScoreId(String patientID, int visitID,LocalDateTime dateTime) {
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
