
//设置cookie
window.createCookie=function(tokenName,tokenvalue,expireday){
    var curtime = new Date();
    curtime.setTime(curtime.getTime()+expireday);
    document.cookie = tokenName+"="+tokenvalue+"_"+curtime.getTime();
}

//获取cookie
window.getCookie=function(tokenName){
    if(document.cookie.length>0){
        var cookie = document.cookie;
        var tk = cookie.split(tokenName+"=");
        var token = tk[1].split("_")[0];
        var expiretime = tk[1].split("_")[1];
        var curtime = new Date();
        var currentTime = curtime.getTime();
        //判断cookie中的token是否失效
        if(tokenName=="token"){
            expiretime = expiretime.split(";")[0];
        }
        if(tokenName=="userName"){
            expiretime = expiretime.split(";")[0];
        }
        if(currentTime<expiretime){
            return token;
        }else{
            return null;
        }
    }
}