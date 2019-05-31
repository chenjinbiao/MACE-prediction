package com.example.demo.local.modal;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
@Entity
@Table(name="DiagOut")
@IdClass(DiagOut.DiagOutId.class)
public class DiagOut {

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

    public Timestamp getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(Timestamp dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    @Id
    @Column(name = "patientID")
    private String patientID;
    @Id
    @Column(name = "visitID")
    private int visitID;
    @Column(name = "diagnosisDate")
    private Timestamp diagnosisDate;

    @Column(name = "dischargeDate")
    private Timestamp dischargeDate;
    public static class DiagOutId implements Serializable
    {

        private String patientID;
        private int visitID;

        public DiagOutId() {

        }
        public DiagOutId(String patient_id, int visit_id) {
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
