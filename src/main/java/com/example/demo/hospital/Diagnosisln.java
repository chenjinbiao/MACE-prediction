package com.example.demo.hospital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name="DiagnosisIn")
public class Diagnosisln {


    public Diagnosisln() {
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(String diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public Timestamp getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Timestamp diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

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

    public Integer getDiagnosisNO() {
        return diagnosisNO;
    }

    public void setDiagnosisNO(Integer diagnosisNO) {
        this.diagnosisNO = diagnosisNO;
    }

    public Integer getTreatDays() {
        return treatDays;
    }

    public void setTreatDays(Integer treatDays) {
        this.treatDays = treatDays;
    }

    public String getTreatResult() {
        return treatResult;
    }

    public void setTreatResult(String treatResult) {
        this.treatResult = treatResult;
    }

    public String getDiagnosisDesc() {
        return diagnosisDesc;
    }

    public void setDiagnosisDesc(String diagnosisDesc) {
        this.diagnosisDesc = diagnosisDesc;
    }

    @Id
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "diagnosisType")
    private String diagnosisType;

    public Diagnosisln(String identifier, String diagnosisType, Timestamp diagnosisDate, String patientID, int visitID, Integer diagnosisNO, Integer treatDays, String treatResult, String diagnosisDesc) {
        this.identifier = identifier;
        this.diagnosisType = diagnosisType;
        this.diagnosisDate = diagnosisDate;
        this.patientID = patientID;
        this.visitID = visitID;
        this.diagnosisNO = diagnosisNO;
        this.treatDays = treatDays;
        this.treatResult = treatResult;
        this.diagnosisDesc = diagnosisDesc;
    }

    @Column(name = "diagnosisDate")
    private Timestamp diagnosisDate;
    @Column(name = "patientID")
    private String patientID;
    @Column(name = "visitID")
    private int visitID;
    @Column(name = "diagnosisNO")
    private Integer diagnosisNO;
    @Column(name = "treatDays")
    private Integer treatDays;
    @Column(name = "treatResult")
    private String treatResult;
    @Column(name = "diagnosisDesc")
    private String diagnosisDesc;


}
