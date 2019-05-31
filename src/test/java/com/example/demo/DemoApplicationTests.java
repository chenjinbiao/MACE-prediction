package com.example.demo;
import com.example.demo.hospital.DiaRepository;
import com.example.demo.hospital.Diagnosisln;
import com.example.demo.hospital.HospitalTestItem;
import com.example.demo.hospital.HospitalTestItemRespository;
import com.example.demo.local.repository.*;
import com.example.demo.local.modal.*;
import com.example.demo.service.DaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

//import org.apache.commons.beanutils.BeanUtils;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

//    @Autowired
    @Resource
    private UserRepository userRepository;
    @Resource
    private VitalItemRepository vitalItemRepository;
    @Resource
    private JudgePatientListRepository judgePatientListRepository;


    @Autowired
    private DiaRepository diaRepository;
    @Test
    public void test1() {
//        String s ="2016-6-6";
//        String e ="2016-10-6";
        String s ="19239";
        String e ="8";
        List<Diagnosisln> l = diaRepository.findAll();
//        List<List2> list = patientListRepository.search6();
//        int page=0,size=10;
//        Sort sort = new Sort(Sort.Direction.DESC, "patientID");
//        Pageable pageable = PageRequest.of(page, size);
//        List<List2> list2 = patientListRepository.search7();
//        ListPageUtil<List2> listPageUtil = new ListPageUtil<List2>(list2,0, 10);
////        Page<List2> list = patientListRepository.search8(pageable);
//        int a = listPageUtil.getNowPage();
//        listPageUtil.setNowPage(1);
//        List<List2> list3 = listPageUtil.getPagedList();
//        List<List2> list4 = listPageUtil.getData();
//        listPageUtil.setNowPage(5);
//        List<List2> list5 = listPageUtil.getPagedList();
//
//
//        System.out.println(listPageUtil);
        int visitId = 8;
        String patientId ="19239";
        double danbai = 0; double mei = 0;
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        VitalItem patientVitalItem = new VitalItem();
        patientVitalItem.setPatientID(patientId);
        patientVitalItem.setVisitID(visitId);
        patientVitalItem.setDateTime(dateTime);
//        patientVitalItem.setHeight(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromVitalSignByIdAndItem("体重",patientId,visitId).size()>0)
//        patientVitalItem.setWeight(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("体重",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromVitalSignByIdAndItem("心率",patientId,visitId).size()>0)
//        patientVitalItem.setHeartRate(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("心率",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromVitalSignByIdAndItem("脉搏",patientId,visitId).size()>0)
//            patientVitalItem.setHeartRate(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("脉搏",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromVitalSignByIdAndItem("体温",patientId,visitId).size()>0)
//        patientVitalItem.setTemperature(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("体温",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromVitalSignByIdAndItem("舒张压",patientId,visitId).size()>0)
//        patientVitalItem.setSystolicBloodPressure(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("舒张压",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromVitalSignByIdAndItem("血压low",patientId,visitId).size()>0)
//            patientVitalItem.setSystolicBloodPressure(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("血压low",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromVitalSignByIdAndItem("血压high",patientId,visitId).size()>0)
//        patientVitalItem.setDiastolicBloodPressure(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("血压high",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromTestItemByIdAndItem("肌酐",patientId,visitId).size()>0)
//        patientVitalItem.setCreatinine(Double.parseDouble(patientItemRepository.searchAllFromTestItemByIdAndItem("肌酐",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromTestItemByIdAndItem("红细胞比积测定",patientId,visitId).size()>0)
////        patientVitalItem.setCreatinineClearance(Double.parseDouble(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        patientVitalItem.setRedBloodCellVolumeRatio(Double.parseDouble(patientItemRepository.searchAllFromTestItemByIdAndItem("红细胞比积测定",patientId,visitId).get(0).getValue()));
//        if (patientItemRepository.searchAllFromTestItemByIdAndItem("肌酸激酶同工酶",patientId,visitId).size()>0)
//            mei =Double.parseDouble(patientItemRepository.searchAllFromTestItemByIdAndItem("肌酸激酶同工酶",patientId,visitId).get(0).getValue());
//            if (patientItemRepository.searchAllFromTestItemByIdAndItem("肌钙蛋白T",patientId,visitId).size()>0)
//                danbai = Double.parseDouble(patientItemRepository.searchAllFromTestItemByIdAndItem("肌钙蛋白T",patientId,visitId).get(0).getValue());
//
//
//
//                if(danbai > 0.1 || mei >24){
//                    patientVitalItem.setElevatedHeartMarkers(1);
//                }
//                else{
//                    if(danbai > 0 || mei >0)
//                        patientVitalItem.setElevatedHeartMarkers(0);
//                }


//        patientVitalItem.setElevatedHeartMarkers(Integer.parseInt(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        patientVitalItem.setDiabetes(Integer.parseInt(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        patientVitalItem.setHistoryOfPreviousVascularSystemDiseases(Integer.parseInt(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        patientVitalItem.setCardiacArrest(Integer.parseInt(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        patientVitalItem.setElevatedHeartMarkers(Integer.parseInt(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        patientVitalItem.setElevatedHeartMarkers(Integer.parseInt(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//        patientVitalItem.setElevatedHeartMarkers(Integer.parseInt(patientItemRepository.searchAllFromVitalSignByIdAndItem("身高",patientId,visitId).get(0).getValue()));
//
//        vitalItemRepository.save(patientVitalItem);
//        System.out.println(patientVitalItem);



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
                case "血压low":
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

        System.out.println(patientVitalItem);






    }
  @Resource
  private DiagLocalRepository diagLocalRepository;
    @Resource
    private LocalTestItemRepository localTestItemRepository;
    @Resource
    private HospitalTestItemRespository hospitalTestItemRespository;

    @Test
    public void test2() {
//        List<String> list = new ArrayList<String>();
//        System.out.println("ArrayList集合初始化容量："+list.size());
//
//        //添加功能：
//        list.add("Hello");
//        list.add("world");
//        list.add(0,"!");
//        System.out.println("ArrayList当前容量："+list.size());
//
//        //修改功能：
//        list.set(0,"my");
//        list.set(1,"name");
//        System.out.println("ArrayList当前内容："+list.toString());
//
//        //获取功能：
//        String element = list.get(0);
//        System.out.println(element);

        List<LocalTestItem> localTestItemList = localTestItemRepository.findAll();
        List<HospitalTestItem> hospitalTestItemArrayList = new ArrayList<>();
        HospitalTestItem hospitalTestItem = new HospitalTestItem();
        for(LocalTestItem localTestItem: localTestItemList){
            BeanUtils.copyProperties(localTestItem,hospitalTestItem);
            hospitalTestItemArrayList.add(hospitalTestItem);
            hospitalTestItemRespository.saveAndFlush(hospitalTestItem);
        }
        System.out.println(hospitalTestItemArrayList);


//        List<com.example.demo.local.modal.Diagnosisln> la = diagLocalRepository.findAll();
//        List<Diagnosisln> lb = new ArrayList<>();
//        Diagnosisln d = new Diagnosisln();
//        for(com.example.demo.local.modal.Diagnosisln da : la){
//            BeanUtils.copyProperties(da,d);
//            lb.add(d);
//            diaRepository.saveAndFlush(d);
//        }
//        System.out.println(lb);

//        diaRepository.saveAll(lb);
//        diaRepository.saveAndFlushAll(lb);
//        ObjectConvertor convertor;
//
//
//        lb = convertor.toAnotherObj(la, Diagnosisln.class);




        String id = "1";
//        System.out.println(userRepository.findByUserIdAndAndPassword("1","5") == null);
        int page=0,size=2;
        Sort sort = new Sort(Sort.Direction.DESC, "patientID");
        Pageable pageable = PageRequest.of(page, size);
//        Page<User> page11 = patientListRepository.search7();
//        User user =new User("userId","userName","password");
//        userRepository.saveAndFlush(user);
//        userRepository.insertUser();


    }
