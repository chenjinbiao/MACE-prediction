package com.example.demo.service;


import com.example.demo.local.modal.Score;
import com.example.demo.local.modal.UnJudgedPatientList;
import com.example.demo.local.modal.VitalItem;
import com.example.demo.local.repository.JudgePatientListRepository;
import com.example.demo.local.repository.PatientItemRepository;
import com.example.demo.local.repository.ScoreRepository;
import com.example.demo.local.repository.VitalItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ScoreServiceImpl implements ScoreService{
    @Autowired
    PatientItemRepository patientItemRepository;
    @Autowired
    VitalItemRepository vitalItemRepository;
    @Autowired
    JudgePatientListRepository judgedPatientListRepository;
    @Autowired
    ScoreService scoreService;
    @Autowired
    ScoreRepository scoreRepository;
    public Score getScore(String patientId, int visitId){
        Score score =new Score();
        score.setPatientID(patientId);
        score.setVisitID(visitId);
        double grace =scoreService.getGrace(patientId,visitId);
        double crusade =scoreService.getCrusade(patientId,visitId);
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        score.setDateTime(dateTime);
        if(grace!=-1 ||crusade!=-1){
            score.setCrusade(crusade);
            score.setGrace(grace);
            scoreRepository.save(score);
        }else if(scoreRepository.findFirstByPatientIDAndVisitID(patientId,visitId)==null){
            scoreRepository.save(score);
        }


        return  score;
    }



    public double getGrace(String patientId,int visitId){
        VitalItem vitalItem = vitalItemRepository.findFirstByPatientIDAndVisitIDOrderByDateTimeDesc(patientId,visitId);
        UnJudgedPatientList unJudgedPatientList = judgedPatientListRepository.findByPatientIDAndVisitID(patientId,visitId);
        if(vitalItem==null ||unJudgedPatientList ==null){return -1;}
        double grace =0;
        int age =unJudgedPatientList.getAge();
        double heartRate = vitalItem.getHeartRate();
        double sbp = vitalItem.getSystolicBloodPressure();
        double creatinine =vitalItem.getCreatinine()/88.41;
        int killip =vitalItem.getKillip();
        int arrest =vitalItem.getCardiacArrest();
        int marks =vitalItem.getElevatedHeartMarkers();
        int st =vitalItem.getsTSegmentOffset();
        if(killip == -1|| creatinine==-1 || heartRate ==-1||age ==0 || marks ==-1 || st ==-1 ||arrest==-1||sbp == -1){
            grace =-1;
            return grace;
        }

        if(age <40){
            grace =grace;
        }else if(age<50){
            grace =grace+18;
        }else if(age<60){
            grace =grace+36;
        }else if(age<70){
            grace =grace+55;
        }else if(age<80){
            grace =grace+73;
        }else {
            grace =grace+91;
        }
        if(heartRate <=70 ){
            grace =grace;
        }else if(heartRate<90){
            grace =grace+7;
        }else if(heartRate<110){
            grace =grace+13;
        }else if(heartRate<150){
            grace =grace+23;
        }else if(heartRate<200){
            grace =grace+36;
        }else {
            grace =grace+46;
        }
        if(sbp<80){
            grace =grace+63;
        }else if(sbp<100){
            grace =grace+58;
        }else if(sbp<120){
            grace =grace+47;
        }else if(sbp<140){
            grace =grace+37;
        }else if(sbp<160){
            grace =grace+26;
        }else if(sbp<200){
            grace =grace+11;
        }
        if(creatinine<0.4){
            grace =grace+2;
        }else if(creatinine<0.8){
            grace = grace +5;
        }else if(creatinine<1.2){
            grace = grace +8;
        }
        else if(creatinine<1.6){
            grace = grace +11;
        }
        else if(creatinine<2){
            grace = grace +14;
        }
        else if(creatinine<4){
            grace = grace +23;
        }else {
            grace = grace +31;
        }
        switch (killip){
            case 1:break;
            case 2:grace = grace+21;break;
            case 3:grace = grace+43;break;
            case 4:grace = grace +64;break;
            default:break;
        }
        if(arrest==1){
            grace =grace +43;
        }
        if(marks==1){
            grace =grace +15;
        }
        if(st==1){
            grace =grace +30;
        }
        return grace;
    }




    public double getCrusade(String patientId,int visitId){

        VitalItem vitalItem = vitalItemRepository.findFirstByPatientIDAndVisitIDOrderByDateTimeDesc(patientId,visitId);
        UnJudgedPatientList unJudgedPatientList = judgedPatientListRepository.findByPatientIDAndVisitID(patientId,visitId);
        if(vitalItem==null ||unJudgedPatientList ==null){return -1;}
        double crusade = 0;
        double redBlood = vitalItem.getRedBloodCellVolumeRatio();
        double clearance = vitalItem.getCreatinineClearance();
        double heartRate = vitalItem.getHeartRate();
        String sex = unJudgedPatientList.getSex();
        int heartFailure = vitalItem.getCongestiveHeartFailureSign();
        int diseaseHistory = vitalItem.getHistoryOfPreviousVascularSystemDiseases();
        int diabetes =vitalItem.getDiabetes();
        double sbp = vitalItem.getSystolicBloodPressure();
        if(redBlood == -1|| clearance==-1 || heartRate ==-1||sex ==null || heartFailure ==-1 || diseaseHistory ==-1 ||diabetes==-1||sbp == -1){
            crusade =-1;
            return crusade;
        }

        if(redBlood<31){
            crusade = crusade + 9;
        }else if(redBlood<34){
            crusade = crusade + 7;
        }else if(redBlood<37){
            crusade = crusade + 3;
        }else if(redBlood<40){
            crusade = crusade + 2;
        }else {
            crusade = crusade + 0;
        }

        if(clearance<15 || clearance==15){
            crusade = crusade + 39;
        }else if(clearance<30|| clearance==30){
            crusade = crusade + 35;
        }else if(clearance<60|| clearance==60){
            crusade = crusade + 28;
        }else if(clearance<90|| clearance==90){
            crusade = crusade + 17;
        }else if(clearance<120|| clearance==120){
            crusade = crusade + 7;
        }else {
            crusade = crusade + 0;
        }

        if(heartRate <=70 ){
            crusade =crusade;
        }else if(heartRate<=80){
            crusade = crusade+1;
        }else if(heartRate<=90){
            crusade = crusade+3;
        }else if(heartRate<=100){
            crusade = crusade+6;
        }else if(heartRate<=110){
            crusade = crusade+8;
        }else if(heartRate<=120){
            crusade = crusade+10;
        }else{
            crusade = crusade+11;
        }

        if(sex.equals("女"))
            crusade = crusade+8;

        if(heartFailure == 1)
            crusade =crusade+7;
        if(diseaseHistory ==1)
            crusade =crusade+6;
        if(diabetes==1)
            crusade = crusade+6;

        if(sbp<=90){
            crusade =crusade+10;
        }else if(sbp<=100){
            crusade =crusade+8;
        }else if(sbp<=120){
            crusade =crusade+5;
        }else if(sbp<=180){
            crusade =crusade+1;
        }else if(sbp<=200){
            crusade =crusade+3;
        }else {
            crusade =crusade+5;
        }

        return crusade;

    }


}
