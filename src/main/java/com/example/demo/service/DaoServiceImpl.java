package com.example.demo.service;

import com.example.demo.local.modal.VitalItem;
import com.example.demo.local.repository.JudgePatientListRepository;
import com.example.demo.local.repository.PatientItemRepository;
import com.example.demo.local.repository.VitalItemRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DaoServiceImpl implements DaoService{
    @Resource
    private VitalItemRepository vitalItemRepository;
    @Resource
    private JudgePatientListRepository judgePatientListRepository;
    @Resource
    private PatientItemRepository patientItemRepository;

    public VitalItem getPatientVitalItemByPatientId(String patientId,int visitId){
        return vitalItemRepository.findFirstByPatientIDAndVisitIDOrderByDateTimeDesc(patientId,visitId);
    }
}
