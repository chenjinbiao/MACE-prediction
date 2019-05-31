package com.example.demo.service;

import com.example.demo.ListPageUtil;
import com.example.demo.local.modal.*;
import com.example.demo.local.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class QueueServiceImpl implements QueueService{

    @Resource
    private VitalItemRepository vitalItemRepository;
    @Resource
    private JudgePatientListRepository judgePatientListRepository;
    @Resource
    private PatientItemRepository patientItemRepository;
    @Resource
    private QueueRepository queueRepository;
    @Resource
    private RecordRepository recordRepository;
    @Resource
    private DiagOutRepository diagOutRepository;
    @Autowired
    private DaoService daoService;
    @Resource
    private QueueService queueService;
    private static List<Queue> enqueueList = new ArrayList<Queue>();
    private static List<Queue> dequeueList = new ArrayList<Queue>();
    private static ListPageUtil<Queue> enqueueListPageUtil;
    private static ListPageUtil<Queue> dequeueListPageUtil;
    private static int nowEnqueuePage = 1;
    private static int nowDequeuePage = 1;
    private static int recordFlagSum = 0;
    @Override
    public void updateDequeueListById(String patienID, int visitId,double grace,double crusade){

        dequeueList.stream().filter(t -> t.getPatientID().equals(patienID)&t.getVisitID()==visitId).findFirst().get().setGraceAndCrusade(grace,crusade);

    }
    @Override
    public void addIntoDequeueListByDate(String type,String startDate,String endDate){
        dequeueList.clear();
        String p="";
        int v = 0;
        List<Queue> list = new ArrayList<>();
        if(type.equals("0")){
            list = queueRepository.searchDequeueListByDischargeDate(startDate,endDate);

        }
        else if(type.equals("1")){
            list = queueRepository.searchDequeueListByDiagnosisDate(startDate,endDate);
        }

        if(list.size()>0){
            for(Queue queue:list){
                if(p != queue.getPatientID()){
                    p =queue.getPatientID();
                    v = queue.getVisitID();
                    dequeueList.add(queue);
                }
                else if(p == queue.getPatientID()&&v!=queue.getVisitID()){
                    p =queue.getPatientID();
                    v = queue.getVisitID();
                    dequeueList.add(queue);
                }
            }
        }

    }
    @Override
    public void deleteDequeue(String patientId,int visitId){
        Queue queue = dequeueList.stream().filter(t -> t.getPatientID().equals(patientId)&&t.getVisitID()==visitId).findFirst().get();
//        enqueueList.removeIf(t->(t.getPatientID().equals(patientId)));
        UnJudgedPatientList unJudgedPatientList = judgePatientListRepository.findByPatientIDAndVisitID(patientId,visitId);
        unJudgedPatientList.setJudgeflag(1);
        judgePatientListRepository.saveAndFlush(unJudgedPatientList);
        dequeueList.removeIf(t->(t.getPatientID().equals(patientId)&&t.getVisitID()==visitId));
    }
    @Override
    public void searchAllDequeue(){
        List<Queue> list = queueRepository.searchDequeueList();
        String p ="";
        int v =0;
        dequeueList.clear();
            if(list.size()>0){
                for(Queue queue:list){
                    if(p != queue.getPatientID()){
                        p =queue.getPatientID();
                        v = queue.getVisitID();
                        dequeueList.add(queue);
                    }
                    else if(p == queue.getPatientID()&&v!=queue.getVisitID()){
                        p =queue.getPatientID();
                        v = queue.getVisitID();
                        dequeueList.add(queue);
                    }
                }

        }

    }
    @Override
    public List<Queue> getDequeueList(){

        if(dequeueList.size()>0){

            dequeueListPageUtil =  new ListPageUtil<Queue>(dequeueList,0, 10);
            dequeueListPageUtil.setNowPage(nowDequeuePage);
            return  dequeueListPageUtil.getPagedList();
        }
        return  new ArrayList<>();

    }

    @Override
    public Queue getDequeue(String patienID, int visitId){
        return dequeueList.stream().filter(t -> t.getPatientID().equals(patienID)&t.getVisitID()==visitId).findFirst().get();
    }
    @Override
    public void setNowDequeuePage(int page){
        nowDequeuePage =page;
    }

    @Override
    public int  getDqueuePage(){
        if (dequeueList.size()==0)
            return 1;
        else {
            return  dequeueListPageUtil.getTotalPage();}
    }
    @Override
    public void deleteEnqueue(String patientId, int visitId){
        Queue queue = enqueueList.stream().filter(t -> t.getPatientID().equals(patientId)&t.getVisitID()==visitId).findFirst().get();
//        enqueueList.removeIf(t->(t.getPatientID().equals(patientId)));
        UnJudgedPatientList unJudgedPatientList = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(patientId,visitId);
        unJudgedPatientList.setJudgeflag(1);
        judgePatientListRepository.saveAndFlush(unJudgedPatientList);

    }
    @Override
    public void discharge(String patientId, int visitId){
        Queue queue = enqueueList.stream().filter(t -> t.getPatientID().equals(patientId)&t.getVisitID()==visitId).findFirst().get();
//        enqueueList.removeIf(t->(t.getPatientID().equals(patientId)));
        DiagOut diagOut = new DiagOut();
        diagOut.setPatientID(patientId);
        diagOut.setVisitID(visitId);
        diagOut.setDiagnosisDate(queue.getDiagnosisDate());
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        diagOut.setDischargeDate(timestamp);
        diagOutRepository.saveAndFlush(diagOut);

    }
    @Override
    public void saveRecord(Record record){
        recordRepository.saveAndFlush(record);
    }
    @Override
    public void deleteRecord(String patientId,int visitId,int recordNo){
        recordRepository.deleteRecord(patientId,visitId,recordNo);
    }
    @Override
    public List<Record> getRecordsList(String patientId,int visitId){
        return recordRepository.findAllByPatientIDAndVisitIDOrderByRecordNoDesc(patientId,visitId);
    }

    @Override
    public Queue getEnqueue(String patienID, int visitId){
        return enqueueList.stream().filter(t -> t.getPatientID().equals(patienID)&t.getVisitID()==visitId).findFirst().get();
    }
    @Override
    public void setNowEnqueuePage(int page){
        nowEnqueuePage =page;
    }
    @Override
    public int  getEnqueuePage(){
        if (enqueueList.size()==0)
            return 1;
        else {
        return  enqueueListPageUtil.getTotalPage();}
    }
    @Override
    public List<Queue> getEnqueueList2(){
        return  enqueueListPageUtil.getPagedList();
    }
    @Override
    public int getEnqueueListSize(){
        return  enqueueList.size();
    }
    @Override
    public int getRecordFlagSum(){

        return  recordFlagSum;
    }
    @Override
    public List<Queue> getEnqueueList(){

//        List<UnJudgedPatientList> list = judgePatientListRepository.findAllByJudgeflagGreaterThan(2);
        List<Queue> list= queueRepository.searchEnqueueList();
        enqueueList.clear();
        recordFlagSum = 0;
        String p ="";
        int v = 0;
        if(list.size()>0){
            for(Queue queue:list){

                if(p != queue.getPatientID()){
                    p =queue.getPatientID();
                    v = queue.getVisitID();
                    List<Record> recordList = queueService.getRecordsList(p,v);

                    if(recordList.size() != 0){
                        queue.setRecordFlag(1);
                        recordFlagSum++;
                    }
                    enqueueList.add(queue);
                } else if(p == queue.getPatientID()&&v!=queue.getVisitID()){
                    p =queue.getPatientID();
                    v = queue.getVisitID();
                    List<Record> recordList = queueService.getRecordsList(p,v);
                    System.out.println(recordList.size());
                    if(recordList.size() != 0){
                        queue.setRecordFlag(1);
                        recordFlagSum++;
                    }
                    //to judge discharge or not
                    //
                    enqueueList.add(queue);
                }

            }
                enqueueListPageUtil =  new ListPageUtil<Queue>(enqueueList,0, 10);
                enqueueListPageUtil.setNowPage(nowEnqueuePage);
                 return  enqueueListPageUtil.getPagedList();
        }
        return  new ArrayList<>();


    }

    @Override
    public VitalItem getNowPatientVitalItem(String patientId, int visitId,int type){
        Queue thePatient = new Queue();
        if(type == 0){
            thePatient = enqueueList.stream().filter(t -> t.getPatientID().equals(patientId)&t.getVisitID()==visitId).findFirst().get();
        }else {
            thePatient = dequeueList.stream().filter(t -> t.getPatientID().equals(patientId)&t.getVisitID()==visitId).findFirst().get();
        }


        String sex = thePatient.getSex();
        int age = thePatient.getAge();


        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        VitalItem patientVitalItem = new VitalItem();

        double danbai = 0; double mei = 0;
        patientVitalItem.setPatientID(patientId);
        patientVitalItem.setVisitID(visitId);
        patientVitalItem.setDateTime(dateTime);
        List<PatientItem> list1 = patientItemRepository.searchAllFromVitalSignById(patientId,visitId);
        List<PatientItem> list2 = patientItemRepository.searchAllFromTestItemById(patientId,visitId);
        int flag1 = 0;
        int flag2 = 0;
        int flag3 = 0;
        int flag4 = 0;
        int flag5 = 0;
        int flag6 = 0;
        int flag7 = 0;
        int flag8 = 0;
        int flag9 = 0;
        int flag10 = 0;
//        double danbai = 0; double mei = 0;
        for (PatientItem patientItem:list1){

            switch (patientItem.getItem()){

                case "身高":
                    if(flag1 == 0){
                        flag1 = 1;
                        patientVitalItem.setHeight(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "体重":
                    if(flag2 == 0){
                        flag2 = 1;
                        patientVitalItem.setWeight(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "心率":
                    if(flag3 == 0){
                        flag3= 1;
                        patientVitalItem.setHeartRate(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "脉搏":
                    if(flag3 == 0){
                        flag3 = 1;
                        patientVitalItem.setHeartRate(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "体温":
                    if(flag4 == 0){
                        flag4 = 1;
                        patientVitalItem.setTemperature(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "舒张压":
                    if(flag5 == 0){
                        flag5 = 1;
                        patientVitalItem.setDiastolicBloodPressure(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "血压Low":
                    if(flag5 == 0){
                        flag5 = 1;
                        patientVitalItem.setDiastolicBloodPressure(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "血压high":
                    if(flag6 == 0){
                        flag6 = 1;
                        patientVitalItem.setSystolicBloodPressure(Double.parseDouble(patientItem.getValue()));
                    }
                    break;

            }
        }
        for (PatientItem patientItem:list2){

            switch (patientItem.getItem()){

                case "肌酐":
                    if(flag7 == 0){
                        flag7 = 1;
                        patientVitalItem.setCreatinine(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "红细胞比积测定":
                    if(flag8 == 0){
                        flag8 = 1;
                        patientVitalItem.setRedBloodCellVolumeRatio(Double.parseDouble(patientItem.getValue()));
                    }
                    break;
                case "肌酸激酶同工酶":
                    if(flag9 == 0){
                        flag9 = 1;
                        mei = Double.parseDouble(patientItem.getValue());
//                        patientVitalItem.setHeight(Double.parseDouble(patientItem.getValue()));
                    }
                    break;

                case "肌钙蛋白T":
                    if(flag10 == 0){
                        flag10 = 1;
                        danbai = Double.parseDouble(patientItem.getValue());
//                        patientVitalItem.setHeight(Double.parseDouble(patientItem.getValue()));
                    }
                    break;

            }
        }

        if(danbai > 0.1 || mei >24){
            patientVitalItem.setElevatedHeartMarkers(1);
        }
        else{
            if(danbai > 0 || mei >0)
                patientVitalItem.setElevatedHeartMarkers(0);
        }
        double creatinineClear = -1;
        double creatinine = patientVitalItem.getCreatinine();
        double weight = patientVitalItem.getWeight();
        if(creatinine > -1 && weight>-1 &&age>0 && sex.length()>0){
            if(sex.equals("男")){
                creatinineClear = 8841*(140 -age)*weight/7200/creatinine;
            }
            else {
                creatinineClear = 0.85*8841*(140 -age)*weight/7200/creatinine;
            }
        }
        String S = new DecimalFormat("#.00").format(creatinineClear);
        creatinineClear =Double.parseDouble(S);
        patientVitalItem.setCreatinineClearance(creatinineClear);
//        vitalItemRepository.save(patientVitalItem);
        VitalItem patientVitalItem2 = daoService.getPatientVitalItemByPatientId(patientId,visitId);
        if(patientVitalItem2!=null){

            patientVitalItem.setsTSegmentOffset(patientVitalItem2.getsTSegmentOffset());
            patientVitalItem.setKillip(patientVitalItem2.getKillip());
            patientVitalItem.setCardiacArrest(patientVitalItem2.getCardiacArrest());
            patientVitalItem.setCongestiveHeartFailureSign(patientVitalItem2.getCongestiveHeartFailureSign());
            patientVitalItem.setHistoryOfPreviousVascularSystemDiseases(patientVitalItem2.getHistoryOfPreviousVascularSystemDiseases());
            patientVitalItem.setDiabetes(patientVitalItem2.getDiabetes());
        }

        return patientVitalItem;
    }

}
