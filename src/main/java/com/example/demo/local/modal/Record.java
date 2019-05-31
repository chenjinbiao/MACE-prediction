package com.example.demo.local.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name="Record")
@IdClass(Record.RecordId.class)
public class Record {


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

    public int getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(int recordNo) {
        this.recordNo = recordNo;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getIschemiaEvent() {
        return ischemiaEvent;
    }

    public void setIschemiaEvent(String ischemiaEvent) {
        this.ischemiaEvent = ischemiaEvent;
    }

    public String getBleedEvent() {
        return bleedEvent;
    }

    public void setBleedEvent(String bleedEvent) {
        this.bleedEvent = bleedEvent;
    }

    @Id
    @Column(name = "patientID")
    private String patientID;
    @Id
    @Column(name = "visitID")
    private int visitID;
    @Id
    @Column(name = "recordNo")
    private int recordNo;
    @Column(name = "recordDate")
    private LocalDate recordDate;
    @Column(name = "ischemiaEvent")
    private String ischemiaEvent="无";
    @Column(name = "bleedEvent")
    private String bleedEvent="0";

    public String getDeadEvent() {
        return deadEvent;
    }

    public void setDeadEvent(String deadEvent) {
        this.deadEvent = deadEvent;
    }

    @Column(name = "deadEvent")
    private String deadEvent="无";
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "userId")
    private String userId;

    public static class RecordId implements Serializable
    {

        private String patientID;

        private int visitID;
        private int recordNo;
        public RecordId() {

        }
        public RecordId(String patientID, int visitID,int recordNo) {
            this.patientID = patientID;
            this.visitID = visitID;
            this.recordNo = recordNo;
        }
        @Override
        public int hashCode() {
            final int prime = 37;
            int result = 17;
            result =prime*result + patientID.hashCode();
            result =prime*result + visitID;
            result =prime*result + recordNo;
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

