package com.qg.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.qg.common.Constants;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.OrderException;
import com.qg.pojo.QgGoods;
import com.qg.pojo.QgOrder;
import com.qg.pojo.QgUser;
import com.qg.service.LocalOderService;
import com.qg.service.QgGoodsService;
import com.qg.service.QgOrderService;
import com.qg.utils.EmptyUtils;
import com.qg.utils.RedisUtil;
import com.qg.vo.GoodsVo;
import com.qg.vo.OrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocalOderServiceImpl implements LocalOderService{

    @Reference
    private QgOrderService qgOrderService;

    @Reference
    private QgGoodsService qgGoodsService;

    @Autowired
    private RedisUtil redisUtil;

    /***
     * 查询订单的业务方法
     * @return
     * @throws Exception
     */
    public ReturnResult<List<OrderVo>> queryOrderList(String token)throws Exception{
        List<OrderVo> orderVoList=null;
        String userJson=redisUtil.getStr(token);
        QgUser qgUser= JSONObject.parseObject(userJson,QgUser.class);
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("userId",qgUser.getId());
        //1.根据用户信息查询订单
        List<QgOrder> qgOrderList=qgOrderService.getQgOrderListByMap(param);
        //2.组合OrderVo
        if(EmptyUtils.isNotEmpty(qgOrderList)){
            orderVoList=new ArrayList<OrderVo>();
            for (QgOrder qgOrder:qgOrderList){
                OrderVo orderVo=new OrderVo();
                BeanUtils.copyProperties(qgOrder,orderVo);
                String goodsVoJson=redisUtil.getStr(Constants.goodsPrefix+orderVo.getGoodsId());
                if(EmptyUtils.isEmpty(goodsVoJson)){
                    QgGoods qgGoods=qgGoodsService.getQgGoodsById(orderVo.getGoodsId());
                    orderVo.setGoodsImg(qgGoods.getGoodsImg());
                }else{
                    GoodsVo goodsVo=JSONObject.parseObject(goodsVoJson,GoodsVo.class);
                    orderVo.setGoodsImg(goodsVo.getGoodsImg());
                }
                orderVoList.add(orderVo);
            }
            return ReturnResultUtils.returnSuccess(orderVoList);
        }
        return ReturnResultUtils.returnSuccess(null);
    }


    /***
     * 根据订单id查询订单信息（订单编号和订单金额）
     */
    public ReturnResult<QgOrder> queryOrderById(String orderId,String token)throws Exception{
        QgOrder qgOrder=qgOrderService.getQgOrderById(orderId);
        String userJson=redisUtil.getStr(token);
        QgUser qgUser= JSONObject.parseObject(userJson,QgUser.class);
        //如果没有查询到订单或者非法查询（查询别人的订单）则返回没找到订单
        if(EmptyUtils.isEmpty(qgOrder) || !qgOrder.getUserId().equals(qgUser.getId())){
            return ReturnResultUtils.returnFail(OrderException.ORDER_NOT_EXIST.getCode(),OrderException.ORDER_NOT_EXIST.getMessage());
        }
        return ReturnResultUtils.returnSuccess(qgOrder);
    }

}
