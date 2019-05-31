package com.example.demo.local.modal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
//@Table(name="AllPatients")
@IdClass(Queue.QueueId.class)
public class Queue {
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

    public Timestamp getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Timestamp diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Timestamp getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Timestamp dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public double getGrace() {
        return grace;
    }

    public void setGrace(double grace) {
        this.grace = grace;
    }
    public void setGraceAndCrusade(double grace,double crusade) {
        this.grace = grace;this.crusade = crusade;
    }
    public double getCrusade() {
        return crusade;
    }

    public void setCrusade(double crusade) {
        this.crusade = crusade;
    }

    public double getIschemiaModelScorece() {
        return ischemiaModelScorece;
    }

    public void setIschemiaModelScorece(double graischemiaModelScorece) {
        this.ischemiaModelScorece = graischemiaModelScorece;
    }

    public double getBleedModelScore() {
        return bleedModelScore;
    }

    public void setBleedModelScore(double bleedModelScore) {
        this.bleedModelScore = bleedModelScore;
    }

    @Id
    @Column(name = "patientID")
    private String patientID;
    @Id
    @Column(name = "visitID")
    private int visitID;
    @Column(name = "diagnosisDate")
    private Timestamp diagnosisDate;
    @Column(name = "kinName")
    private String kinName;
    @Column(name = "sex")
    private String sex;
    @Column(name = "age")
    private int age;
    @Column(name = "dischargeDate")
    private Timestamp dischargeDate ;
    @Column(name = "GRACE")
    private double grace= -1;
    @Column(name = "CRUSADE")
    private double crusade= -1;
    @Column(name = "ischemiaModelScore")
    private double ischemiaModelScorece= -1;
    @Column(name = "bleedModelScore")
    private double bleedModelScore= -1;
    @Column(name = "dischargeFlag")
    private int dischargeFlag = 0;
    @Column(name = "recordFlag")
    private int recordFlag = 0;

    public int getDischargeFlag() {
        return dischargeFlag;
    }

    public void setDischargeFlag(int dischargeFlag) {
        this.dischargeFlag = dischargeFlag;
    }

    public int getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(int recordFlag) {
        this.recordFlag = recordFlag;
    }

    public int getJudgeflag() {
        return judgeflag;
    }

    public void setJudgeflag(int judgeflag) {
        this.judgeflag = judgeflag;
    }

    @Column(name = "judgeflag")
    private int judgeflag;
//    @Column(name = "judgeflag")
//    private int judgeflag;

    public static class QueueId implements Serializable
    {

        private String patientID;
        private int visitID;

        public QueueId() {

        }
        public QueueId(String patient_id, int visit_id) {
            this.patientID = patient_id;
            this.visitID = visit_id;

        }
        @Override
        public int hashCode() {
            final int prime = 37;
            int result = 17;
            result =prime*result + patientID.hashCode();
            result =prime*result + visitID;

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
