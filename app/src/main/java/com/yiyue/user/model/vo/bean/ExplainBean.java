package com.yiyue.user.model.vo.bean;

/**
 * Created by Lizhuo on 2018/10/26.
 * 推荐用户说明
 */
public class ExplainBean {
    /**
     "data": {
     "explain": "1、被邀请人必须使用邀请人的邀请链接（注意必须是完整的邀请链接）或填写邀请码注册。\n2、奖励类型，系统默认奖励为意约代币奖励，可自由选择为余额钱包奖励。\n3、奖励代币可上链到外部数字货币交易所钱包，可转赠给平台用户好友；奖励余额可用于平台消费或提现至支付宝，微信钱包。\n4、邀请奖励在每笔订单完成后返还给邀请人。\n5、邀请人享受的好友奖励永久有效。\n6、本计划自2018年11月正式开始，具体开始及结束时间以官方公告为准。",
     "dtos": ""
     }
     */

    private String explain;
    private String dtos;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getDtos() {
        return dtos;
    }

    public void setDtos(String dtos) {
        this.dtos = dtos;
    }
}
