package com.kgc.hz.testprovider.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kgc.hz.testcommon.entity.Users;
import com.kgc.hz.testcommon.utils.EmptyUtils;


public class CheckToken {

    /**
     * 验证token是否失效
     *ID 键 token值
     * @param token
     * @param util
     * @return
     */
//    public static boolean checkToken(String token, RedisUtil util) {
//        String user = util.getStr(token);
//        if (EmptyUtils.isNotEmpty(user)) {
//            UserInfo userInfo = JSON.toJavaObject((JSON) JSONObject.parse(user),
//                    UserInfo.class);
//            if (EmptyUtils.isNotEmpty(userInfo)) {
//                String id = String.valueOf(userInfo.getId());
//                String oldtoken = util.getStr(id);
//                if (EmptyUtils.isNotEmpty(oldtoken)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        } else {
//            return false;
//        }
//        return false;
//    }

    /**
     * userName 键 token值
     * @param token
     * @param util
     * @return
     */
    public static boolean checkToken(String token, RedisUtil util) {
        String user = util.getStr(token);
        if (EmptyUtils.isNotEmpty(user)) {
            Users userInfo = JSON.toJavaObject((JSON) JSONObject.parse(user),
                    Users.class);
            if (EmptyUtils.isNotEmpty(userInfo)) {
                //userName 为键 取token
//                String id = userInfo.getUserName();
                String oldtoken = util.getStr(userInfo.getUsername());
                if (EmptyUtils.isNotEmpty(oldtoken)) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return false;
    }

}
