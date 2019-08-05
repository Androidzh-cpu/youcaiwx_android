package com.ucfo.youcai.entity.answer;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-3.  下午 3:17
 * Package: com.ucfo.youcai.entity.answer
 * FileName: AnswerKnowListBean
 * Description:TODO 获取知识点列表
 */
public class AnswerKnowListBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : ["总论","成本度量和计量基本概念","案例-税收成本和会计成本的区别","案例-成本和意识","案例-复杂性（1）","风险管理流程-风险监控","风险管理流程-风险评估-分析方法"]
     */

    private int code;
    private String msg;
    private List<String> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
