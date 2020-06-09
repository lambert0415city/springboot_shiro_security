$(function(){
    $("#deleteList").click(function () {
        if(confirm("批量删除？")){
            var ids = $(".cbx:checked");
            var id=[];
            for(var i =0;i<ids.length;i++){
                id[i]=$(ids[i]).val();
            }
            alert(id);
            $.ajax({
                url:"deletes",
                type:"get",
                data:"ids="+id,
                dataType:"json",
                success:function(result){
                    alert(result);
                    if(result){
                        alert(result);
                        alert("批量删除成功!");
                        location.href="showenterprise";
                    }
                }
            });
        }

    })
    searchPage(null);

    // $("#selectAll").click(function () {
    //     if($(":checkbox").attr("checked") != "checked" ){
    //         $(":checkbox").attr("checked", "checked");
    //     }else{
    //         $(":checkbox").removeAttribute("checked");
    //     }
    // })

});


function selectAll() {
    /*if($(":checkbox").attr("checked") != "checked" ){
        alert("1");
        $(":checkbox").attr("checked", "checked");
    }else{
        alert("2");
        $(":checkbox").removeAttr("checked");
    }*/
    if($(":checkbox[class='cbx']").prop("checked")){
        $(":checkbox[class='cbx']").prop("checked",false);
    }else{
        $(":checkbox[class='cbx']").prop("checked",true);
    }

}

//分页查询
function searchPage(param){
    if(param==null){
        param={};
        param.curPage=1;
        param.pageSize=3;
    }else{
        param.pageSize=3;
    }
    $.ajax({
        url:"enterprise",
        type:"get",
        data:param,
        success:function(result){
            if(result!=null){
                $(".one").html(result);
            }
        }
    });
}

function modify(id) {
    location.href="updatetalent/"+id;
}

function del(id) {
    if(confirm("确定删除？")){
        $.ajax({
            url:"delete/"+id,
            type:"get",
            dataType:"json",
            success:function(result){
                if(result !=null){
                    alert(JSON.stringify(result));
                    if(result.status == 4){
                        alert("删除成功");
                        location.href="showenterprise";
                    }else if(result.status == 5){
                        alert(result.data);
                    }else {
                        alert(result.msg);
                    }
                }
            }
        });
    }

}

function falsedel(id) {
    if(confirm("确定假删除？")){
        $.ajax({
            url:"falsedelete?id="+id,
            type:"get",
            success:function(result){
                alert(result);
                if(result !=null){
                    if(result == true){
                        alert("删除成功");
                        location.href="showenterprise";
                    }
                }
            }
        });
    }

}

//下一页
function nextPage(){
    var curPage = $(".curPage").text();
    var totalPageCount =$(".totalPageCount").text();
    if(curPage<totalPageCount){
        curPage++;
        var param={};
        param.curPage=curPage;
        searchPage(param);
    }else{
        alert("已经是最后一页了");
    }

}

//首页
function firstPage(){
    searchPage(null);
}

//上一页
function prePage(){
    var curPage = $(".curPage").text();
    if(curPage>1){
        curPage--;
        var param={};
        param.curPage=curPage;
        searchPage(param);
    }else{
        alert("已经是首页了");
    }
}
//末页
function endPage(){
    var curPage = $(".curPage").text();
    var totalPageCount =$(".totalPageCount").text();
    if(curPage<totalPageCount){
        var param={};
        param.curPage=totalPageCount;
        searchPage(param);
    }else{
        alert("已经是最后一页了");
    }
}