package com.example.demo.local.repository;

import com.example.demo.local.modal.UnJudgedPatientList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JudgePatientListRepository extends JpaRepository<UnJudgedPatientList,String> {
//    @Query(value ="select distinct d.patientID as patientID,d.visitID as visitID,d.diagnosisDate as diagnosisDate,p.kinName as kinName,p.sex as sex,p.dateOfBirth as dateOfBirth,0 as judgeflag,0 as age,d.diagnosisDesc as diagnosisDesc" +" from DiagnosisIn as d ,PatientInfo as p where (d.diagnosisType LIKE '门诊诊断' or d.diagnosisType LIKE '入院初诊') and d.patientID = p.patientID", nativeQuery = true)
//    List<UnJudgedPatientList> searchAll();
//@Query(value ="select distinct d.patientID as patientID,d.visitID as visitID,d.diagnosisDate as diagnosisDate,p.kinName as kinName,p.sex as sex,p.dateOfBirth as dateOfBirth,0 as judgeflag,0 as age,d.diagnosisDesc as diagnosisDesc" +" from DiagnosisIn as d ,PatientInfo as p where (d.diagnosisType LIKE '门诊诊断' or d.diagnosisType LIKE '入院初诊') and d.patientID = p.patientID and d.diagnosisDate >:s AND d.diagnosisDate <:e ", nativeQuery = true)
//List<UnJudgedPatientList> searchByDate(@Param("s") String s, @Param("e") String e);
//    @Query(value ="select distinct d.patientID as patientID,d.visitID as visitID,d.diagnosisDate as diagnosisDate,p.kinName as kinName,p.sex as sex,p.dateOfBirth as dateOfBirth,0 as judgeflag,0 as age,d.diagnosisDesc as diagnosisDesc" +" from DiagnosisIn as d ,PatientInfo as p where (d.diagnosisType LIKE '门诊诊断' or d.diagnosisType LIKE '入院初诊') and d.patientID = p.patientID and d.patientID =:patientId AND d.visitID=:visitId ", nativeQuery = true)
//    List<UnJudgedPatientList> searchById(@Param("patientId") String patientId, @Param("visitId") int visitId);
//
//    @Query(value ="select *" +" from AllPatients a where a.patientID =:patientId AND a.visitID=:visitId ", nativeQuery = true)
//    List<UnJudgedPatientList> searchByIdFromAllPatienta(@Param("patientId") String patientId, @Param("visitId") int visitId);
//    @Query(value ="select *" +" from AllPatients a where a.diagnosisDate >:s AND a.diagnosisDate <:e  ", nativeQuery = true)
//List<UnJudgedPatientList> searchByDateFromAllPatienta(@Param("s") String s, @Param("e") String e);
    @Query(value ="select  distinct a.PatientIdentifier as patientID,p.EncounterIdentifier as visitID,AdmitDateTime as diagnosisDate,Condition as diagnosisDesc,DemographicDetails_BirthData_BirthDate  as dateOfBirth,Name_Name_FullName as kinName,DemographicDetails_Gender as sex,0 as judgeflag,0 as age  from admission a,problem_diagnosis p,person_patient t where a.PatientIdentifier=p.PatientIdentifier and a.EncounterIdentifier = p.EncounterIdentifier and a.PatientIdentifier=t.PatientIdentifier  and p.ClassOfDiagnosis_Name like  '门诊诊断';", nativeQuery = true)
    List<UnJudgedPatientList> searchAll();
//    @Query(value ="select distinct d.patientID as patientID,d.visitID as visitID,d.diagnosisDate as diagnosisDate,p.kinName as kinName,p.sex as sex,p.dateOfBirth as dateOfBirth,0 as judgeflag,0 as age,d.diagnosisDesc as diagnosisDesc" +" from DiagnosisIn as d ,PatientInfo as p where (d.diagnosisType LIKE '门诊诊断' or d.diagnosisType LIKE '入院初诊') and d.patientID = p.patientID and d.diagnosisDate >:s AND d.diagnosisDate <:e ", nativeQuery = true)
//    List<UnJudgedPatientList> searchByDate(@Param("s") String s, @Param("e") String e);
//    @Query(value ="select distinct d.patientID as patientID,d.visitID as visitID,d.diagnosisDate as diagnosisDate,p.kinName as kinName,p.sex as sex,p.dateOfBirth as dateOfBirth,0 as judgeflag,0 as age,d.diagnosisDesc as diagnosisDesc" +" from DiagnosisIn as d ,PatientInfo as p where (d.diagnosisType LIKE '门诊诊断' or d.diagnosisType LIKE '入院初诊') and d.patientID = p.patientID and d.patientID =:patientId AND d.visitID=:visitId ", nativeQuery = true)
//    List<UnJudgedPatientList> searchById(@Param("patientId") String patientId, @Param("visitId") int visitId);

    @Query(value ="select *" +" from AllPatients a where a.patientID =:patientId AND a.visitID=:visitId ", nativeQuery = true)
    List<UnJudgedPatientList> searchByIdFromAllPatienta(@Param("patientId") String patientId, @Param("visitId") int visitId);
    @Query(value ="select *" +" from AllPatients a where a.diagnosisDate >:s AND a.diagnosisDate <:e  ", nativeQuery = true)
    List<UnJudgedPatientList> searchByDateFromAllPatienta(@Param("s") String s, @Param("e") String e);
    UnJudgedPatientList findByPatientIDAndVisitID(String patientId,int visitId);
    UnJudgedPatientList findFirstByPatientIDOrderByVisitIDDesc(String patientId);
    List<UnJudgedPatientList> findAllByJudgeflagGreaterThan(int num);
    UnJudgedPatientList findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(String patientId,int visitId);
}
