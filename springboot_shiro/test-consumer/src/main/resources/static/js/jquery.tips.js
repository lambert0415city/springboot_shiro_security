/**
* jquery tips 提示插件 jquery.tips.js v0.1beta
*
* 使用方法
* $(selector).tips({   //selector 为jquery选择器
*  msg:'your messages!',    //你的提示消息  必填
*  side:1,  //提示窗显示位置  1，2，3，4 分别代表 上右下左 默认为1（上） 可选
*  color:'#FFF', //提示文字色 默认为白色 可选
*  bg:'#F00',//提示窗背景色 默认为红色 可选
*  time:2,//自动关闭时间 默认2秒 设置0则不自动关闭 可选
*  x:0,//横向偏移  正数向右偏移 负数向左偏移 默认为0 可选
*  y:0,//纵向偏移  正数向下偏移 负数向上偏移 默认为0 可选
* })
*
*
*/
(function ($) {
    $.fn.tips = function(options){
        var defaults = {
            side:1,
            msg:'',
            color:'#FFF',
            bg:'#F00',
            time:5,
            x:0,
            y:0
        };
        var options = $.extend(defaults, options);
        if (!options.msg||isNaN(options.side)) {
        throw new Error('params error');
    }
    if(!$('#jquery_tips_style').length){
        var style='<style id="jquery_tips_style" type="text/css">';
        style+='.jq_tips_box{padding:10px;position:absolute;overflow:hidden;display:inline;display:none;z-index:10176523;}';
        style+='.jq_tips_arrow{display:block;width:0px;height:0px;position:absolute;}';
        style+='.jq_tips_top{border-left:10px solid transparent;left:20px;bottom:0px;}';
        style+='.jq_tips_left{border-top:10px solid transparent;right:0px;top:18px;}';
        style+='.jq_tips_bottom{border-left:10px solid transparent;left:20px;top:0px;}';
        style+='.jq_tips_right{border-top:10px solid transparent;left:0px;top:18px;}';
        style+='.jq_tips_info{word-wrap: break-word;word-break:normal;border-radius:4px;padding:5px 8px;max-width:130px;overflow:hidden;box-shadow:1px 1px 1px #999;font-size:12px;cursor:pointer;}';
        style+='</style>';
        $(document.body).append(style);
    }
        this.each(function(){
            var element=$(this);
            var element_top=element.offset().top,element_left=element.offset().left,element_height=element.outerHeight(),element_width=element.outerWidth();
            options.side=options.side<1?1:options.side>4?4:Math.round(options.side);
            var sideName=options.side==1?'top':options.side==2?'right':options.side==3?'bottom':options.side==4?'left':'top';
            var tips=$('<div class="jq_tips_box"><i class="jq_tips_arrow jq_tips_'+sideName+'"></i><div class="jq_tips_info">'+options.msg+'</div></div>').appendTo(document.body);
            tips.find('.jq_tips_arrow').css('border-'+sideName,'10px solid '+options.bg);
            tips.find('.jq_tips_info').css({
                color:options.color,
                backgroundColor:options.bg
            });
            switch(options.side){
                case 1:
                    tips.css({
                        top:element_top-tips.outerHeight()+options.x,
                        left:element_left-10+options.y
                    });
                    break;
                case 2:
                    tips.css({
                        top:element_top-20+options.x,
                        left:element_left+element_width+options.y
                    });
                    break;
                case 3:
                    tips.css({
                        top:element_top+element_height+options.x,
                        left:element_left-10+options.y
                    });
                    break;
                case 4:
                    tips.css({
                        top:element_top-20+options.x,
                        left:element_left-tips.outerWidth()+options.y
                    });
                    break;
                default:
            }
            var closeTime;
            if(options.time){
                closeTime=setTimeout(function(){
                    tips.click();
                },options.time*1000);
                tips.hover(function(){
                    clearTimeout(closeTime);
                },function(){
                    closeTime=setTimeout(function(){
                        tips.click();
                    },options.time*1000);
                });
            }
            tips.fadeIn('fast').click(function(){
                clearTimeout(closeTime);
                tips.fadeOut('fast',function(){
                    tips.remove();
                });
            });
        });
        return this;
    };
})(jQuery);

function getFileUrl(fileId) { 
	var url; 
	var file = document.getElementById(fileId);
	var agent = navigator.userAgent;
	if (agent.indexOf("MSIE")>=1) {
	url = file.value; 
	} else if(agent.indexOf("Firefox")>0) { 
	url = window.URL.createObjectURL(file.files.item(0)); 
	} else if(agent.indexOf("Chrome")>0) {
	url = window.URL.createObjectURL(file.files.item(0)); 
	} 
	return url; 
} ;
function changeTempimg(){
	var file = document.getElementById("file");
	if(file.value==''){
	//设置默认图片
	document.getElementById("tempimg").src='static/images/zwtp1.gif';
	}else{
		document.getElementById("tempimg").src=getFileUrl("file");
	}
};

function showSelectImg(fileInputId,imgId) {
	var obj = document.getElementById(fileInputId);
	var img = document.getElementById(imgId);
	var imgSrc = "";
	if(obj.value) {//上传图片
		if(obj.files && obj.files[0]) {
			var imgType="image/jpg,image/jpeg,image/png,image/gif";
			var type = file.files[0].type;
			imgSrc = window.URL.createObjectURL(obj.files.item(0));
			if(imgType.indexOf(type) == -1){
				alert("请上传JPG、JPEG或PNG格式图片！");
				return false;
			} else {
				img.src = imgSrc;
			}
		} else {
			if("gif,jpg,jpeg,png".indexOf(obj.value.split('.').pop().toLowerCase()) == -1) {
				alert("请上传JPG、JPEG或PNG格式图片！");
				return false;
			} else {
				obj.select();
				imgSrc = document.selection.createRange().text;
				var localImagId = img.parentNode;
				localImagId.setAttribute("class","logowh");
				//必须设置初始大小
				localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				img.style.display = "none";
				document.selection.empty();
			}
		}
	}
 }