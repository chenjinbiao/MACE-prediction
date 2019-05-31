package com.example.demo.service;

import com.example.demo.ListPageUtil;
import com.example.demo.local.modal.PatientItem;
import com.example.demo.local.modal.UnJudgedPatientList;
import com.example.demo.local.modal.VitalItem;
import com.example.demo.local.repository.JudgePatientListRepository;
import com.example.demo.local.repository.PatientItemRepository;
import com.example.demo.local.repository.VitalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService{

    @Resource
    private VitalItemRepository vitalItemRepository;
    @Resource
    private JudgePatientListRepository judgePatientListRepository;
    @Resource
    private PatientItemRepository patientItemRepository;
    @Autowired
    private DaoService daoService;

    private static List<UnJudgedPatientList> patientList = new ArrayList<UnJudgedPatientList>();
    private static ListPageUtil<UnJudgedPatientList> patientListListPageUtil;
    private static int nowPage = 1;
    @Override
    public void setNowPage(int page){
        nowPage = page;
    }
    @Override
    public int getPage(){
        if (patientList.size()==0){
            return 1;
        }
        else {
            return patientListListPageUtil.getTotalPage();
        }

    }
    @Override
    public void deletePatientListById(String patientId,int visitId){
        patientList.removeIf(t->(t.getPatientID().equals(patientId)&t.getVisitID()==visitId ));

    }
    @Override
    public List<UnJudgedPatientList> showUnJudgedPatientList(int page){
        if (patientList.size()==0){
            return new ArrayList<UnJudgedPatientList>();
        }
        else {
            nowPage = page;
            patientListListPageUtil = new ListPageUtil<UnJudgedPatientList>(patientList,0, 10);
            patientListListPageUtil.setNowPage(nowPage);
            return patientListListPageUtil.getPagedList();
        }

    }
    @Override
    public UnJudgedPatientList showPatient(String patientId,int visitId){
        return patientList.stream().filter(t -> t.getPatientID().equals(patientId)&t.getVisitID()==visitId).findFirst().get();
    }

    @Override
    public VitalItem getPatientVitalItem(String patientId,int visitId){
        UnJudgedPatientList thePatient = patientList.stream().filter(t -> t.getPatientID().equals(patientId)&t.getVisitID()==visitId).findFirst().get();
//        int visitId = thePatient.getVisitID();
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

        return patientVitalItem;
    }
    @Override
    public VitalItem getNowPatientVitalItem(String patientId,int visitId){
        UnJudgedPatientList thePatient = patientList.stream().filter(t -> t.getPatientID().equals(patientId)&t.getVisitID()==visitId).findFirst().get();

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
    @Override
    public String addIntoUnJudgedPatientListById(String patientId,int vId){

        try {
            int visitId;
            if(vId == 0){
                UnJudgedPatientList patient =judgePatientListRepository.findFirstByPatientIDOrderByVisitIDDesc(patientId);
                visitId = patient.getVisitID();
            }else {
                visitId = vId;
            }
            patientList.removeIf(t->(t.getPatientID().equals(patientId) && t.getVisitID()==visitId));
            //从大数据库取
//            patientList.add(judgePatientListRepository.searchById(patientId,visitId).get(0));
            //从本地数据库取
//            if(judgePatientListRepository.searchByIdFromAllPatienta(patientId,visitId).get(0).getJudgeflag() ==0 ){
                patientList.add(0,judgePatientListRepository.searchByIdFromAllPatienta(patientId,visitId).get(0));
//            }


        }
        catch (Exception e){
            System.out.println("数据库无ID");
            return "数据库无ID";
        }
        return "1";
    }

    @Override
    public List<UnJudgedPatientList> addIntoUnJudgedPatientListByDate(String start,String end){

        try {
            patientList.clear();
            String p ="";
            int v =0;

            for (UnJudgedPatientList patient : judgePatientListRepository.searchByDateFromAllPatienta(start,end)) {
                if(p!= patient.getPatientID()&& v!= patient.getVisitID()){
                    if(patient.getJudgeflag() ==0 ){
                        patientList.add(patient);
                    }

                    p =patient.getPatientID();
                    v = patient.getVisitID();
                }

            }
            System.out.println(patientList.size());
        }
        catch (Exception e){
            System.out.println("日期格式错误");
        }
        return patientList;
    }
}
