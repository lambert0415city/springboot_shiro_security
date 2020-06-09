$(function () {
    $("#newgroup").on("click",function(){
        //用layer弹窗
        // window.location.href = "createBook.jsp";
        layer.open({
            type: 2,
            title: '新增群组',
            shadeClose: true,
            shade: false,
            skin:"layui-layer-molv",
            area: ['800px', '400px'],
            anim:4,
            content: 'subpages/create.html'
        });
    })

    $("#newapply").on("click",function(){
        //用layer弹窗
        // window.location.href = "createBook.jsp";
        layer.open({
            type: 2,
            title: '新增群组',
            shadeClose: true,
            shade: false,
            skin:"layui-layer-molv",
            area: ['800px', '400px'],
            anim:4,
            content: 'subpages/newapply.html'
        });
    })

    $("#newinvite").on("click",function(){
        layer.open({
            type: 2,
            title: '新增邀请',
            shadeClose: true,
            shade: false,
            skin:"layui-layer-molv",
            area: ['800px', '400px'],
            anim:4,
            content: 'subpages/newinvite.html'
        });
    })

});

//修改（与增加共享）
function change(id){
    //用layer弹窗
    // window.location.href = "modifyBook.jsp?bid="+bid;
    layer.open({
        type: 2,
        title: '修改群组',
        shadeClose: true,
        skin:"layui-layer-molv",
        anim:5,
        area: ['800px', '400px'],
        content: "subpages/change.html?bid="+id
    });
}