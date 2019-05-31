package com.example.demo.timer;

import com.example.demo.hospital.DiaRepository;
import com.example.demo.hospital.Diagnosisln;
import com.example.demo.hospital.HospitalTestItem;
import com.example.demo.hospital.HospitalTestItemRespository;
import com.example.demo.local.modal.LocalTestItem;
import com.example.demo.local.repository.DiagLocalRepository;
import com.example.demo.local.repository.LocalTestItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class ScheduledTask {
    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private int fixedDelayCount = 1;
    private int fixedRateCount = 1;
    private int initialDelayCount = 1;
    private int cronCount = 1;
    private int connectCount = 1;
    @Resource
    LocalTestItemRepository localTestItemRepository;
    @Resource
    HospitalTestItemRespository hospitalTestItemRespository;
    @Resource
    DiagLocalRepository diagLocalRepository;
    @Resource
    DiaRepository diaRepository;
    @Scheduled(fixedDelay = 300000)        //fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring scheduling会再次调用该方法
    public void databaseOperate() {
        logger.info("===数据库: 第{}次连接开始"+ new Date(), connectCount);
        List<LocalTestItem> localTestItemList = localTestItemRepository.findAll();
        List<HospitalTestItem> hospitalTestItemArrayList = new ArrayList<>();
        HospitalTestItem hospitalTestItem = new HospitalTestItem();
        for(LocalTestItem localTestItem: localTestItemList){
            BeanUtils.copyProperties(localTestItem,hospitalTestItem);
            hospitalTestItemArrayList.add(hospitalTestItem);
            hospitalTestItemRespository.saveAndFlush(hospitalTestItem);
        }
        System.out.println(hospitalTestItemArrayList);


        List<com.example.demo.local.modal.Diagnosisln> la = diagLocalRepository.findAll();
        List<Diagnosisln> lb = new ArrayList<>();
        Diagnosisln d = new Diagnosisln();
        for(com.example.demo.local.modal.Diagnosisln da : la){
            BeanUtils.copyProperties(da,d);
            lb.add(d);
            diaRepository.saveAndFlush(d);
        }
        logger.info("===数据库: 第{}次连接结束"+ new Date(), connectCount++);
    }

//    @Scheduled(fixedDelay = 15000)        //fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring scheduling会再次调用该方法
//    public void testFixDelay() {
//        logger.info("===fixedDelay: 第{}次执行方法", fixedDelayCount++);
//    }
//
//    @Scheduled(fixedRate = 10000)        //fixedRate = 5000表示当前方法开始执行5000ms后，Spring scheduling会再次调用该方法
//    public void testFixedRate() {
//        logger.info("===fixedRate: 第{}次执行方法", fixedRateCount++);
//    }
//
//    @Scheduled(initialDelay = 1000, fixedRate = 5000)   //initialDelay = 1000表示延迟1000ms执行第一次任务
//    public void testInitialDelay() {
//        logger.info("===initialDelay: 第{}次执行方法", initialDelayCount++);
//    }
//
//    @Scheduled(cron = "0 15 10 * * ?")  //cron接受cron表达式，根据cron表达式确定定时规则
//    public void testCron() {
//        logger.info("===initialDelay: 第{}次执行方法", cronCount++);
//    }


}
