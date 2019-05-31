package com.example.demo.local.repository;

import com.example.demo.local.modal.VitalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VitalItemRepository extends JpaRepository<VitalItem,String> {

   VitalItem findFirstByPatientIDOrderByDateTimeDesc(String patientId);
   VitalItem findFirstByPatientIDAndVisitIDOrderByDateTimeDesc(String patientId,int visitId);
}
