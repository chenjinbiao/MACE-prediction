package com.example.demo.local.modal;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="AllPatients")
@IdClass(UnJudgedPatientList.UnJudgedPatientListId.class)
public class UnJudgedPatientList {
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

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public int getJudgeflag() {
        return judgeflag;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }

    public void setJudgeflag(int judgeflag) {
        this.judgeflag = judgeflag;
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
    @Column(name = "dateOfBirth")
    private Timestamp dateOfBirth;
    @Column(name = "judgeflag")
    private int judgeflag;
    @Column(name = "age")
    private int age;
    @Column(name = "diagnosisDesc")
    private String diagnosisDesc;


    public String getDiagnosisDesc() {
        return diagnosisDesc;
    }

    public void setDiagnosisDesc(String diagnosisDesc) {
        this.diagnosisDesc = diagnosisDesc;
    }

    public static class UnJudgedPatientListId implements Serializable
    {

        private String patientID;
        private int visitID;

        public UnJudgedPatientListId() {

        }
        public UnJudgedPatientListId(String patient_id, int visit_id) {
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
