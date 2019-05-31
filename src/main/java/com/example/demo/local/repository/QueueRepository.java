package com.example.demo.local.repository;

import com.example.demo.local.modal.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue,String> {


    @Query(value ="select distinct a.patientID as patientID,a.visitID as visitID,a.diagnosisDate as diagnosisDate,a.kinName as kinName,a.sex as sex,a.age as age," +
            "s.GRACE as GRACE,s.CRUSADE as CRUSADE,s.ischemiaModelScore as ischemiaModelScore," +
            "s.bleedModelScore as bleedModelScore,s.date_time as dischargeDate,a.judgeflag as judgeflag, 0 as dischargeFlag, 0 as recordFlag from AllPatients a,SCORE s where  not exists (select * from DiagOut d where a.patientID=d.patientID and a.visitID=d.visitID)AND a.judgeflag>2 and a.patientID=s.patientID and a.visitID=s.visitID order by a.diagnosisDate DESC,s.date_time DESC ", nativeQuery = true)
    List<Queue> searchEnqueueList();
    @Query(value ="select distinct a.patientID as patientID,a.visitID as visitID,a.diagnosisDate as diagnosisDate,a.kinName as kinName,a.sex as sex,a.age as age," +
            "s.GRACE as GRACE,s.CRUSADE as CRUSADE,s.ischemiaModelScore as ischemiaModelScore," +
            "s.bleedModelScore as bleedModelScore,s.date_time, d.dischargeDate as dischargeDate,a.judgeflag as judgeflag , 0 as dischargeFlag, 0 as recordFlag from AllPatients a,SCORE s,DiagOut d where a.judgeflag>2 and a.patientID=s.patientID and a.visitID=s.visitID and d.patientID=a.patientID and d.visitID=a.visitID order by d.dischargeDate DESC,a.diagnosisDate DESC,s.date_time DESC ", nativeQuery = true)
    List<Queue> searchDequeueList();

    @Query(value ="select distinct a.patientID as patientID,a.visitID as visitID,a.diagnosisDate as diagnosisDate,a.kinName as kinName,a.sex as sex,a.age as age," +
            "s.GRACE as GRACE,s.CRUSADE as CRUSADE,s.ischemiaModelScore as ischemiaModelScore," +
            "s.bleedModelScore as bleedModelScore,s.date_time, d.dischargeDate as dischargeDate,a.judgeflag as judgeflag , 0 as dischargeFlag, 0 as recordFlag from AllPatients a,SCORE s,DiagOut d " +
            "where d.dischargeDate >:s AND d.dischargeDate <:e AND a.judgeflag>2 and a.patientID=s.patientID and a.visitID=s.visitID and d.patientID=a.patientID and d.visitID=a.visitID order by d.dischargeDate DESC,a.diagnosisDate DESC,s.date_time DESC ", nativeQuery = true)
    List<Queue> searchDequeueListByDischargeDate(@Param("s") String s, @Param("e") String e);

    @Query(value ="select distinct a.patientID as patientID,a.visitID as visitID,a.diagnosisDate as diagnosisDate,a.kinName as kinName,a.sex as sex,a.age as age," +
            "s.GRACE as GRACE,s.CRUSADE as CRUSADE,s.ischemiaModelScore as ischemiaModelScore," +
            "s.bleedModelScore as bleedModelScore,s.date_time, d.dischargeDate as dischargeDate,a.judgeflag as judgeflag , 0 as dischargeFlag, 0 as recordFlag from AllPatients a,SCORE s,DiagOut d " +
            "where  a.diagnosisDate >:s AND a.diagnosisDate <:e AND a.judgeflag>2 and a.patientID=s.patientID and a.visitID=s.visitID and d.patientID=a.patientID and d.visitID=a.visitID order by d.dischargeDate DESC,a.diagnosisDate DESC,s.date_time DESC ", nativeQuery = true)
    List<Queue> searchDequeueListByDiagnosisDate(@Param("s") String s, @Param("e") String e);
}
