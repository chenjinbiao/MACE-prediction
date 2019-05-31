
---------------------------------------------------
针对identifier的分割
-------------------------------------------------
CREATE function Get_StrArrayStrOfIndex
  (
    @str varchar(1024), --要分割的字符串
    @split varchar(10), --分隔符号
    @index int --取第几个元素
  )
  returns varchar(1024)
as
  begin
    declare @location int
    declare @start int
    declare @next int
    declare @seed int

    set @str=ltrim(rtrim(@str))
    set @start=1
    set @next=1
    set @seed=len(@split)

    set @location=charindex(@split,@str)
    while @location<>0 and @index>@next
      begin
        set @start=@location+@seed
        set @location=charindex(@split,@str,@start)
        set @next=@next+1
      end
    if @location =0 select @location =len(@str)+1
    --这儿存在两种情况：1、字符串不存在分隔符号 2、字符串中存在分隔符号，跳出while循环后，@location为0，那默认为字符串后边有一个分隔符号。

    return substring(@str,@start,@location-@start)
  end;

select  distinct identifier,patientID,visitID,itemName,referenceUnits from TestItemClipped,AllPatients where dbo.Get_StrArrayStrOfIndex(identifier,'|',1) = AllPatients.patientID and dbo.Get_StrArrayStrOfIndex(identifier,'|',2) = AllPatients.visitID;
select  distinct identifier,a.patientID,a.visitID,t.itemName,t.result,t.resultDateTime from TestItem t,AllPatients a where dbo.Get_StrArrayStrOfIndex(t.identifier,'|',1) = a.patientID and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',2) = a.visitID ;


CREATE function Get_StrArrayStrOfIndex
  (
    @str varchar(1024), --要分割的字符串
    @split varchar(10), --分隔符号
    @index int --取第几个元素
  )
  returns varchar(1024)
as
  begin
    declare @location int
    declare @start int
    declare @next int
    declare @seed int

    set @str=ltrim(rtrim(@str))
    set @start=1
    set @next=1
    set @seed=len(@split)

    set @location=charindex(@split,@str)
    while @location<>0 and @index>@next
      begin
        set @start=@location+@seed
        set @location=charindex(@split,@str,@start)
        set @next=@next+1
      end
    if @location =0 select @location =len(@str)+1
    --这儿存在两种情况：1、字符串不存在分隔符号 2、字符串中存在分隔符号，跳出while循环后，@location为0，那默认为字符串后边有一个分隔符号。

    return substring(@str,@start,@location-@start)
  end;

select  distinct identifier,patientID,visitID,itemName,referenceUnits from TestItemClipped,AllPatients where dbo.Get_StrArrayStrOfIndex(identifier,'|',1) = AllPatients.patientID and dbo.Get_StrArrayStrOfIndex(identifier,'|',2) = AllPatients.visitID;
select  distinct identifier,a.patientID,a.visitID,t.itemName,t.result,t.resultDateTime from TestItem t,AllPatients a where dbo.Get_StrArrayStrOfIndex(t.identifier,'|',1) = a.patientID and dbo.Get_StrArrayStrOfIndex(t.identifier,'|',2) = a.visitID ;

----------------------------------------------------------

CREATE TABLE [DiagnosisIn] (
  [identifier] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [diagnosisDate] datetime  NULL,
  [diagnosisDesc] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [diagnosisNO] int  NULL,
  [diagnosisType] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [operTreatIndicator] bit  NULL,
  [patientID] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [treatDays] int  NULL,
  [treatResult] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [visitID] int  NULL
    PRIMARY KEY (identifier)
);
CREATE TABLE [VitalSignSpe] (
  [identifier] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [patientID] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [recordingTime] datetime  NULL,
  [signs] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [timePoint] datetime  NULL,
  [units] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [measureResult] decimal(18)  NULL,
  [visitID] int  NULL,
  [signValues] decimal(18)  NULL
   PRIMARY KEY (identifier)
)

