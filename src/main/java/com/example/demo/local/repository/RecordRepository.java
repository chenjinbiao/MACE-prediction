package com.example.demo.local.repository;

import com.example.demo.local.modal.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record,String> {

    List<Record> findAllByPatientIDAndVisitIDOrderByRecordNoDesc(String patientId,int visitId);
    @Modifying
    @Transactional
    @Query(value = "delete from Record where patientID =:pId and visitID=:vId and  recordNo=:rNo" , nativeQuery = true)
    void deleteRecord(@Param("pId")String patientId, @Param("vId")int visitId, @Param("rNo")int recordNo);
    void removeRecordByPatientIDAndVisitIDAndRecordNo(String patientId,int visitId,int recordId);

}
