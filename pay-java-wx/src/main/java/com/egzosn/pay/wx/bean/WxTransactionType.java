package com.egzosn.pay.wx.bean;

import com.alibaba.fastjson.JSONObject;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.TransactionType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信交易类型
 * @author egan
 *
 * email egzosn@gmail.com
 * date 2016/10/19 22:58
 */
public enum  WxTransactionType implements TransactionType {
    /**
     * 公众号支付
     */
    JSAPI("pay/unifiedorder") {
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            parameters.put("openid", order.getOpenid());
        }
    },
    /**
     * 扫码付
     */
    NATIVE("pay/unifiedorder") {
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            parameters.put("product_id", order.getOutTradeNo());
        }
    },
    /**
     * 移动支付
     */
    APP("pay/unifiedorder"),
    /**
     * H5支付
     */
    MWEB("pay/unifiedorder"){
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            //H5支付专用
            LinkedHashMap value = new LinkedHashMap();
            value.put("type", "Wap");
            //WAP网站URL地址
            value.put("wap_url", order.getWapUrl());
            //WAP 网站名
            value.put("wap_name", order.getWapName());
            JSONObject sceneInfo = new JSONObject();
            sceneInfo.put("h5_info", value);
            parameters.put("scene_info", sceneInfo.toJSONString());
        }
    },
    /**
     * 刷卡付
     */
    MICROPAY("pay/micropay"){
        @Override
        public void setAttribute(Map<String, Object> parameters, PayOrder order) {
            parameters.put("auth_code", order.getAuthCode());
            parameters.remove("notify_url");
            parameters.remove("trade_type");
        }
    },
    // TODO 2017/3/8 19:14 author: egan  交易辅助接口
    /**
     * 查询订单
     */
    QUERY("pay/orderquery"),
    /**
     * 关闭订单
     */
    CLOSE("pay/closeorder"),
    /**
     * 申请退款
     */
    REFUND("secapi/pay/refund"),
    /**
     * 查询退款
     */
    REFUNDQUERY("pay/refundquery"),
    /**
     * 下载对账单
     */
    DOWNLOADBILL("pay/downloadbill"),
    /**
     * 银行卡转账
     */
    @Deprecated
    BANK("mmpaysptrans/pay_bank"),
    /**
     *  转账查询
     */
    @Deprecated
    QUERY_BANK("mmpaysptrans/query_bank")
    ;

    WxTransactionType(String method) {
        this.method = method;
    }

    private String method;

    @Override
    public String getType() {
        return this.name();
    }
    @Override
    public String getMethod() {
        return this.method;
    }
    public  void setAttribute(Map<String, Object> parameters, PayOrder order){

    }
}
