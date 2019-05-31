package com.example.demo.service;

import com.example.demo.local.modal.UnJudgedPatientList;
import com.example.demo.local.modal.VitalItem;

import java.util.List;

public interface PatientService {

    //展示符合条件的未判断患者
    List<UnJudgedPatientList> showUnJudgedPatientList(int page);
    String addIntoUnJudgedPatientListById(String patientId,int visitId);
    //根据ID查找添加患者
    List<UnJudgedPatientList> addIntoUnJudgedPatientListByDate(String start,String end);//根据日期查找
    void setNowPage(int page);//设置当前页面
    int getPage();//返回总页面数
    UnJudgedPatientList showPatient(String patientId,int visitId);//返回当前患者信息
    VitalItem getPatientVitalItem(String patientId,int visitId);//返回VitalItem
    void deletePatientListById(String patientId,int visitId);
    VitalItem getNowPatientVitalItem(String patientId,int visitId);
}
