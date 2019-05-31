package com.example.demo.controller;

import com.example.demo.local.repository.*;
import com.example.demo.local.modal.*;
import com.example.demo.local.modal.Queue;
//import com.example.demo.local.repository.JudgePatientListRepository;
//import com.example.demo.local.repository.UserRepository;
//import com.example.demo.local.repository.VitalItemRepository;
import com.example.demo.service.DaoService;
import com.example.demo.service.PatientService;
import com.example.demo.service.QueueService;
import com.example.demo.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RestController
public class WebController {
    private static final String MAIN_PATH_NAME= "main";
    private static final String LOGIN_PATH_NAME= "login";
    private static final String REGISTER_PATH_NAME= "register";
    private static final String ENQUEUE_PATH_NAME= "enqueue";
    private static final String ENQUEUE2_PATH_NAME= "enqueue2";
    private static final String DEQUEUE_PATH_NAME= "dequeue";
    private static final String DEQUEUE2_PATH_NAME= "dequeue2";
    private static User active_user = new User();
    private static VitalItem active_patientVitalItem = new VitalItem();
    private static UnJudgedPatientList active_patient = new UnJudgedPatientList();
    @Autowired
    private DaoService daoService;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private QueueService queueService;
    //登入界面
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(){
//        Map<String,Object> map =new HashMap<String,Object>();
        ModelAndView modelAndView = new ModelAndView(LOGIN_PATH_NAME);

        return modelAndView;
    }
    //获取登入界面参数并登入
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> toLogin(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String userId=request.getParameter("userId");
        String password=request.getParameter("password");
//        System.out.println(userName + password+userName.length());
//        System.out.println(u.length());
//        System.out.println(userName.length());
        if(userId.length()>0 && password.length()>0){
            if(userRepository.findByUserIdAndAndPassword(userId,password) == null){
                map.put("result","账号或密码错误");
            }
            else{
                active_user = userRepository.findByUserIdAndAndPassword(userId,password);
                request.getSession().setAttribute("user",active_user);
                map.put("result","1");
            }


        }else{
            map.put("result","请填写完整");
        }
        return map;

    }
    //注册界面
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView toRegister(){
//        Map<String,Object> map =new HashMap<String,Object>();
        ModelAndView modelAndView = new ModelAndView(REGISTER_PATH_NAME);

        return modelAndView;
    }
    //获取注册界面参数并注册登入
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map2 =new HashMap<String,Object>();

        String userName=request.getParameter("username");
        String password=request.getParameter("password");
        String password2=request.getParameter("password2");
        String userId = request.getParameter("userId");
        System.out.println(userName + password+userName.length());

        System.out.println(userName.length());
        if(userName.length()>0 && password.length()>0  && userId.length()>0){
            if(password2.equals(password)){ User user =new User(userId,userName,password);
                if(userRepository.findAllByUserId(userId).size()==0){
                    userRepository.save(user);
                    active_user = user;
                    request.getSession().setAttribute("user",user);
                    map2.put("result","1");
                }
                else{
                    map2.put("result","账号已存在");
                }
            }else {map2.put("result","两次输入密码不一致");}

        }else{
            map2.put("result","请填写完整");
        }
        return map2;

    }
    //    @RequestMapping(value = "/register")
//    public ModelAndView register(){
////        Map<String,Object> map =new HashMap<String,Object>();
//        ModelAndView modelAndView = new ModelAndView(REGISTER_PATH_NAME);
//        return modelAndView;
//    }