CREATE TABLE [TestItem] (
  [identifier] nvarchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [abnormalIndicator] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [itemCode] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [itemNO] int  NULL,
  [itemName] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [referenceUnits] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [referenceValue] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [result] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [resultDateTime] datetime  NULL,
  [units] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [reportItemCode] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL,
  [reportItemName] nvarchar(max) COLLATE Chinese_PRC_CI_AS  NULL
   PRIMARY KEY (identifier)
)
CREATE TABLE users(
  userId nvarchar(32) NOT NULL ,
  name nvarchar(32)DEFAULT NULL,
  password  nvarchar(32)DEFAULT NULL,
  PRIMARY KEY (userId)
)  ;
CREATE TABLE AllPatients(
  patientID nvarchar(32) NOT NULL ,
  visitID int NOT NULL,
  diagnosisDate DATETIME DEFAULT  '0000-00-00 00:00:00',
  kinName  nvarchar(32)DEFAULT NULL,
  sex  nvarchar(32)DEFAULT NULL,
  dateOfBirth   DATETIME  DEFAULT  '0000-00-00 00:00:00',
  judgeflag int DEFAULT 0,
  age int DEFAULT 0,
  diagnosisDesc  nvarchar(100)DEFAULT NULL,
  PRIMARY KEY (patientID,visitID)
)  ;

CREATE TABLE 	SCORE(
  patientID nvarchar(32) NOT NULL ,
  visitID int NOT NULL,
  date_time DateTime NOT NULL,
  GRACE NUMERIC  DEFAULT NULL,
  CRUSADE  NUMERIC DEFAULT NULL,
  ischemiaModelScore  float DEFAULT NULL,
  bleedModelScore   float  DEFAULT  NULL,
  ischemiaDoctorScore  NUMERIC DEFAULT NULL,
  bleedDoctorScore   NUMERIC  DEFAULT  NULL,
  PRIMARY KEY (patientID,visitID,date_time)
)  ;

CREATE TABLE 	VitalItem(
  patientID nvarchar(32) NOT NULL ,
  visitID int NOT NULL,
  date_time DateTime NOT NULL,
  height float  DEFAULT  NULL,
  weight  float DEFAULT NULL,
  heartRate  float DEFAULT NULL,
  temperature   float  DEFAULT  NULL,
  systolicBloodPressure   float  DEFAULT  NULL,
  diastolicBloodPressure    float  DEFAULT  NULL,
  creatinine   float  DEFAULT  NULL,
  creatinineClearance   float  DEFAULT  NULL,
  redBloodCellVolumeRatio   float  DEFAULT  NULL,
  elevatedHeartMarkers   NUMERIC  DEFAULT  NULL,
  diabetes  int  DEFAULT  NULL,
  historyOfPreviousVascularSystemDiseases  int  DEFAULT  NULL,
  cardiacArrestDiabetes  NUMERIC  DEFAULT  NULL,
  sTSegmentOffset  int  DEFAULT  NULL,
  congestiveHeartFailureSign  INT  DEFAULT  NULL,
  killip  INT  DEFAULT  NULL,
  PRIMARY KEY (patientID,visitID,date_time)
)  ;


CREATE TABLE 	Record(
  patientID nvarchar(32) NOT NULL ,
  visitID int NOT NULL,
  recordNo int NOT NULL,
  recordDate Date  DEFAULT  NULL,
  ischemiaEvent  nvarchar(32) DEFAULT NULL,
  bleedEvent  nvarchar(32) DEFAULT NULL,
  userId  nvarchar(32) DEFAULT NULL,


  PRIMARY KEY (patientID,visitID,recordNo)
)  ;

CREATE TABLE 	DiagOut(
  patientID nvarchar(32) NOT NULL ,
  visitID int NOT NULL,
  diagnosisDate DateTime  DEFAULT  NULL,
  dischargeDate DateTime DEFAULT NULL,
  PRIMARY KEY (patientID,visitID)
)  ;