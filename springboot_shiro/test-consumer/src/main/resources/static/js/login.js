$(function(){
    $("#loginBtn").click(function(){
        var userName = $("#userName").val();
        var pwd = $("#password").val();
        if(checkParam(userName,pwd)){
            var param={};
            param.username = userName;
            param.password = pwd;
            $.ajax({
                url:"../loginuser",
                type:"post",
                data:param,
                // dataType:"json",
                success:function(result){
                    alert(JSON.stringify(result));
                    if(result!=""){
                        if(result.result==true){
                            alert("登录成功");
                            createCookie("token",result.data,10*60*1000);
                            createCookie("userName",userName,10*60*1000);
                            location.href="index.html";
                        }else {
                            alert(result.data);
                        }
                    }
                }
            });
        }else{
            alert("用户名或密码为空");
        }
    });
});

function checkParam(userName,pwd){
    if(userName==""){
        return false;
    }
    if(pwd==""){
        return false;
    }
    return true;
}