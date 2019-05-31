package com.example.demo.service;

import com.example.demo.local.modal.Queue;
import com.example.demo.local.modal.Record;
import com.example.demo.local.modal.VitalItem;

import java.util.List;

public interface QueueService {

    int getEnqueueListSize();
    int getRecordFlagSum();
    List<Queue> getEnqueueList2();
    List<Queue> getEnqueueList();//得到当前在院患者列表
    void setNowEnqueuePage(int page);//设置页数
    int  getEnqueuePage();//得到在院患者列表总页数
    Queue getEnqueue(String patienID, int visitId);//返回具体的在院患者
    VitalItem getNowPatientVitalItem(String patientId, int visitId,int type);//得到某位已诊断患者的具体信息
    List<Record> getRecordsList(String patientId, int visitId);//得到事件记录表
    void saveRecord(Record record);//保存事件记录
    void deleteRecord(String patientId,int visitId,int recordNo);//删除事件记录
    void discharge(String patientId, int visitId);//出院
    void deleteEnqueue(String patientId, int visitId);//删除患者（把诊断标记改为1）
    void setNowDequeuePage(int page);
    Queue getDequeue(String patienID, int visitId);
    List<Queue> getDequeueList();//得到出院患者列表
    int  getDqueuePage();
    void searchAllDequeue();
    void addIntoDequeueListByDate(String type,String startDate,String endDate);
    void updateDequeueListById(String patienID,int visitId,double grace,double crusade);
    void deleteDequeue(String patientId,int visitId);//删除患者
}
