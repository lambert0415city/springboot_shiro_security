package com.qg.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.github.wxpay.sdk.WXPay;
import com.qg.common.Constants;
import com.qg.config.AlipayConfig;
import com.qg.config.QgWxPayConfig;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.pojo.QgGoods;
import com.qg.pojo.QgGoodsTempStock;
import com.qg.pojo.QgOrder;
import com.qg.pojo.QgTrade;
import com.qg.service.*;
import com.qg.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class LocalPayServiceImpl implements LocalPayService{

    @Autowired
    private AlipayConfig alipayConfig;

    @Reference
    private QgOrderService qgOrderService;

    @Reference
    private QgGoodsService qgGoodsService;

    @Reference
    private QgTradeService qgTradeService;

    @Reference
    private QgGoodsTempStockService qgGoodsTempStockService;

    @Autowired
    private QgWxPayConfig qgWxPayConfig;

    @Override
    public String createAliForm(String orderId) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        QgOrder qgOrder=qgOrderService.getQgOrderById(orderId);
        QgGoods qgGoods=qgGoodsService.getQgGoodsById(qgOrder.getGoodsId());
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ qgOrder.getOrderNo() +"\","
                + "\"total_amount\":\""+ qgOrder.getAmount() +"\","
                + "\"subject\":\""+ qgGoods.getGoodsName() +"\","
                + "\"body\":\""+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    @Override
    public boolean validateAliPay(Map<String,String[]> requestParams) throws Exception {
        Map<String,String> params = new HashMap<String,String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
    }

    @Override
    public String dealPaySuccess(String orderNo,String tradeNo,Integer payMethod) throws Exception {
        QgOrder qgOrder=qgOrderService.queryQgOrderByNo(orderNo);
        //1.保存交易记录
        saveTrade(qgOrder.getAmount(),orderNo,tradeNo,payMethod);
        //2.修改订单状态
        updateOrder(qgOrder);
        //3.修改库存状态
        updateStock(qgOrder.getStockId());
        return qgOrder.getId();
    }

    @Override
    public boolean validateDealPaySuccess(String tradeNo) throws Exception {
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("tradeNo",tradeNo);
        Integer count=qgTradeService.getQgTradeCountByMap(param);
        return count>0;
    }

    @Override
    public Map<String, String> getWxMap(String orderId) throws Exception {
        WXPay wxpay = new WXPay(qgWxPayConfig);
        Map<String, String> data = new HashMap<String, String>();
        QgOrder qgOrder=qgOrderService.getQgOrderById(orderId);
        QgGoods qgGoods=qgGoodsService.getQgGoodsById(qgOrder.getGoodsId());
        data.put("body", qgGoods.getGoodsName());
        data.put("out_trade_no", qgOrder.getOrderNo());
        data.put("fee_type", "CNY");
//        data.put("total_fee", new Double(qgOrder.getAmount()*100).intValue()+"");
        data.put("total_fee", "1");
        data.put("spbill_create_ip", "123.12.12.123");
        data.put("notify_url", qgWxPayConfig.getNotifyUrl());
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        data.put("product_id", qgGoods.getId());
        return wxpay.unifiedOrder(data);
    }

    private void saveTrade(double amount,String orderNo,String tradeNo,Integer payMethod) throws Exception {
        QgTrade qgTrade=new QgTrade();
        qgTrade.setId(IdWorker.getId());
        qgTrade.setAmount(amount);
        qgTrade.setCreatedTime(new Date());
        qgTrade.setOrderNo(orderNo);
        qgTrade.setPayMethod(payMethod);
        qgTrade.setTradeNo(tradeNo);
        qgTrade.setUpdatedTime(new Date());
        qgTradeService.qdtxAddQgTrade(qgTrade);
    }

    private void updateOrder(QgOrder qgOrder)throws Exception{
        qgOrder.setStatus(Constants.OrderStatus.paySuccess);
        qgOrder.setUpdatedTime(new Date());
        qgOrderService.qdtxModifyQgOrder(qgOrder);
    }

    private void updateStock(String stockId)throws Exception{
        QgGoodsTempStock qgGoodsTempStock=qgGoodsTempStockService.getQgGoodsTempStockById(stockId);
        qgGoodsTempStock.setStatus(Constants.StockStatus.paySuccess);
        qgGoodsTempStock.setUpdatedTime(new Date());
        qgGoodsTempStockService.qdtxModifyQgGoodsTempStock(qgGoodsTempStock);
    }

    public ReturnResult checkOrderSuccess(String orderId)throws Exception{
        QgOrder qgOrder=qgOrderService.getQgOrderById(orderId);
        boolean flag= (qgOrder.getStatus()==Constants.OrderStatus.paySuccess);
        Map<String,Boolean> result=new HashMap<String,Boolean>();
        result.put("flag",flag);
        return ReturnResultUtils.returnSuccess(result);
    }
}
