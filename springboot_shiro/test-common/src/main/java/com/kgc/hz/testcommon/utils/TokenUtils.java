package com.kgc.hz.testcommon.utils;

import java.text.ParseException;
import java.util.Date;

/***
 * 生成token的工具类
 */
public class TokenUtils {



    public static String createToken(String prifix, String tail){
        String token= null;
        try {
            String timestemp=DateUtil.format(new Date(),"YYYY-MM-dd hh:mm:ss");
            String source=prifix+tail+timestemp;
            token= MD5.getMd5(source,16);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 保单token
     * @param prefix
     * @param productTypeId
     * @return
     */
    public static String createPolicyId(String prefix, int productTypeId){
        String token= null;
        try {
            String timestemp=DateUtil.format(new Date(),"YYYY-MM-dd hh:mm:ss");
            token = SecurityUtils.md5Hex(prefix + timestemp + productTypeId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static String createCustomerCode(String prefix, String cname){
        String token= null;
        try {
            String timestemp=DateUtil.format(new Date(),"YYYY-MM-dd hh:mm:ss");
            token = SecurityUtils.md5Hex(prefix + timestemp + cname);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return token;
    }

}
