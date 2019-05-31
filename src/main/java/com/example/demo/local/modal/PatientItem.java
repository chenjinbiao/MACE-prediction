package com.example.demo.local.modal;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
//@Table(name="AllPatients")
@IdClass(PatientItem.PatientItemId.class)
public class PatientItem {
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

    public Timestamp getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Timestamp recordDate) {
        this.recordDate = recordDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
//    @Id
//    @Column(name = "identifier")
//    private String identifier;
    @Id
    @Column(name = "patientID")
    private String patientID;
    @Id
    @Column(name = "visitID")
    private int visitID;
    @Column(name = "recordDate")
    private Timestamp recordDate;
    @Id
    @Column(name = "item")
    private String item;
//    @Id
    @Column(name = "value")
    private String value;
    public static class PatientItemId implements Serializable
    {

        private String patientID;
        private int visitID;
        private String item;
        public PatientItemId() {

        }
        public PatientItemId(String patient_id, int visit_id,String item) {
            this.patientID = patient_id;
            this.visitID = visit_id;
            this.item = item;

        }
        @Override
        public int hashCode() {
            final int prime = 37;
            int result = 17;
            result =prime*result + patientID.hashCode();
            result =prime*result + visitID;
            result =prime*result + item.hashCode();

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