//    @RequestMapping(value = "/main",method = RequestMethod.GET)
//    public ModelAndView main(HttpServletRequest request){
//        ModelAndView modelAndView = new ModelAndView(MAIN_PATH_NAME);
//        modelAndView.addObject("active_user", active_user);
//        modelAndView.addObject("unJudgedPatientLists", patientService.showUnJudgedPatientList());
//        modelAndView.addObject("nowPage", 1);
//        modelAndView.addObject("pageSize", patientService.getPage());
//        return modelAndView;
//    }

    //未判断患者界面，后台得到前端的两个日期（根据日期搜索功能来得到患者）
    @RequestMapping(value = "/main/PageNum={nowPage}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> judgePatient(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map3 =new HashMap<String,Object>();
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        System.out.println(startTime);
        System.out.println(endTime);
        if(startTime.length()>0 && endTime.length()>0){
            List<UnJudgedPatientList> list = patientService.addIntoUnJudgedPatientListByDate(startTime,endTime);
            map3.put("result","1");
        }else{
            map3.put("result","请填写完整");
        }
//        String password2=request.getParameter("password2");
//        String userId = request.getParameter("userId");
//        String page=request.getParameter("nowPage");

        return map3;

    }
    //根据ID搜索功能来添加患者到未判断患者界面，后台得到前端的两个ID
    @RequestMapping(value = "/main/PageNum={nowPage}/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addUnjudgedPatient(HttpServletRequest request, HttpServletResponse response)throws IOException {
        Map<String,Object> map3 =new HashMap<String,Object>();
        String patientId=request.getParameter("patientId");
        String visitId=request.getParameter("visitId");
        System.out.println(patientId);
        System.out.println(visitId);
        String result;
        if(patientId.length()>0){
            if(visitId.length()==0){
                result = patientService.addIntoUnJudgedPatientListById(patientId,0);
            }
            else {
                result =  patientService.addIntoUnJudgedPatientListById(patientId, Integer.parseInt(visitId));
            }

            map3.put("result",result);

        }else{
            map3.put("result","请填写完整ID");
        }
//        String password2=request.getParameter("password2");
//        String userId = request.getParameter("userId");

        return map3;

    }

    //未判断患者界面分页界面显示
    @RequestMapping(value = "/main/PageNum={nowPage}",method = RequestMethod.GET)
    public ModelAndView nowPageWindow(@PathVariable int nowPage){
        ModelAndView modelAndView = new ModelAndView(MAIN_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
        List<UnJudgedPatientList> list =patientService.showUnJudgedPatientList(nowPage);
//        patientService.setNowPage(nowPage);
        System.out.println(nowPage);
        modelAndView.addObject("modal2", 0);
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", patientService.getPage());
        modelAndView.addObject("unJudgedPatientLists", list);

        modelAndView.addObject("patientVitalItem", active_patientVitalItem);
        modelAndView.addObject("patientVitalItem2", active_patientVitalItem);
        modelAndView.addObject("patient_id", "404");
        modelAndView.addObject("patient", active_patient);
        Score score = new Score();
        modelAndView.addObject("score", score);
        return modelAndView;

    }
    //未判断患者界面患者详细信息界面显示，同时提取多数据库中最近一次的重要特征项与上一次保存的重要特征项，并在打开页面之前计算Score
    @RequestMapping(value = "/main/PageNum={nowPage}/{id}/{vid}",method = RequestMethod.GET)
    public ModelAndView patientMessageWindow(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid){
        ModelAndView modelAndView = new ModelAndView(MAIN_PATH_NAME);
        UnJudgedPatientList patient = patientService.showPatient(id,vid);
        modelAndView.addObject("active_user", active_user);
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("patient_id", id);
        VitalItem patientVitalItem = new VitalItem();
        patientVitalItem = patientService.getPatientVitalItem(id,vid);
        modelAndView.addObject("patientVitalItem", patientVitalItem);
        VitalItem patientVitalItem2 = daoService.getPatientVitalItemByPatientId(id,vid);
        if (patientVitalItem2 == null){
            patientVitalItem2 = patientVitalItem;
        }
        modelAndView.addObject("patientVitalItem2", patientVitalItem2);
        List<UnJudgedPatientList> list =patientService.showUnJudgedPatientList(nowPage);
        modelAndView.addObject("nowPage", nowPage);

        modelAndView.addObject("modal2", 1);
        modelAndView.addObject("pageSize", patientService.getPage());
        modelAndView.addObject("unJudgedPatientLists", list);


        Score score = scoreService.getScore(id,vid);
        modelAndView.addObject("score", score);
        return modelAndView;

    }
    @Resource
    JudgePatientListRepository judgePatientListRepository;
    @Resource
    VitalItemRepository vitalItemRepository;

    //未判断患者界面患者详细信息界面显示，获得并保存前端手动输入的特征项，比较有没有改变以及是否判断患者ACS类型，计算SCORE值
    @RequestMapping(value = "/main/PageNum={nowPage}/{id}/{vid}",method = RequestMethod.POST)
    @ResponseBody
    public  void judgePatient(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid) throws IOException{
        Map<String,Object> map3 =new HashMap<String,Object>();
        String acs_event=request.getParameter("acs_event");
        System.out.println("acs_event:"+ acs_event);
        String sel=request.getParameter("sel");
        String sel1=request.getParameter("sel1");
        String sel2=request.getParameter("sel2");
        String sel3=request.getParameter("sel3");
        String sel4=request.getParameter("sel4");
        String sel5=request.getParameter("sel5");
        UnJudgedPatientList patient = patientService.showPatient(id,vid);
        if(acs_event != null){
            patient.setJudgeflag(Integer.parseInt(acs_event));

            judgePatientListRepository.save(patient);

        }

        VitalItem patientVitalItem = patientService.getPatientVitalItem(id,vid);
        int flag =0;
        if(patientVitalItem.getsTSegmentOffset()!=Integer.parseInt(sel)){
            patientVitalItem.setsTSegmentOffset(Integer.parseInt(sel));
            flag = 1;
        }
        if(patientVitalItem.getKillip()!=Integer.parseInt(sel1)){
            patientVitalItem.setKillip(Integer.parseInt(sel1));
            flag = 1;
        }
        if(patientVitalItem.getCardiacArrest()!=Integer.parseInt(sel2)){
            patientVitalItem.setCardiacArrest(Integer.parseInt(sel2));
            flag = 1;
        }
        if(patientVitalItem.getCongestiveHeartFailureSign()!=Integer.parseInt(sel3)){
            patientVitalItem.setCongestiveHeartFailureSign(Integer.parseInt(sel3));
            flag = 1;
        }
        if(patientVitalItem.getHistoryOfPreviousVascularSystemDiseases()!=Integer.parseInt(sel4)){
            patientVitalItem.setHistoryOfPreviousVascularSystemDiseases(Integer.parseInt(sel4));
            flag = 1;
        }
        if(patientVitalItem.getDiabetes()!=Integer.parseInt(sel5)){
            patientVitalItem.setDiabetes(Integer.parseInt(sel5));
            flag = 1;
        }
        if(flag==1){
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            patientVitalItem.setDateTime(dateTime);
            vitalItemRepository.save(patientVitalItem);
            Score score = scoreService.getScore(id,patient.getVisitID());
        }

        if(patient.getJudgeflag()>0){
            patientService.deletePatientListById(id,vid);
        }


        response.sendRedirect("/main/PageNum="+ nowPage);


//        return map3;

    }

    //在列患者界面显示
    @RequestMapping(value = "/enqueue/PageNum={nowPage}",method = RequestMethod.GET)
    public ModelAndView nowPageEnqueue(@PathVariable int nowPage){
        ModelAndView modelAndView = new ModelAndView(ENQUEUE_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
//        List<UnJudgedPatientList> list =patientService.showUnJudgedPatientList(nowPage);
//        modelAndView.addObject("modal2", 0);
        queueService.setNowEnqueuePage(nowPage);
        List<Queue> enqueueList = queueService.getEnqueueList();
        int listSize = queueService.getEnqueueListSize();
        int unrecordNum = listSize - queueService.getRecordFlagSum();
        modelAndView.addObject("listSize", listSize);
        modelAndView.addObject("unrecordNum", unrecordNum);
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getEnqueuePage());
        modelAndView.addObject("enqueueList", enqueueList);

        //患者详细信息赋值
        VitalItem patientVitalItem = new VitalItem();
        modelAndView.addObject("patientVitalItem", patientVitalItem);
        modelAndView.addObject("modal_flag", 0);
        UnJudgedPatientList patient = new UnJudgedPatientList();
        Score score = new Score();
        modelAndView.addObject("score", score);
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("patient_id", "0");
        return modelAndView;

    }
    //在列患者界面显示详细信息界面显示
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/{id}/{vid}",method = RequestMethod.GET)
    public ModelAndView nowPageEnqueue(@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid){
        ModelAndView modelAndView = new ModelAndView(ENQUEUE_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
//        List<UnJudgedPatientList> list =patientService.showUnJudgedPatientList(nowPage);
//        modelAndView.addObject("modal2", 0);
        queueService.setNowEnqueuePage(nowPage);
        List<Queue> enqueueList = queueService.getEnqueueList2();
        //列表页面赋值
        int listSize = queueService.getEnqueueListSize();
        int unrecordNum = listSize - queueService.getRecordFlagSum();
        modelAndView.addObject("listSize", listSize);
        modelAndView.addObject("unrecordNum", unrecordNum);
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getEnqueuePage());
        modelAndView.addObject("enqueueList", enqueueList);
        //患者详细信息赋值
        VitalItem patientVitalItem = queueService.getNowPatientVitalItem(id,vid,0);
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        Score score = scoreService.getScore(id,vid);
        modelAndView.addObject("patientVitalItem", patientVitalItem);
        modelAndView.addObject("modal_flag", 1);
        modelAndView.addObject("score", score);
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("patient_id", id);
        return modelAndView;

    }
    //在列患者界面显示详细信息界面获取前端改变以及获取评分结果
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/{id}/{vid}",method = RequestMethod.POST)
    @ResponseBody
    public  void judgeEnquueuPatient(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid) throws IOException{

        String acs_event=request.getParameter("acs_event");
        System.out.println("acs_event:"+ acs_event);
        String sel=request.getParameter("sel");
        String sel1=request.getParameter("sel1");
        String sel2=request.getParameter("sel2");
        String sel3=request.getParameter("sel3");
        String sel4=request.getParameter("sel4");
        String sel5=request.getParameter("sel5");
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        if(acs_event != null ){
            int acs =Integer.parseInt(acs_event);
            if(patient.getJudgeflag()!=acs){
                patient.setJudgeflag(acs);
                judgePatientListRepository.save(patient);
            }
        }

        VitalItem patientVitalItem = queueService.getNowPatientVitalItem(id,vid,0);
        int flag =0;
        if(patientVitalItem.getsTSegmentOffset()!=Integer.parseInt(sel)){
            patientVitalItem.setsTSegmentOffset(Integer.parseInt(sel));
            flag = 1;
        }
        if(patientVitalItem.getKillip()!=Integer.parseInt(sel1)){
            patientVitalItem.setKillip(Integer.parseInt(sel1));
            flag = 1;
        }
        if(patientVitalItem.getCardiacArrest()!=Integer.parseInt(sel2)){
            patientVitalItem.setCardiacArrest(Integer.parseInt(sel2));
            flag = 1;
        }
        if(patientVitalItem.getCongestiveHeartFailureSign()!=Integer.parseInt(sel3)){
            patientVitalItem.setCongestiveHeartFailureSign(Integer.parseInt(sel3));
            flag = 1;
        }
        if(patientVitalItem.getHistoryOfPreviousVascularSystemDiseases()!=Integer.parseInt(sel4)){
            patientVitalItem.setHistoryOfPreviousVascularSystemDiseases(Integer.parseInt(sel4));
            flag = 1;
        }
        if(patientVitalItem.getDiabetes()!=Integer.parseInt(sel5)){
            patientVitalItem.setDiabetes(Integer.parseInt(sel5));
            flag = 1;
        }
        if(flag==1){
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            patientVitalItem.setDateTime(dateTime);
            vitalItemRepository.save(patientVitalItem);
            Score score = scoreService.getScore(id,patient.getVisitID());
        }

//        if(patient.getJudgeflag()>0){
//            patientService.deletePatientListById(id);
//        }
        response.sendRedirect("/enqueue/PageNum="+ nowPage);
    }

    //在列患者界面显示事件记录界面显示
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/Records/{id}/{vid}",method = RequestMethod.GET)
    @ResponseBody
    public  ModelAndView enquueuPatientRecordList(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid) throws IOException{

        ModelAndView modelAndView = new ModelAndView(ENQUEUE2_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
        queueService.setNowEnqueuePage(nowPage);
        List<Queue> enqueueList = queueService.getEnqueueList();
        //列表页面赋值
        int listSize = queueService.getEnqueueListSize();
        int unrecordNum = listSize - queueService.getRecordFlagSum();
        modelAndView.addObject("listSize", listSize);
        modelAndView.addObject("unrecordNum", unrecordNum);
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getEnqueuePage());
        modelAndView.addObject("enqueueList", enqueueList);
        //患者事件记录表赋值
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        modelAndView.addObject("record_modal_flag", 1);
        modelAndView.addObject("patient", patient);

        List<Record> recordList = queueService.getRecordsList(id,vid);
        int nextRecord;
        if(recordList.size()==0){
            nextRecord =1;
        }else{nextRecord = recordList.get(0).getRecordNo()+1;}

        modelAndView.addObject("recordList", recordList);
        modelAndView.addObject("nextRecord", nextRecord);
        modelAndView.addObject("record_modal2_flag", 0);
        Record record = new Record();
        record.setRecordNo(1);

        modelAndView.addObject("thisRecord", record);
        return modelAndView;

    }
    //在列患者界面显示事件记录界面特定记录界面显示
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/Records/{id}/{vid}/{recordNo}",method = RequestMethod.GET)
    public  ModelAndView enquueuPatientRecord(@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid,@PathVariable int recordNo) {

        ModelAndView modelAndView = new ModelAndView(ENQUEUE2_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
        queueService.setNowEnqueuePage(nowPage);
        List<Queue> enqueueList = queueService.getEnqueueList();
        //列表页面赋值
        int listSize = queueService.getEnqueueListSize();
        int unrecordNum = listSize - queueService.getRecordFlagSum();
        modelAndView.addObject("listSize", listSize);
        modelAndView.addObject("unrecordNum", unrecordNum);
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getEnqueuePage());
        modelAndView.addObject("enqueueList", enqueueList);
        //患者事件记录表赋值
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        modelAndView.addObject("record_modal_flag", 1);
        modelAndView.addObject("patient", patient);
//        modelAndView.addObject("patient_id", id);
        modelAndView.addObject("record_modal2_flag", 1);
        List<Record> recordList = queueService.getRecordsList(id,vid);
        int size = recordList.size();
        if(size == 0|| recordNo > recordList.get(0).getRecordNo()){
            Record record = new Record();
            record.setRecordNo(recordNo);
            LocalDate dateTime = LocalDate.now();
            dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            record.setRecordDate(dateTime);
            modelAndView.addObject("thisRecord", record);


        }else {
            Record record = recordList.stream().filter(t -> t.getRecordNo()==recordNo).findFirst().get();
            modelAndView.addObject("thisRecord", record);
        }

        int nextRecord = 1;
        if(recordList.size()!=0){
            nextRecord = recordList.get(0).getRecordNo()+1;
        }
        modelAndView.addObject("recordList", recordList);
        modelAndView.addObject("nextRecord", nextRecord);


        return modelAndView;

    }
    //在列患者界面显示事件记录界面特定记录界面修改或添加信息获取
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/Records/{id}/{vid}/{recordNo}",method = RequestMethod.POST)
    @ResponseBody
    public  void updateEnquueuPatientRecord(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid,@PathVariable int recordNo) throws IOException{


        String qx=request.getParameter("qx");
        String cx=request.getParameter("cx");
        String dead=request.getParameter("dead");
        String recordDateString=request.getParameter("recordDate");

        Record record = new Record();
        LocalDate dateTime = LocalDate.parse(recordDateString);

        if(!qx.isEmpty()||!cx.isEmpty()||!dead.isEmpty()){
            record.setPatientID(id);
            record.setVisitID(vid);
            record.setRecordNo(recordNo);
            record.setBleedEvent(cx);
            record.setIschemiaEvent(qx);
            record.setDeadEvent(dead);

            record.setRecordDate(dateTime);
            record.setUserId(active_user.getUserId());
            queueService.saveRecord(record);
        }


        response.sendRedirect("/enqueue/PageNum="+ nowPage +"/Records/" +id+"/"+ vid);

    }
    //在列患者界面显示事件记录界面特定记录删除
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/Records/{id}/{vid}/delete/{recordNo}",method = RequestMethod.GET)
    @ResponseBody
    public  void deleteEnquueuPatientRecord(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid,@PathVariable int recordNo) throws IOException{

        queueService.deleteRecord(id,vid,recordNo);
        response.sendRedirect("/enqueue/PageNum="+ nowPage +"/Records/" +id+"/"+vid);

    }

    //在列患者出院
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/out/{pid}/{vid}",method = RequestMethod.GET)
    @ResponseBody
    public  void enquueuPatientDischarge(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String pid,@PathVariable int vid) throws IOException{

        List<Record> recordList = queueService.getRecordsList(pid,vid);
        if(recordList.size()==0){

            response.sendRedirect("/enqueue/PageNum="+ nowPage+ "/Records/" + pid+"/"+vid);
        }else{
            queueService.discharge(pid,vid);
            response.sendRedirect("/enqueue/PageNum="+ nowPage);
        }



    }
    //在列患者删除，judgeflag  turn to 1
    @RequestMapping(value = "/enqueue/PageNum={nowPage}/delete/{pid}/{vid}",method = RequestMethod.GET)
    @ResponseBody
    public  void enquueuPatientDelete(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String pid,@PathVariable int vid) throws IOException{

        queueService.deleteEnqueue(pid,vid);

        response.sendRedirect("/enqueue/PageNum="+ nowPage);

    }
    //出院患者界面显示
    @RequestMapping(value = "/dequeue/PageNum={nowPage}",method = RequestMethod.GET)
    public ModelAndView nowPageDequeue(@PathVariable int nowPage){
        ModelAndView modelAndView = new ModelAndView(DEQUEUE_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
        queueService.setNowDequeuePage(nowPage);
//        queueService.searchAllDequeue();
        List<Queue> dequeueList = queueService.getDequeueList();
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getDqueuePage());
        modelAndView.addObject("dequeueList", dequeueList);

        //患者详细信息赋值
        VitalItem patientVitalItem = new VitalItem();
        modelAndView.addObject("patientVitalItem", patientVitalItem);
        modelAndView.addObject("modal_flag", 0);
        UnJudgedPatientList patient = new UnJudgedPatientList();
        Score score = new Score();
        modelAndView.addObject("score", score);
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("patient_id", "0");
        return modelAndView;
    }

    //出院患者界面详细信息界面显示
    @RequestMapping(value = "/dequeue/PageNum={nowPage}/{id}/{vid}",method = RequestMethod.GET)
    public ModelAndView nowPageDequeue(@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid){
        ModelAndView modelAndView = new ModelAndView(DEQUEUE_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
        queueService.setNowDequeuePage(nowPage);
//        queueService.searchAllDequeue();
        List<Queue> dequeueList = queueService.getDequeueList();
        //列表页面赋值
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getDqueuePage());
        modelAndView.addObject("dequeueList", dequeueList);
        //患者详细信息赋值
        VitalItem patientVitalItem = queueService.getNowPatientVitalItem(id,vid,1);
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        Score score = scoreService.getScore(id,vid);
        modelAndView.addObject("patientVitalItem", patientVitalItem);
        modelAndView.addObject("modal_flag", 1);
        modelAndView.addObject("score", score);
        modelAndView.addObject("patient", patient);
        modelAndView.addObject("patient_id", id);
        return modelAndView;

    }

    @RequestMapping(value = "/dequeue/PageNum={nowPage}/{id}/{vid}",method = RequestMethod.POST)
    @ResponseBody
    public  void judgeDequueuPatient(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid) throws IOException{

        String acs_event=request.getParameter("acs_event");
        System.out.println("acs_event:"+ acs_event);
        String sel=request.getParameter("sel");
        String sel1=request.getParameter("sel1");
        String sel2=request.getParameter("sel2");
        String sel3=request.getParameter("sel3");
        String sel4=request.getParameter("sel4");
        String sel5=request.getParameter("sel5");
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        if(acs_event != null ){
            int acs =Integer.parseInt(acs_event);
            if(patient.getJudgeflag()!=acs){
                patient.setJudgeflag(acs);
                judgePatientListRepository.save(patient);
            }
        }

        VitalItem patientVitalItem = queueService.getNowPatientVitalItem(id,vid,1);
        int flag =0;
        if(patientVitalItem.getsTSegmentOffset()!=Integer.parseInt(sel)){
            patientVitalItem.setsTSegmentOffset(Integer.parseInt(sel));
            flag = 1;
        }
        if(patientVitalItem.getKillip()!=Integer.parseInt(sel1)){
            patientVitalItem.setKillip(Integer.parseInt(sel1));
            flag = 1;
        }
        if(patientVitalItem.getCardiacArrest()!=Integer.parseInt(sel2)){
            patientVitalItem.setCardiacArrest(Integer.parseInt(sel2));
            flag = 1;
        }
        if(patientVitalItem.getCongestiveHeartFailureSign()!=Integer.parseInt(sel3)){
            patientVitalItem.setCongestiveHeartFailureSign(Integer.parseInt(sel3));
            flag = 1;
        }
        if(patientVitalItem.getHistoryOfPreviousVascularSystemDiseases()!=Integer.parseInt(sel4)){
            patientVitalItem.setHistoryOfPreviousVascularSystemDiseases(Integer.parseInt(sel4));
            flag = 1;
        }
        if(patientVitalItem.getDiabetes()!=Integer.parseInt(sel5)){
            patientVitalItem.setDiabetes(Integer.parseInt(sel5));
            flag = 1;
        }
        if(flag==1){
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            patientVitalItem.setDateTime(dateTime);
            vitalItemRepository.save(patientVitalItem);
            Score score = scoreService.getScore(id,patient.getVisitID());
            queueService.updateDequeueListById(id,vid,score.getGrace(),score.getCrusade());
        }
//
//        if(patient.getJudgeflag()>0){
//
//        }
        response.sendRedirect("/dequeue/PageNum="+ nowPage);
    }
    //出院患者界面事件记录界面显示
    @RequestMapping(value = "/dequeue/PageNum={nowPage}/Records/{id}/{vid}",method = RequestMethod.GET)
    @ResponseBody
    public  ModelAndView dequueuPatientRecordList(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid) throws IOException{
        ModelAndView modelAndView = new ModelAndView(DEQUEUE2_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
        queueService.setNowDequeuePage(nowPage);
        queueService.searchAllDequeue();
        List<Queue> dequeueList = queueService.getDequeueList();
        //列表页面赋值
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getDqueuePage());
        modelAndView.addObject("dequeueList", dequeueList);
        //患者事件记录表赋值
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        modelAndView.addObject("record_modal_flag", 1);
        modelAndView.addObject("patient", patient);

        List<Record> recordList = queueService.getRecordsList(id,vid);
        int nextRecord;
        if(recordList.size()==0){
            nextRecord =1;
        }else{nextRecord = recordList.get(0).getRecordNo()+1;}

        modelAndView.addObject("recordList", recordList);
        modelAndView.addObject("nextRecord", nextRecord);
        modelAndView.addObject("record_modal2_flag", 0);
        Record record = new Record();
        record.setRecordNo(1);

        modelAndView.addObject("thisRecord", record);
        return modelAndView;

    }
    //出院患者界面事件记录界面特定记录显示
    @RequestMapping(value = "/dequeue/PageNum={nowPage}/Records/{id}/{vid}/{recordNo}",method = RequestMethod.GET)
    public  ModelAndView dequueuPatientRecord(@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid,@PathVariable int recordNo) {

        ModelAndView modelAndView = new ModelAndView(DEQUEUE2_PATH_NAME);
        modelAndView.addObject("active_user", active_user);
        queueService.setNowDequeuePage(nowPage);
        queueService.searchAllDequeue();
        List<Queue> dequeueList = queueService.getDequeueList();
        //列表页面赋值
        modelAndView.addObject("nowPage", nowPage);
        modelAndView.addObject("pageSize", queueService.getDqueuePage());
        modelAndView.addObject("dequeueList", dequeueList);
        //患者事件记录表赋值
        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        modelAndView.addObject("record_modal_flag", 1);
        modelAndView.addObject("patient", patient);
//        modelAndView.addObject("patient_id", id);
        modelAndView.addObject("record_modal2_flag", 1);
        List<Record> recordList = queueService.getRecordsList(id,vid);
        int size = recordList.size();
        if(size == 0|| recordNo > recordList.get(0).getRecordNo()){
            Record record = new Record();
            record.setRecordNo(recordNo);
            LocalDate dateTime = LocalDate.now();
            dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            record.setRecordDate(dateTime);
            modelAndView.addObject("thisRecord", record);


        }else {
            Record record = recordList.stream().filter(t -> t.getRecordNo()==recordNo).findFirst().get();
            modelAndView.addObject("thisRecord", record);
        }

        int nextRecord = 1;
        if(recordList.size()!=0){
            nextRecord = recordList.get(0).getRecordNo()+1;
        }
        modelAndView.addObject("recordList", recordList);
        modelAndView.addObject("nextRecord", nextRecord);


        return modelAndView;

    }
    @RequestMapping(value = "/dequeue/PageNum={nowPage}/Records/{id}/{vid}/{recordNo}",method = RequestMethod.POST)
    @ResponseBody
    public  void updateDequueuPatientRecord(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid,@PathVariable int recordNo) throws IOException{

        UnJudgedPatientList patient = judgePatientListRepository.findFirstByPatientIDAndVisitIDOrderByDiagnosisDateDesc(id,vid);
        String qx=request.getParameter("qx");
        String cx=request.getParameter("cx");
        String recordDateString=request.getParameter("recordDate");

        Record record = new Record();
        LocalDate dateTime = LocalDate.parse(recordDateString);

        if(!qx.isEmpty()||!cx.isEmpty()){
            record.setPatientID(id);
            record.setVisitID(patient.getVisitID());
            record.setRecordNo(recordNo);
            record.setBleedEvent(cx);
            record.setIschemiaEvent(qx);
            record.setRecordDate(dateTime);
            record.setUserId(active_user.getUserId());
            queueService.saveRecord(record);
        }


        response.sendRedirect("/dequeue/PageNum="+ nowPage +"/Records/" +id +"/"+ vid);

    }

    @RequestMapping(value = "/dequeue/PageNum={nowPage}/Records/{id}/{vid}/delete/{recordNo}",method = RequestMethod.GET)
    @ResponseBody
    public  void deleteDequueuPatientRecord(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String id,@PathVariable int vid,@PathVariable int recordNo) throws IOException{

        queueService.deleteRecord(id,vid,recordNo);
        response.sendRedirect("/dequeue/PageNum="+ nowPage +"/Records/" +id+"/"+vid);

    }
    //出院患者界面根据日期以及出院入院类别搜索并显示患者
    @RequestMapping(value = "/dequeue/PageNum={nowPage}/search",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> searchDequeueList(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map3 =new HashMap<String,Object>();
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String selectDate=request.getParameter("selectDate");
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(selectDate);
        if(startDate.length()>0 && endDate.length()>0){
            queueService.addIntoDequeueListByDate(selectDate,startDate,endDate);
            map3.put("result","1");
        }else{
            map3.put("result","请填写完整");
        }
//        String password2=request.getParameter("password2");
//        String userId = request.getParameter("userId");
//        String page=request.getParameter("nowPage");

        return map3;

    }
    //在列患者删除，judgeflag  turn to 1
    @RequestMapping(value = "/dequeue/PageNum={nowPage}/delete/{pid}/{vid}",method = RequestMethod.GET)
    @ResponseBody
    public  void dequueuPatientDelete(HttpServletRequest request, HttpServletResponse response,@PathVariable int nowPage,@PathVariable String pid,@PathVariable String vid) throws IOException{

        queueService.deleteDequeue(pid,Integer.parseInt(vid));
        response.sendRedirect("/dequeue/PageNum="+ nowPage);

    }
}
