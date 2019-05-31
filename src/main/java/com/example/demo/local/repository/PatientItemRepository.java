package com.example.demo.local.repository;

import com.example.demo.local.modal.PatientItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientItemRepository extends JpaRepository<PatientItem,String> {
//    @Query(value ="select a.patientID as patientID,a.visitID as visitID,v.signs as item,v.measureResult as value,v.recordingTime as recordDate from VitalSignSpe v,AllPatients a " +
//            "WHERE (v.signs LIKE '脉搏' or v.signs LIKE '心率'or v.signs LIKE '血压high'or v.signs LIKE '体重'or v.signs LIKE '身高'or v.signs LIKE '舒张压'or " +
//            "v.signs LIKE '血压Low'or v.signs LIKE '体温') " +
//            "and a.patientID = v.patientID AND a.visitID = v.visitID ", nativeQuery = true)
//    List<PatientItem> searchAllFromVitalSign();
//    @Query(value ="select distinct a.patientID as patientID,a.visitID as visitID,v.signs as item,v.measureResult as value,v.recordingTime as recordDate from VitalSignSpe v,AllPatients a " +
//            "WHERE  a.patientID = v.patientID AND a.visitID = v.visitID and a.patientID =:patientId AND a.visitID=:visitId ", nativeQuery = true)
//    List<PatientItem> searchAllFromVitalSignById(@Param("patientId") String patientId, @Param("visitId") int visitId);
//    @Query(value ="select distinct a.patientID as patientID,a.visitID as visitID,signs as item,measureResult as value,recordingTime as recordDate,units from VitalSignSpe v,AllPatients a " +
//            "WHERE (signs like :item) " +
//            "and a.patientID = v.patientID AND a.visitID = v.visitID and a.patientID =:patientId AND a.visitID=:visitId", nativeQuery = true)
//    List<PatientItem> searchAllFromVitalSignByIdAndItem(@Param("item") String item,@Param("patientId") String patientId, @Param("visitId") int visitId);
//
//
//    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,t.itemName as item,t.result as value,t.resultDateTime as recordDate from TestItem t,AllPatients a " +
//            "where (itemName like '肌酸激酶同工酶' or itemName like '肌钙蛋白T' or itemName like '肌酐' or itemName like '红细胞比积测定' )" +
//            "and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',1) = a.patientID  and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',2)= CONVERT(varchar(50), a.visitID) order by t.resultDateTime DESC", nativeQuery = true)
//    List<PatientItem> searchAllFromTestItem();
//
//    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,t.itemName as item,t.result as value,t.resultDateTime as recordDate from TestItem t,AllPatients a " +
//            "where (itemName like '肌酸激酶同工酶' or itemName like '肌钙蛋白T' or itemName like '肌酐' or itemName like '红细胞比积测定' )" +
//            "and a.patientID =:patientId AND a.visitID=:visitId and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',1) = a.patientID  and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',2)= CONVERT(varchar(50), a.visitID) order by t.resultDateTime DESC", nativeQuery = true)
//    List<PatientItem> searchAllFromTestItemById(@Param("patientId") String patientId, @Param("visitId") int visitId);
//
//    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,t.itemName as item,t.result as value,t.resultDateTime as recordDate from TestItem t,AllPatients a " +
//            "where (itemName like :item)" +
//            "and a.patientID =:patientId AND a.visitID=:visitId and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',1) = a.patientID  and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',2)= CONVERT(varchar(50), a.visitID) order by t.resultDateTime DESC", nativeQuery = true)
//    List<PatientItem> searchAllFromTestItemByIdAndItem(@Param("item") String item,@Param("patientId") String patientId, @Param("visitId") int visitId);


    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,VitalItem as item,VitalItemValue as value,RecordDatetime as recordDate from AllPatients a,physical_sign p " +
            " where  a.visitID = p.EncounterIdentifier and a.patientID=p.PatientIdentifier and (VitalItem LIKE '脉搏' or VitalItem LIKE '心率'or VitalItem LIKE '血压high'or VitalItem LIKE '体重'or VitalItem LIKE '身高'or VitalItem LIKE '舒张压'or VitalItem LIKE '血压Low'or VitalItem LIKE '体温') ", nativeQuery = true)
    List<PatientItem> searchAllFromVitalSign();
    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,VitalItem as item,VitalItemValue as value,RecordDatetime as recordDate from AllPatients a,physical_sign p " +
            "WHERE  a.visitID = p.EncounterIdentifier and a.patientID=p.PatientIdentifier and a.patientID =:patientId AND a.visitID=:visitId ", nativeQuery = true)
    List<PatientItem> searchAllFromVitalSignById(@Param("patientId") String patientId, @Param("visitId") int visitId);
//    @Query(value ="select distinct a.patientID as patientID,a.visitID as visitID,signs as item,measureResult as value,recordingTime as recordDate,units from VitalSignSpe v,AllPatients a " +
//            "WHERE (signs like :item) " +
//            "and a.patientID = v.patientID AND a.visitID = v.visitID and a.patientID =:patientId AND a.visitID=:visitId", nativeQuery = true)
//    List<PatientItem> searchAllFromVitalSignByIdAndItem(@Param("item") String item,@Param("patientId") String patientId, @Param("visitId") int visitId);


//    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,t.itemName as item,t.result as value,t.resultDateTime as recordDate from TestItem t,AllPatients a " +
//            "where (itemName like '肌酸激酶同工酶' or itemName like '肌钙蛋白T' or itemName like '肌酐' or itemName like '红细胞比积测定' )" +
//            "and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',1) = a.patientID  and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',2)= CONVERT(varchar(50), a.visitID) order by t.resultDateTime DESC", nativeQuery = true)
//    List<PatientItem> searchAllFromTestItem();

    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,Result_TestItem_Name as item,Result_Result as value,DatetimeTestExcuted as recordDate from AllPatients a,Laboratory_test_data p  where  a.visitID = p.EncounterIdentifier and a.patientID=p.PatientIdentifier and ((Result_TestItem_Name LIKE '肌酸激酶同工酶' or Result_TestItem_Name LIKE '肌钙蛋白T'or Result_TestItem_Name LIKE '红细胞比积测定'or Result_TestItem_Name LIKE '肌酐')) ORDER BY DatetimeTestExcuted DESC ", nativeQuery = true)
    List<PatientItem> searchAllFromTestItemById(@Param("patientId") String patientId, @Param("visitId") int visitId);

//    @Query(value ="select  distinct a.patientID as patientID,a.visitID as visitID,t.itemName as item,t.result as value,t.resultDateTime as recordDate from TestItem t,AllPatients a " +
//            "where (itemName like :item)" +
//            "and a.patientID =:patientId AND a.visitID=:visitId and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',1) = a.patientID  and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',2)= CONVERT(varchar(50), a.visitID) order by t.resultDateTime DESC", nativeQuery = true)
//    List<PatientItem> searchAllFromTestItemByIdAndItem(@Param("item") String item,@Param("patientId") String patientId, @Param("visitId") int visitId);

}
