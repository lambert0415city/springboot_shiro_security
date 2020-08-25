package com.qg.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.qg.common.Constants;
import com.qg.config.QgWxPayConfig;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.PayException;
import com.qg.pojo.QgGoods;
import com.qg.pojo.QgOrder;
import com.qg.service.LocalPayService;
import com.qg.service.QgGoodsService;
import com.qg.service.QgOrderService;
import com.qg.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class WxPayController {

    @Autowired
    private QgWxPayConfig qgWxPayConfig;

    @Reference
    private QgGoodsService qgGoodsService;

    @Reference
    private QgOrderService qgOrderService;

    @Autowired
    private LocalPayService localPayService;
    /****
     * 请求微信统一下单接口获取codeUrl
     * @return
     */
    @RequestMapping("/v/reqWxCode")
    @ResponseBody
    public ReturnResult reqWxCode(String orderId)throws Exception{
        Map<String,String> respMap=localPayService.getWxMap(orderId);
        if(respMap.get("return_code").equals("SUCCESS") && respMap.get("result_code").equals("SUCCESS")){
            String codeUrl=respMap.get("code_url");
            Map<String,String> resultMap=new HashMap<String, String>();
            resultMap.put("codeUrl",codeUrl);
            return ReturnResultUtils.returnSuccess(resultMap);
        }else{
            return ReturnResultUtils.returnFail(PayException.WX_PAY_BUZY.getCode(),PayException.WX_PAY_BUZY.getMessage());
        }
    }

    @RequestMapping(value = "/wxPayNotify",method = RequestMethod.POST)
    public synchronized void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.获取xml数据
            String xmlData=getNotifyXML(request);
            //2.将xml数据转化成为map 进行 签名验证
            Map<String,String> xmlMap= WXPayUtil.xmlToMap(xmlData);
            boolean flag=WXPayUtil.isSignatureValid(xmlMap,qgWxPayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
            if(flag){
                //执行业务逻辑
                String out_trade_no=xmlMap.get("out_trade_no");
                String transaction_id=xmlMap.get("transaction_id");
                boolean dealFlag=localPayService.validateDealPaySuccess(transaction_id);
                if(!dealFlag){
                    localPayService.dealPaySuccess(out_trade_no,transaction_id, Constants.PayMethod.wxPay);
                }
                //给微信平台进行反馈
                Map<String,String> resultMap= WXPayUtil.xmlToMap(xmlData);
                resultMap.put("return_code","SUCCESS");
                resultMap.put("return_msg","OK");
                String resultXML=WXPayUtil.mapToXml(resultMap);
                response.getWriter().write(resultXML);
                response.getWriter().flush();
                response.getWriter().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取微信传递的xml
     * @param request
     * @return
     * @throws Exception
     */
    private String getNotifyXML(HttpServletRequest request)throws Exception{
        StringBuffer stringBuffer=new StringBuffer();
        InputStream inputStream=null;
        BufferedReader bufferedReader=null;
        inputStream=request.getInputStream();
        bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        String xml="";
        while ((xml=bufferedReader.readLine())!=null){
            stringBuffer.append(xml);
        }
        if(EmptyUtils.isNotEmpty(bufferedReader)){
            bufferedReader.close();
        }
        if(EmptyUtils.isNotEmpty(inputStream)){
            inputStream.close();
        }
        return stringBuffer.toString();
    }
    /***
     * 轮询查询订单是否支付成功
     * @param orderId
     * @return
     * @throws Exception
     */
    @RequestMapping("/v/checkOrderSuccess")
    @ResponseBody
    public ReturnResult checkOrderSuccess(String orderId)throws Exception{
       return localPayService.checkOrderSuccess(orderId);
    }
}
