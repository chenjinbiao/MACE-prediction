package com.example.demo.service;

import com.example.demo.local.modal.VitalItem;

public interface DaoService {
    VitalItem getPatientVitalItemByPatientId(String patientId,int visitId);//从vitalitem数据库中查找
}
