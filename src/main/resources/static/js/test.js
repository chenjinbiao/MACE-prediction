function showPic(e,sUrl){
    var x,y;
    x = e.clientX;
    y = e.clientY;
    document.getElementById("Layer1").style.left = x - 500+'px';
    document.getElementById("Layer1").style.top = y - 200+'px';
    document.getElementById("Layer1").innerHTML = "<img border='0' src=\"" + sUrl + "\">";
    document.getElementById("Layer1").style.display = "";
}
function hiddenPic(){
    document.getElementById("Layer1").innerHTML = "";
    document.getElementById("Layer1").style.display = "none";
}

// function discharge(patientId,visitId) {
//     $.ajax({
//         type: "POST",
//         url: "/enqueue/PageNum={nowPage}/Records/Out",
//         data: {
//             "patientId": patientId,
//             "visitId": visitId
//         },
//         dataType: "json",
//         success: function (data) {
//             if (data.result == "1") {
//                 window.location.href = "/enqueue/PageNum=1";
//             } else {
//                 window.location.href = "/enqueue/PageNum=1";
//                 // var tip = document.getElementById("tip");
//                 // tip.innerText= "请输入账号或密码";
//                 alert(data.result);
//             }
//         }
//     })
// }
// function out(e)  {
//     var p= e;
//     var url1= "/enqueue/PageNum={nowPage}/out/{"+p+ "}";
//     $.ajax({
//         type : "POST",
//         url : url1,
//         data : {
//             "patientId" : p
//             // "visitId" : $("#visitId").val()
//         },
//         dataType : "json",
//         success : function(data) {
//             if (data.result == "1") {
//                 window.location.href = "/enqueue/PageNum=1" ;
//                 // var size = data.lastpage.toString();
//                 // alert(size);
//
//             } else {
//                 // window.location.href ="/main";
//                 // var tip = document.getElementById("tip");
//                 // tip.innerText= "请输入账号或密码";
//                 alert(data.result);
//             }
//         }
//     });
// }

function jsGetAge(strBirthday)
{
    var returnAge;
    var strBirthdayArr1 = strBirthday.split(" ");
    var strBirthdayArr1=strBirthday.split("-");
    var birthYear = strBirthdayArr[0];
    var birthMonth = strBirthdayArr[1];
    var birthDay = strBirthdayArr[2];

    var d = new Date();
    var nowYear = d.getYear();
    var nowMonth = d.getMonth() + 1;
    var nowDay = d.getDate();

    if(nowYear == birthYear)
    {
        returnAge = 0;//同年 则为0岁
    }
    else
    {
        var ageDiff = nowYear - birthYear ; //年之差
        if(ageDiff > 0)
        {
            if(nowMonth == birthMonth)
            {
                var dayDiff = nowDay - birthDay;//日之差
                if(dayDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
            else
            {
                var monthDiff = nowMonth - birthMonth;//月之差
                if(monthDiff < 0)
                {
                    returnAge = ageDiff - 1;
                }
                else
                {
                    returnAge = ageDiff ;
                }
            }
        }
        else
        {
            returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
        }
    }

    return returnAge;//返回周岁年龄

}
function showModal(id) {

    // var modal = document.getElementById('myModal');
    var modal1 = document.getElementById(id);
    modal1.style.display = "block";

// 点击 <span> 元素上的 (x), 关闭模态框
}

function closeModal(id) {
    var modal1 = document.getElementById(id);
    modal1.style.display = "none";
}
function showBarc() {
    var barc = document.getElementById('barc');
    barc.style.display = "block";
}
function closeBarc() {
    var barc = document.getElementById('barc');
    barc.style.display = "none";
}
function showAcs() {
    var acs = document.getElementById('acs');
    acs.style.display = "block";
}
function closeAcs() {
    var acs = document.getElementById('acs');
    acs.style.display = "none";
}
function showModal1() {
    var modal = document.getElementById('myModal');

    // 获取图片模态框，alt 属性作为图片弹出中文本描述
    var img = document.getElementById('myImg');
    var modalImg = document.getElementById("img01");
    var captionText = document.getElementById("caption");
    img.onclick = function(){
        modal.style.display = "block";
        modalImg.src = this.src;
        modalImg.alt = this.alt;
        captionText.innerHTML = this.alt;
    }

    // 获取 <span> 元素，设置关闭模态框按钮
    var span = document.getElementsByClassName("close")[0];

    // 点击 <span> 元素上的 (x), 关闭模态框
    span.onclick = function() {
        modal.style.display = "none";
    }

}
function test(){
    $("#myImg").click(function () {
        task = "add";
        initData();
        // $('#myModalLabel').text('新增教程');
        $("#myModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });

    });

}

function turnPage(){
    //翻页
    var p =document.getElementsByClassName("page-item");
    var img = document.getElementById('myImg');
    $('#cancelSave').click(function(){
        $("#myModal").modal('hide');
    });
    $("#page").click(function (e) {
        $.ajax({
            type : "POST",
            url : "/main",
            data : {
                "startTime" : $("#startTime").val(),
                "endTime" : $("#endTime").val()
            },
            dataType : "json",
            success : function(data) {
                if (data.result == "1") {
                    window.location.href ="/main";
                } else {
                    window.location.href ="/main";
                    // var tip = document.getElementById("tip");
                    // tip.innerText= "请输入账号或密码";
                    alert(data.result);
                }
            }
        });
    });
}