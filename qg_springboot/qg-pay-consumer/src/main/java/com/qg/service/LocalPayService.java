package com.qg.service;

import com.qg.dto.ReturnResult;

import java.util.Map;

/***
 * 支付本地业务
 */
public interface LocalPayService {

    public String createAliForm(String orderId)throws Exception;

    public boolean validateAliPay(Map<String,String[]> requestParams)throws Exception;

    public String dealPaySuccess(String orderNo,String tradeNo,Integer payMethod) throws Exception;

    public boolean validateDealPaySuccess(String tradeNo)throws Exception;

    public Map<String,String> getWxMap(String orderId)throws Exception;

    public ReturnResult checkOrderSuccess(String orderId)throws Exception;
}