//建立数据库表格，之后都从这里取
    @Test
    public void test3() {
        String s ="1954-04-28 00:00:00.0";
        String e ="2016-10-6 00:00:00.0";
        String p ="19239";
        int age = GetAge.getAgeByBirth(e);
        System.out.println(age);
        List<UnJudgedPatientList> lists = judgePatientListRepository.searchAll();
        for (UnJudgedPatientList patient : lists) {
            if(patient.getSex().equals("M")){
                patient.setSex("男");
            }
            else if(patient.getSex().equals("F")){
                patient.setSex("女");
            }
            String d = patient.getDateOfBirth().toString();
            patient.setAge(GetAge.getAgeByBirth(d));
            judgePatientListRepository.save(patient);

        }

    }
    @Autowired
    private PatientItemRepository patientItemRepository;
    @Autowired
    private DaoService daoService;
    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private RecordRepository recordRepository;
    @Test

    public void test4() {

        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("C:/Users/dell/Desktop/test3/hello.py");

        PyFunction pyFunction = interpreter.get("hello", PyFunction.class); // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyObject pyObject = pyFunction.__call__(); // 调用函数

        System.out.println(pyObject);

//        List<PatientItem> ll = patientItemRepository.searchAllFromVitalSign();
//        VitalItem v =daoService.getPatientVitalItemByPatientId("19239");
//        VitalItem v1 =daoService.getPatientVitalItemByPatientId("263886");
//        VitalItem v2=daoService.getPatientVitalItemByPatientId("1");
//        VitalItem v3 =daoService.getPatientVitalItemByPatientId("2");
//        List<UnJudgedPatientList> list = judgePatientListRepository.findAllByJudgeflagGreaterThan(2);
//        System.out.println(list);
//        List<PatientItem> l2 = patientItemRepository.searchAllFromTestItemById("760671",3);
//        recordRepository.deleteRecord("926466",5,1);
//        List<Queue> list =queueRepository.searchDequeueListByDischargeDate("2019-05-01","2019-05-04");
//        System.out.println(list);

//

    }
//        int v =8;
////        List<List2> list = patientListRepository.search6();
//        int page=0,size=10;
//        Sort sort = new Sort(Sort.Direction.DESC, "patientID");
//        Pageable pageable = PageRequest.of(page, size);
//        List<UnJudgedPatientList> list3 = judgePatientListRepository.searchById(p,v);
//        judgePatientListRepository.saveAll(list3);
//        List<UnJudgedPatientList> list2 = judgePatientListRepository.searchByDate(s,e);
//        judgePatientListRepository.saveAll(list2);
//        List<UnJudgedPatientList> list1 = judgePatientListRepository.searchAll();
//        judgePatientListRepository.saveAll(list1);
//
//
////        List<UnJudgedPatientList> list1 = judgePatientListRepository.searchAll();
//
//        ListPageUtil<UnJudgedPatientList> listPageUtil = new ListPageUtil<UnJudgedPatientList>(list2,2, 2);
////        Page<List2> list = patientListRepository.search8(pageable);
//
//        System.out.println(listPageUtil);


}
